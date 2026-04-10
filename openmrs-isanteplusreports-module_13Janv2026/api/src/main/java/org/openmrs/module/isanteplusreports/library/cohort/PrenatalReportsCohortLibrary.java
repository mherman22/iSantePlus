package org.openmrs.module.isanteplusreports.library.cohort;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;

public class PrenatalReportsCohortLibrary {
    
    private static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(IsantePlusReportsConstants.FULL_DATA_EXPORTS_RESOURCE_PATH + sqlResourceName);
        
        SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
            MessageUtil.translate(cohortDefinitionName));
        
        return definition;
    }
    
    public static CohortDefinition pregnantWomenTestedForSyphilis() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("pregnancy_women_tested_for_syphilis.sql",
            "isanteplusreports.pregnancy_women_tested_for_syphilis");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition pregnantWomenDiagnosedWithSyphilis() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_pregnant_women_diagnosed_syphilis.sql",
            "isanteplusreports.pregnancy_women_diagnosed_with_syphilis");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition pregnantWomenFirstVisitAfterFirstTrimester() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(
            "number_women_seen_first_prenatal_visit_first_trimester.sql",
            "isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition visitsByPregnantWomentInClinic() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_visits_by_pregnant_women_to_the_clinic.sql",
            "isanteplusreports.number_visits_by_pregnant_women_to_the_clinic");
        
        return cohortDefinition;
    }
}
