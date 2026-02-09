package org.openmrs.module.isanteplusreports.psychosocial;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.psychosocial.model.PsychosocialDatasetDefinition;
import org.openmrs.module.isanteplusreports.psychosocial.model.PsychosocialReportResult;
import org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.isanteplusreports.psychosocial.PsychosocialReportsConstants.*;

@Component
public class PsychosocialReportDataset {

    public List<PsychosocialDatasetDefinition> getAllPsychosocialReports() {

        List<PsychosocialDatasetDefinition> psychosocialDatasetDefinitionList = new ArrayList<>();

        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_PSYCHO_ASSESS_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_INCOMPLETE_PSYCHO_ASSESS_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_PzSYCHO_DIAGNOSES_RESOLVE_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_DIAGNOSES_RESOLVE_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_FOLLOWUP_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_SOCIAL_NEEDS_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO__AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_PSYCHO_AFTER_VIRAL_LOAD_SUP_MILLE_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_THREE_PSYCHO_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_MESSAGE,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_NUM_SQL,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_DEN_SQL,
                ACTIVE_PATIENT_WITH_VL_SUP_MILLE_WITH_PSYCHO_AT_TIME_INDICATOR_UUID
        ));
        psychosocialDatasetDefinitionList.add(new PsychosocialDatasetDefinition(
                NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_MESSAGE,
                NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_NUM_SQL,
                NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENTINDICATOR_DEN_SQL,
                NEW_ACTIVE_PATIENT_WITH_WITH_PSYCHO_ASSESSMENT_INDICATOR_UUID
        ));

        return psychosocialDatasetDefinitionList;
    }

    public List<PsychosocialReportResult> cohortIndicators(List<String> uuids, Date startDate, Date endDate) {

        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        List<PsychosocialReportResult> psychosocialReportResultList = new ArrayList<>();

        if (uuids != null) {
            for (String uuid : uuids) {
                for (PsychosocialDatasetDefinition datasetDefinition : getAllPsychosocialReports()) {
                    if (uuid.equals(datasetDefinition.getUuid())) {
                        List<PatientSummary> patientListNume = isantePlusReportsService.getPsychosocialReport(PSYCHOSOCIAL_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlNum(), startDate, endDate);
                        List<PatientSummary> patientListDeno = isantePlusReportsService.getPsychosocialReport(PSYCHOSOCIAL_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlDeno(), startDate, endDate);
                        PsychosocialReportResult psychosocialReportResult = new PsychosocialReportResult(datasetDefinition.getLabel(), patientListNume, patientListDeno);
                        psychosocialReportResultList.add(psychosocialReportResult);
                    }
                }
            }
        }

        return psychosocialReportResultList;
    }

    public List<PatientSummary> getAllPatientSummaries(String identifiers) {
        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        return isantePlusReportsService.getPatientsByIdentifiersPsychosocial(PSYCHOSOCIAL_REPORTS_RESOURCE_PATH + PsychosocialReportsConstants.PSYCHOSOCIAL_REPORTS_PATIENT_LIST_SQL, identifiers);
    }

}
