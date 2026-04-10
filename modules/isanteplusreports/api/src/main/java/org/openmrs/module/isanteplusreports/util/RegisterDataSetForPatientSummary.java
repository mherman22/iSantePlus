package org.openmrs.module.isanteplusreports.util;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.PATIENT_SUMMARY_RESOURCE_PATH;

import java.io.Console;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.hibernate.SQLQuery;
import org.openmrs.Patient;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.dataset.definitions.FormRecentlyFilledDataSetDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.util.OpenmrsClassLoader;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterDataSetForPatientSummary {

    EvaluationContext context = new EvaluationContext();
    SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
    static IsantePlusReportsProperties props = new IsantePlusReportsProperties();
    @Autowired
    private static DbSessionFactory sessionFactory;

    static Parameter id = new Parameter("id", "id", Integer.class);


    /**
     * Given a location on the classpath, return the contents of this resource as a String
     */
    public static String getStringFromResource(String resourceName) {
        InputStream is = null;
        try {
            is = OpenmrsClassLoader.getInstance().getResourceAsStream(resourceName);
            return IOUtils.toString(is, "UTF-8");
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to load resource: " + resourceName, e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public SqlDataSetDefinition sqlDataSetDefinition(String resourceName, String name, String description) {
        return sqlDataSetDefinitionWithResourcePath(resourceName, name, description, PATIENT_SUMMARY_RESOURCE_PATH);
    }

    public SqlDataSetDefinition sqlDataSetDefinitionWithResourcePath(String resourceName, String name, String description, String resourcePath) {
        String sql = IsantePlusReportsUtil.getStringFromResource(resourcePath + resourceName);
        SqlDataSetDefinition definition = new SqlDataSetDefinition();
        definition.setSqlQuery(sql);
        definition.setName(name);
        definition.setDescription(description);

        return definition;
    }

    public ReportDefinition reportDefinition(String name, String description) {
        ReportDefinition rDefinition = new ReportDefinition();
        rDefinition.setName(name);
        rDefinition.setDescription(description);
        return rDefinition;
    }


    // has been moved to ReportUtil in reporting module, use the one there
/*	@Deprecated
	public static List<Map<String, Object>> simplify(DataSet dataSet) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr, messagePropertiesFr,props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
        sqlData.addParameter(startDate);
        EvaluationContext context = new EvaluationContext();
		SimpleDataSet data = new SimpleDataSet(sqlData, context);
		List<Map<String, Object>> simplified = new ArrayList<Map<String, Object>>();
		for (data row : dataSet) {
			simplified.add(row.getColumnValuesByKey());
		}
		return simplified;
	}
	*/
    public DataSet patientDemographic(Patient p) {
        return findDataSet(p, "patient_demographic.sql", "Patient démographique", "Patient démographique");
    }

    // has been moved to ReportUtil in reporting module, use the one there
    public DataSet findDataSet(Patient p, String sql, String messageProperties, String messagePropertiesFr) {
        SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr, messagePropertiesFr, props.ISANTEPLUS_SUMMARY_RESOURCE_PATH);
        id.setDefaultValue(p.getPatientId());
        sqlData.addParameter(id);
        // EvaluationContext context = new EvaluationContext();
        ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties);
        repDefinition.addParameter(id);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("id", "${id}");
        repDefinition.addDataSetDefinition(sqlData, mappings);

        ReportData reportData = new ReportData(repDefinition, null);
        //return reportData.getDataSets();
        DataSet dataset = null;
        //SimpleDataSet dataset = null;
        for (String key : reportData.getDataSets().keySet()) {

            dataset = reportData.getDataSets().get(key);
        }
        return dataset;
	      
			/*SimpleDataSet dataSet = new SimpleDataSet(sqlData, context);
			  SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlData.getSqlQuery().toString());
			  List<Object[]> list = query.list();
			  int i = 0;
			  for (Object[] o : list) {
		          DataSetRow row = new DataSetRow();
		          row.addColumnValue(new DataSetColumn(, "identifier", String.class), o[i]);
		          dataSet.addRow(row);
		          i = i + 1;
		      }*/
        //return dataSet;
			  
			  
			/*List<Map<String, Object>> simplified = new ArrayList<Map<String, Object>>();
			for (data row : dataSet) {
				dataSet.add(row.getColumnValuesByKey());
			}
			return dataSet;*/
    }

    @SuppressWarnings("unchecked")
    public DataSet lastSixForms(Patient p) {
        id.setDefaultValue(p.getPatientId());
        StringBuilder sqlQuery = new StringBuilder(
                "select"
                        + " DATE_FORMAT(enc.date_created, '%d-%m-%Y') as 'Date de création',"
                        + " CASE WHEN enc.date_changed is null then DATE_FORMAT(enc.date_created, '%d-%m-%Y') ELSE DATE_FORMAT(enc.date_changed, '%d-%m-%Y')  END as 'Dernière modification',"
                        + " f.name as Fiches, usr.username as utilisateur");
        sqlQuery.append(" FROM openmrs.encounter enc, openmrs.form f, openmrs.users usr");
        sqlQuery.append(" WHERE p.patient_id=enc.patient_id");
        sqlQuery.append(" AND enc.encounter_type=entype.encounter_type_id");
        sqlQuery.append(" AND enc.form_id=f.form_id");
        sqlQuery.append(" AND enc.creator=usr.user_id");
        sqlQuery.append(" AND enc.patient_id = :id");
        sqlQuery.append(" GROUP BY DATE(enc.date_created) DESC");
        sqlQuery.append(" LIMIT 6");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("creation", "creation", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("modification", "modification", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("utilisateur", "utilisateur", String.class), o[2]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

}
