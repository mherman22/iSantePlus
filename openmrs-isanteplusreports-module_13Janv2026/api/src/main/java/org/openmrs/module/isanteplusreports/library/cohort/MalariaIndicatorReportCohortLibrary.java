package org.openmrs.module.isanteplusreports.library.cohort;

import java.util.Date;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class MalariaIndicatorReportCohortLibrary {
	
	private static final Parameter LOCATION = new Parameter("location", "Location", Date.class);
	
	private static final Parameter END_DATE = new Parameter("endDate", "End Date", Date.class);
	
	private static final Parameter START_DATE = new Parameter("startDate", "Start Date", Date.class);
	
	private static final String COHORT_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(IsantePlusReportsConstants.MALARIA_INDICATORS_RESOURCE_PATH + sqlResourceName);
		
		SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
		    MessageUtil.translate(cohortDefinitionName));
		
		return definition;
	}
	
	public static CompositionCohortDefinition compositionCohortDefinition(String cohortDefinitionName) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName(cohortDefinitionName);
		cd.addParameter(START_DATE);
		cd.addParameter(END_DATE);
		cd.addParameter(LOCATION);
		
		return cd;
	}
	
	public static CohortDefinition patientsWithAFeverOfLessThan2WeeksCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PatientsWithAFeverOfLessThan2Weeks.sql",
		    "isanteplusreports.patients_with_a_fever_of_less_than_2_weeks");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition patientClinicVisitsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PatientClinicVisits.sql",
		    "isanteplusreports.patient_clinic_visits");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesWithSuspectedMalariaCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesWithSuspectedMalaria.sql",
		    "isanteplusreports.cases_with_suspected_malaria");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesWithConfirmedMalariaCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesWithConfirmedMalaria.sql",
		    "isanteplusreports.cases_with_confirmed_malaria");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesTreatedWithChloroquineCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesTreatedWithChloroquine.sql",
		    "isanteplusreports.cases_treated_with_chloroquine");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesTreatedWithQuinineCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesTreatedWithQuinine.sql",
		    "isanteplusreports.cases_treated_with_quinine");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesTreatedWithPrimaquineCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesTreatedWithPrimaquine.sql",
		    "isanteplusreports.cases_treated_with_primaquine");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesTreatedWithChloroquineAndPrimaquineCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesTreatedWithChloroquineAndPrimaquine.sql",
		    "isanteplusreports.cases_treated_with_chloroquine_and_primaquine");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesTreatedWithQuinineAndChloroquineAndPrimaquinCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesTreatedWithQuinineAndChloroquineAndPrimaquin.sql",
		    "isanteplusreports.cases_treated_with_quinine_and_chloroquine_and_primaquin");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition patientsTestedForMalariaCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PatientsTestedForMalaria.sql",
		    "isanteplusreports.patients_tested_for_malaria");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition microscopicMalariaTestsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("MicroscopicMalariaTests.sql",
		    "isanteplusreports.microscopic_malaria_tests");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition positiveMicroscopicMalariaTestResultsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PositiveMicroscopicMalariaTestResults.sql",
		    "isanteplusreports.positive_microscopic_malaria_test_results");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition positiveFalciparumMicroscopicMalariaTestResultsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PositiveFalciparumMicroscopicMalariaTestResults.sql",
		    "isanteplusreports.positive_falciparum_microscopic_malaria_test_results");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition positiveMixedMicroscopicMalariaTestResultsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PositiveMixedMicroscopicMalariaTestResults.sql",
		    "isanteplusreports.positive_mixed_microscopic_malaria_test_results");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition rapidMalariaTestsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("RapidMalariaTests.sql",
		    "isanteplusreports.rapid_malaria_tests");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesWithSevereMalariaCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesWithSevereMalaria.sql",
		    "isanteplusreports.cases_with_severe_malaria");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition patientsHospitalizedCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PatientsHospitalized.sql",
		    "isanteplusreports.patients_hospitalized");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition casesWithNonConfirmedMalariaCasesCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition("isanteplusreports.non_confirmed_malaria_cases");
		
		cd.addSearch("MalariaSuspects", ReportUtils.map(casesWithSuspectedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("QuinineTreatment", ReportUtils.map(casesTreatedWithQuinineCohort(), COHORT_PARAMS));
		cd.addSearch("ChloroquineTreatment", ReportUtils.map(casesTreatedWithChloroquineCohort(), COHORT_PARAMS));
		cd.addSearch("PrimaquineTreatment", ReportUtils.map(casesTreatedWithPrimaquineCohort(), COHORT_PARAMS));
		
		cd.setCompositionString(
		    "MalariaSuspects AND NOT ConfirmedMalaria AND (QuinineTreatment OR ChloroquineTreatment OR PrimaquineTreatment)");
		
		return cd;
	}
	
	public static CohortDefinition casesWithConfirmedMalariaDuringPregnancyCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("CasesWithConfirmedMalariaDuringPregnancy.sql",
		    "isanteplusreports.cases_with_confirmed_malaria_during_pregnancy");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition positiveRapidMalariaTestResultsCohort() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PositiveRapidMalariaTestResults.sql",
		    "isanteplusreports.positive_rapid_malaria_test_results");
		
		return cohortDefinition;
	}
	
	public static CohortDefinition plasmodiumPositiveMicroscopicTestPatientsOtherThanFalciparumCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition(
		    "isanteplusreports.plasmodium_positive_microscopic_test_patients_other_than_falciparum");
		
		cd.addSearch("PositivePlasmodium", ReportUtils.map(positiveMicroscopicMalariaTestResultsCohort(), COHORT_PARAMS));
		cd.addSearch("PositiveFalciparum",
		    ReportUtils.map(positiveFalciparumMicroscopicMalariaTestResultsCohort(), COHORT_PARAMS));
		cd.setCompositionString("PositivePlasmodium AND NOT PositiveFalciparum");
		
		return cd;
	}
	
	public static CohortDefinition confirmedMalariaCasesTreatedWithEitherQuinineAloneOrChloroquineAloneOrPrimaquineAloneOrChloroquineAndPrimaquineOrQuinineAndChloroquinAndPrimaquinCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition(
		    "isanteplusreports.confirmed_malaria_cases_treated_with_either_quinine_alone_or_chloroquine_alone_or_primaquine_alone_or_chloroquine_and_primaquine_or_quinine_and_chloroquin_and_primaquin");
		
		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("QuinineAlone", ReportUtils.map(casesTreatedWithQuinineCohort(), COHORT_PARAMS));
		cd.addSearch("ChloroquineAlone", ReportUtils.map(casesTreatedWithChloroquineCohort(), COHORT_PARAMS));
		cd.addSearch("PrimaquineAlone", ReportUtils.map(casesTreatedWithPrimaquineCohort(), COHORT_PARAMS));
		cd.addSearch("ChlroquineAndPrimaquine",
		    ReportUtils.map(casesTreatedWithChloroquineAndPrimaquineCohort(), COHORT_PARAMS));
		cd.addSearch("QuinineAndChlroquineAndPrimaquine",
		    ReportUtils.map(casesTreatedWithQuinineAndChloroquineAndPrimaquinCohort(), COHORT_PARAMS));
		cd.setCompositionString(
		    "ConfirmedMalaria AND ((QuinineAlone OR ChloroquineAlone OR PrimaquineAlone) OR ChlroquineAndPrimaquine OR QuinineAndChlroquineAndPrimaquine)");
		
		return cd;
	}
	
	public static CohortDefinition confirmedMalariaCasesTreatedWithChloroquineAndPrimaquineOrQuinineAndChloroquineAndPrimaquineCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition(
		    "isanteplusreports.confirmed_malaria_cases_treated_with_chloroquine_and_primaquine_or_quinine_and_chloroquine_and_primaquine");
		
		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("ChlroquineAndPrimaquine",
		    ReportUtils.map(casesTreatedWithChloroquineAndPrimaquineCohort(), COHORT_PARAMS));
		cd.addSearch("QuinineAndChlroquineAndPrimaquine",
		    ReportUtils.map(casesTreatedWithQuinineAndChloroquineAndPrimaquinCohort(), COHORT_PARAMS));
		cd.setCompositionString("ConfirmedMalaria AND (ChlroquineAndPrimaquine OR QuinineAndChlroquineAndPrimaquine)");
		
		return cd;
	}
	
	public static CohortDefinition suspectedCasesOfConfirmedMalariaCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition(
		    "isanteplusreports.suspected_cases_of_confirmed_malaria");
		
		cd.addSearch("SuspectedMalaria", ReportUtils.map(casesWithSuspectedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.setCompositionString("SuspectedMalaria AND ConfirmedMalaria");
		
		return cd;
	}
	
	public static CohortDefinition casesForSevereMalariaAndPutOnTreatmentAccordingToTheNationalStandardsOfTheMsppCohort() {
		
		CompositionCohortDefinition cd = compositionCohortDefinition(
		    "isanteplusreports.cases_for_severe_malaria_and_put_on_treatment_according_to_the_national_standards_of_the_mspp");
		
		cd.addSearch("SevereMalaria", ReportUtils.map(casesWithSevereMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("QuinineAlone", ReportUtils.map(casesTreatedWithQuinineCohort(), COHORT_PARAMS));
		cd.addSearch("ChloroquineAlone", ReportUtils.map(casesTreatedWithChloroquineCohort(), COHORT_PARAMS));
		cd.addSearch("PrimaquineAlone", ReportUtils.map(casesTreatedWithPrimaquineCohort(), COHORT_PARAMS));
		
		cd.setCompositionString(
		    "SevereMalaria AND ConfirmedMalaria AND (QuinineAlone AND ChloroquineAlone AND PrimaquineAlone)");
		
		return cd;
	}

	public static CohortDefinition patientsWithMalariaHospitalizedCohort() {
    
		CompositionCohortDefinition cd = compositionCohortDefinition("isanteplusreports.patients_with_malaria_hospitalized");

		cd.addSearch("ConfirmedMalaria", ReportUtils.map(casesWithConfirmedMalariaCohort(), COHORT_PARAMS));
		cd.addSearch("hospitalized", ReportUtils.map(patientsHospitalizedCohort(), COHORT_PARAMS));
		cd.setCompositionString("ConfirmedMalaria AND hospitalized");
    
		return cd;
	}



	
	
}
