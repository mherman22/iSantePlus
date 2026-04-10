/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.isanteplus.page.controller;

import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.isanteplus.*;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Transactional
public class TriagePageController {

    public String get(UiSessionContext sessionContext, PageModel model,
                      @SpringBean AdtService service,
                      @SpringBean("visitService") VisitService visitService,
                      @SpringBean("patientService") PatientService patientService,
                      @SpringBean("encounterService") EncounterService encounterService,
                      @RequestParam(value = "critere", required = false) String critere,
                      @RequestParam(value = "patientId", required = false) Integer patientId,
                      @RequestParam(value = "visitId", required = false) Integer visitId,
                      @RequestParam("app") AppDescriptor app) {

        Location sessionLocation = sessionContext.getSessionLocation();

        IsantePlusService isantePlusService = Context.getService(IsantePlusService.class);
        List<IsantePlusRelationship> relationshipList = isantePlusService.getAllRelationships(sessionContext.getLocale().getLanguage().trim());

        Map<String, List<LocationAddress>> locations =
                isantePlusService.getLocationAddressesGroupedByNature(
                        Arrays.asList(
                                "state_province",
                                "city_village",
                                "municipal_section",
                                "locality"
                        )
                );

        model.addAttribute("stateProvinces", locations.get("state_province"));
        model.addAttribute("cityVillages", locations.get("city_village"));
        model.addAttribute("municipalSections", locations.get("municipal_section"));
        model.addAttribute("localities", locations.get("locality"));

        Location visitLocation;

        if (sessionLocation == null) {
            return "redirect:login.htm";
        } else {
            visitLocation = service.getLocationThatSupportsVisits(sessionLocation);
        }

        if (visitLocation == null) {
            throw new IllegalStateException("Configuration required: no visit location found based on session location");
        }

        model.addAttribute("relationshipsList", relationshipList);

        String patientPageUrl = app.getConfig().get("patientPageUrl").getTextValue();
        model.addAttribute("patientPageUrl", patientPageUrl);

        return null;
    }


    public String post(
            @SpringBean("personService") PersonService personService,
            @SpringBean("patientService") PatientService patientService,
            @SpringBean("visitService") VisitService visitService,
            @SpringBean("encounterService") EncounterService encounterService,
            @SpringBean("obsService") ObsService obsService,
            @SpringBean("conceptService") ConceptService conceptService,
            @SpringBean("formService") FormService formService,

            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "phone", required = false) String phone,

            @RequestParam(value = "departement", required = false) String departement,
            @RequestParam(value = "commune", required = false) String commune,
            @RequestParam(value = "sectionCommunale", required = false) String sectionCommunale,
            @RequestParam(value = "localite", required = false) String localite,
            @RequestParam(value = "additionalAdresses", required = false) String additionalAdresses,

            @RequestParam(value = "contactName", required = false) String contactName,
            @RequestParam(value = "relation", required = false) String relation,
            @RequestParam(value = "contactPhone", required = false) String contactPhone,

            @RequestParam(value = "ageGroup", required = false) String ageGroup,
            @RequestParam(value = "arrivalMode", required = false) String arrivalModes,
            @RequestParam(value = "evaluation", required = false) List<String> evaluations,

            @RequestParam(value = "intervention", required = false) String intervention,
            @RequestParam(value = "disposition", required = false) String disposition,

            @RequestParam(value = "poids", required = false) Double poids,
            @RequestParam(value = "taille", required = false) Double taille,
            @RequestParam(value = "fr", required = false) Double fr,
            @RequestParam(value = "sao2", required = false) Double sao2,
            @RequestParam(value = "fc", required = false) Double fc,

            @RequestParam(value = "tasys", required = false) Double tasys,
            @RequestParam(value = "tadias", required = false) Double tadias,

            @RequestParam(value = "glycemie", required = false) Double glycemie,
            @RequestParam(value = "temp", required = false) Double temperature,

            @RequestParam(value = "pc", required = false) Double pc,
            @RequestParam(value = "pb", required = false) Double pb,

            @RequestParam(value = "ge", required = false) Double ge,
            @RequestParam(value = "gv", required = false) Double gv,
            @RequestParam(value = "gm", required = false) Double gm,

            @RequestParam(value = "typeDouleur", required = false) String typeDouleur,
            @RequestParam(value = "scoreDouleur", required = false) Double scoreDouleur,

            @RequestParam(value = "signature", required = false) String signature,

            UiSessionContext sessionContext,
            PageModel model) {

        Date now = new Date();

        IsantePlusService isantePlusService = Context.getService(IsantePlusService.class);

        List<IsantePlusRelationship> relationshipList =
                isantePlusService.getAllRelationships(sessionContext.getLocale().getLanguage().trim());

        Map<String, List<LocationAddress>> locations =
                isantePlusService.getLocationAddressesGroupedByNature(
                        Arrays.asList(
                                "state_province",
                                "city_village",
                                "municipal_section",
                                "locality"
                        )
                );

        if (!isValidPatient(firstName, lastName, gender, birthDate)) {
            return getString(model, relationshipList, locations);
        }

        Person person = isantePlusService.createPerson(personService, sessionContext, now,
                firstName, lastName, gender, birthDate);

        Patient patient = isantePlusService.createPatient(patientService, sessionContext, person);

        Visit visit = isantePlusService.createVisit(visitService, sessionContext, patient);

        Encounter encounter = isantePlusService.createEncounter(encounterService, formService,
                sessionContext, patient, visit);

        isantePlusService.saveEmergencyContact(obsService, conceptService, sessionContext,
                person, encounter, contactName, contactPhone, relation);

        isantePlusService.savePatientInformation(obsService, conceptService, sessionContext,
                person, encounter, ageGroup, arrivalModes, evaluations);

        isantePlusService.saveMedicalDecision(obsService, conceptService, sessionContext,
                person, encounter, disposition, intervention);

        isantePlusService.saveVitalSigns(obsService, conceptService, sessionContext,
                person, encounter,
                poids, taille, fr, sao2, fc,
                tasys, tadias, glycemie, temperature,
                pc, pb, ge, gv, gm,
                typeDouleur, scoreDouleur);

        isantePlusService.saveSignature(obsService, conceptService, sessionContext,
                person, encounter, signature);

        model.addAttribute("visitId", visit.getId());

        return getString(model, relationshipList, locations);
    }

    private String getString(PageModel model, List<IsantePlusRelationship> relationshipList, Map<String, List<LocationAddress>> locations) {
        model.addAttribute("relationshipsList", relationshipList);
        model.addAttribute("stateProvinces", locations.get("state_province"));
        model.addAttribute("cityVillages", locations.get("city_village"));
        model.addAttribute("municipalSections", locations.get("municipal_section"));
        model.addAttribute("localities", locations.get("locality"));
        return null;
    }

    private boolean isValidPatient(String firstName, String lastName, String gender, LocalDate birthDate) {
        return ((firstName != null && !firstName.trim().isEmpty())
                && (lastName != null && !lastName.trim().isEmpty())
                && (gender != null && !gender.trim().isEmpty()))
                && birthDate != null;
    }
}










