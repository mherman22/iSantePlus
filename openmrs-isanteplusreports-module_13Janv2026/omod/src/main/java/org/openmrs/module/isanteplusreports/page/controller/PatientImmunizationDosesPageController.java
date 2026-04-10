package org.openmrs.module.isanteplusreports.page.controller;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientImmunizationDosesPageController {
	
	public void controller(PageModel model, @RequestParam("patientId") Patient patient) {
	/*	JSONObject patientOpts = new JSONObject();
		patientOpts.put("name", patient.getPersonName().getFullName());
		model.addAttribute("patientPropts", patientOpts); */
	
	DataSet dataset = Context.getService(IsantePlusReportsService.class).patientImmunizationDoses(patient);
		List<DataSetColumn> columns = null;
		Iterator<DataSetRow> columnsValues = null;
		if(dataset != null){
			columns = dataset.getMetaData().getColumns();
			columnsValues = dataset.iterator();
		}
		
		PatientIdentifier pi = patient.getPatientIdentifier("iSantePlus ID");
		String location = null;
		if(pi != null){
			 location = pi.getLocation().getName();
		}
		
		model.addAttribute("location", location);	
		model.addAttribute("patient", patient);
		model.addAttribute("columns", columns);
		model.addAttribute("columnsvalues", columnsValues);
	}


}
