package org.openmrs.module.isanteplusreports.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LastSixFormsFragmentController {

	protected final Log log = LogFactory.getLog(getClass());

	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		DataSet dataset = Context.getService(IsantePlusReportsService.class).lastSixForms(patient);
		DataSet datasetFirstVisit = Context.getService(IsantePlusReportsService.class).firstVisitForms(patient);
		model.addAttribute("columns", dataset.getMetaData().getColumns());
		model.addAttribute("columnsvalues", dataset.iterator());
		model.addAttribute("columnsfirstvisit", datasetFirstVisit.getMetaData().getColumns());
		model.addAttribute("columnsvalues_b", datasetFirstVisit.iterator());
	}
}
