package org.openmrs.module.isanteplusreports.comorbidity;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.COMORBIDITY_RESOURCE_PATH;

public class ComorbidityReportsConstants {

    public static final String COMORBIDITY_REPORTS_RESOURCE_PATH = COMORBIDITY_RESOURCE_PATH;

    public static final String COMORBIDITY_REPORTS_PATIENT_LIST_SQL = "patient_summary_list.sql";

    // Prévalence de l’Hypertension artérielle dans la population de l’établissement (SSP et VIH)
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_UUID = "f7884a17-f9cc-481f-b355-ac7534e6c17e";
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.indicator.hypertension.arterielle.label";
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_NUM_SQL = "hypertension_art_nume.sql";
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_DEN_SQL = "hypertension_art_deno.sql";

    // Prévalence du diabète de type 1 dans la population fréquentant l’établissement
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_UUID = "bafae732-c30c-4a32-b78f-b3fae426b337";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.indicator.diabete_1.arterielle.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_NUM_SQL = "hypertension_art_nume.sql";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_DEN_SQL = "hypertension_art_deno.sql";

    // Prévalence du diabète de type 2 dans la population fréquentant l’établissement
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_UUID = "3648a2c3-a8f9-4ba8-8fab-80ee91ec952f";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.indicator.diabete_2.arterielle.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_NUM_SQL = "hypertension_art_nume.sql";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_DEN_SQL = "hypertension_art_deno.sql";

     // Prévalence du diabète de type 2 dans la population fréquentant l’établissement
    public static final String PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_UUID = "6aad97f8-61bf-4a03-a8a1-d9cd91fb84b3";
    public static final String PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.indicator.cancer.label";
    public static final String PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_NUM_SQL = "hypertension_art_nume.sql";
    public static final String PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_DEN_SQL = "hypertension_art_deno.sql";



}
