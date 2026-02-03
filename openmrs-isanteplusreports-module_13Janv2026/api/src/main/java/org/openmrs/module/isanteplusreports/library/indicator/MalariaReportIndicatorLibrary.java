package org.openmrs.module.isanteplusreports.library.indicator;

import org.openmrs.module.isanteplusreports.library.cohort.MalariaIndicatorReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class MalariaReportIndicatorLibrary {
	
	private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static CohortIndicator percentageOfAllPatientsWithAFeverOfLessThan2WeeksCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.percentage_of_all_patients_with_a_fever_of_less_than_2_weeks",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientsWithAFeverOfLessThan2WeeksCohort(), IND_PARAMS),
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientClinicVisitsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator percentageOfPatientsWithFeverSuspectedCaseReceivingAnAntimalarialDrugWhoCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.percentage_of_patients_with_fever_suspected_case_receiving_an_antimalarial_drug_who",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.casesWithNonConfirmedMalariaCasesCohort(), IND_PARAMS),
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.casesWithSuspectedMalariaCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator percentageOfPatientsWithConfirmedMalariaWhoHaveBeenTreatedAccordingToTheNationalStandardsOfTheMsppWhoCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.percentage_of_patients_with_confirmed_malaria_who_have_been_treated_according_to_the_national_standards_of_the_mspp_who",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary
		            .confirmedMalariaCasesTreatedWithChloroquineAndPrimaquineOrQuinineAndChloroquineAndPrimaquineCohort(),
		        IND_PARAMS),
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary
		            .confirmedMalariaCasesTreatedWithEitherQuinineAloneOrChloroquineAloneOrPrimaquineAloneOrChloroquineAndPrimaquineOrQuinineAndChloroquinAndPrimaquinCohort(),
		        IND_PARAMS));
	}
	
	public static CohortIndicator percentageOfPatientsWithFeverWhoHaveBeenTestedForMalariaWhoCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.percentage_of_patients_with_fever_who_have_been_tested_for_malaria_who",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientsTestedForMalariaCohort(), IND_PARAMS),
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientsWithAFeverOfLessThan2WeeksCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfCasesOfConfirmedMalariaCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_cases_of_confirmed_malaria",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.casesWithConfirmedMalariaCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsUndergoingMicroscopicMalariaTestCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_patients_undergoing_microscopic_malaria_test",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.microscopicMalariaTestsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWithAPositiveMicroscopicTestForMalariaCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.number_of_patients_with_a_positive_microscopic_test_for_malaria",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.positiveMicroscopicMalariaTestResultsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWithAPositiveMicroscopyTestWithPlasmodiumFalciparumCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.number_of_patients_with_a_positive_microscopy_test_with_plasmodium_falciparum.",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.positiveFalciparumMicroscopicMalariaTestResultsCohort(),
		        IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWithMixedPositivePlasmodiumMicroscopyTestWhoCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.number_of_patients_with_mixed_positive_plasmodium_microscopy_test_who", ReportUtils.map(
		        MalariaIndicatorReportCohortLibrary.positiveMixedMicroscopicMalariaTestResultsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPlasmodiumPositiveMicroscopicTestPatientsOtherThanFalciparumCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.number_of_plasmodium_positive_microscopic_test_patients_other_than_falciparum",
		    ReportUtils.map(
		        MalariaIndicatorReportCohortLibrary.plasmodiumPositiveMicroscopicTestPatientsOtherThanFalciparumCohort(),
		        IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWhoHaveHadARdtForMalariaCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_patients_who_have_had_a_rdt_for_malaria",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.rapidMalariaTestsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWithPositiveRdtForMalariaCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_patients_with_positive_rdt_for_malaria",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.positiveRapidMalariaTestResultsCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfSuspectedCasesOfMalariaTestedCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_suspected_cases_of_malaria_tested",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientsTestedForMalariaCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfSuspectedCasesOfConfirmedMalariaCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_suspected_cases_of_confirmed_malaria",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.suspectedCasesOfConfirmedMalariaCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfConfirmedCasesForSevereMalariaAndPutOnTreatmentAccordingToTheNationalStandardsOfTheMsppCohort() {
		return EmrReportingUtils.cohortIndicator(
		    "isanteplusreports.number_of_confirmed_cases_for_severe_malaria_and_put_on_treatment_according_to_the_national_standards_of_the_mspp",
		    ReportUtils.map(
		        MalariaIndicatorReportCohortLibrary
		                .casesForSevereMalariaAndPutOnTreatmentAccordingToTheNationalStandardsOfTheMsppCohort(),
		        IND_PARAMS));
	}
	
	public static CohortIndicator numberOfPatientsWithMalariaHospitalizedCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_patients_with_malaria_hospitalized",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.patientsWithMalariaHospitalizedCohort(), IND_PARAMS));
	}
	
	public static CohortIndicator numberOfCasesOfConfirmedMalariaForPregnantWomenCohort() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.number_of_cases_of_confirmed_malaria_for_pregnant_women",
		    ReportUtils.map(MalariaIndicatorReportCohortLibrary.casesWithConfirmedMalariaDuringPregnancyCohort(),
		        IND_PARAMS));
	}
	
}
