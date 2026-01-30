package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.dataset.definitions.FormRecentlyFilledDataSetDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = FormRecentlyFilledDataSetDefinition.class)
public class FormRecentlyFilledDataSetEvaluator implements DataSetEvaluator {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	@Autowired
	private EmrApiProperties emrApiProperties;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	@Override
	public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {
		// TODO Auto-generated method stub
		FormRecentlyFilledDataSetDefinition dsd = (FormRecentlyFilledDataSetDefinition) dataSetDefinition;
		Integer total = dsd.getTotal();
		//PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
		StringBuilder sqlQuery = new StringBuilder(
		        "select"
		                + " p.st_id as 'No. de patient attribué par le site', usr.username as utilisateur, entype.name as Fiche,"
		                + " DATE(enc.date_created) as 'Date de création',"
		                + " CASE WHEN enc.date_changed is null then enc.date_created ELSE enc.date_changed  END as 'Dernière modification',"
		                + " f.name as Fiches, p.patient_id,f.uuid as formUuid, enc.uuid as encounterUuid");
		sqlQuery.append(" FROM isanteplus.patient p, openmrs.encounter enc, openmrs.encounter_type entype, openmrs.form f, openmrs.users usr");
		sqlQuery.append(" WHERE p.patient_id=enc.patient_id");
		sqlQuery.append(" AND enc.encounter_type=entype.encounter_type_id");
		sqlQuery.append(" AND enc.form_id=f.form_id");
		sqlQuery.append(" AND enc.creator=usr.user_id");
		sqlQuery.append(" GROUP BY DATE(enc.date_created) DESC");
		if (total > 0) {
			sqlQuery.append(" LIMIT :total");
		}
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
		//query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
		if (total != null) {
			query.setInteger("total", total);
		}
		/*
		if (startDate != null) {
			query.setTimestamp("endDate", endDate);
		}*/
		
		List<Object[]> list = query.list();
		SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
		for (Object[] o : list) {
			DataSetRow row = new DataSetRow();
			row.addColumnValue(new DataSetColumn("numero", "numero", String.class), o[0]);
			row.addColumnValue(new DataSetColumn("utilisateur", "utilisateur", String.class), o[1]);
			row.addColumnValue(new DataSetColumn("fiche", "fiche", String.class), o[2]);
			row.addColumnValue(new DataSetColumn("creation", "creation", String.class), o[3]);
			row.addColumnValue(new DataSetColumn("modification", "modification", String.class), o[4]);
			row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[5]);
			row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[6]);
			row.addColumnValue(new DataSetColumn("form_uuid", "form_uuid", String.class), o[7]);
			row.addColumnValue(new DataSetColumn("encounter_uuid", "encounter_uuid", String.class), o[8]);
			dataSet.addRow(row);
		}
		return dataSet;
	}
	
}
