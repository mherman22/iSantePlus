package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.isanteplusreports.dataset.definitions.DdpReportByPeriodDataSetDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
@Handler(supports = DdpReportByPeriodDataSetDefinition.class)
public class DdpReportByPeriodDataSetEvaluator implements DataSetEvaluator {
	
private final Log log = LogFactory.getLog(getClass());
	
	//private IsantePlusReportsDAO dao;
	private IsantePlusReportsDAO dao;
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(IsantePlusReportsDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @return the dao
	 */
	public IsantePlusReportsDAO getDao() {
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
	
	/*
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	
	/**
	 * @return the sessionFactory
	 */
	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}*/
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {

		DdpReportByPeriodDataSetDefinition dsd = (DdpReportByPeriodDataSetDefinition) dataSetDefinition;
		Date startDate = ObjectUtil.nvl(dsd.getStartDate(), DateUtils.addDays(new Date(), -7));
		Date endDate = ObjectUtil.nvl(dsd.getEndDate(), new Date());
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		//PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder(
				"select ddp,Institution_communautaire,CONCAT(ROUND(((ddp /(Institution_communautaire+ddp)) * 100),2), '%') as pourcentage, (Institution_communautaire+ddp) as 'Patient unique' from("
		       + "select "
		                + "COUNT(DISTINCT CASE WHEN (pdisp.ddp = 1065) THEN pdisp.patient_id else null END) AS ddp,"
		                + "COUNT(DISTINCT CASE WHEN (pdisp.ddp = 0) THEN pdisp.patient_id else null END) AS Institution_communautaire"
		                + " FROM isanteplus.patient_dispensing pdisp,"
		                + " (select pdi.patient_id,max(ifnull(DATE(pdi.dispensation_date),DATE(pdi.visit_date))) as visit_date from isanteplus.patient_dispensing pdi where pdi.arv_drug = 1065 AND pdi.voided <> 1 AND ifnull(DATE(pdi.dispensation_date),DATE(pdi.visit_date)) BETWEEN :startDate AND :endDate group by 1)B"
		                + " WHERE pdisp.ddp IN(1065,0)"
		                + " AND pdisp.patient_id = B.patient_id"
		                + " AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date)) = B.visit_date"
		                + " AND pdisp.arv_drug = 1065"
		                + " AND pdisp.voided <> 1"
		                + " AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date)) BETWEEN :startDate AND :endDate) A");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		
		if (startDate != null) {
			query.setTimestamp("startDate", startDate);
		}
		if (endDate != null) {
			query.setTimestamp("endDate", endDate);
		}
		
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("ddp", "ddp", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("institution_com", "institution_com", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("pourcentage", "pourcentage", String.class), o[2]);
			row.addColumnValue(new DataSetColumn("pat_unique", "pat_unique", String.class), o[3]);
			dataSet.addRow(row);
		}
		return dataSet;
	}	
	
}
