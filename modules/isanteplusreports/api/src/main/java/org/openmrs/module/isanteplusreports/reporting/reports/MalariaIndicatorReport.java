package org.openmrs.module.isanteplusreports.reporting.reports;

import java.util.Arrays;
import java.util.List;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.library.indicator.MalariaReportIndicatorLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ColumnParameters;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;

public class MalariaIndicatorReport {
	
	private static final String MALARIA_INDICATOR_REPORT_UUID = "8c5fb6a1-758d-4b95-9cdb-a25b57276ec5";
	
	private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static void registerReport() {
		
		CohortIndicatorDataSetDefinition dsd = reportDataSetDefinition();
		
		IsantePlusReportsUtil.registerIndicatorReportsWithStartAndEndDateParams(
		    "isanteplusreports.malaria_indicator_report",
		    MessageUtil.translate("isanteplusreports.malaria_indicator_report"), MALARIA_INDICATOR_REPORT_UUID, dsd);

	}
	
	private static CohortIndicatorDataSetDefinition reportDataSetDefinition() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		
		dsd.setName("isanteplusreports.malaria_indicator_report");
		dsd.setDescription(MessageUtil.translate("isanteplusreports.malaria_indicator_report"));
		
		ColumnParameters col = new ColumnParameters("1", "", "");
		
		List<ColumnParameters> allColumns = Arrays.asList(col);
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.percentage_of_all_patients_with_a_fever_of_less_than_2_weeks",
		    MessageUtil.translate("isanteplusreports.percentage_of_all_patients_with_a_fever_of_less_than_2_weeks"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.percentageOfAllPatientsWithAFeverOfLessThan2WeeksCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("01"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.percentage_of_patients_with_fever_suspected_case_receiving_an_antimalarial_drug_who",
		    MessageUtil.translate(
		        "isanteplusreports.percentage_of_patients_with_fever_suspected_case_receiving_an_antimalarial_drug_who"),
		    ReportUtils.map(MalariaReportIndicatorLibrary
		            .percentageOfPatientsWithFeverSuspectedCaseReceivingAnAntimalarialDrugWhoCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("02"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.percentage_of_patients_with_confirmed_malaria_who_have_been_treated_according_to_the_national_standards_of_the_mspp_who",
		    MessageUtil.translate(
		        "isanteplusreports.percentage_of_patients_with_confirmed_malaria_who_have_been_treated_according_to_the_national_standards_of_the_mspp_who"),
		    ReportUtils.map(MalariaReportIndicatorLibrary
		            .percentageOfPatientsWithConfirmedMalariaWhoHaveBeenTreatedAccordingToTheNationalStandardsOfTheMsppWhoCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("03"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.percentage_of_patients_with_fever_who_have_been_tested_for_malaria_who",
		    MessageUtil
		            .translate("isanteplusreports.percentage_of_patients_with_fever_who_have_been_tested_for_malaria_who"),
		    ReportUtils.map(
		        MalariaReportIndicatorLibrary.percentageOfPatientsWithFeverWhoHaveBeenTestedForMalariaWhoCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("04"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_cases_of_confirmed_malaria",
		    MessageUtil.translate("isanteplusreports.number_of_cases_of_confirmed_malaria"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfCasesOfConfirmedMalariaCohort(), IND_PARAMS), allColumns,
		    Arrays.asList("05"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_patients_undergoing_microscopic_malaria_test",
		    MessageUtil.translate("isanteplusreports.number_of_patients_undergoing_microscopic_malaria_test"), ReportUtils
		            .map(MalariaReportIndicatorLibrary.numberOfPatientsUndergoingMicroscopicMalariaTestCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("06"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_patients_with_a_positive_microscopic_test_for_malaria",
		    MessageUtil.translate("isanteplusreports.number_of_patients_with_a_positive_microscopic_test_for_malaria"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfPatientsWithAPositiveMicroscopicTestForMalariaCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("07"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.number_of_patients_with_a_positive_microscopy_test_with_plasmodium_falciparum",
		    MessageUtil.translate(
		        "isanteplusreports.number_of_patients_with_a_positive_microscopy_test_with_plasmodium_falciparum"),
		    ReportUtils.map(
		        MalariaReportIndicatorLibrary.numberOfPatientsWithAPositiveMicroscopyTestWithPlasmodiumFalciparumCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("08"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.number_of_patients_with_mixed_positive_plasmodium_microscopy_test_who",
		    MessageUtil.translate("isanteplusreports.number_of_patients_with_mixed_positive_plasmodium_microscopy_test_who"),
		    ReportUtils.map(
		        MalariaReportIndicatorLibrary.numberOfPatientsWithMixedPositivePlasmodiumMicroscopyTestWhoCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("09"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.number_of_plasmodium_positive_microscopic_test_patients_other_than_falciparum",
		    MessageUtil.translate(
		        "isanteplusreports.number_of_plasmodium_positive_microscopic_test_patients_other_than_falciparum"),
		    ReportUtils.map(
		        MalariaReportIndicatorLibrary.numberOfPlasmodiumPositiveMicroscopicTestPatientsOtherThanFalciparumCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("10"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_patients_who_have_had_a_rdt_for_malaria",
		    MessageUtil.translate("isanteplusreports.number_of_patients_who_have_had_a_rdt_for_malaria"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfPatientsWhoHaveHadARdtForMalariaCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("11"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_patients_with_positive_rdt_for_malaria",
		    MessageUtil.translate("isanteplusreports.number_of_patients_with_positive_rdt_for_malaria"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfPatientsWithPositiveRdtForMalariaCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("12"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_suspected_cases_of_malaria_tested",
		    MessageUtil.translate("isanteplusreports.number_of_suspected_cases_of_malaria_tested"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfSuspectedCasesOfMalariaTestedCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("13"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_suspected_cases_of_confirmed_malaria",
		    MessageUtil.translate("isanteplusreports.number_of_suspected_cases_of_confirmed_malaria"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfSuspectedCasesOfConfirmedMalariaCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("14"));
		
		EmrReportingUtils.addRow(dsd,
		    "isanteplusreports.number_of_confirmed_cases_for_severe_malaria_and_put_on_treatment_according_to_the_national_standards_of_the_mspp",
		    MessageUtil.translate(
		        "isanteplusreports.number_of_confirmed_cases_for_severe_malaria_and_put_on_treatment_according_to_the_national_standards_of_the_mspp"),
		    ReportUtils.map(MalariaReportIndicatorLibrary
		            .numberOfConfirmedCasesForSevereMalariaAndPutOnTreatmentAccordingToTheNationalStandardsOfTheMsppCohort(),
		        IND_PARAMS),
		    allColumns, Arrays.asList("15"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_patients_with_malaria_hospitalized",
		    MessageUtil.translate("isanteplusreports.number_of_patients_with_malaria_hospitalized"),
		    ReportUtils.map(MalariaReportIndicatorLibrary.numberOfPatientsWithMalariaHospitalizedCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("16"));
		
		EmrReportingUtils.addRow(dsd, "isanteplusreports.number_of_cases_of_confirmed_malaria_for_pregnant_women",
		    MessageUtil.translate("isanteplusreports.number_of_cases_of_confirmed_malaria_for_pregnant_women"), ReportUtils
		            .map(MalariaReportIndicatorLibrary.numberOfCasesOfConfirmedMalariaForPregnantWomenCohort(), IND_PARAMS),
		    allColumns, Arrays.asList("17"));
		
		return dsd;
	}
}
