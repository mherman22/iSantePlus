package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SQLQuery;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.isanteplusreports.dataset.definitions.WeeklyMonitoringReportDataSetDefinition;
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

@Handler(supports = WeeklyMonitoringReportDataSetDefinition.class)
public class WeeklyMonitoringReportDataSetEvaluator  implements DataSetEvaluator {

	
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
	
	
	/**
	 * @return the sessionFactory
	 */
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {
		
		WeeklyMonitoringReportDataSetDefinition dsd = (WeeklyMonitoringReportDataSetDefinition) dataSetDefinition;
		Date startDate = ObjectUtil.nvl(dsd.getStartDate(), DateUtils.addDays(new Date(), -7));
		Date endDate = ObjectUtil.nvl(dsd.getEndDate(), new Date());
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		//PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder(
				"SELECT itype.indicator_type_id AS ID,itype.indicator_name_fr AS Nom,"
				+" CASE WHEN (B.Total) IS NULL THEN '0' ELSE B.Total END AS Total"
				+" FROM isanteplus.indicator_type itype"
		        +" LEFT OUTER JOIN"
				+" (select ind.indicator_type_id, count(ind.indicator_type_id) AS Total FROM isanteplus.indicators ind"
				+" WHERE DATE(ind.indicator_date) between :startDate AND :endDate"
				+" GROUP BY 1) B");			
		sqlQuery.append(" ON itype.indicator_type_id = B.indicator_type_id ");
		sqlQuery.append(" WHERE itype.report_type_id = 1");
		sqlQuery.append(" GROUP BY itype.indicator_type_id");
		
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		if (startDate != null) {
			query.setTimestamp("startDate", startDate);
		}
		if (endDate != null) {
			query.setTimestamp("endDate", endDate);
		}
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("id", "id", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("Indicateur", "Indicateur", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("Total", "Total", String.class), o[2]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
}
