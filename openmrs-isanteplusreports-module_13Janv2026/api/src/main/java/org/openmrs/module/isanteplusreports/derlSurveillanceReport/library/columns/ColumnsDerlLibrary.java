package org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.columns;

import org.apache.commons.lang.ArrayUtils;

public class ColumnsDerlLibrary {

    private static final String COLUMN_NAME_1 = "1";
    private static final String COLUMN_NAME_2 = "2";
    private static final String COLUMN_NAME_3 = "3";
    private static final String COLUMN_NAME_4 = "4";
    private static final String COLUMN_NAME_5 = "5";
    private static final String COLUMN_NAME_6 = "6";
    private static final String COLUMN_NAME_7 = "7";
    private static final String COLUMN_NAME_8 = "8";
    private static final String COLUMN_NAME_9 = "9";
    private static final String COLUMN_NAME_10 = "10";
    private static final String COLUMN_NAME_11 = "11";
    private static final String COLUMN_NAME_12 = "12";

    private static final String COLUMN_NAME_13 = "13";
    private static final String COLUMN_NAME_14 = "14";
    private static final String COLUMN_NAME_15 = "15";
    private static final String COLUMN_NAME_16 = "16";
    private static final String COLUMN_NAME_17 = "17";
    private static final String COLUMN_NAME_18 = "18";
    private static final String COLUMN_NAME_19 = "19";
    private static final String COLUMN_NAME_20 = "20";
    private static final String COLUMN_NAME_21 = "21";
    private static final String COLUMN_NAME_22 = "22";
    private static final String COLUMN_NAME_23 = "23";
    private static final String COLUMN_NAME_24 = "24";

    private static final String COLUMN_NAME_25 = "25";
    private static final String COLUMN_NAME_26 = "26";
    private static final String COLUMN_NAME_27 = "27";
    private static final String COLUMN_NAME_28 = "28";
    private static final String COLUMN_NAME_29 = "29";
    private static final String COLUMN_NAME_30 = "30";
    private static final String COLUMN_NAME_31 = "31";
    private static final String COLUMN_NAME_32 = "32";
    private static final String COLUMN_NAME_33 = "33";
    private static final String COLUMN_NAME_34 = "34";
    private static final String COLUMN_NAME_35 = "35";
    private static final String COLUMN_NAME_36 = "36";

    private static final String COLUMN_NAME_37 = "37";
    private static final String COLUMN_NAME_38 = "38";
    private static final String COLUMN_NAME_39 = "39";
    private static final String COLUMN_NAME_40 = "40";
    private static final String COLUMN_NAME_41 = "41";
    private static final String COLUMN_NAME_42 = "42";
    private static final String COLUMN_NAME_43 = "43";
    private static final String COLUMN_NAME_44 = "44";
    private static final String COLUMN_NAME_45 = "45";
    private static final String COLUMN_NAME_46 = "46";
    private static final String COLUMN_NAME_47 = "47";
    private static final String COLUMN_NAME_48 = "48";

    private static final String COLUMN_NAME_49 = "49";
    private static final String COLUMN_NAME_50 = "50";
    private static final String COLUMN_NAME_51 = "51";
    private static final String COLUMN_NAME_52 = "52";
    private static final String COLUMN_NAME_53 = "43";

    private static final String COLUMN_NAME_TOTAL = "Total";


    public static final String[] COLUMNS_ARRAY_WEEKLY = {
            COLUMN_NAME_1,
            COLUMN_NAME_2,
            COLUMN_NAME_3,
            COLUMN_NAME_4,
            COLUMN_NAME_5,
            COLUMN_NAME_6,
            COLUMN_NAME_7,
            COLUMN_NAME_8,
            COLUMN_NAME_9,
            COLUMN_NAME_10,
            COLUMN_NAME_11,
            COLUMN_NAME_12,
            COLUMN_NAME_13,
            COLUMN_NAME_14,
            COLUMN_NAME_15,
            COLUMN_NAME_16,
            COLUMN_NAME_17,
            COLUMN_NAME_18,
            COLUMN_NAME_19,
            COLUMN_NAME_20,
            COLUMN_NAME_21,
            COLUMN_NAME_22,
            COLUMN_NAME_23,
            COLUMN_NAME_24,
            COLUMN_NAME_25,
            COLUMN_NAME_26,
            COLUMN_NAME_27,
            COLUMN_NAME_28,
            COLUMN_NAME_29,
            COLUMN_NAME_30,
            COLUMN_NAME_31,
            COLUMN_NAME_32,
            COLUMN_NAME_33,
            COLUMN_NAME_34,
            COLUMN_NAME_35,
            COLUMN_NAME_36,
            COLUMN_NAME_37,
            COLUMN_NAME_38,
            COLUMN_NAME_39,
            COLUMN_NAME_40,
            COLUMN_NAME_41,
            COLUMN_NAME_42,
            COLUMN_NAME_43,
            COLUMN_NAME_44,
            COLUMN_NAME_45,
            COLUMN_NAME_46,
            COLUMN_NAME_47,
            COLUMN_NAME_48,
            COLUMN_NAME_49,
            COLUMN_NAME_50,
            COLUMN_NAME_51,
            COLUMN_NAME_52,
            COLUMN_NAME_53,
            COLUMN_NAME_TOTAL
    };

    private static final String COLUMN_NAME_BF = "BF";

    public static final String[] COULUMNS_ARRAY_1_BY_1 = {COLUMN_NAME_BF};


    public static String[] generateColumnArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_1 + column,
                COLUMN_NAME_2 + column,
                COLUMN_NAME_3 + column,
                COLUMN_NAME_4 + column,
                COLUMN_NAME_5 + column,
                COLUMN_NAME_6 + column,
                COLUMN_NAME_7 + column,
                COLUMN_NAME_8 + column,
                COLUMN_NAME_9 + column,
                COLUMN_NAME_10 + column,
                COLUMN_NAME_11 + column,
                COLUMN_NAME_12 + column,
                COLUMN_NAME_13 + column,
                COLUMN_NAME_14 + column,
                COLUMN_NAME_15 + column,
                COLUMN_NAME_16 + column,
                COLUMN_NAME_17 + column,
                COLUMN_NAME_18 + column,
                COLUMN_NAME_19 + column,
                COLUMN_NAME_20 + column,
                COLUMN_NAME_21 + column,
                COLUMN_NAME_22 + column,
                COLUMN_NAME_23 + column,
                COLUMN_NAME_24 + column,
                COLUMN_NAME_25 + column,
                COLUMN_NAME_26 + column,
                COLUMN_NAME_27 + column,
                COLUMN_NAME_28 + column,
                COLUMN_NAME_29 + column,
                COLUMN_NAME_30 + column,
                COLUMN_NAME_31 + column,
                COLUMN_NAME_32 + column,
                COLUMN_NAME_33 + column,
                COLUMN_NAME_34 + column,
                COLUMN_NAME_35 + column,
                COLUMN_NAME_36 + column,
                COLUMN_NAME_37 + column,
                COLUMN_NAME_38 + column,
                COLUMN_NAME_39 + column,
                COLUMN_NAME_40 + column,
                COLUMN_NAME_41 + column,
                COLUMN_NAME_42 + column,
                COLUMN_NAME_43 + column,
                COLUMN_NAME_44 + column,
                COLUMN_NAME_45 + column,
                COLUMN_NAME_46 + column,
                COLUMN_NAME_47 + column,
                COLUMN_NAME_48 + column,
                COLUMN_NAME_49 + column,
                COLUMN_NAME_50 + column,
                COLUMN_NAME_51 + column,
                COLUMN_NAME_52 + column,
                COLUMN_NAME_53 + column,
                COLUMN_NAME_TOTAL + column,
        };

        return columns;
    }

    public static String[] getAllImmediateColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("N"),
                generateColumnArrayNames("R"));
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("C"),
                generateColumnArrayNames("D"));

        String[] ConcanetSet3 = (String[]) ArrayUtils.addAll(ConcanetSet1,
                ConcanetSet2);


        String[] ConcanetSet4 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("E"),
                generateColumnArrayNames("F"));
        String[] ConcanetSet5 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("G"),
                generateColumnArrayNames("H"));

        String[] ConcanetSet6 = (String[]) ArrayUtils.addAll(ConcanetSet4,
                ConcanetSet5);

        String[] ConcanetSet7 = (String[]) ArrayUtils.addAll(ConcanetSet3,
                ConcanetSet6);

        String[] ConcanetSet8 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("I"),
                generateColumnArrayNames("J"));

        String[] ConcanetSet9 = (String[]) ArrayUtils.addAll(ConcanetSet7,
                ConcanetSet8);

        String[] ConcanetSet10 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("K"),
                generateColumnArrayNames("L"));
        String[] ConcanetSet11 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("M"),
                generateColumnArrayNames("O"));

        String[] ConcanetSet12 = (String[]) ArrayUtils.addAll(ConcanetSet10,
                ConcanetSet11);

        String[] ConcanetSet13 = (String[]) ArrayUtils.addAll(ConcanetSet9,
                ConcanetSet12);

        String[] ConcanetSet14 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("P"),
                generateColumnArrayNames("Q"));
        String[] ConcanetSet15 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("S"),
                generateColumnArrayNames("T"));

        String[] ConcanetSet16 = (String[]) ArrayUtils.addAll(ConcanetSet14,
                ConcanetSet15);

        String[] ConcanetSet17 = (String[]) ArrayUtils.addAll(ConcanetSet13,
                ConcanetSet16);

        return ConcanetSet17;
    }

    public static final String[] IMMEDIATE_COLUMNS_ARRAY = getAllImmediateColumnsArray();


    public static String[] getAllWeeklyColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WN"),
                generateColumnArrayNames("WR"));
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WC"),
                generateColumnArrayNames("WD"));

        String[] ConcanetSet3 = (String[]) ArrayUtils.addAll(ConcanetSet1,
                ConcanetSet2);

        String[] ConcanetSet4 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WE"),
                generateColumnArrayNames("WF"));
        String[] ConcanetSet5 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WG"),
                generateColumnArrayNames("WH"));

        String[] ConcanetSet6 = (String[]) ArrayUtils.addAll(ConcanetSet4,
                ConcanetSet5);

        String[] ConcanetSet7 = (String[]) ArrayUtils.addAll(ConcanetSet3,
                ConcanetSet6);

        String[] ConcanetSet8 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WI"),
                generateColumnArrayNames("WJ"));
        String[] ConcanetSet9 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WK"),
                generateColumnArrayNames("WL"));

        String[] ConcanetSet10 = (String[]) ArrayUtils.addAll(ConcanetSet8,
                ConcanetSet9);

        String[] ConcanetSet11 = (String[]) ArrayUtils.addAll(ConcanetSet7,
                ConcanetSet10);

        String[] ConcanetSet12 = (String[]) ArrayUtils.addAll(generateColumnArrayNames("WM"),
                generateColumnArrayNames("WO"));

        String[] ConcanetSet13 = (String[]) ArrayUtils.addAll(ConcanetSet11,
                ConcanetSet12);


        return ConcanetSet13;
    }

    public static final String[] WEEKLY_COLUMNS_ARRAY = getAllWeeklyColumnsArray();

    private static final String COLUMN_NAME_JANVIER = "1";
    private static final String COLUMN_NAME_FEVRIER = "2";
    private static final String COLUMN_NAME_MARS = "3";
    private static final String COLUMN_NAME_AVRIL = "4";
    private static final String COLUMN_NAME_MAI = "5";
    private static final String COLUMN_NAME_JUIN = "6";
    private static final String COLUMN_NAME_JUILLET = "7";
    private static final String COLUMN_NAME_AOUT = "8";
    private static final String COLUMN_NAME_SEPTEMBRE = "9";
    private static final String COLUMN_NAME_OCTOBRE = "10";
    private static final String COLUMN_NAME_NOVEMBRE = "11";
    private static final String COLUMN_NAME_DECEMBRE = "12";

    private static final String COLUMN_NAME_MONTH_TOTAL = "Total";

    public static final String[] COLUMNS_ARRAY_MONTHLY = {
            COLUMN_NAME_JANVIER,
            COLUMN_NAME_FEVRIER,
            COLUMN_NAME_MARS,
            COLUMN_NAME_AVRIL,
            COLUMN_NAME_MAI,
            COLUMN_NAME_JUIN,
            COLUMN_NAME_JUILLET,
            COLUMN_NAME_AOUT,
            COLUMN_NAME_SEPTEMBRE,
            COLUMN_NAME_OCTOBRE,
            COLUMN_NAME_NOVEMBRE,
            COLUMN_NAME_DECEMBRE,
            COLUMN_NAME_MONTH_TOTAL
    };

    public static String[] generateColumnMonthlyArrayNames(String column) {
        String[] columns = {
                COLUMN_NAME_JANVIER + column,
                COLUMN_NAME_FEVRIER + column,
                COLUMN_NAME_MARS + column,
                COLUMN_NAME_AVRIL + column,
                COLUMN_NAME_MAI + column,
                COLUMN_NAME_JUIN + column,
                COLUMN_NAME_JUILLET + column,
                COLUMN_NAME_AOUT + column,
                COLUMN_NAME_SEPTEMBRE + column,
                COLUMN_NAME_OCTOBRE + column,
                COLUMN_NAME_NOVEMBRE + column,
                COLUMN_NAME_DECEMBRE + column,
                COLUMN_NAME_MONTH_TOTAL + column,
        };

        return columns;
    }

    public static String[] getAllMonthlyColumnsArray() {
        String[] ConcanetSet1 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("MN"),
                generateColumnMonthlyArrayNames("MR"));
        String[] ConcanetSet2 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("MC"),
                generateColumnMonthlyArrayNames("MD"));

        String[] ConcanetSet3 = (String[]) ArrayUtils.addAll(ConcanetSet1,
                ConcanetSet2);


        String[] ConcanetSet4 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("ME"),
                generateColumnMonthlyArrayNames("MF"));
        String[] ConcanetSet5 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("MG"),
                generateColumnMonthlyArrayNames("MH"));

        String[] ConcanetSet6 = (String[]) ArrayUtils.addAll(ConcanetSet4,
                ConcanetSet5);

        String[] ConcanetSet7 = (String[]) ArrayUtils.addAll(ConcanetSet3,
                ConcanetSet6);


        String[] ConcanetSet8 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("MI"),
                generateColumnMonthlyArrayNames("MJ"));
        String[] ConcanetSet9 = (String[]) ArrayUtils.addAll(generateColumnMonthlyArrayNames("MK"),
                generateColumnMonthlyArrayNames("ML"));

        String[] ConcanetSet10 = (String[]) ArrayUtils.addAll(ConcanetSet8,
                ConcanetSet9);


        String[] ConcanetSet11 = (String[]) ArrayUtils.addAll(ConcanetSet7,
                ConcanetSet10);

        String[] ConcanetSet12 = (String[]) ArrayUtils.addAll(ConcanetSet11,
                generateColumnMonthlyArrayNames("MM"));


        return ConcanetSet12;
    }

    public static final String[] MONTHLY_COLUMNS_ARRAY = getAllMonthlyColumnsArray();


}
