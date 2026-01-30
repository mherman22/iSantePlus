package org.openmrs.module.isanteplusreports.pnlsReport.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.ReportData;

public class pnlsReportUtils {

    public static ReportData getReportData(String reportUuid, Date startDate, Date endDate,
                                           Map<String, Object> additionalOptions) {
        return HealthQualUtils.getReportData(reportUuid, startDate, endDate, additionalOptions);
    }

    public static String replaceNonBreakingSpaces(String stringToClean) {
        return HealthQualUtils.replaceNonBreakingSpaces(stringToClean);
    }

    public static CohortIndicator cohortIndicatorFromSqlResource(String sql, String name, List<Parameter> parameters) {
        return EmrReportingUtils.cohortIndicator(name, parameters,
                Mapped.mapStraightThrough(cohortFromSqlResource(sql, name, parameters)));
    }

    public static CohortDefinition cohortFromSqlResource(String sqlResourceName, String name, List<Parameter> parameters) {
        String sql = IsantePlusReportsUtil
                .getStringFromResource(PnlsReportConstants.PNLS_REPORTS_RESOURCE_PATH + sqlResourceName);

        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName(name);
        cd.setDescription(MessageUtil.translate(name));
        for (Parameter parameter : parameters) {
            cd.addParameter(parameter);
        }

        cd.setQuery(sql);
        return cd;
    }

    public static void addAgeAndGenderColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                              String gender) {
        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("0-1" + gender, "0-1 Years", CohortIndicator, "age=0_1" + genderDimension, dsd);
        constructColumn("1-4" + gender, "1-4 Years", CohortIndicator, "age=1_4" + genderDimension, dsd);
        constructColumn("5-9" + gender, "5-9 Years", CohortIndicator, "age=5_9" + genderDimension, dsd);
        constructColumn("10-14" + gender, "10-14 Years", CohortIndicator, "age=10_14" + genderDimension, dsd);
        constructColumn("15-19" + gender, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension, dsd);
        constructColumn("20-24" + gender, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension, dsd);
        constructColumn("25-29" + gender, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension, dsd);
        constructColumn("30-34" + gender, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension, dsd);
        constructColumn("35-39" + gender, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension, dsd);
        constructColumn("40-44" + gender, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension, dsd);
        constructColumn("45-49" + gender, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension, dsd);
        constructColumn("50-54" + gender, "50-54 Years", CohortIndicator, "age=50_54" + genderDimension, dsd);
        constructColumn("55-59" + gender, "55-59 Years", CohortIndicator, "age=55_59" + genderDimension, dsd);
        constructColumn("60-64" + gender, "60-64 Years", CohortIndicator, "age=60_64" + genderDimension, dsd);
        constructColumn("65" + gender, "65+ Years", CohortIndicator, "age=65" + genderDimension, dsd);
        constructColumn("UnKnown" + gender, " unknown Years", CohortIndicator, "age=unknown" + genderDimension, dsd);
        constructColumn("Total" + gender, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }

    public static void addAgeAndGenderColumns1(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                               String gender) {
        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("0-1" + gender, "0-1 Years", CohortIndicator, "age=0_1" + genderDimension, dsd);
        constructColumn("1-4" + gender, "1-4 Years", CohortIndicator, "age=1_4" + genderDimension, dsd);
        constructColumn("5-9" + gender, "5-9 Years", CohortIndicator, "age=5_9" + genderDimension, dsd);
        constructColumn("10-14" + gender, "10-14 Years", CohortIndicator, "age=10_14" + genderDimension, dsd);
        constructColumn("15-19" + gender, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension, dsd);
        constructColumn("20-24" + gender, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension, dsd);
        constructColumn("25-29" + gender, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension, dsd);
        constructColumn("30-34" + gender, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension, dsd);
        constructColumn("35-39" + gender, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension, dsd);
        constructColumn("40-44" + gender, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension, dsd);
        constructColumn("45-49" + gender, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension, dsd);
        constructColumn("50-54" + gender, "50-54 Years", CohortIndicator, "age=50_54" + genderDimension, dsd);
        constructColumn("55-59" + gender, "55-59 Years", CohortIndicator, "age=55_59" + genderDimension, dsd);
        constructColumn("60-64" + gender, "60-64 Years", CohortIndicator, "age=60_64" + genderDimension, dsd);
        constructColumn("65" + gender, "65+ Years", CohortIndicator, "age=65" + genderDimension, dsd);
        constructColumn("UnKnown" + gender, " unknown Years", CohortIndicator, "age=unknown" + genderDimension, dsd);
        constructColumn("Total" + gender, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }

    public static void addMultipleAgeAndGenderColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                      String gender, String column) {
        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("0-1" + gender + column, "0-1 Years", CohortIndicator, "age=0_1" + genderDimension, dsd);
        constructColumn("1-4" + gender + column, "1-4 Years", CohortIndicator, "age=1_4" + genderDimension, dsd);
        constructColumn("5-9" + gender + column, "5-9 Years", CohortIndicator, "age=5_9" + genderDimension, dsd);
        constructColumn("10-14" + gender + column, "10-14 Years", CohortIndicator, "age=10_14" + genderDimension, dsd);
        constructColumn("15-19" + gender + column, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension, dsd);
        constructColumn("20-24" + gender + column, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension, dsd);
        constructColumn("25-29" + gender + column, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension, dsd);
        constructColumn("30-34" + gender + column, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension, dsd);
        constructColumn("35-39" + gender + column, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension, dsd);
        constructColumn("40-44" + gender + column, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension, dsd);
        constructColumn("45-49" + gender + column, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension, dsd);
        constructColumn("50-54" + gender + column, "50-54 Years", CohortIndicator, "age=50_54" + genderDimension, dsd);
        constructColumn("55-59" + gender + column, "55-59 Years", CohortIndicator, "age=55_59" + genderDimension, dsd);
        constructColumn("60-64" + gender + column, "60-64 Years", CohortIndicator, "age=60_64" + genderDimension, dsd);
        constructColumn("65" + gender + column, "65+ Years", CohortIndicator, "age=65" + genderDimension, dsd);
        constructColumn("UnKnown" + gender + column, " unknown Years", CohortIndicator, "age=unknown" + genderDimension,
                dsd);
        constructColumn("Total" + gender + column, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }

    public static void addTotalColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator) {
        constructColumn("0-1T", "0-1 Years", CohortIndicator, "age=0_1", dsd);
        constructColumn("1-4T", "1-4 Years", CohortIndicator, "age=1_4", dsd);
        constructColumn("5-9T", "5-9 Years", CohortIndicator, "age=5_9", dsd);
        constructColumn("10-14T", "10-14 Years", CohortIndicator, "age=10_14", dsd);
        constructColumn("15-19T", "15-19 Years", CohortIndicator, "age=15_19", dsd);
        constructColumn("20-24T", "20-24 Years", CohortIndicator, "age=20_24", dsd);
        constructColumn("25-29T", "25-29 Years", CohortIndicator, "age=25_29", dsd);
        constructColumn("30-34T", "30-34 Years", CohortIndicator, "age=30_34", dsd);
        constructColumn("35-39T", "35-39 Years", CohortIndicator, "age=35_39", dsd);
        constructColumn("40-44T", "40-44 Years", CohortIndicator, "age=40_44", dsd);
        constructColumn("45-49T", "45-49 Years", CohortIndicator, "age=45_49", dsd);
        constructColumn("50-54T", "50-54 Years", CohortIndicator, "age=50_54", dsd);
        constructColumn("55-59T", "55-59 Years", CohortIndicator, "age=55_59", dsd);
        constructColumn("60-64T", "60-64 Years", CohortIndicator, "age=60_64", dsd);
        constructColumn("65T", "65+ Years", CohortIndicator, "age=65", dsd);
        constructColumn("UnKnownT", " unknown Years", CohortIndicator, "age=unknown", dsd);
        constructColumn("TotalT", "Total Patients", CohortIndicator, "", dsd);
    }

    public static void addTotalColumns1(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator) {
        constructColumn("0-1T", "0-1 Years", CohortIndicator, "age=0_1", dsd);
        constructColumn("1-4T", "1-4 Years", CohortIndicator, "age=1_4", dsd);
        constructColumn("5-9T", "5-9 Years", CohortIndicator, "age=5_9", dsd);
        constructColumn("10-14T", "10-14 Years", CohortIndicator, "age=10_14", dsd);
        constructColumn("15-19T", "15-19 Years", CohortIndicator, "age=15_19", dsd);
        constructColumn("20-24T", "20-24 Years", CohortIndicator, "age=20_24", dsd);
        constructColumn("25-29T", "25-29 Years", CohortIndicator, "age=25_29", dsd);
        constructColumn("30-34T", "30-34 Years", CohortIndicator, "age=30_34", dsd);
        constructColumn("35-39T", "35-39 Years", CohortIndicator, "age=35_39", dsd);
        constructColumn("40-44T", "40-44 Years", CohortIndicator, "age=40_44", dsd);
        constructColumn("45-49T", "45-49 Years", CohortIndicator, "age=45_49", dsd);
        constructColumn("50-54T", "50-54 Years", CohortIndicator, "age=50_54", dsd);
        constructColumn("55-59T", "55-59 Years", CohortIndicator, "age=55_59", dsd);
        constructColumn("60-64T", "60-64 Years", CohortIndicator, "age=60_64", dsd);
        constructColumn("65T", "65+ Years", CohortIndicator, "age=65", dsd);
        constructColumn("UnKnownT", " unknown Years", CohortIndicator, "age=unknown", dsd);
        constructColumn("TotalT", "Total Patients", CohortIndicator, "", dsd);
    }

    public static void addMultipleTotalColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                               String column) {
        constructColumn("0-1T" + column, "0-1 Years", CohortIndicator, "age=0_1", dsd);
        constructColumn("1-4T" + column, "1-4 Years", CohortIndicator, "age=1_4", dsd);
        constructColumn("5-9T" + column, "5-9 Years", CohortIndicator, "age=5_9", dsd);
        constructColumn("10-14T" + column, "10-14 Years", CohortIndicator, "age=10_14", dsd);
        constructColumn("15-19T" + column, "15-19 Years", CohortIndicator, "age=15_19", dsd);
        constructColumn("20-24T" + column, "20-24 Years", CohortIndicator, "age=20_24", dsd);
        constructColumn("25-29T" + column, "25-29 Years", CohortIndicator, "age=25_29", dsd);
        constructColumn("30-34T" + column, "30-34 Years", CohortIndicator, "age=30_34", dsd);
        constructColumn("35-39T" + column, "35-39 Years", CohortIndicator, "age=35_39", dsd);
        constructColumn("40-44T" + column, "40-44 Years", CohortIndicator, "age=40_44", dsd);
        constructColumn("45-49T" + column, "45-49 Years", CohortIndicator, "age=45_49", dsd);
        constructColumn("50-54T" + column, "50-54 Years", CohortIndicator, "age=50_54", dsd);
        constructColumn("55-59T" + column, "55-59 Years", CohortIndicator, "age=55_59", dsd);
        constructColumn("60-64T" + column, "60-64 Years", CohortIndicator, "age=60_64", dsd);
        constructColumn("65T" + column, "65+ Years", CohortIndicator, "age=65", dsd);
        constructColumn("UnKnownT" + column, " unknown Years", CohortIndicator, "age=unknown", dsd);
        constructColumn("TotalT" + column, "Total Patients", CohortIndicator, "", dsd);
    }

    public static void addAgeAndGenderAndNotEnrollOnArtReasonColumns(CohortIndicatorDataSetDefinition dsd,
                                                                     CohortIndicator CohortIndicator, String gender, String column, String dimension) {

        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }

        String reasonDimension = "";
        if (StringUtils.isNotBlank(dimension)) {
            reasonDimension = "|enrolRsn=" + dimension;
        } else {
            reasonDimension = "";
        }

        constructColumn("0-1" + gender + column, "0-1 Years", CohortIndicator, "age=0_1" + genderDimension + reasonDimension, dsd);
        constructColumn("1-4" + gender + column, "1-4 Years", CohortIndicator, "age=1_4" + genderDimension + reasonDimension, dsd);
        constructColumn("5-9" + gender + column, "5-9 Years", CohortIndicator, "age=5_9" + genderDimension + reasonDimension, dsd);
        constructColumn("10-14" + gender + column, "10-14 Years", CohortIndicator, "age=10_14" + genderDimension + reasonDimension, dsd);
        constructColumn("15-19" + gender + column, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension + reasonDimension, dsd);
        constructColumn("20-24" + gender + column, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension + reasonDimension, dsd);
        constructColumn("25-29" + gender + column, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension + reasonDimension, dsd);
        constructColumn("30-34" + gender + column, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension + reasonDimension, dsd);
        constructColumn("35-39" + gender + column, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension + reasonDimension, dsd);
        constructColumn("40-44" + gender + column, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension + reasonDimension, dsd);
        constructColumn("45-49" + gender + column, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension + reasonDimension, dsd);
        constructColumn("50-54" + gender + column, "50-54 Years", CohortIndicator, "age=50_54" + genderDimension + reasonDimension, dsd);
        constructColumn("55-59" + gender + column, "55-59 Years", CohortIndicator, "age=55_59" + genderDimension + reasonDimension, dsd);
        constructColumn("60-64" + gender + column, "60-64 Years", CohortIndicator, "age=60_64" + genderDimension + reasonDimension, dsd);
        constructColumn("65" + gender + column, "65+ Years", CohortIndicator, "age=65" + genderDimension + reasonDimension, dsd);
        constructColumn("UnKnown" + gender + column, " unknown Years", CohortIndicator, "age=unknown" + genderDimension + reasonDimension, dsd);
        constructColumn("Total" + gender + column, "Total Patients", CohortIndicator, "gender=" + gender + reasonDimension, dsd);
    }

    public static void addKeyPopulationColums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                              String column) {
        constructColumn("MSM" + column, "MSM", CohortIndicator, "keyPopn=MSM", dsd);
        constructColumn("SP" + column, "Sex Profesional", CohortIndicator, "keyPopn=SEX", dsd);
        constructColumn("TSG" + column, "Transgender", CohortIndicator, "keyPopn=TRANSG", dsd);
        constructColumn("CP" + column, "Captives", CohortIndicator, "keyPopn=CAPT", dsd);
        constructColumn("DRUG" + column, "Drug Users", CohortIndicator, "keyPopn=DRUG", dsd);
        constructColumn("Total" + column, "Total", CohortIndicator, "", dsd);
    }

    public static void addKeyPopulationSingleRowColums(CohortIndicatorDataSetDefinition dsd,
                                                       CohortIndicator CohortIndicator) {
        constructColumn("MSM", "MSM", CohortIndicator, "keyPopn=MSM", dsd);
        constructColumn("SP", "Sex Profesional", CohortIndicator, "keyPopn=SEX", dsd);
        constructColumn("TSG", "Transgender", CohortIndicator, "keyPopn=TRANSG", dsd);
        constructColumn("CP", "Captives", CohortIndicator, "keyPopn=CAPT", dsd);
        constructColumn("DRUG", "Drug Users", CohortIndicator, "keyPopn=DRUG", dsd);
    }

    public static void addKeySpecimenSingleRowColums(CohortIndicatorDataSetDefinition dsd,
                                                       CohortIndicator CohortIndicator) {
        constructColumn("MSM", "MSM", CohortIndicator, "specimen=MSM", dsd);
        constructColumn("SP", "Sex Profesional", CohortIndicator, "specimen=SEX", dsd);
        constructColumn("TSG", "Transgender", CohortIndicator, "specimen=TRANSG", dsd);
        constructColumn("CP", "Captives", CohortIndicator, "specimen=CAPT", dsd);
        constructColumn("DRUG", "Drug Users", CohortIndicator, "specimen=DRUG", dsd);
        constructColumn("FMEENC", "FMEENC", CohortIndicator, "specimen=FMEENC", dsd);
        constructColumn("FMEALT", "FMEENC", CohortIndicator, "specimen=FMEALT", dsd);
    }

    public static void addKeyScreeneTbSingleRowColums(CohortIndicatorDataSetDefinition dsd,
                                                       CohortIndicator CohortIndicator) {
        constructColumn("SCRMSYMPT", "symptomes", CohortIndicator, "scrnTb=SCRMSYMPT", dsd);
        constructColumn("CXRXRAY", "radiographieThorax", CohortIndicator, "scrnTb=CXRXRAY", dsd);
        constructColumn("PCRMWRD", "pcrMoleculaire", CohortIndicator, "scrnTb=PCRMWRD", dsd);
    }

    public static void addGenderAndAgeBy15Colums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                 String gender, String column) {

        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }

        constructColumn("<15" + gender + column, "<15 Years", CohortIndicator, "age=<15" + genderDimension, dsd);
        constructColumn(">15" + gender + column, ">15 Years", CohortIndicator, "age=>15" + genderDimension, dsd);
        constructColumn("UnKnown" + gender + column, "UnKnown", CohortIndicator, "age=unknown" + genderDimension, dsd);
        constructColumn("Total" + gender + column, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }

    public static void addGenderAndAgeBy2Colums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                String gender, String column) {
        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("<2" + gender + column, "<2 Years", CohortIndicator, "age=<2" + genderDimension, dsd);
    }

    public static void addGenderAndAgeBy14Colums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                 String gender, String column) {
        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("2-14" + gender + column, "2-14 Years", CohortIndicator, "age=2-14" + genderDimension, dsd);
        constructColumn(">14" + gender + column, ">14 Years", CohortIndicator, "age=>14" + genderDimension, dsd);
        constructColumn("UnKnown" + gender + column, "UnKnown", CohortIndicator, "age=unknown" + genderDimension, dsd);
        constructColumn("Total" + gender + column, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }

    public static void addTotalGenderAndAgeBy15Colums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator, String column) {
        constructColumn("<15" + column, "<15 Years", CohortIndicator, "age=<15", dsd);
        constructColumn(">15" + column, ">15 Years", CohortIndicator, "age=>15", dsd);
        constructColumn("UnKnown" + column, "UnKnown", CohortIndicator, "age=unknown", dsd);
        constructColumn("Total" + column, "Total Patients", CohortIndicator, "", dsd);
    }

    public static void constructColumn(String columnName, String ColumnDescription, CohortIndicator CohortIndicator,
                                       String dimension, CohortIndicatorDataSetDefinition dsd) {
        dsd.addColumn(columnName, ColumnDescription, Mapped.mapStraightThrough(CohortIndicator), dimension);
    }


    public static void addCervicalCancerStatusColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                      String cancerDimension) {
        String dimension = "";
        if (StringUtils.isNotBlank(cancerDimension)) {
            dimension = "|cervStat=" + cancerDimension;
        }
        constructColumn("15-19" + cancerDimension, "15-19 Years", CohortIndicator, "age=15_19" + dimension, dsd);
        constructColumn("20-24" + cancerDimension, "20-24 Years", CohortIndicator, "age=20_24" + dimension, dsd);
        constructColumn("25-29" + cancerDimension, "25-29 Years", CohortIndicator, "age=25_29" + dimension, dsd);
        constructColumn("30-34" + cancerDimension, "30-34 Years", CohortIndicator, "age=30_34" + dimension, dsd);
        constructColumn("35-39" + cancerDimension, "35-39 Years", CohortIndicator, "age=35_39" + dimension, dsd);
        constructColumn("40-44" + cancerDimension, "40-44 Years", CohortIndicator, "age=40_44" + dimension, dsd);
        constructColumn("45-49" + cancerDimension, "45-49 Years", CohortIndicator, "age=45_49" + dimension, dsd);
        constructColumn("50-54" + cancerDimension, "50-54 Years", CohortIndicator, "age=50_54" + dimension, dsd);
        constructColumn("55-59" + cancerDimension, "55-59 Years", CohortIndicator, "age=55_59" + dimension, dsd);
        constructColumn("60-64" + cancerDimension, "60-64 Years", CohortIndicator, "age=60_64" + dimension, dsd);
        constructColumn("65" + cancerDimension, "65+ Years", CohortIndicator, "age=65" + dimension, dsd);
        constructColumn("UnKnown" + cancerDimension, " unknown Years", CohortIndicator, "age=unknown" + dimension, dsd);
        constructColumn("Total" + cancerDimension, "Total Patients", CohortIndicator, "cervStat=" + cancerDimension, dsd);
    }


    public static void addCervicalTotalCancerStatusColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator) {

        constructColumn("15-19T", "15-19 Years", CohortIndicator, "age=15_19", dsd);
        constructColumn("20-24T", "20-24 Years", CohortIndicator, "age=20_24", dsd);
        constructColumn("25-29T", "25-29 Years", CohortIndicator, "age=25_29", dsd);
        constructColumn("30-34T", "30-34 Years", CohortIndicator, "age=30_34", dsd);
        constructColumn("35-39T", "35-39 Years", CohortIndicator, "age=35_39", dsd);
        constructColumn("40-44T", "40-44 Years", CohortIndicator, "age=40_44", dsd);
        constructColumn("45-49T", "45-49 Years", CohortIndicator, "age=45_49", dsd);
        constructColumn("50-54T", "50-54 Years", CohortIndicator, "age=50_54", dsd);
        constructColumn("55-59T", "55-59 Years", CohortIndicator, "age=55_59", dsd);
        constructColumn("60-64T", "60-64 Years", CohortIndicator, "age=60_64", dsd);
        constructColumn("65T", "65+ Years", CohortIndicator, "age=65", dsd);
        constructColumn("UnKnownT", " unknown Years", CohortIndicator, "age=unknown", dsd);
        constructColumn("TotalT", "Total Patients", CohortIndicator, "", dsd);
    }

    public static void addCervicalCancerbyAgeColumns(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator, String Column) {
        constructColumn("15-19" + Column, "15-19 Years", CohortIndicator, "age=15_19", dsd);
        constructColumn("20-24" + Column, "20-24 Years", CohortIndicator, "age=20_24", dsd);
        constructColumn("25-29" + Column, "25-29 Years", CohortIndicator, "age=25_29", dsd);
        constructColumn("30-34" + Column, "30-34 Years", CohortIndicator, "age=30_34", dsd);
        constructColumn("35-39" + Column, "35-39 Years", CohortIndicator, "age=35_39", dsd);
        constructColumn("40-44" + Column, "40-44 Years", CohortIndicator, "age=40_44", dsd);
        constructColumn("45-49" + Column, "45-49 Years", CohortIndicator, "age=45_49", dsd);
        constructColumn("50-54" + Column, "50-54 Years", CohortIndicator, "age=50_54", dsd);
        constructColumn("55-59" + Column, "55-59 Years", CohortIndicator, "age=55_59", dsd);
        constructColumn("60-64" + Column, "60-64 Years", CohortIndicator, "age=60_64", dsd);
        constructColumn("65" + Column, "65+ Years", CohortIndicator, "age=65", dsd);
        constructColumn("UnKnown" + Column, " unknown Years", CohortIndicator, "age=unknown", dsd);
        constructColumn("Total" + Column, "Total Patients", CohortIndicator, "", dsd);
    }

    public static void addMultipleAgeAndGenderColumnsStaringat15(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
                                                                 String gender, String column) {

        String genderDimension = "";
        if (StringUtils.equals(gender, "M")) {
            genderDimension = "|gender=M";
        } else if (StringUtils.equals(gender, "F")) {
            genderDimension = "|gender=F";
        }
        constructColumn("15-19" + gender + column, "15-19 Years", CohortIndicator, "age=15_19" + genderDimension, dsd);
        constructColumn("20-24" + gender + column, "20-24 Years", CohortIndicator, "age=20_24" + genderDimension, dsd);
        constructColumn("25-29" + gender + column, "25-29 Years", CohortIndicator, "age=25_29" + genderDimension, dsd);
        constructColumn("30-34" + gender + column, "30-34 Years", CohortIndicator, "age=30_34" + genderDimension, dsd);
        constructColumn("35-39" + gender + column, "35-39 Years", CohortIndicator, "age=35_39" + genderDimension, dsd);
        constructColumn("40-44" + gender + column, "40-44 Years", CohortIndicator, "age=40_44" + genderDimension, dsd);
        constructColumn("45-49" + gender + column, "45-49 Years", CohortIndicator, "age=45_49" + genderDimension, dsd);
        constructColumn("50-54" + gender + column, "50-54 Years", CohortIndicator, "age=50_54" + genderDimension, dsd);
        constructColumn("55-59" + gender + column, "55-59 Years", CohortIndicator, "age=55_59" + genderDimension, dsd);
        constructColumn("60-64" + gender + column, "60-64 Years", CohortIndicator, "age=60_64" + genderDimension, dsd);
        constructColumn("65" + gender + column, "65+ Years", CohortIndicator, "age=65" + genderDimension, dsd);
        constructColumn("UnKnown" + gender + column, " unknown Years", CohortIndicator, "age=unknown" + genderDimension,
                dsd);
        constructColumn("Total" + gender + column, "Total Patients", CohortIndicator, "gender=" + gender, dsd);
    }
}
