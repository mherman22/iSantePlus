package org.openmrs.module.isanteplusreports.page.controller;

import java.util.Iterator;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.isanteplusreports.healthqual.builder.HealthQualReportBuilder;
import org.openmrs.module.isanteplusreports.util.RegisterDataSetForPatientSummary;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientSummaryPageController {
	
	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
		/*JSONObject patientOpts = new JSONObject();
		patientOpts.put("name", patient.getPersonName().getFullName());
		model.addAttribute("patientPropts", patientOpts);*/
		//List<FormHistory> formHistory = null;
		//List<FormHistory> lastSixformHistory = null;
		RegisterDataSetForPatientSummary registerSummary = new RegisterDataSetForPatientSummary();
		DataSet dataset = registerSummary.patientDemographic(patient);
		List<DataSetColumn> columns = null;
		Iterator<DataSetRow> columnsValues = null;
		if(dataset != null){
			/*model.addAttribute("demographicColumns", dataset.getMetaData().getColumns());
			model.addAttribute("demographicValues", dataset.iterator());*/
			columns = dataset.getMetaData().getColumns();
			columnsValues = dataset.iterator();
		}
		//IsantePlusServiceImpl iSantePlusImpl = new IsantePlusServiceImpl();
		/*formHistory = Context.getService(IsantePlusService.class)
				.getAllFormHistoryForAPatient(patient);*/
		//formHistory = iSantePlusImpl.getAllFormHistoryForAPatient(patient);
	/*	if (formHistory != null)
			Collections.reverse(formHistory);
		for(int i=0; i<=formHistory.size();i++){
			if(i<=6){
				lastSixformHistory = formHistory;
			}
		}*/
		PatientIdentifier pi = patient.getPatientIdentifier("iSantePlus ID");
		String location = null;
		if(pi != null){
			 location = pi.getLocation().getName();
		}
		HealthQualReportBuilder builder = new HealthQualReportBuilder();
		model.addAttribute("location", location);	
		model.addAttribute("patient", patient);
		model.addAttribute("demographicColumns", columns);
		model.addAttribute("demographicValues", columnsValues);
	}

}
