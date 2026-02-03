package org.openmrs.module.isanteplusreports.library.cohort;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;

public class PmtctReportsCohortLibrary {
    
    private static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(IsantePlusReportsConstants.FULL_DATA_EXPORTS_RESOURCE_PATH + sqlResourceName);
        
        SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
            MessageUtil.translate(cohortDefinitionName));
        
        return definition;
    }
    
    public static CohortDefinition pregnantWomenTestedForHiv() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_pregnancy_women_hiv_tested.sql",
            "isanteplusreports.number_pregnancy_women_hiv_tested");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition pregnantWomenTestedHivPositive() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_prenancy_women_vih_positive.sql",
            "isanteplusreports.number_prenancy_women_vih_positive");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition hivPositivePregnantWomenWhoGaveBirthAtHospital() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(
            "number_hiv_women_gave_birth_at_hospital.sql",
            "isanteplusreports.number_hiv_pregnancy_women_gave_birth_at_hospital");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition exposedInfantsConfirmedHivPositive() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_exposed_infants_confirmed_hiv.sql",
            "isanteplusreports.number_exposed_infants_confirmed_hiv");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition hivPositiveWomenEnrolledInCareWhoBecamePregnant() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("womenEnrolledBecamePregnant.sql",
            "isanteplusreports.womenEnrolledBecamePregnant");
        
        return cohortDefinition;
    }
    
    public static CohortDefinition infantsBornToMothersOnProphylaxis() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("number_infants_from_mother_on_prophylaxis.sql",
            "isanteplusreports.number_infants_from_mother_on_prophylaxis");
        
        return cohortDefinition;
    }
}
