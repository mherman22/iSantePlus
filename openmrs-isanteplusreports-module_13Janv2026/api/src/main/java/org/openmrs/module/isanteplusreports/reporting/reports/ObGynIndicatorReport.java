package org.openmrs.module.isanteplusreports.reporting.reports;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.library.indicator.ObGynReportIndicatorLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;

public class ObGynIndicatorReport {
	
	private static final String OBGYN_INDICATOR_REPORT_UUID = "0616e88a-011f-4433-9d98-1743448977af";
	
	private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static void registerReport() {
		
		CohortIndicatorDataSetDefinition dsd = reportDataSetDefinition();
		
		IsantePlusReportsUtil.registerIndicatorReportsWithStartAndEndDateParams(
		    "isanteplusreports.obgyn_indicator_report",
		    MessageUtil.translate("isanteplusreports.obgyn_indicator_report"), OBGYN_INDICATOR_REPORT_UUID, dsd);

	}
	
	private static CohortIndicatorDataSetDefinition reportDataSetDefinition() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		
		dsd.setName("isanteplusreports.obgyn_indicator_report");
		dsd.setDescription(MessageUtil.translate("isanteplusreports.obgyn_indicator_report"));
		
		
		dsd.addColumn(
			 "isanteplusreports.number_of_pregnant_women_who_had_a_first_consultation_form_for_the_selected_period", 
			 MessageUtil.translate("isanteplusreports.number_of_pregnant_women_who_had_a_first_consultation_form_for_the_selected_period"),
	         ReportUtils.map(ObGynReportIndicatorLibrary.numberOfPregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriodCohort(), IND_PARAMS),
	         "" );
		
		dsd.addColumn(
            "isanteplusreports.number_of_pregnant_women_who_had_a_first_consultation_form_for_the_selected_period", 
            MessageUtil.translate("isanteplusreports.number_of_pregnant_women_who_had_a_first_consultation_form_for_the_selected_period"),
            ReportUtils.map(ObGynReportIndicatorLibrary.numberOfPregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriodCohort(), IND_PARAMS),
            "");

		dsd.addColumn(
            "isanteplusreports.number_of_visits_of_pregnant_women_for_the_selected_period", 
            MessageUtil.translate("isanteplusreports.number_of_visits_of_pregnant_women_for_the_selected_period"),
            ReportUtils.map(ObGynReportIndicatorLibrary.numberOfVisitsOfPregnantWomenForTheSelectedPeriodCohort(), IND_PARAMS),
            "");

		dsd.addColumn(
            "isanteplusreports.number_of_pregnant_women_who_had_at_least_one_visit_for_selected_period", 
            MessageUtil.translate("isanteplusreports.number_of_pregnant_women_who_had_at_least_one_visit_for_selected_period"),
            ReportUtils.map(ObGynReportIndicatorLibrary.numberOfPregnantWomenWhoHadAtLeastOneVisitForSelectedPeriodCohort(), IND_PARAMS),
            "");
        
		return dsd;
	}
}
