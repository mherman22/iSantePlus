package org.openmrs.module.isanteplusreports.library.indicator;

import org.openmrs.module.isanteplusreports.library.cohort.ObGynIndicatorReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class ObGynReportIndicatorLibrary {
	
	private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
    public static CohortIndicator numberOfPregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriodCohort() {
        return EmrReportingUtils.cohortIndicator(
            "isanteplusreports.number_of_pregnant_women_who_had_a_first_consultation_form_for_the_selected_period",
            ReportUtils.map(ObGynIndicatorReportCohortLibrary.pregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriodCohort(), IND_PARAMS));
    }
 
    public static CohortIndicator numberOfVisitsOfPregnantWomenForTheSelectedPeriodCohort() {
        return EmrReportingUtils.cohortIndicator(
            "isanteplusreports.number_of_visits_of_pregnant_women_for_the_selected_period",
            ReportUtils.map(ObGynIndicatorReportCohortLibrary.visitsOfPregnantWomenForTheSelectedPeriodCohort(), IND_PARAMS));
    }
 
    public static CohortIndicator numberOfPregnantWomenWhoHadAtLeastOneVisitForSelectedPeriodCohort() {
        return EmrReportingUtils.cohortIndicator(
            "isanteplusreports.number_of_pregnant_women_who_had_at_least_one_visit_for_selected_period",
            ReportUtils.map(ObGynIndicatorReportCohortLibrary.pregnantWomenWhoHadAtLeastOneVisitForSelectedPeriodCohort(), IND_PARAMS));
    }
 	
}
