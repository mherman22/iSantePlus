package org.openmrs.module.isanteplusreports.alertprecoce;

import org.openmrs.module.isanteplusreports.alertprecoce.model.AlertPrecoceIndicator;
import org.openmrs.module.isanteplusreports.alertprecoce.util.AlertPrecoceReportsConstants;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AlertPrecoceManager {

    @Autowired
    private ReportDefinitionService reportDefinitionService;

    /**
     * AlertPrecoce
     */
    private static final String[] alertPrecoceIndicatorsUuid = {
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_1_UUID,
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_2_UUID,
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_4_UUID,
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_5_UUID,
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_6_UUID,
            AlertPrecoceReportsConstants.ALERT_PRECOCE_INDICATOR_7_UUID
    };

    /**
     * ART
     */
    private static final String[] artDistributionIndicatorsUuid = {
            AlertPrecoceReportsConstants.APPOINTMENT_PERIOD_INDICATOR_UUID,
            AlertPrecoceReportsConstants.DISPENSINGART_PERIOD_INDICATOR_UUID,
            AlertPrecoceReportsConstants.NOTTAKINGART_PERIOD_INDICATOR_UUID,
            AlertPrecoceReportsConstants.TAKINGART_PERIOD_INDICATOR_UUID
    };

    /**
     * PsychoSocial
     */
    private static final String[] psychoSocialIndicatorsUuid = {
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_UUID,
            AlertPrecoceReportsConstants.NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_UUID
    };

    /**
     * Comorbidity
     */
    private static final String[] comorbidityHTAIndicatorsUuid = {
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_PREVALENCE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSIVE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_ANTIHYPERTENSIVE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE1_PREVALENCE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE2_PREVALENCE_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_GLYCEMIE_PROPORTION_INDICATOR_UUID,
            AlertPrecoceReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_TA_SYS_PROPORTION_INDICATOR_UUID
    };

    private static final String[] comorbidityDiabetesIndicatorsUuid = {

    };

    private static final String[] fingerPrintIndicatorsUuid = {
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITH_FINGER_PRINT_UUID,
            AlertPrecoceReportsConstants.ACTIVE_PATIENT_WITHOUT_FINGER_PRINT_UUID
    };

    private static final String[] transisionedPatientIndicatorsUuid = {
            AlertPrecoceReportsConstants.TRANSISIONED_PATIENT_FROM_PEDIATRIC_TO_ADULT_UUID
    };

    private static final String[] vitalStatisticsIndicatorsUuid = {
            AlertPrecoceReportsConstants.VITAL_STATISTICS_UUID
    };


    public List<AlertPrecoceIndicator> getAlertPrecoceIndicators() {
        return uuidToReportDefinition(Arrays.asList(alertPrecoceIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getArtDistributionIndicators() {
        return uuidToReportDefinition(Arrays.asList(artDistributionIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getPsychoSocialIndicators() {
        return uuidToReportDefinition(Arrays.asList(psychoSocialIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getComorbidityHTAIndicators() {
        return uuidToReportDefinition(Arrays.asList(comorbidityHTAIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getComorbidityDiabetesIndicators() {
        return uuidToReportDefinition(Arrays.asList(comorbidityDiabetesIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getFingerPrintIndicators() {
        return uuidToReportDefinition(Arrays.asList(fingerPrintIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getTransisionedPatientIndicators() {
        return uuidToReportDefinition(Arrays.asList(transisionedPatientIndicatorsUuid));
    }

    public List<AlertPrecoceIndicator> getvitalStatisticsIndicators() {
        return uuidToReportDefinition(Arrays.asList(vitalStatisticsIndicatorsUuid));
    }

    private List<AlertPrecoceIndicator> uuidToReportDefinition(List<String> uuids) {
        List<AlertPrecoceIndicator> indicators = new ArrayList<>();
        for (String uuid : uuids) {
            ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(uuid);
            if (reportDefinition != null) {
                indicators.add(new AlertPrecoceIndicator(reportDefinition, null));
            }
        }
        return indicators;
    }

}
