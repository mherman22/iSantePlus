package org.openmrs.module.isanteplusreports.library.cohort;

import java.util.Date;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class SingleIndicatorReportCohortLibrary {
	
	private static final Parameter LOCATION = new Parameter("location", "Location", Date.class);
	
	private static final Parameter END_DATE = new Parameter("endDate", "End Date", Date.class);
	
	private static final Parameter START_DATE = new Parameter("startDate", "Start Date", Date.class);
	
	public static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(IsantePlusReportsConstants.SINGLE_INDICATORS_RESOURCE_PATH + sqlResourceName);
		
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
	
	public static CohortDefinition artEnrollmentAmongMedicallyEligible() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("ArtEnrollmentAmongMedicallyEligible.sql",
		    "isanteplusreports.art_enrollment_among_medically_eligible");
		
		return cohortDefinition;
	}

	public static CohortDefinition patientsWhoDiscontinuedDrugs() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("DiscontinuedDrugs.sql",
		    "isanteplusreports.discontinued_drugs");
		
		return cohortDefinition;
	}	
	
	public static CohortDefinition patientsWhoDiscontinuedCotrimoxazole() {
		
		SqlCohortDefinition cohortDefinition = sqlCohortDefinition("DiscontinuedCotrimoxazole.sql",
		    "isanteplusreports.discontinued_cotrimoxazole");
		
		return cohortDefinition;
	}
		
}
