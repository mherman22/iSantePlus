package org.openmrs.module.isanteplusreports.comorbidity;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.comorbidity.model.ComorbidityDatasetDefinition;
import org.openmrs.module.isanteplusreports.comorbidity.model.ComorbidityReportResult;
import org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.isanteplusreports.comorbidity.ComorbidityReportsConstants.COMORBIDITY_REPORTS_RESOURCE_PATH;

@Component
public class ComorbidityReportDataset {

    public List<ComorbidityDatasetDefinition> getAllComorbidityReports() {

        List<ComorbidityDatasetDefinition> comorbidityDatasetDefinitionList = new ArrayList<>();

        comorbidityDatasetDefinitionList.add(new ComorbidityDatasetDefinition(
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_MESSAGE,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_NUM_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_DEN_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_HYPERTENSION_ART_INDICATOR_UUID));

        comorbidityDatasetDefinitionList.add(new ComorbidityDatasetDefinition(
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_MESSAGE,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_NUM_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_DEN_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_1_INDICATOR_UUID));

        comorbidityDatasetDefinitionList.add(new ComorbidityDatasetDefinition(
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_MESSAGE,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_NUM_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_DEN_SQL,
                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_DIABETE_2_INDICATOR_UUID));

//        comorbidityDatasetDefinitionList.add(new ComorbidityDatasetDefinition(
//                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_MESSAGE,
//                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_NUM_SQL,
//                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_DEN_SQL,
//                ComorbidityReportsConstants.PERCENT_PATIENT_COMORBID_CANCER_INDICATOR_UUID));

        return comorbidityDatasetDefinitionList;
    }

    public List<ComorbidityReportResult> cohortIndicators(List<String> uuids, Date startDate, Date endDate) {

        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        List<ComorbidityReportResult> comorbidityReportDefinitionList = new ArrayList<>();

        if(uuids != null) {
            for (String uuid : uuids) {
                for (ComorbidityDatasetDefinition datasetDefinition : getAllComorbidityReports()) {
                    if (uuid.equals(datasetDefinition.getUuid())) {
                        List<PatientSummary> patientListNume = isantePlusReportsService.getComorbidityReport(COMORBIDITY_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlNum(), startDate, endDate);
                        List<PatientSummary> patientListDeno = isantePlusReportsService.getComorbidityReport(COMORBIDITY_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlDeno(), startDate, endDate);
                        ComorbidityReportResult comorbidityReportResult = new ComorbidityReportResult(datasetDefinition.getLabel(), patientListNume, patientListDeno);
                        comorbidityReportDefinitionList.add(comorbidityReportResult);
                    }
                }
            }
        }

        return comorbidityReportDefinitionList;
    }

    public List<PatientSummary> getAllPatientSummaries(String identifiers) {
        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        return isantePlusReportsService.getPatientsByIdentifiersComorbidity(COMORBIDITY_REPORTS_RESOURCE_PATH + ComorbidityReportsConstants.COMORBIDITY_REPORTS_PATIENT_LIST_SQL, identifiers);
    }

}
