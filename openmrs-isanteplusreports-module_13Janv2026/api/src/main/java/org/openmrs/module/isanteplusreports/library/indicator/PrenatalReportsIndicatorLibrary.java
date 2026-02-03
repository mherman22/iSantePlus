package org.openmrs.module.isanteplusreports.library.indicator;

import org.openmrs.module.isanteplusreports.library.cohort.PrenatalReportsCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class PrenatalReportsIndicatorLibrary {
    
    private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
    
    public static CohortIndicator pregnantWomenTestedForSyphilis() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.pregnancy_women_tested_for_syphilis",
            ReportUtils.map(PrenatalReportsCohortLibrary.pregnantWomenTestedForSyphilis(), IND_PARAMS));
    }
    
    public static CohortIndicator pregnantWomenDiagnosedWithSyphilis() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.pregnancy_women_diagnosed_with_syphilis",
            ReportUtils.map(PrenatalReportsCohortLibrary.pregnantWomenDiagnosedWithSyphilis(), IND_PARAMS));
    }
    
    public static CohortIndicator pregnantWomenFirstVisitAfterFirstTrimester() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester",
            ReportUtils.map(PrenatalReportsCohortLibrary.pregnantWomenFirstVisitAfterFirstTrimester(), IND_PARAMS));
    }
    
    public static CohortIndicator visitsByPregnantWomentInClinic() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.number_visits_by_pregnant_women_to_the_clinic",
            ReportUtils.map(PrenatalReportsCohortLibrary.visitsByPregnantWomentInClinic(), IND_PARAMS));
    }
    
}
