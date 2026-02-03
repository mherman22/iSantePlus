package org.openmrs.module.isanteplusreports.fragment.controller;

import java.util.List;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class ImpressionsCliniquesFragmentController {
	
	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		List<Obs> impressionsCliniques = Context.getService(IsantePlusReportsService.class).getImpressionsCliniques(patient);
		model.addAttribute("impressionsCliniques", impressionsCliniques);
	}
}
