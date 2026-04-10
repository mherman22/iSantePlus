/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.openmrs.module.isanteplusreports.api.dao.IsantePlusReportsDao;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class ArvByPeriodPatientList {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Log log = LogFactory.getLog(getClass());
	
	//private IsantePlusReportsDAO dao;
	private IsantePlusReportsDao dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IsantePlusReportsDao dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the dao
	 */
	public IsantePlusReportsDao getDao() {
		return dao;
	}
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	@Autowired
	private EmrApiProperties emrApiProperties;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	public DbSessionFactory getHibernateSessionFactory() {
		return sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return (SessionFactory) sessionFactory;
	}
	
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/*public DbSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public DbSession getCurrentSession() {
		return new DbSession(sessionFactory);
	}
	
	public SessionFactory getHibernateSessionFactory() {
		return sessionFactory;
	}
	*/
	/*
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	private org.hibernate.Session getCurrentSession() {
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		try {
			return sessionFactory.getCurrentSession();
		}
		catch (NoSuchMethodError ex) {
			try {
				Method method = sessionFactory.getClass().getMethod("getCurrentSession", null);
				return (org.hibernate.Session) method.invoke(sessionFactory, null);
			}
			catch (Exception e) {
				throw new RuntimeException("Failed to get the current hibernate session", e);
			}
		}
	}*/
	
	public DataSet patientListArvByPeriod(Integer id, Date startDate, Date endDate) {
		/*ArvByPeriodDataSetDefinition dsd = (ArvByPeriodDataSetDefinition) dataSetDefinition;
			Date startDate = ObjectUtil.nvl(dsd.getStartDate(), startD);
			Date endDate = ObjectUtil.nvl(dsd.getEndDate(), endD);
			startDate = DateUtil.getStartOfDay(startDate);
			endDate = DateUtil.getEndOfDay(endDate);*/
		EvaluationContext context = new EvaluationContext();
		SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
		String result = null;
		if (id == 30) {
			result = " between 0 AND 30";
		}
		if (id == 60) {
			result = " between 31 AND 60";
		}
		if (id == 90) {
			result = " between 61 AND 90";
		}
		if (id == 120) {
			result = " between 91 AND 120";
		}
		if (id == 121) {
			result = " > 120";
		}
		
		StringBuilder sqlQuery = new StringBuilder("select "
		        + "p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom");
		sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv");
		sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
		sqlQuery.append(" AND pdis.visit_id=parv.visit_id");
		sqlQuery.append(" DATEDIFF(pdis.next_dispensation_date,pdis.visit_date)" + result);
		if (startDate != null) {
			sqlQuery.append(" AND pdis.visit_date >= " + startDate);
		}
		if (endDate != null) {
			sqlQuery.append(" AND pdis.visit_date <= " + endDate);
		}
		
		//SQLQuery query = sessionFactory.getHibernateSessionFactory().getCurrentSession().createSQLQuery(sqlQuery.toString());
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		
		//SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("ID National", "ID National", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[2]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
}
