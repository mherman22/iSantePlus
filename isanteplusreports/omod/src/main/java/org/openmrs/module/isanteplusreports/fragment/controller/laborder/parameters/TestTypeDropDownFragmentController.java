package org.openmrs.module.isanteplusreports.fragment.controller.laborder.parameters;

import org.openmrs.module.isanteplusreports.laboratory.testorder.LabTestOrderService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class TestTypeDropDownFragmentController {

    public void controller(FragmentModel model, @SpringBean("isanteplusreports.LabTestOrderService") LabTestOrderService service) {
        model.addAttribute("tests", service.getAllTestConcepts());
    }
}

