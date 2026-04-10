package org.openmrs.module.isanteplusreports.library.cohort;

import java.util.List;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class HealthQualReportCohortLibrary {
    
    private static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(IsantePlusReportsConstants.HEALTHQUAL_REPORT_RESOURCE_PATH + sqlResourceName);
        
        SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
            MessageUtil.translate(cohortDefinitionName));
        
        return definition;
    }
    
    public static CohortDefinition retentionOfPatientsOnArtDen() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("retention_of_patients_on_art_den.sql",
            "isanteplusreports.retention_of_patients_on_art_den");
        
        return cohortDefinition;
    }

    public static CohortDefinition retentionOfPatientsOnArtNum() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("retention_of_patients_on_art_num.sql",
            "isanteplusreports.retention_of_patients_on_art_num");
        
        return cohortDefinition;
    }

	public static CohortDefinition cohortFromSqlResource(String sqlResourceName, String name, List<Parameter> parameters) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(IsantePlusReportsConstants.HEALTHQUAL_REPORT_RESOURCE_PATH + sqlResourceName);

	   	SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName(name);
        cd.setDescription(MessageUtil.translate(name));
        for (Parameter parameter : parameters) {
			cd.addParameter(parameter);
		}

        cd.setQuery(sql);
 
        return cd;
	}

}
