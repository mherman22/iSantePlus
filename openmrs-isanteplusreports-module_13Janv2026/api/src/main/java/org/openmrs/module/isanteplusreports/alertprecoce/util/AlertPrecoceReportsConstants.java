package org.openmrs.module.isanteplusreports.alertprecoce.util;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.REPORTS_SQL_PATH;

public class AlertPrecoceReportsConstants {

    static final String ALERT_PRECOCE_REPORTS_RESOURCE_PATH = REPORTS_SQL_PATH + "alertePrecoceReports/";

    // SQL files
    static final String NUMBER_PATIENTS_SQL = "numberOfPatients.sql";


    // indicators' UUIDs
    public static final String NUMBER_PATIENTS_UUID = "956494eb-e546-48ff-91b7-ff3ae3046713";

    //Misc
    public static final String ALERT_PRECOCE_GENERAL_PURPOSE_SUFFIX = "_AP";

    public static final String ALERT_PRECOCE_INDICATOR_1_UUID = "ffea8cf5-0705-43e1-bda8-1212361b5837";
    public static final String TIMELYWITHDRAWALART_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.retrait_a_temps_arv.label";
    public static final String TIMELYWITHDRAWALART_NUM_INDICATOR_SQL = "timelywithdrawalart_num.sql";
    public static final String TIMELYWITHDRAWALART_DEN_INDICATOR_SQL = "timelywithdrawalart_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_2_UUID = "13e6b765-b108-4ef1-a575-48dcb8f3882f";
    public static final String RETENTIONONART_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.retention_tar.label";
    public static final String RETENTIONONART_NUM_INDICATOR_SQL = "retentionontar_num.sql";
    public static final String RETENTIONONART_DEN_INDICATOR_SQL = "retentionontar_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_3_UUID = "294f299f-2358-40f3-bc41-ea088c93f8bc";
    public static final String RUPTURESTOCK_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.rupture_stock.label";
    public static final String RUPTURESTOCK_NUM_INDICATOR_SQL = "rupturestock_num.sql";
    public static final String RUPTURESTOCK_DEN_INDICATOR_SQL = "rupturestock_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_4_UUID = "751900db-1bcd-4d17-aae3-9f4f69f76b82";
    public static final String VIRALLOADSUPPRESSION_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.viral_load_suppression.label";
    public static final String VIRALLOADSUPPRESSION_NUM_INDICATOR_SQL = "viralloadsuppression_num.sql";
    public static final String VIRALLOADSUPPRESSION_DEN_INDICATOR_SQL = "viralloadsuppression_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_5_UUID = "4ba94f1b-efa6-430d-b1f1-6f7fc26baf9d";
    public static final String VIRALLOADCOMPLETION_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.viral_load_process_completion.label";
    public static final String VIRALLOADCOMPLETION_NUM_INDICATOR_SQL = "viralloadprocesscompletion_num.sql";
    public static final String VIRALLOADCOMPLETION_DEN_INDICATOR_SQL = "viralloadprocesscompletion_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_6_UUID = "b29f22c1-0db0-41e0-b8f4-e428a6806a4c";
    public static final String APPROPRIATECHANGE_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.appropriate_change.label";
    public static final String APPROPRIATECHANGE_NUM_INDICATOR_SQL = "appropriatechange_num.sql";
    public static final String APPROPRIATECHANGE_DEN_INDICATOR_SQL = "appropriatechange_den.sql";


    public static final String ALERT_PRECOCE_INDICATOR_7_UUID = "01064e83-03ad-4dd9-9564-0ffec86c9b66";
    public static final String TREATMENTINTERRUPTION_INDICATOR_MESSAGE = "isanteplusreports.alertprecoce.treatment_interruption.label";
    public static final String TREATMENTINTERRUPTION_NUM_INDICATOR_SQL = "treatmentinterruption_num.sql";
    public static final String TREATMENTINTERRUPTION_DEN_INDICATOR_SQL = "treatmentinterruption_den.sql";

    public static final String ALERT_PRECOCE_INDICATOR_8_UUID = "7f76d7be-d792-4f08-8081-92e5204dd1a2";

    /*ARV distribution indicators*/
    public static final String APPOINTMENT_PERIOD_INDICATOR_UUID = "0b5ac4c8-5659-4952-9693-3ecfe1bbc6b7";
    public static final String APPOINTMENT_PERIOD_INDICATOR_MESSAGE = "isanteplusreports.artdistribution.appointmentperiod.message";
    public static final String APPOINTMENT_PERIOD_INDICATOR_SQL = "appointmentperiod.sql";

    public static final String DISPENSINGART_PERIOD_INDICATOR_UUID = "a7139b90-9a85-4dfa-919c-948706face65";
    public static final String DISPENSINGART_PERIOD_INDICATOR_MESSAGE = "isanteplusreports.artdistribution.arvdispense.message";
    public static final String DISPENSINGART_PERIOD_INDICATOR_SQL = "dispensingartperiod.sql";

    public static final String NOTTAKINGART_PERIOD_INDICATOR_UUID = "0a272874-2160-4c78-a7c4-95694e50c8f7";
    public static final String NOTTAKINGART_PERIOD_INDICATOR_MESSAGE = "isanteplusreports.artdistribution.nontakingarv.message";
    public static final String NOTTAKINGART_PERIOD_INDICATOR_NUM_SQL = "nottakingart_num.sql";
    public static final String NOTTAKINGART_PERIOD_INDICATOR_DEN_SQL = "nottakingart_den.sql";


    public static final String TAKINGART_PERIOD_INDICATOR_UUID = "36a61eda-79fe-431a-8189-86b12a859c9e";
    public static final String TAKINGART_PERIOD_INDICATOR_MESSAGE = "isanteplusreports.artdistribution.takingarv.message";
    public static final String TAKINGART_PERIOD_INDICATOR_NUM_SQL = "takingart_num.sql";
    public static final String TAKINGART_PERIOD_INDICATOR_DEN_SQL = "takingart_den.sql";


    /*PsychoSocial*/
    public static final String ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_UUID = "d121bd5b-d267-4018-be9d-119ed694382a";
    public static final String ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientwithAssPsycho.label";
    public static final String ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_NUM_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";
    public static final String ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_DEN_SQL = "activePatientOnArvHavingPsychosocialAssessment_den.sql";

    public static final String ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_UUID = "9ccb0880-de74-4e55-9994-8c57427be260";
    public static final String ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientwithAssPsychoIncomplete.label";
    public static final String ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_NUM_SQL = "activePatientWithIncompletePsychosocialAssessment_num.sql";
    public static final String ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_DEN_SQL = "activePatientWithIncompletePsychosocialAssessment_den.sql";

    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_UUID = "6d349d95-9279-4dfe-a039-558ede8ce98b";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientwithAssPsychoDiagnosis.label";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_NUM_SQL = "activePatientWithPsychosocialDiagnoses_num.sql";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_DEN_SQL = "activePatientWithPsychosocialDiagnoses_den.sql";

    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_UUID = "dfe8f920-f2e2-4d46-ac54-af341cf8aad9";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithDiagnosisPsychoResolve.label";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_NUM_SQL = "activePatientWithPsychosocialDiagnosesResolve_num.sql";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_DEN_SQL = "activePatientWithPsychosocialDiagnosesResolve_den.sql";

    public static final String ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_UUID = "c33735a7-e89c-42c8-a483-cbb95eb2a913";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithPsychoFollowUp.label";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_NUM_SQL = "activePatientWithPsychosocialFollowUp_num.sql";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_DEN_SQL = "activePatientWithPsychosocialFollowUp_den.sql";

    public static final String ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_UUID = "a5b4b5c2-589b-4254-b3bd-0ced0c80fac2";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithPsychoSocialNeeds.label";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_NUM_SQL = "activePatientWithPsychosocialNeeds_num.sql";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_DEN_SQL = "activePatientWithPsychosocialNeeds_den.sql";

    public static final String ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_UUID = "9f70ba23-1558-4ad4-a7a8-25e38b326eb5";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithPsychoAfterViralLoadSupMille.label";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_NUM_SQL = "activePatientPsychoAfterViralLoadSupMille_num.sql";
    public static final String ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_DEN_SQL = "activePatientPsychoAfterViralLoadSupMille_den.sql";

    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_UUID = "5e3d71fe-16f2-45ee-b761-2bc57643e539";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithViralLoadSupMilleWiththreePsychoAtTime.label";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_NUM_SQL = "activePatientWithViralLoadSupMilleWithThreePsycho_num.sql";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_DEN_SQL = "activePatientWithViralLoadSupMilleWithThreePsycho_den.sql";

    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_UUID = "88cf2a8d-c4c0-4ae7-aff7-d452006a61ab";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.activePatientWithViralLoadSupMilleWithPsychoAtTime.label";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_NUM_SQL = "activePatientWithViralLoadSupMilleWithPsychoAtTime_num.sql";
    public static final String ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_DEN_SQL = "activePatientWithViralLoadSupMilleWithPsychoAtTime_den.sql";

    public static final String NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_UUID = "89b930b2-5f60-4501-9607-12b9c46fb087";
    public static final String NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_MESSAGE = "isanteplusreports.psychosocial.indicator.newActivePatientWithPsychoEvaluation.label";
    public static final String NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_NUM_SQL = "newActivePatientWithPsychoAssessment_num.sql";
    public static final String NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENTINDICATOR_DEN_SQL = "newActivePatientWithPsychoAssessment_den.sql";


    /*Constant for FingerPrint report*/
    public static final String ACTIVE_PATIENT_WITH_FINGER_PRINT_UUID = "80dc287b-9da3-45fe-b136-178cd547cbbf";
    public static final String ACTIVE_PATIENT_WITH_FINGER_PRINT_MESSAGE = "isanteplusreports.fingerprint.indicator.activePatientWithFingerPrint";
    public static final String ACTIVE_PATIENT_WITH_FINGER_PRINT_NUM_SQL = "activePatientWithFingerPrint_num.sql";
    public static final String ACTIVE_PATIENT_WITH_FINGER_PRINT_DEN_SQL = "activePatientWithFingerPrint_den.sql";

    public static final String ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_UUID = "b015b45c-8af9-47dd-bbb0-5530083268c4";
    public static final String ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_MESSAGE = "isanteplusreports.fingerprint.indicator.activePatientWithoutFingerPrint";
    public static final String ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_NUM_SQL = "activePatientWithoutFingerPrint_num.sql";


    /*Constants for transitioned patient from Pediatric to Adult*/
    public static final String TRANSISIONED_PATIENT_FROM_PEDIATRIC_TO_ADULT_UUID = "49d1312e-eef5-49ae-8e62-15321227e25a";
    public static final String TRANSISIONED_PATIENT_FROM_PEDIATRIC_TO_ADULT_MESSAGE = "isanteplusreports.report.transitionedPatient";
    public static final String TRANSISIONED_PATIENT_NUM_SQL = "transitionedPatient_num.sql";
    public static final String TRANSISIONED_PATIENT_DEN_SQL = "transitionedPatient_den.sql";


    /* Constants vitalStatistics */
    public static final String VITAL_STATISTICS_UUID = "501e869e-f562-49da-8e3b-cb112f42837b";
    public static final String VITAL_STATISTICS_MESSAGE = "isanteplusreports.report.vitalStatistics";
    public static final String VITAL_STATISTICS_NUM_SQL = "vitalStatistics_num.sql";
    public static final String VITAL_STATISTICS_DEN_SQL = "vitalStatistics_den.sql";


    /**-------------------------------------------------------Comorbidity----------------------------------------------------*/
    public static final String PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_UUID = "cd229fb0-f34f-4f0d-b021-429eddbdf109";
    public static final String PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.hta.indicator.prevalence.label";
    public static final String PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";

    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_UUID = "1dd19826-08be-4693-bc4d-404e2ecf53d9";
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.hta.indicator.hypertensive.label";
    public static final String PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";

    public static final String PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_UUID = "80e935af-f244-4358-a0b0-77bea8b2724a";
    public static final String PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.hta.indicator.antihypertensive.label";
    public static final String PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_NUM_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";
    public static final String PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_DEN_SQL = "activePatientOnArvHavingPsychosocialAssessment_den.sql";

    public static final String PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_UUID = "220edbf5-d3e8-4168-b0f5-91c70ec4f97a";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.diabete1.indicator.prevalence.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";

    public static final String PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_UUID = "d3d78c5d-2359-4d26-a3e1-2a80b174b866";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.diabete2.indicator.prevalence.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";

    public static final String PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_UUID = "8462784c-6a07-4f27-9b25-03e8c8bbc13b";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.diabete.glycemie.indicator.proportion.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";

    public static final String PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_UUID = "901c41ef-032c-43e5-beaa-a0f8be6bb16d";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_MESSAGE = "isanteplusreports.comorbidity.diabete.ta.systolique.indicator.proportion.label";
    public static final String PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_SQL = "activePatientOnArvHavingPsychosocialAssessment_num.sql";






}
