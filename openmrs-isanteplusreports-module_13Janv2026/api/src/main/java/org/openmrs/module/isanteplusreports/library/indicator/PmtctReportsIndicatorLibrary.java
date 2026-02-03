package org.openmrs.module.isanteplusreports.library.indicator;

import org.openmrs.module.isanteplusreports.library.cohort.PmtctReportsCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class PmtctReportsIndicatorLibrary {
    
    private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
    
    public static CohortIndicator pregnantWomenTestedForHiv() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_pregnancy_women_hiv_tested",
            ReportUtils.map(PmtctReportsCohortLibrary.pregnantWomenTestedForHiv(), IND_PARAMS));
    }
    
    public static CohortIndicator pregnantWomenTestedHivPositive() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_prenancy_women_vih_positive",
            ReportUtils.map(PmtctReportsCohortLibrary.pregnantWomenTestedHivPositive(), IND_PARAMS));
    }
    
    public static CohortIndicator hivPositivePregnantWomenWhoGaveBirthAtHospital() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_hiv_pregnancy_women_gave_birth_at_hospital",
            ReportUtils.map(PmtctReportsCohortLibrary.hivPositivePregnantWomenWhoGaveBirthAtHospital(), IND_PARAMS));
    }
    
    public static CohortIndicator exposedInfantsConfirmedHivPositive() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_exposed_infants_confirmed_hiv",
            ReportUtils.map(PmtctReportsCohortLibrary.exposedInfantsConfirmedHivPositive(), IND_PARAMS));
    }
    
    public static CohortIndicator hivPositiveWomenEnrolledInCareWhoBecamePregnant() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.womenEnrolledBecamePregnant",
            ReportUtils.map(PmtctReportsCohortLibrary.hivPositiveWomenEnrolledInCareWhoBecamePregnant(), IND_PARAMS));
    }
    
    public static CohortIndicator infantsBornToMothersOnProphylaxis() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_infants_from_mother_on_prophylaxis",
            ReportUtils.map(PmtctReportsCohortLibrary.infantsBornToMothersOnProphylaxis(), IND_PARAMS));
    }
    
}
