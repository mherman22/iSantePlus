package org.openmrs.module.isanteplus.fragment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplus.PatientSearchInfos;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindPatientCriteriaFragmentController {

    public void get() {
    }

    public void findPatientByCriteria(@RequestParam("criteria") String criteria,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        if (criteria == null || criteria.trim().isEmpty()) {
            return; // rien si vide
        }

        List<PatientSearchInfos> results = Context.getService(IsantePlusService.class).getAllPatientSearchInfos(criteria);

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            new ObjectMapper().writeValue(response.getWriter(), results);
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException("Erreur AJAX findPatientByCriteria", e);
        }
    }
}
