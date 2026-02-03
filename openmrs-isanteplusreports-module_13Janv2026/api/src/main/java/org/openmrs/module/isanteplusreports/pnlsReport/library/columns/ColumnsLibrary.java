package org.openmrs.module.isanteplusreports.pnlsReport.library.columns;

import org.apache.commons.lang.ArrayUtils;

public class ColumnsLibrary {
    //columns for two row age and gender report
    private static final String COLUMN_NAME_0_1_M = "0-1M";

    private static final String COLUMN_NAME_1_4_M = "1-4M";

    private static final String COLUMN_NAME_5_9_M = "5-9M";

    private static final String COLUMN_NAME_10_14_M = "10-14M";

    private static final String COLUMN_NAME_15_19_M = "15-19M";

    private static final String COLUMN_NAME_20_24_M = "20-24M";

    private static final String COLUMN_NAME_25_29_M = "25-29M";

    private static final String COLUMN_NAME_30_34_M = "30-34M";

    private static final String COLUMN_NAME_35_39_M = "35-39M";

    private static final String COLUMN_NAME_40_44_M = "40-44M";

    private static final String COLUMN_NAME_45_49_M = "45-49M";
    private static final String COLUMN_NAME_50_54_M = "50-54M";
    private static final String COLUMN_NAME_55_59_M = "55-59M";
    private static final String COLUMN_NAME_60_64_M = "60-64M";
    private static final String COLUMN_NAME_65_M = "65M";

    private static final String COLUMN_NAME_50_M = "50M";

    private static final String COLUMN_NAME_UK_M = "UnKnownM";

    private static final String COLUMN_NAME_Total_M = "TotalM";

    private static final String COLUMN_NAME_0_1_F = "0-1F";

    private static final String COLUMN_NAME_1_4_F = "1-4F";

    private static final String COLUMN_NAME_5_9_F = "5-9F";

    private static final String COLUMN_NAME_10_14_F = "10-14F";

    private static final String COLUMN_NAME_15_19_F = "15-19F";

    private static final String COLUMN_NAME_20_24_F = "20-24F";

    private static final String COLUMN_NAME_25_29_F = "25-29F";

    private static final String COLUMN_NAME_30_34_F = "30-34F";

    private static final String COLUMN_NAME_35_39_F = "35-39F";

    private static final String COLUMN_NAME_40_44_F = "40-44F";

    private static final String COLUMN_NAME_45_49_F = "45-49F";
    private static final String COLUMN_NAME_50_54_F = "50-54F";
    private static final String COLUMN_NAME_55_59_F = "55-59F";
    private static final String COLUMN_NAME_60_64_F = "60-64F";
    private static final String COLUMN_NAME_65_F = "65F";

    private static final String COLUMN_NAME_50_F = "50F";

    private static final String COLUMN_NAME_UK_F = "UnKnownF";

    private static final String COLUMN_NAME_Total_F = "TotalF";

    private static final String COLUMN_NAME_0_1_T = "0-1T";

    private static final String COLUMN_NAME_1_4_T = "1-4T";

    private static final String COLUMN_NAME_5_9_T = "5-9T";

    private static final String COLUMN_NAME_10_14_T = "10-14T";

    private static final String COLUMN_NAME_15_19_T = "15-19T";

    private static final String COLUMN_NAME_20_24_T = "20-24T";

    private static final String COLUMN_NAME_25_29_T = "25-29T";

    private static final String COLUMN_NAME_30_34_T = "30-34T";

    private static final String COLUMN_NAME_35_39_T = "35-39T";

    private static final String COLUMN_NAME_40_44_T = "40-44T";

    private static final String COLUMN_NAME_45_49_T = "45-49T";
    private static final String COLUMN_NAME_50_54_T = "50-54T";
    private static final String COLUMN_NAME_55_59_T = "55-59T";
    private static final String COLUMN_NAME_60_64_T = "60-64T";
    private static final String COLUMN_NAME_65_T = "65T";

    private static final String COLUMN_NAME_50_T = "50T";

    private static final String COLUMN_NAME_UK_T = "UnKnownT";

    private static final String COLUMN_NAME_Total_T = "TotalT";

    public static final String[] COLUMNS_ARRAY_14_BY_3 = {
            COLUMN_NAME_0_1_M,
            COLUMN_NAME_1_4_M,
            COLUMN_NAME_5_9_M,
            COLUMN_NAME_10_14_M,
            COLUMN_NAME_15_19_M,
            COLUMN_NAME_20_24_M,
            COLUMN_NAME_25_29_M,
            COLUMN_NAME_30_34_M,
            COLUMN_NAME_35_39_M,
            COLUMN_NAME_40_44_M,
            COLUMN_NAME_45_49_M,
            COLUMN_NAME_50_54_M,
            COLUMN_NAME_55_59_M,
            COLUMN_NAME_60_64_M,
            COLUMN_NAME_65_M,
            COLUMN_NAME_UK_M,
            COLUMN_NAME_Total_M,
            COLUMN_NAME_0_1_F,
            COLUMN_NAME_1_4_F,
            COLUMN_NAME_5_9_F,
            COLUMN_NAME_10_14_F,
            COLUMN_NAME_15_19_F,
            COLUMN_NAME_20_24_F,
            COLUMN_NAME_25_29_F,
            COLUMN_NAME_30_34_F,
            COLUMN_NAME_35_39_F,
            COLUMN_NAME_40_44_F,
            COLUMN_NAME_45_49_F,
            COLUMN_NAME_50_54_F,
            COLUMN_NAME_55_59_F,
            COLUMN_NAME_60_64_F,
            COLUMN_NAME_65_F,
            COLUMN_NAME_UK_F,
            COLUMN_NAME_Total_F,
            COLUMN_NAME_0_1_T,
            COLUMN_NAME_1_4_T,
            COLUMN_NAME_5_9_T,
            COLUMN_NAME_10_14_T,
            COLUMN_NAME_15_19_T,
            COLUMN_NAME_20_24_T,
            COLUMN_NAME_25_29_T,
            COLUMN_NAME_30_34_T,
            COLUMN_NAME_35_39_T,
            COLUMN_NAME_40_44_T,
            COLUMN_NAME_45_49_T,
            COLUMN_NAME_50_54_T,
            COLUMN_NAME_55_59_T,
            COLUMN_NAME_60_64_T,
            COLUMN_NAME_65_T,
            COLUMN_NAME_UK_T,
            COLUMN_NAME_Total_T
    };

    public static final String[] COLUMNS_ARRAY_17_BY_3 = {
            COLUMN_NAME_0_1_M,
            COLUMN_NAME_1_4_M,
            COLUMN_NAME_5_9_M,
            COLUMN_NAME_10_14_M,
            COLUMN_NAME_15_19_M,
            COLUMN_NAME_20_24_M,
            COLUMN_NAME_25_29_M,
            COLUMN_NAME_30_34_M,
            COLUMN_NAME_35_39_M,
            COLUMN_NAME_40_44_M,
            COLUMN_NAME_45_49_M,
            COLUMN_NAME_50_54_M,
            COLUMN_NAME_55_59_M,
            COLUMN_NAME_60_64_M,
            COLUMN_NAME_65_M,
            COLUMN_NAME_UK_M,
            COLUMN_NAME_Total_M,
            COLUMN_NAME_0_1_F,
            COLUMN_NAME_1_4_F,
            COLUMN_NAME_5_9_F,
            COLUMN_NAME_10_14_F,
            COLUMN_NAME_15_19_F,
            COLUMN_NAME_20_24_F,
            COLUMN_NAME_25_29_F,
            COLUMN_NAME_30_34_F,
            COLUMN_NAME_35_39_F,
            COLUMN_NAME_40_44_F,
            COLUMN_NAME_45_49_F,
            COLUMN_NAME_50_54_F,
            COLUMN_NAME_55_59_F,
            COLUMN_NAME_60_64_F,
            COLUMN_NAME_65_F,
            COLUMN_NAME_UK_F,
            COLUMN_NAME_Total_F,
            COLUMN_NAME_0_1_T,
            COLUMN_NAME_1_4_T,
            COLUMN_NAME_5_9_T,
            COLUMN_NAME_10_14_T,
            COLUMN_NAME_15_19_T,
            COLUMN_NAME_20_24_T,
            COLUMN_NAME_25_29_T,
            COLUMN_NAME_30_34_T,
            COLUMN_NAME_35_39_T,
            COLUMN_NAME_40_44_T,
            COLUMN_NAME_45_49_T,
            COLUMN_NAME_50_54_T,
            COLUMN_NAME_55_59_T,
            COLUMN_NAME_60_64_T,
            COLUMN_NAME_65_T,
            COLUMN_NAME_UK_T,
            COLUMN_NAME_Total_T
    };

    private static final String COLUMN_NAME_BF = "BF";

    public static final String[] COULUMNS_ARRAY_1_BY_1 = {COLUMN_NAME_BF};


    public static String[] genarateColumnArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_0_1_M + column,
                COLUMN_NAME_1_4_M + column,
                COLUMN_NAME_5_9_M + column,
                COLUMN_NAME_10_14_M + column,
                COLUMN_NAME_15_19_M + column,
                COLUMN_NAME_20_24_M + column,
                COLUMN_NAME_25_29_M + column,
                COLUMN_NAME_30_34_M + column,
                COLUMN_NAME_35_39_M + column,
                COLUMN_NAME_40_44_M + column,
                COLUMN_NAME_45_49_M + column,
                COLUMN_NAME_50_54_M + column,
                COLUMN_NAME_55_59_M + column,
                COLUMN_NAME_60_64_M + column,
                COLUMN_NAME_65_M + column,
                COLUMN_NAME_UK_M + column,
                COLUMN_NAME_Total_M + column,
                COLUMN_NAME_0_1_F + column,
                COLUMN_NAME_1_4_F + column,
                COLUMN_NAME_5_9_F + column,
                COLUMN_NAME_10_14_F + column,
                COLUMN_NAME_15_19_F + column,
                COLUMN_NAME_20_24_F + column,
                COLUMN_NAME_25_29_F + column,
                COLUMN_NAME_30_34_F + column,
                COLUMN_NAME_35_39_F + column,
                COLUMN_NAME_40_44_F + column,
                COLUMN_NAME_45_49_F + column,
                COLUMN_NAME_50_54_F + column,
                COLUMN_NAME_55_59_F + column,
                COLUMN_NAME_60_64_F + column,
                COLUMN_NAME_65_F + column,
                COLUMN_NAME_UK_F + column,
                COLUMN_NAME_Total_F + column,
        };

        return columns;
    }

    public static String[] genarateTotalColumnArrayNames(String column) {
        String[] totalColumns = {
                COLUMN_NAME_0_1_T + column,
                COLUMN_NAME_1_4_T + column,
                COLUMN_NAME_5_9_T + column,
                COLUMN_NAME_10_14_T + column,
                COLUMN_NAME_15_19_T + column,
                COLUMN_NAME_20_24_T + column,
                COLUMN_NAME_25_29_T + column,
                COLUMN_NAME_30_34_T + column,
                COLUMN_NAME_35_39_T + column,
                COLUMN_NAME_40_44_T + column,
                COLUMN_NAME_45_49_T + column,
                COLUMN_NAME_50_54_T + column,
                COLUMN_NAME_55_59_T + column,
                COLUMN_NAME_60_64_T + column,
                COLUMN_NAME_65_T + column,
                COLUMN_NAME_UK_T + column,
                COLUMN_NAME_Total_T + column
        };
        return totalColumns;
    }

    public static String[] genarateTotalColumnArrayNames() {
        String[] totalColumns = {
                COLUMN_NAME_0_1_T,
                COLUMN_NAME_1_4_T,
                COLUMN_NAME_5_9_T,
                COLUMN_NAME_10_14_T,
                COLUMN_NAME_15_19_T,
                COLUMN_NAME_20_24_T,
                COLUMN_NAME_25_29_T,
                COLUMN_NAME_30_34_T,
                COLUMN_NAME_35_39_T,
                COLUMN_NAME_40_44_T,
                COLUMN_NAME_45_49_T,
                COLUMN_NAME_50_54_T,
                COLUMN_NAME_55_59_T,
                COLUMN_NAME_60_64_T,
                COLUMN_NAME_65_T,
                COLUMN_NAME_UK_T,
                COLUMN_NAME_Total_T
        };
        return totalColumns;
    }

    public static String[] getAllColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("A"), genarateColumnArrayNames("B"));
        String[] set2 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("C"), genarateColumnArrayNames("D"));
        String[] set3 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("E"), genarateColumnArrayNames("F"));

        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, set2);
        //String[]  ConcanetSet2 = (String[]) ArrayUtils.addAll(set3 ,);
        String[] finalColumnsSet = (String[]) ArrayUtils.addAll(ConcanetSet1, set3);
        return finalColumnsSet;
    }

    public static String[] getAllCtxColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("N"), genarateTotalColumnArrayNames("N"));
        String[] set2 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("A"), genarateTotalColumnArrayNames("A"));

        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, set2);

        return ConcanetSet1;
    }

    public static final String[] COLUMNS_ARRAY_14_BY_17 = getAllColumnsArray();

    private static final String COLUMN_KEY_POPN_MSM = "MSM";

    private static final String COLUMN_KEY_POPN_SEX = "SP";

    private static final String COLUMN_KEY_POPN_TRANSGENDER = "TSG";

    private static final String COLUMN_KEY_POPN_CAPTIVES = "CP";

    private static final String COLUMN_KEY_POPN_DRUG = "DRUG";
    private static final String COLUMN_KEY_SPCM_FMEENC = "FMEENC";
    private static final String COLUMN_KEY_SPCM_FMEALT = "FMEALT";
    private static final String COLUMN_KEY_SCRN_SYMPT = "SCRMSYMPT";
    private static final String COLUMN_KEY_CXR_X_RAY = "CXRXRAY";
    private static final String COLUMN_KEY_PCR_MWRD = "PCRMWRD";

    /*private static final String COLUMN_KEY_POPN_TOTAL = "Total";*/

    public static String[] genarateKeyPopnColumnArrayNames(String column) {
        String[] KeyPonColumns = {
                COLUMN_KEY_POPN_MSM + column,
                COLUMN_KEY_POPN_SEX + column,
                COLUMN_KEY_POPN_TRANSGENDER + column,
                COLUMN_KEY_POPN_CAPTIVES + column,
                COLUMN_KEY_POPN_DRUG + column/*,
				COLUMN_KEY_POPN_TOTAL +column*/
        };
        return KeyPonColumns;
    }

    public static String[] genarateScreeneTbColumnArrayNames(String column) {
        String[] KeyColumns = {
                COLUMN_KEY_SCRN_SYMPT + column,
                COLUMN_KEY_CXR_X_RAY + column,
                COLUMN_KEY_PCR_MWRD + column
        };
        return KeyColumns;
    }

    public static String[] getAllKeyPopnColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateKeyPopnColumnArrayNames("N"), genarateKeyPopnColumnArrayNames("R"));
        return ConcanetSet1;
    }

    public static String[] getAllScreeneTbColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateScreeneTbColumnArrayNames("N"), genarateScreeneTbColumnArrayNames("R"));
        return ConcanetSet1;
    }

    public static final String[] KEY_POPN_COLUMNS_ARRAY = getAllKeyPopnColumnsArray();
    public static final String[] KEY_SCRN_COLUMNS_ARRAY = getAllScreeneTbColumnsArray();

    public static final String[] CTX_COLUMNS_ARRAY = getAllCtxColumnsArray();

    private static final String COLUMN_NAME_LS_15_M = "<15M";
    private static final String COLUMN_NAME_LS_14_M = "2-14M";
    private static final String COLUMN_NAME_LS_2_M = "<2M";

    private static final String COLUMN_NAME_GT_15_M = ">15M";
    private static final String COLUMN_NAME_GT_14_M = ">14M";

    private static final String COLUMN_NAME_LS_15_F = "<15F";
    private static final String COLUMN_NAME_LS_14_F = "2-14F";
    private static final String COLUMN_NAME_LS_2_F = "<2F";

    private static final String COLUMN_NAME_GT_15_F = ">15F";
    private static final String COLUMN_NAME_GT_14_F = ">14F";

    private static final String COLUMN_NAME_LS_15_T = "<15T";
    private static final String COLUMN_NAME_LS_2_T = "<2T";
    private static final String COLUMN_NAME_LS_14_T = "2-14T";

    private static final String COLUMN_NAME_GT_15_T = ">15T";
    private static final String COLUMN_NAME_GT_14_T = ">14T";


    public static String[] genarateGenderAndAgeColumnBy15ArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_LS_15_M + column,
                COLUMN_NAME_GT_15_M + column,
                COLUMN_NAME_UK_M + column,
                COLUMN_NAME_Total_M + column,
                COLUMN_NAME_LS_15_F + column,
                COLUMN_NAME_GT_15_F + column,
                COLUMN_NAME_UK_F + column,
                COLUMN_NAME_Total_F + column
        };
        return columns;
    }

    public static String[] genarateGenderAndAgeColumnBy14ArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_LS_14_M + column,
                COLUMN_NAME_GT_14_M + column,
                COLUMN_NAME_UK_M + column,
                COLUMN_NAME_Total_M + column,
                COLUMN_NAME_LS_14_F + column,
                COLUMN_NAME_GT_14_F + column,
                COLUMN_NAME_UK_F + column,
                COLUMN_NAME_Total_F + column
        };
        return columns;
    }

    public static String[] genarateGenderAndAgeColumnSuppBy14ArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_GT_14_M + column,
                COLUMN_NAME_UK_M + column,
                COLUMN_NAME_Total_M + column,
                COLUMN_NAME_GT_14_F + column,
                COLUMN_NAME_UK_F + column,
                COLUMN_NAME_Total_F + column
        };
        return columns;
    }

    public static String[] genarateGenderAndAgeColumnBy2ArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_LS_2_M + column,
                COLUMN_NAME_LS_2_F + column,
        };
        return columns;
    }

    public static String[] genarateTotalColumnBy15ArrayNames() {
        String[] columns = {
                COLUMN_NAME_LS_15_T,
                COLUMN_NAME_GT_15_T,
                COLUMN_NAME_UK_T,
                COLUMN_NAME_Total_T,
        };
        return columns;
    }

    public static String[] genarateTotalColumnBy2ArrayNames() {
        String[] columns = {
                COLUMN_NAME_LS_2_T,
        };
        return columns;
    }

    public static String[] genarateTotalColumnBy14ArrayNames() {
        String[] columns = {
                COLUMN_NAME_LS_14_T,
                COLUMN_NAME_GT_14_T,
                COLUMN_NAME_UK_T,
                COLUMN_NAME_Total_T,
        };
        return columns;
    }

    public static String[] getAllGenderAndAgeBy15ColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy15ArrayNames("A"), genarateGenderAndAgeColumnBy15ArrayNames("B"));
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeSupp15ColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnSuppBy14ArrayNames("A"), genarateGenderAndAgeColumnSuppBy14ArrayNames("B"));
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeBy2ColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy2ArrayNames("A"), genarateGenderAndAgeColumnBy2ArrayNames("B"));
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeBy14ColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy14ArrayNames("A"), genarateGenderAndAgeColumnBy14ArrayNames("B"));
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeBy15ColumnsArraySingleRow() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy15ArrayNames("A"), genarateTotalColumnBy15ArrayNames());
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeBy2ColumnsArraySingleRow() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy2ArrayNames("A"), genarateTotalColumnBy2ArrayNames());
        return ConcanetSet1;
    }

    public static String[] getAllGenderAndAgeBy14ColumnsArraySingleRow() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy14ArrayNames("A"), genarateTotalColumnBy14ArrayNames());
        return ConcanetSet1;
    }

    public static final String[] COLUMNS_ARRAY_AGE_BY_15 = getAllGenderAndAgeBy15ColumnsArray();
    public static final String[] COLUMNS_ARRAY_AGE_SUPP_15 = getAllGenderAndAgeSupp15ColumnsArray();
    public static final String[] COLUMNS_ARRAY_AGE_BY_2 = getAllGenderAndAgeBy2ColumnsArray();
    public static final String[] COLUMNS_ARRAY_AGE_BY_14 = getAllGenderAndAgeBy14ColumnsArray();

    public static final String[] COLUMNS_ARRAY_4BY3 = getAllGenderAndAgeBy15ColumnsArraySingleRow();
    public static final String[] COLUMNS_ARRAY_3BY3 = getAllGenderAndAgeBy2ColumnsArraySingleRow();
    public static final String[] COLUMNS_ARRAY_2BY14 = getAllGenderAndAgeBy14ColumnsArraySingleRow();

    private static final String COLUMN_NAME_CRACHAT_TB_DIAGNOSIS_TEST = "CT";
    private static final String COLUMN_NAME_GENEEXPERT_TB_DIAGNOSIS_TEST = "GE";
    private static final String COLUMN_NAME_OTHER_TB_DIAGNOSIS_TEST = "OT";

    public static final String[] COLUMNS_ARRAY_ARV_PATIENTS_BY_TB_DIAGNOSIS_TEST = {
            COLUMN_NAME_CRACHAT_TB_DIAGNOSIS_TEST,
            COLUMN_NAME_GENEEXPERT_TB_DIAGNOSIS_TEST,
            COLUMN_NAME_OTHER_TB_DIAGNOSIS_TEST
    };


    public static String[] getAll15By4ColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("N"), genarateColumnArrayNames("A"));
        return set1;
    }

    public static final String[] COLUMNS_ARRAY_17_BY_4 = getAll15By4ColumnsArray();

    public static final String[] COLUMNS_ARRAY_KEY_POPN_SINGLE_ROW = {
            COLUMN_KEY_POPN_MSM,
            COLUMN_KEY_POPN_SEX,
            COLUMN_KEY_POPN_TRANSGENDER,
            COLUMN_KEY_POPN_CAPTIVES,
            COLUMN_KEY_POPN_DRUG,
    };

    public static final String[] COLUMNS_ARRAY_SPECIMEN_SINGLE_ROW = {
            COLUMN_KEY_POPN_MSM,
            COLUMN_KEY_POPN_SEX,
            COLUMN_KEY_POPN_TRANSGENDER,
            COLUMN_KEY_POPN_CAPTIVES,
            COLUMN_KEY_POPN_DRUG,
            COLUMN_KEY_SPCM_FMEENC,
            COLUMN_KEY_SPCM_FMEALT
    };

    public static final String[] COLUMNS_ARRAY_KEY_SCREENTB_SINGLE_ROW = {
            COLUMN_KEY_SCRN_SYMPT,
            COLUMN_KEY_CXR_X_RAY,
            COLUMN_KEY_PCR_MWRD,
    };

    public static String[] getAllGenderAndAgeBy15For6RowsColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(genarateGenderAndAgeColumnBy15ArrayNames("A"), genarateGenderAndAgeColumnBy15ArrayNames("B"));
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(ConcanetSet1, genarateGenderAndAgeColumnBy15ArrayNames("C"));
        return ConcanetSet2;
    }

    public static final String[] COLUMNS_ARRAY_4By7 = getAllGenderAndAgeBy15For6RowsColumnsArray();

    public static String[] getAllRegimenLinesColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("1"), genarateTotalColumnArrayNames("1"));
        String[] set2 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("2"), genarateTotalColumnArrayNames("2"));
        String[] set3 = (String[]) ArrayUtils.addAll(genarateColumnArrayNames("3"), genarateTotalColumnArrayNames("3"));

        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, set2);
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(ConcanetSet1, set3);

        return ConcanetSet2;
    }

    public static final String[] COLUMNS_ARRAY_ACTIVE_PATIENTS_BY_REGIME = getAllRegimenLinesColumnsArray();

    private static final String COLUMN_NAME_15_19 = "15-19";

    private static final String COLUMN_NAME_20_24 = "20-24";

    private static final String COLUMN_NAME_25_29 = "25-29";

    private static final String COLUMN_NAME_30_34 = "30-34";

    private static final String COLUMN_NAME_35_39 = "35-39";

    private static final String COLUMN_NAME_40_44 = "40-44";

    private static final String COLUMN_NAME_45_49 = "45-49";
    private static final String COLUMN_NAME_50_54 = "50-54";
    private static final String COLUMN_NAME_55_59 = "55-59";
    private static final String COLUMN_NAME_60_64 = "60-64";

    private static final String COLUMN_NAME_65 = "65";

    private static final String COLUMN_NAME_UK = "UnKnown";

    private static final String COLUMN_NAME_Total = "Total";


    public static String[] genarateCervicalCancerStatusColumns(String column) {
        String[] columns = {
                COLUMN_NAME_15_19 + column,
                COLUMN_NAME_20_24 + column,
                COLUMN_NAME_25_29 + column,
                COLUMN_NAME_30_34 + column,
                COLUMN_NAME_35_39 + column,
                COLUMN_NAME_40_44 + column,
                COLUMN_NAME_45_49 + column,
                COLUMN_NAME_50_54 + column,
                COLUMN_NAME_55_59 + column,
                COLUMN_NAME_60_64 + column,
                COLUMN_NAME_65 + column,
                COLUMN_NAME_UK + column,
                COLUMN_NAME_Total + column,
        };
        return columns;
    }


    public static String[] getAllCervicalCancerStatusColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateCervicalCancerStatusColumns("NEG"), genarateCervicalCancerStatusColumns("POS"));
        String[] set2 = (String[]) ArrayUtils.addAll(genarateCervicalCancerStatusColumns("SUS"), genarateCervicalCancerStatusColumns("T"));
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, set2);
        return ConcanetSet1;
    }

    public static final String[] COLUMNS_ARRAY_CERVICAL_CANCER_STATUS = getAllCervicalCancerStatusColumnsArray();

    public static String[] getAllCervicalCancerTretmentColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateCervicalCancerStatusColumns("1"), genarateCervicalCancerStatusColumns("2"));
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, genarateCervicalCancerStatusColumns("3"));
        return ConcanetSet1;
    }

    public static final String[] COLUMNS_ARRAY_CERVICAL_CANCER_TREATMENT = getAllCervicalCancerTretmentColumnsArray();

    public static String[] genarateFamilyPlanningColumns(String gender, String column) {
        String[] columns = {
                COLUMN_NAME_15_19 + gender + column,
                COLUMN_NAME_20_24 + gender + column,
                COLUMN_NAME_25_29 + gender + column,
                COLUMN_NAME_30_34 + gender + column,
                COLUMN_NAME_35_39 + gender + column,
                COLUMN_NAME_40_44 + gender + column,
                COLUMN_NAME_45_49 + gender + column,
                COLUMN_NAME_50_54 + gender + column,
                COLUMN_NAME_55_59 + gender + column,
                COLUMN_NAME_60_64 + gender + column,
                COLUMN_NAME_65 + gender + column,
                COLUMN_NAME_UK + gender + column,
                COLUMN_NAME_Total + gender + column,
        };
        return columns;
    }

    public static String[] getAllFamilyPlanningColumnsArray() {
        String[] set1 = (String[]) ArrayUtils.addAll(genarateFamilyPlanningColumns("F", "A"), genarateFamilyPlanningColumns("F", "B"));
        String[] set2 = (String[]) ArrayUtils.addAll(genarateFamilyPlanningColumns("F", "C"), genarateFamilyPlanningColumns("F", "D"));
        String[] set3 = (String[]) ArrayUtils.addAll(genarateFamilyPlanningColumns("F", "E"), genarateFamilyPlanningColumns("F", "F"));
        String[] set4 = (String[]) ArrayUtils.addAll(genarateFamilyPlanningColumns("M", "G"), genarateFamilyPlanningColumns("F", "H"));
        String[] set5 = (String[]) ArrayUtils.addAll(genarateFamilyPlanningColumns("M", "I"), genarateFamilyPlanningColumns("F", "J"));

        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(set1, set2);
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(set3, set4);
        String[] ConcanetSet3 = (String[]) ArrayUtils.addAll(ConcanetSet1, ConcanetSet2);
        String[] ConcanetSet4 = (String[]) ArrayUtils.addAll(ConcanetSet3, set5);
        return ConcanetSet4;
    }

    public static final String[] COLUMNS_ARRAY_FAMILY_PLANNING = getAllFamilyPlanningColumnsArray();
}
