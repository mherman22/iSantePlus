package org.openmrs.module.isanteplusreports.library.cohort.common;

import java.util.Date;

import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.common.DurationUnit;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class CommonCohortLibrary {
	  /**
	   * Patients who are female
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition femaleCohort() {
	    GenderCohortDefinition cohort = new GenderCohortDefinition();
	    cohort.setName("femaleCohort");
	    cohort.setFemaleIncluded(true);
	    cohort.setMaleIncluded(false);
	    return cohort;
	  }

	  /**
	   * Patients who are male
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition maleCohort() {
	    GenderCohortDefinition cohort = new GenderCohortDefinition();
	    cohort.setName("maleCohort");
	    cohort.setMaleIncluded(true);
	    cohort.setFemaleIncluded(false);
	    return cohort;
	  }	
	  
	  /**
	   * Patients who are between a given age range
	   *
	   * @return the cohort definition
	   */
	  
	  public CohortDefinition ageRangeCohort(int minAge, int maxAge) {
		    AgeCohortDefinition cohort = new AgeCohortDefinition();
		    cohort.setName("age between" + minAge + " years and " + maxAge + " years");
		    cohort.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		    cohort.setMinAge(minAge);
		    cohort.setMinAgeUnit(DurationUnit.YEARS);
		    cohort.setMaxAge(maxAge);
		    cohort.setMaxAgeUnit(DurationUnit.YEARS);
		    return cohort;
	 }
	  
	  
	  /**
	   * Patients who are aged minimum a given age
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition agedAtLeastCohort(int minAge) {
	        AgeCohortDefinition cd = new AgeCohortDefinition();
	        cd.setName("aged at least " + minAge);
	        cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
	        cd.setMinAge(minAge);
	        cd.setMinAgeUnit(DurationUnit.YEARS);
	        return cd;
	    }
	  
	  /**
	   * Patients who are aged maximum a given age
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition agedAtMostCohort(int maxAge) {
	        AgeCohortDefinition cd = new AgeCohortDefinition();
	        cd.setName("aged at most " + maxAge);
	        cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
	        cd.setMaxAge(maxAge);
	        cd.setMaxAgeUnit(DurationUnit.YEARS);
	        return cd;
	    }
	  
	  /**
	   * Patients whose age is unknown
	   *
	   * @return the cohort definition
	   */
	  public CohortDefinition unknownAgeCohort() {
		    AgeCohortDefinition cohort = new AgeCohortDefinition();
		    cohort.setName("uk");
		    cohort.setMinAge(200);
		    cohort.setUnknownAgeIncluded(true);
		    return cohort;
		 }
	  
}
