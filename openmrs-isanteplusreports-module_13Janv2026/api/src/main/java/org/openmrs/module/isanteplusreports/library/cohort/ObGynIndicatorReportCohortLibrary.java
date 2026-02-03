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

public class ObGynIndicatorReportCohortLibrary {
	
	private static final Parameter LOCATION = new Parameter("location", "Location", Date.class);
	
	private static final Parameter END_DATE = new Parameter("endDate", "End Date", Date.class);
	
	private static final Parameter START_DATE = new Parameter("startDate", "Start Date", Date.class);
	
	private static final String COHORT_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(IsantePlusReportsConstants.OBGYN_INDICATORS_RESOURCE_PATH + sqlResourceName);
		
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
	
    public static CohortDefinition pregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriodCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWhoHadAFirstConsultationFormForTheSelectedPeriod.sql", "isanteplusreports.pregnant_women_who_had_a_first_consultation_form_for_the_selected_period");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition visitsOfPregnantWomenForTheSelectedPeriodCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("VisitsOfPregnantWomenForTheSelectedPeriod.sql", "isanteplusreports.visits_of_pregnant_women_for_the_selected_period");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWhoHadAtLeastOneVisitForSelectedPeriodCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWhoHadAtLeastOneVisitForSelectedPeriod.sql", "isanteplusreports.pregnant_women_who_had_at_least_one_visit_for_selected_period");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithAClinicVisitPlannedInTheNext7DaysCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithAClinicVisitPlannedInTheNext7Days.sql", "isanteplusreports.pregnant_women_with_a_clinic_visit_planned_in_the_next_7_days");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithAClinicVisitPlannedInTheNext14DaysCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithAClinicVisitPlannedInTheNext14Days.sql", "isanteplusreports.pregnant_women_with_a_clinic_visit_planned_in_the_next_14_days");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithProbableDateOfDeliveryForTheSpecifiedPeriodCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithProbableDateOfDeliveryForTheSpecifiedPeriod.sql", "isanteplusreports.pregnant_women_with_probable_date_of_delivery_for_the_specified_period");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithoutABirthPlanCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithoutABirthPlan.sql", "isanteplusreports.pregnant_women_without_a_birth_plan");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition highRiskPregnanciesCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("HighRiskPregnancies.sql", "isanteplusreports.high_risk_pregnancies");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnanciesWithGestationalAgeGreaterThan12WeeksCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnanciesWithGestationalAgeGreaterThan12Weeks.sql", "isanteplusreports.pregnancies_with_gestational_age_greater_than_12_weeks");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition patientsWithHaemogramCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PatientsWithHaemogram.sql", "isanteplusreports.patients_with_haemogram");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWhoHaveNotBeenPlacedUnderIronSupplementCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWhoHaveNotBeenPlacedUnderIronSupplement.sql", "isanteplusreports.pregnant_women_who_have_not_been_placed_under_iron_supplement");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWhoHaveNotBeenPlacedUnderFolicAcidSupplementCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWhoHaveNotBeenPlacedUnderFolicAcidSupplement.sql", "isanteplusreports.pregnant_women_who_have_not_been_placed_under_folic_acid_supplement");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWhoHaveNotBeenVaccinatedWithTetanusToxoidCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWhoHaveNotBeenVaccinatedWithTetanusToxoid.sql", "isanteplusreports.pregnant_women_who_have_not_been_vaccinated_with_tetanus_toxoid");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithIronDeficiencyAnemiaWhoHaveBeenPrescribedIronCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithIronDeficiencyAnemiaWhoHaveBeenPrescribedIron.sql", "isanteplusreports.pregnant_women_with_iron_deficiency_anemia_who_have_been_prescribed_iron");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition pregnantWomenWithSickleCellAnemiaWhoHaveBeenPrescribedFolicAcidCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("PregnantWomenWithSickleCellAnemiaWhoHaveBeenPrescribedFolicAcid.sql", "isanteplusreports.pregnant_women_with_sickle_cell_anemia_who_have_been_prescribed_folic_acid");
        
        return cohortDefinition;
    }
 
 
    public static CohortDefinition womenWithElevatedBloodPressureAccompaniedBySignsOfToxemiaFrom20WeeksOfPregnancyCohort() {
        
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition("WomenWithElevatedBloodPressureAccompaniedBySignsOfToxemiaFrom20WeeksOfPregnancy.sql", "isanteplusreports.women_with_elevated_blood_pressure_accompanied_by_signs_of_toxemia_from_20_weeks_of_pregnancy");
        
        return cohortDefinition;
    }
 
    public static CohortDefinition pregnanciesWithGestationalAgeGreaterThan12WeeksWithoutHaemogramCohort() {
        
        CompositionCohortDefinition cd = compositionCohortDefinition("isanteplusreports.pregnancies_with_gestational_age_greater_than_12_weeks_without_haemogram");

        cd.addSearch("GestationGT12Weeks", ReportUtils.map(pregnanciesWithGestationalAgeGreaterThan12WeeksCohort(), COHORT_PARAMS));
        cd.addSearch("WithHaemogram", ReportUtils.map(patientsWithHaemogramCohort(), COHORT_PARAMS));
        cd.setCompositionString("GestationGT12Weeks AND NOT WithHaemogram");
        
        return cd;
    }
 


	
	
}
