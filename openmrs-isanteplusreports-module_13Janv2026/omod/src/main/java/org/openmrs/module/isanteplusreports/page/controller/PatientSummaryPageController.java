package org.openmrs.module.isanteplusreports.page.controller;

import java.util.Iterator;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.model.PatientAddressHistory;
import org.openmrs.module.isanteplusreports.util.RegisterDataSetForPatientSummary;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientSummaryPageController {

    public void controller(PageModel model,
                           @RequestParam("patientId") Patient patient) {

        RegisterDataSetForPatientSummary registerSummary = new RegisterDataSetForPatientSummary();
        DataSet dataset = registerSummary.patientDemographic(patient);
        List<DataSetColumn> columns = null;
        Iterator<DataSetRow> columnsValues = null;
        if (dataset != null) {
            columns = dataset.getMetaData().getColumns();
            columnsValues = dataset.iterator();
        }

        IsantePlusReportsService isantePlusReportsService =
                Context.getService(IsantePlusReportsService.class);

        List<PatientAddressHistory> patientAddressHistoryList =
                isantePlusReportsService.getAddressHistory(patient.getPatientId());

        PatientIdentifier pi = patient.getPatientIdentifier("iSantePlus ID");

        String location = null;
        if (pi != null) {
            location = pi.getLocation().getName();
        }

        System.out.println("patient:"+patient.getPatientId());

        model.addAttribute("location", location);
        model.addAttribute("patient", patient);
        model.addAttribute("demographicColumns", columns);
        model.addAttribute("demographicValues", columnsValues);
        model.addAttribute("patientAddressHistoryList", patientAddressHistoryList);
    }

}
