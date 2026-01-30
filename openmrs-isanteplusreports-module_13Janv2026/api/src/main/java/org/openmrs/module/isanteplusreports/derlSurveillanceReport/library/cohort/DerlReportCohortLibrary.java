package org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.cohort;

import java.util.Date;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlReportConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;

public class DerlReportCohortLibrary {

    public static SqlCohortDefinition sqlCohortDefinition(String sqlResourceName, String cohortDefinitionName) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(DerlReportConstants.DERL_REPORTS_RESOURCE_PATH + sqlResourceName);

        SqlCohortDefinition definition = IsantePlusReportsUtil.sqlCohortDefinition(sql, cohortDefinitionName,
                MessageUtil.translate(cohortDefinitionName));

        return definition;
    }


    //Weekly surveillance report
    public static CohortDefinition weeklyDerlSurveillanceCohort() {
        //SqlCohortDefinition cohortDefinition =  sqlCohortDefinition(DerlReportConstants..WEEK_COHORT_SQL,"reason definition");
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK1_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly1DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK1_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly2DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK2_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly3DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK3_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly4DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK4_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly5DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK5_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly6DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK6_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly7DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK7_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }


    public static CohortDefinition weekly8DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK8_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly9DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK9_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly10DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK10_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly11DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK11_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly12DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK12_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly13DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK13_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly14DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK14_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly15DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK15_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly16DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK16_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly17DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK17_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly18DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK18_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly19DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK19_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly20DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK20_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly21DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK21_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly22DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK22_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly23DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK23_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly24DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK24_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly25DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK25_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly26DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK26_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly27DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK27_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly28DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK28_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly29DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK29_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly30DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK30_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly31DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK31_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly32DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK32_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly33DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK33_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly34DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK34_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly35DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK35_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly36DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK36_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly37DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK37_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly38DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK38_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly39DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK39_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly40DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK40_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly41DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK41_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly42DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK42_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly43DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK43_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly44DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK44_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly45DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK45_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly46DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK46_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly47DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK47_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly48DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK48_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly49DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK49_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly50DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK50_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly51DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK51_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly52DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK52_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition weekly53DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.WEEK53_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }



    public static CohortDefinition monthly1DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH1_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly2DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH2_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly3DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH3_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly4DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH4_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly5DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH5_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly6DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH6_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly7DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH7_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly8DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH8_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly9DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH9_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly10DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH10_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly11DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH11_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }

    public static CohortDefinition monthly12DerlSurveillanceCohort() {
        SqlCohortDefinition cohortDefinition = sqlCohortDefinition(DerlReportConstants.MONTH12_COHORT_SQL, "reason definition");
        return cohortDefinition;
    }
}
