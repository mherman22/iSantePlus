package org.openmrs.module.isanteplusreports.fragment.controller;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplusreports.IsantePlusReportsObs;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LabsResultFragmentController {
	public void controller(FragmentModel model, @FragmentParam("patientId") Patient patient) {
		List<IsantePlusReportsObs> labresult = Context.getService(IsantePlusReportsService.class).getLabsResult(patient);
		model.addAttribute("labresult", labresult);
	}

}
