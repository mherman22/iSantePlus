package org.openmrs.module.isanteplusreports.library.indicator;

import java.util.List;

import org.openmrs.module.isanteplusreports.library.cohort.HealthQualReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;

public class HealthQualReportIndicatorLibrary {
    
    private static final String IND_PARAMS = "startDate=${startDate},endDate=${endDate},location=${location}";
    
    public static CohortIndicator retentionOfPatientsOnArtDen() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.retention_of_patients_on_art_den",
            ReportUtils.map(HealthQualReportCohortLibrary.retentionOfPatientsOnArtDen(), IND_PARAMS));
    }
    
    public static CohortIndicator retentionOfPatientsOnArtNum() {
        return EmrReportingUtils.cohortIndicator("isanteplusreports.retention_of_patients_on_art_num",
            ReportUtils.map(HealthQualReportCohortLibrary.retentionOfPatientsOnArtNum(), IND_PARAMS));
    }
    
    public static CohortIndicator cohortIndicatorFromSqlResource(String sql, String name, List<Parameter> parameters) {
        return EmrReportingUtils.cohortIndicator(name, parameters,
            Mapped.mapStraightThrough(HealthQualReportCohortLibrary.cohortFromSqlResource(sql, name, parameters)));
    }

}
