package org.openmrs.module.isanteplusreports.alertprecoce.util;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.alertprecoce.AlertPrecoceReportDataset;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants;
import org.openmrs.module.isanteplusreports.pnlsReport.utils.pnlsReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.sqlDataSetDefinitionWithResourcePath;
import static org.openmrs.module.isanteplusreports.alertprecoce.util.AlertPrecoceReportsConstants.*;
import static org.openmrs.module.isanteplusreports.pnlsReport.RegisterAllPnlsReports.getParameters;

public class RegisterAllAlertPrecoceReports {

    private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
            Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);

    public static void registerAll() {
        numberOfPatients();

        /*Alert precoce indicator*/
        timelyWithdrawalArt();
        retentionOnArt();
        ruptureStock();
        viralLoadSuppression();
        viralLoadCompletion();
        appropriateChange();
        treatmentInterruption();

        /*Distribution ART*/
        appointmentPeriod();
        dispensingArtPeriod();
        notTakinArtPeriod();
        takinArtPeriod();

        /*PsychoSociale*/
        activePatientWithPsychoSocialAssessement();
        activePatientWithIncompletePsychoSocialAssessement();
        activePatientWithPsychoSocialDiagnoses();
        activePatientWithPsychoSocialDiagnosesResolve();
        activePatientWithPsychoSocialFollowUp();
        activePatientWithPsychoSocialNeeds();
        activePatientPsychoAfterViralLoadSupMille();
        activePatientWithViralLoadSupMilleWithThreePsycho();
        activePatientWithViralLoadSupMilleWithPsychoAtTime();
        newActivePatientWithPsychoAssessment();

        /*Comorbidite*/
        percentPatientComorbidHtaPrevalence();
        percentPatientComorbidHtaHypertensive();
        percentPatientComorbidHtaAntiHypertensive();
        percentPatientComorbidDiabete1Prevalence();
        percentPatientComorbidDiabete2Prevalence();
        percentPatientComorbidDiabeteGlycemieProportion();
        percentPatientComorbidDiabeteTaSysProportion();

        //Others
        activePatientWithFingerPrint();
        activePatientWithoutFingerPrint();
        transitionedPatientFromPediatricToAdult();
        vitalStatistics();

    }


    /*Alert Precoce report*/

    private static void numberOfPatients() {
        registerAlertPrecoceReportWithStartAndEndDateParams(NUMBER_PATIENTS_SQL,
                "Nombre de patients", NUMBER_PATIENTS_UUID);
    }

    private static void timelyWithdrawalArt() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.TIMELYWITHDRAWALART_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.TIMELYWITHDRAWALART_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.TIMELYWITHDRAWALART_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_1_UUID);
    }

    private static void retentionOnArt() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.RETENTIONONART_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.RETENTIONONART_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.RETENTIONONART_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_2_UUID);
    }

    private static void ruptureStock() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.RUPTURESTOCK_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.RUPTURESTOCK_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.RUPTURESTOCK_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_3_UUID);
    }

    private static void viralLoadSuppression() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.VIRALLOADSUPPRESSION_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.VIRALLOADSUPPRESSION_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.VIRALLOADSUPPRESSION_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_4_UUID);
    }

    private static void viralLoadCompletion() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.VIRALLOADCOMPLETION_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.VIRALLOADCOMPLETION_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.VIRALLOADCOMPLETION_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_5_UUID);
    }

    private static void appropriateChange() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.APPROPRIATECHANGE_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.APPROPRIATECHANGE_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.APPROPRIATECHANGE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_6_UUID);
    }

    private static void treatmentInterruption() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.TREATMENTINTERRUPTION_NUM_INDICATOR_SQL,
                AlertPrecoceReportsConstants.TREATMENTINTERRUPTION_DEN_INDICATOR_SQL,
                AlertPrecoceReportsConstants.TREATMENTINTERRUPTION_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_7_UUID);
    }

    /*Alert Precoce report*/
    /*ART Distribution*/
    private static void appointmentPeriod() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.APPOINTMENT_PERIOD_INDICATOR_SQL,
                AlertPrecoceReportsConstants.APPOINTMENT_PERIOD_INDICATOR_SQL,
                "Rendez-vous pour la période",
                AlertPrecoceReportsConstants.APPOINTMENT_PERIOD_INDICATOR_UUID);
    }

    private static void dispensingArtPeriod() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.DISPENSINGART_PERIOD_INDICATOR_SQL,
                AlertPrecoceReportsConstants.DISPENSINGART_PERIOD_INDICATOR_SQL,
                "ARV dispensé pour la période",
                AlertPrecoceReportsConstants.DISPENSINGART_PERIOD_INDICATOR_UUID);
    }

    private static void notTakinArtPeriod() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.NOTTAKINGART_PERIOD_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.NOTTAKINGART_PERIOD_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.NOTTAKINGART_PERIOD_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.NOTTAKINGART_PERIOD_INDICATOR_UUID);
    }

    private static void takinArtPeriod() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.TAKINGART_PERIOD_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.TAKINGART_PERIOD_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.TAKINGART_PERIOD_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.TAKINGART_PERIOD_INDICATOR_UUID);
    }

    /*PsychoSocial Indicator report*/
    private static void activePatientWithPsychoSocialAssessement() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_UUID);
    }

    private static void activePatientWithIncompletePsychoSocialAssessement() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_UUID);
    }

    private static void activePatientWithPsychoSocialDiagnoses() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_UUID);
    }

    private static void activePatientWithPsychoSocialDiagnosesResolve() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_UUID);
    }

    private static void activePatientWithPsychoSocialFollowUp() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_UUID);
    }

    private static void activePatientWithPsychoSocialNeeds() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_UUID);
    }

    private static void activePatientPsychoAfterViralLoadSupMille() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_UUID);
    }

    private static void activePatientWithViralLoadSupMilleWithThreePsycho() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_UUID);
    }

    private static void activePatientWithViralLoadSupMilleWithPsychoAtTime() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_UUID);
    }

    private static void newActivePatientWithPsychoAssessment() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENTINDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_UUID);
    }

    private static void activePatientWithFingerPrint() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_UUID);
    }

    private static void activePatientWithoutFingerPrint() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_NUM_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_DEN_SQL,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_MESSAGE,
                AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_UUID);
    }

    private static void transitionedPatientFromPediatricToAdult() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.TRANSISIONED_PATIENT_NUM_SQL,
                AlertPrecoceReportsConstants.TRANSISIONED_PATIENT_DEN_SQL,
                AlertPrecoceReportsConstants.TRANSISIONED_PATIENT_FROM_PEDIATRIC_TO_ADULT_MESSAGE,
                AlertPrecoceReportsConstants.TRANSISIONED_PATIENT_FROM_PEDIATRIC_TO_ADULT_UUID);
    }

    private static void vitalStatistics() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.VITAL_STATISTICS_NUM_SQL,
                AlertPrecoceReportsConstants.VITAL_STATISTICS_DEN_SQL,
                AlertPrecoceReportsConstants.VITAL_STATISTICS_MESSAGE,
                AlertPrecoceReportsConstants.VITAL_STATISTICS_UUID);
    }


    //Comorbidity
    private static void percentPatientComorbidHtaPrevalence() {
        registerAlertPrecoceReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_UUID);
    }

    private static void percentPatientComorbidHtaHypertensive() {
        registerAlertPrecoceReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_UUID);
    }

    private static void percentPatientComorbidHtaAntiHypertensive() {
        registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_NUM_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_DEN_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_UUID);
    }

    private static void percentPatientComorbidDiabete1Prevalence() {
        registerAlertPrecoceReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_UUID);
    }

    private static void percentPatientComorbidDiabete2Prevalence() {
        registerAlertPrecoceReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_UUID);
    }

    private static void percentPatientComorbidDiabeteGlycemieProportion() {
        registerAlertPrecoceReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_UUID);
    }

    private static void percentPatientComorbidDiabeteTaSysProportion() {
        registerSingleColumnReportWithStartAndEndDateParams(
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_SQL,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_MESSAGE,
                AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_UUID);
    }



    private static void registerAlertPrecoceReportWithStartAndEndDateParams(String sql, String messageProperties,
                                                                            String uuid) {
        SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messageProperties, messageProperties,
                ALERT_PRECOCE_REPORTS_RESOURCE_PATH);
        sqlData.addParameter(START_DATE);
        sqlData.addParameter(END_DATE);
        Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

        Map<String, Object> mappings = new HashMap<>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(sqlData, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    public static void registerAlertPrecoceReportWithStartAndEndDateParamsNumDen(String sqlNum,
                                                                                 String sqlDen,
                                                                                 String messageProperties,
                                                                                 String uuid) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(START_DATE);
        parameters.add(END_DATE);
        DataSetDefinition dataSetDefinition = new AlertPrecoceReportDataset().constructDataSet(messageProperties, sqlDen, sqlNum, parameters);
        Context.getService(DataSetDefinitionService.class).saveDefinition(dataSetDefinition);

        Map<String, Object> mappings = new HashMap<>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dataSetDefinition, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerSingleColumnReportWithStartAndEndDateParams(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        dsd.addColumn("BF", "Single Column", Mapped.mapStraightThrough(CohortIndicator), "");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_1BY1, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

}
