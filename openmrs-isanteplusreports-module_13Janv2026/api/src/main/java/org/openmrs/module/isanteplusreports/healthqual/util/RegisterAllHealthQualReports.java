package org.openmrs.module.isanteplusreports.healthqual.util;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.sqlDataSetDefinitionWithResourcePath;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_10_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_10_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_10_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_11_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_11_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_11_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_12_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_12_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_12_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_13_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_13_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_13_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_14_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_14_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_14_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_15_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_15_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_15_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_16_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_16_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_16_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_1_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_1_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_1_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_2_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_2_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_2_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_4_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_4_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_4_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_5_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_5_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_5_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_6_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_6_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_6_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_7_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_7_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_7_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_8_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_8_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_8_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_9_DEN_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_9_INDICATOR_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.ADULT_9_NUM_INDICATOR_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.HEALTH_QUAL_REPORTS_RESOURCE_PATH;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_MESSAGE;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_SQL;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants.NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_UUID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.healthqual.HealthQualReportDataset;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllHealthQualReports {
	
	private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
	        Date.class);
	
	private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);
	
	private final static Parameter CURRENT_DATE = new Parameter("currentDate",
	        "isanteplusreports.healthqual.currentDate.label", Date.class);
	
	private final static Parameter PERIOD = new Parameter("period", "isanteplusreports.healthqual.option.label.periodMonths",
	        Integer.class);
	
	private final static Parameter AGE = new Parameter("age", "isanteplusreports.healthqual.option.label.ageDays",
	        Integer.class);
	
	public static void registerAll() {
		numberOfPatientsBySex();
		
		// adult
		healthQualRetentionOfPatientsOnArt();
		healthQualCd4AssessmentAtEnrolment();
		healthQualArvEnrollment();
		healthQualAdultHivAndCortimixazoleProphy();
		healthQualAdultHivOnArtWithAdherentEvaluation();
		healthQualAdultHivOnArtAdherentToTreatment();
		healthQualProportionOfPLHIVTestedForTB();
		healthQualAdultPlhivAndInh();
		healthQualProportionOfHIVPatientsWithNutritionalAssessment();
		healthQualProportionOfUndernourishedHIVPatients();
		healthQualProportionOfHivWomenUsingFamilyPlanning();
		healthQualProportionOfHIVPregnantWithHAART();
		healthQualProportionOfPregnantWithPrenataleCareOrLD();
		healthQualProportionOfHivOnArtViralLoadTest6Months();
		healthQualProportionOfHivOnArtViralLoadTest18Months();
		healthQualProportionOfHivOnArtUndetectableViralLoadTest6Months();
		
		// pediatric
		healthQualChildrenRegularlyFollowedOnArt();
		healthQualChildrenHivAndPlacedOnArt();
		healthQualPediatricHivAndReceivedCotrimoxazoleProphylaxis();
		healthQualPediatricBenefitedFromAnAdherence();
		healthQualChildrenOnArtWhoAreConsideredAdherent();
		healthQualProportionOfHIVChildrenTestedForTB();
		healthQualProportionOfHIVChildrenOlderThanOneYearOfAgeReceivedINH();
		healthQualProportionOfHIVChildrenYoungerThanOneYearOfAgeReceivedINH();
		healthQualProportionOfChildrenWithNutritionalAssessment();
		healthQualChildrenImmunizationAndVaccination();
		healthQualPediatricHivAndArtProphy();
		healthQualPediatricReceivedPcrTest();
		healthQualPediatricNegativePcrTest();
		healthQualEarlyInfantDiagnosis();
	}
	
	private static void numberOfPatientsBySex() {
		registerHealthEqualReportWithStartAndEndDateParams(NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_SQL,
		    NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_MESSAGE, NUMBER_OF_ACTIVE_PATIENTS_BY_SEX_UUID);
	}
	
	private static void healthQualRetentionOfPatientsOnArt() {
		/*        registerHealthEqualReportWithCurrentDateAndPeriodParams(ADULT_1_INDICATOR_SQL, ADULT_1_INDICATOR_MESSAGE,
		    HEALTH_QUAL_ADULT_1_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(ADULT_1_NUM_INDICATOR_SQL, ADULT_1_DEN_INDICATOR_SQL,
		    ADULT_1_INDICATOR_MESSAGE, HEALTH_QUAL_ADULT_1_INDICATOR_UUID);
	}
	
	private static void healthQualCd4AssessmentAtEnrolment() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_2_INDICATOR_SQL, ADULT_2_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_2_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_2_NUM_INDICATOR_SQL, ADULT_2_DEN_INDICATOR_SQL,
		    ADULT_2_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_2_INDICATOR_UUID);
		
	}
	
	private static void healthQualArvEnrollment() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_3_INDICATOR_SQL, ADULT_3_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_3_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.ADULT_3_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.ADULT_3_DEN_INDICATOR_SQL, HealthQualReportsConstants.ADULT_3_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_3_INDICATOR_UUID);
		
	}
	
	private static void healthQualAdultHivAndCortimixazoleProphy() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_4_INDICATOR_SQL, ADULT_4_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_4_INDICATOR_UUID);*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_4_NUM_INDICATOR_SQL, ADULT_4_DEN_INDICATOR_SQL,
		    ADULT_4_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_4_INDICATOR_UUID);
		
	}
	
	private static void healthQualAdultHivOnArtWithAdherentEvaluation() {
		/*        registerHealthEqualReportWithCurrentDateParam(ADULT_5_INDICATOR_SQL, ADULT_5_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_5_INDICATOR_UUID);*/
		registerHealthEqualReportWithCurrentDateParamNumDen(ADULT_5_NUM_INDICATOR_SQL, ADULT_5_DEN_INDICATOR_SQL,
		    ADULT_5_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_5_INDICATOR_UUID);
		
	}
	
	private static void healthQualAdultHivOnArtAdherentToTreatment() {
		/*       registerHealthEqualReportWithCurrentDateParam(ADULT_6_INDICATOR_SQL, ADULT_6_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_6_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithCurrentDateParamNumDen(ADULT_6_NUM_INDICATOR_SQL, ADULT_6_DEN_INDICATOR_SQL,
		    ADULT_6_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_6_INDICATOR_UUID);
		
	}
	
	private static void healthQualProportionOfPLHIVTestedForTB() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_7_INDICATOR_SQL, ADULT_7_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_7_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_7_NUM_INDICATOR_SQL, ADULT_7_DEN_INDICATOR_SQL,
		    ADULT_7_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_7_INDICATOR_UUID);
		
	}
	
	private static void healthQualAdultPlhivAndInh() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_8_INDICATOR_SQL, ADULT_8_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_8_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_8_NUM_INDICATOR_SQL, ADULT_8_DEN_INDICATOR_SQL,
		    ADULT_8_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_8_INDICATOR_UUID);
		
	}
	
	private static void healthQualProportionOfHIVPatientsWithNutritionalAssessment() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_9_INDICATOR_SQL, ADULT_9_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_9_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_9_NUM_INDICATOR_SQL, ADULT_9_DEN_INDICATOR_SQL,
		    ADULT_9_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_9_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfUndernourishedHIVPatients() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_10_INDICATOR_SQL, ADULT_10_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_10_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_10_NUM_INDICATOR_SQL, ADULT_10_DEN_INDICATOR_SQL,
		    ADULT_10_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_10_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHivWomenUsingFamilyPlanning() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_11_INDICATOR_SQL, ADULT_11_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_11_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_11_NUM_INDICATOR_SQL, ADULT_11_DEN_INDICATOR_SQL,
		    ADULT_11_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_11_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHIVPregnantWithHAART() {
		/*       registerHealthEqualReportWithStartAndEndDateParams(ADULT_12_INDICATOR_SQL, ADULT_12_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_12_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_12_NUM_INDICATOR_SQL, ADULT_12_DEN_INDICATOR_SQL,
		    ADULT_12_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_12_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfPregnantWithPrenataleCareOrLD() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_13_INDICATOR_SQL, ADULT_13_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_13_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_13_NUM_INDICATOR_SQL, ADULT_13_DEN_INDICATOR_SQL,
		    ADULT_13_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_13_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHivOnArtViralLoadTest6Months() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_14_INDICATOR_SQL, ADULT_14_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_14_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_14_NUM_INDICATOR_SQL, ADULT_14_DEN_INDICATOR_SQL,
		    ADULT_14_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_14_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHivOnArtViralLoadTest18Months() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_15_INDICATOR_SQL, ADULT_15_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_15_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_15_NUM_INDICATOR_SQL, ADULT_15_DEN_INDICATOR_SQL,
		    ADULT_15_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_15_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHivOnArtUndetectableViralLoadTest6Months() {
		/*        registerHealthEqualReportWithStartAndEndDateParams(ADULT_16_INDICATOR_SQL, ADULT_16_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_ADULT_16_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(ADULT_16_NUM_INDICATOR_SQL, ADULT_16_DEN_INDICATOR_SQL,
		    ADULT_16_INDICATOR_MESSAGE, HealthQualReportsConstants.HEALTH_QUAL_ADULT_16_INDICATOR_UUID);
	}
	
	private static void healthQualChildrenRegularlyFollowedOnArt() {
		/*		registerHealthEqualReportWithCurrentDateAndPeriodParams(PEDIATRIC_1_INDICATOR_SQL, PEDIATRIC_1_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_1_INDICATOR_UUID);
		*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_1_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_1_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_1_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_1_INDICATOR_UUID);
	}
	
	private static void healthQualChildrenHivAndPlacedOnArt() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_2_INDICATOR_SQL, PEDIATRIC_2_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_2_INDICATOR_UUID);
		*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_2_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_2_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_2_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_2_INDICATOR_UUID);
	}
	
	private static void healthQualPediatricHivAndReceivedCotrimoxazoleProphylaxis() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_3_INDICATOR_SQL, PEDIATRIC_3_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_3_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_3_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_3_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_3_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_3_INDICATOR_UUID);
	}
	
	private static void healthQualPediatricBenefitedFromAnAdherence() {
		/*		registerHealthEqualReportWithCurrentDateParam(PEDIATRIC_4_INDICATOR_SQL, PEDIATRIC_4_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_4_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithCurrentDateParamNumDen(HealthQualReportsConstants.PEDIATRIC_4_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_4_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_4_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_4_INDICATOR_UUID);
	}
	
	private static void healthQualChildrenOnArtWhoAreConsideredAdherent() {
		/*		registerHealthEqualReportWithCurrentDateParam(PEDIATRIC_5_INDICATOR_SQL, PEDIATRIC_5_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_5_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithCurrentDateParamNumDen(HealthQualReportsConstants.PEDIATRIC_5_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_5_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_5_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_5_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHIVChildrenTestedForTB() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_6_INDICATOR_SQL, PEDIATRIC_6_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_6_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_6_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_6_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_6_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_6_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHIVChildrenOlderThanOneYearOfAgeReceivedINH() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_7_INDICATOR_SQL, PEDIATRIC_7_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_7_INDICATOR_UUID);*/
		
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_7_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_7_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_7_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_7_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfHIVChildrenYoungerThanOneYearOfAgeReceivedINH() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_8_INDICATOR_SQL, PEDIATRIC_8_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_8_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_8_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_8_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_8_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_8_INDICATOR_UUID);
	}
	
	private static void healthQualProportionOfChildrenWithNutritionalAssessment() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_9_INDICATOR_SQL, PEDIATRIC_9_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_9_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_9_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_9_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_9_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_9_INDICATOR_UUID);
	}
	
	private static void healthQualChildrenImmunizationAndVaccination() {
		/*		registerHealthEqualReportWithAgeAndStartAndEndDateParams(PEDIATRIC_10_INDICATOR_SQL, PEDIATRIC_10_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_10_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithAgeAndStartAndEndDateParamsNumDen(
		    HealthQualReportsConstants.PEDIATRIC_10_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_10_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_10_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_10_INDICATOR_UUID);
	}
	
	private static void healthQualPediatricHivAndArtProphy() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_11_INDICATOR_SQL, PEDIATRIC_11_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_11_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_11_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_11_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_11_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_11_INDICATOR_UUID);
	}
	
	private static void healthQualPediatricReceivedPcrTest() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_12_INDICATOR_SQL, PEDIATRIC_12_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_12_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_12_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_12_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_12_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_12_INDICATOR_UUID);
	}
	
	private static void healthQualPediatricNegativePcrTest() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_13_INDICATOR_SQL, PEDIATRIC_13_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_13_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_13_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_13_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_13_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_13_INDICATOR_UUID);
	}
	
	private static void healthQualEarlyInfantDiagnosis() {
		/*		registerHealthEqualReportWithStartAndEndDateParams(PEDIATRIC_14_INDICATOR_SQL, PEDIATRIC_14_INDICATOR_MESSAGE,
				    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_14_INDICATOR_UUID);
		*/
		registerHealthEqualReportWithStartAndEndDateParamsNumDen(HealthQualReportsConstants.PEDIATRIC_14_NUM_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_14_DEN_INDICATOR_SQL,
		    HealthQualReportsConstants.PEDIATRIC_14_INDICATOR_MESSAGE,
		    HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_14_INDICATOR_UUID);
	}
	
	private static void registerHealthEqualReportWithStartAndEndDateParams(String sql, String messageProperties,
	        String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
		    HEALTH_QUAL_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(START_DATE);
		sqlData.addParameter(END_DATE);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	public static void registerHealthEqualReportWithStartAndEndDateParamsNumDen(String sqlNum, String sqlDen,
	        String messageProperties, String uuid) {
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(START_DATE);
		parameters.add(END_DATE);
		
		DataSetDefinition dsd = new HealthQualReportDataset().constructDataSet(messageProperties, sqlDen, sqlNum,
		    parameters);
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	private static void registerHealthEqualReportWithAgeAndStartAndEndDateParams(String sql, String messageProperties,
	        String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
		    HEALTH_QUAL_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(START_DATE);
		sqlData.addParameter(END_DATE);
		sqlData.addParameter(AGE);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("age", "${age}");
		
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addParameter(AGE);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	private static void registerHealthEqualReportWithAgeAndStartAndEndDateParamsNumDen(String sqlNum, String sqlDen,
	        String messageProperties, String uuid) {
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(START_DATE);
		parameters.add(END_DATE);
		parameters.add(AGE);
		
		DataSetDefinition dsd = new HealthQualReportDataset().constructDataSet(messageProperties, sqlDen, sqlNum,
		    parameters);
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("age", "${age}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(END_DATE);
		repDefinition.addParameter(AGE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
		
	}
	
	private static void registerHealthEqualReportWithStartDateAndPeriodParams(String sql, String messageProperties,
	        String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
		    HEALTH_QUAL_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(START_DATE);
		sqlData.addParameter(PERIOD);
		
		Context.getService(SerializedDefinitionService.class).saveDefinition(sqlData);
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("period", "${period}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(START_DATE);
		repDefinition.addParameter(PERIOD);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	private static void registerHealthEqualReportWithCurrentDateAndPeriodParams(String sql, String messageProperties,
	        String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
		    HEALTH_QUAL_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(CURRENT_DATE);
		sqlData.addParameter(PERIOD);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("currentDate", "${currentDate}");
		mappings.put("period", "${period}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(CURRENT_DATE);
		repDefinition.addParameter(PERIOD);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	/**
	 * @should save report definition and return valid instance
	 * @should Execute query and return report with at least 4 datasets
	 */
	public static void registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(String sqlNum, String sqlDen,
	        String messageProperties, String uuid) {
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(CURRENT_DATE);
		parameters.add(PERIOD);
		
		DataSetDefinition dsd = new HealthQualReportDataset().constructDataSet(messageProperties, sqlDen, sqlNum,
		    parameters);
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("currentDate", "${currentDate}");
		mappings.put("period", "${period}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(CURRENT_DATE);
		repDefinition.addParameter(PERIOD);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	private static void registerHealthEqualReportWithCurrentDateParam(String sql, String messageProperties, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
		    HEALTH_QUAL_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(CURRENT_DATE);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("currentDate", "${currentDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(CURRENT_DATE);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
	public static void registerHealthEqualReportWithCurrentDateParamNumDen(String sqlNum, String sqlDen,
	        String messageProperties, String uuid) {
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(CURRENT_DATE);
		
		DataSetDefinition dsd = new HealthQualReportDataset().constructDataSet(messageProperties, sqlDen, sqlNum,
		    parameters);
		Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
		
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("currentDate", "${currentDate}");
		
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(CURRENT_DATE);
		repDefinition.addDataSetDefinition(dsd, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
	}
	
}
