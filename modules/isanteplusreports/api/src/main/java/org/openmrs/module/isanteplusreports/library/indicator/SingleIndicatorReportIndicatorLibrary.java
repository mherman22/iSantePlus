package org.openmrs.module.isanteplusreports.library.indicator;

import org.openmrs.module.isanteplusreports.library.cohort.SingleIndicatorReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class SingleIndicatorReportIndicatorLibrary {
	
	private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
	
	public static CohortIndicator artEnrollmentsAmongMedicallyEligible() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.art_enrollments_among_medically_eligible",
		    ReportUtils.map(SingleIndicatorReportCohortLibrary.artEnrollmentAmongMedicallyEligible(),
		        IND_PARAMS));
	}

	public static CohortIndicator patientsWhoDiscontinuedDrugs() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.discontinued_drugs",
		    ReportUtils.map(SingleIndicatorReportCohortLibrary.patientsWhoDiscontinuedDrugs(),
		        IND_PARAMS));
	}	
	
	public static CohortIndicator patientsWhoDiscontinuedCotrimoxazole() {
		return EmrReportingUtils.cohortIndicator("isanteplusreports.discontinued_cotrimoxazole",
		    ReportUtils.map(SingleIndicatorReportCohortLibrary.patientsWhoDiscontinuedCotrimoxazole(),
		        IND_PARAMS));
	}	

}
