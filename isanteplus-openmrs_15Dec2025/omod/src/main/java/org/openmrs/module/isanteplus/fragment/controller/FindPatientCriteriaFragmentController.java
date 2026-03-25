package org.openmrs.module.isanteplus.fragment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.PatientSearchInfos;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Collections;

public class FindPatientCriteriaFragmentController {

    public SimpleObject findPatientByCriteria(@RequestParam("criteria") String criteria) {

        List<PatientSearchInfos> results;
        if (criteria == null || criteria.trim().isEmpty())
            results = Collections.emptyList();
        else
            results = Context.getService(IsantePlusService.class)
                    .getAllPatientSearchInfos(criteria);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return SimpleObject.create("patientsLoad", mapper.writeValueAsString(results));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}