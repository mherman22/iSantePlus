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
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class VitalsPageController {

    public String get(UiSessionContext sessionContext, PageModel model,
                      @SpringBean AdtService service,
                      @SpringBean("visitService") VisitService visitService,
                      @SpringBean("encounterService") EncounterService encounterService,
                      @RequestParam("app") AppDescriptor app,
                      @RequestParam("patientId") Integer patientId,
                      @RequestParam("visitId") Integer visitId) {

        Location sessionLocation = sessionContext.getSessionLocation();

        Visit visit = visitService.getVisit(visitId);

        List<Encounter> encounterList = encounterService.getEncountersByVisit(visit, false);

        // Comparateur pour trier les ensembles par date d'observation, avec une vérification des ensembles vides
        Comparator<Set<Obs>> comparator = Comparator.comparing(set -> set.isEmpty() ? null : set.iterator().next().getObsDatetime());

        // TreeSet qui trie par date d'observation (ordre croissant)
        Set<Set<Obs>> hashSetObs = new TreeSet<>(comparator);

        // TreeSet qui trie par date d'observation (ordre décroissant)
        TreeSet<Set<Obs>> reverseTreeSetObs = new TreeSet<>(comparator.reversed());

        // Ajout d'éléments aux TreeSets (par exemple, en itérant sur les rencontres ou autres éléments)
        for (Encounter encounter : encounterList) {
            if (encounter.getEncounterType().getUuid().equals("67a71486-1a54-468f-ac3e-7091a9a79584")) {
                Set<Obs> obsSet = encounter.getObs(); // Supposons que tu récupères un Set d'Observations ici
                if (obsSet != null && !obsSet.isEmpty()) { // Vérification que le Set d'Observations n'est pas vide
                    hashSetObs.add(obsSet);               // Ajout au TreeSet ordonné
                    reverseTreeSetObs.add(obsSet);         // Ajout au TreeSet ordonné inverse
                }
            }
        }

        model.addAttribute("hashSetObs", reverseTreeSetObs);

        Location visitLocation;

        if (sessionLocation == null) {
            return "redirect:login.htm";
        } else {
            visitLocation = service.getLocationThatSupportsVisits(sessionLocation);
        }

        if (visitLocation == null) {
            throw new IllegalStateException("Configuration required: no visit location found based on session location");
        }

        model.addAttribute("patientId", patientId);
        model.addAttribute("visitId", visitId);

        List<VisitDomainWrapper> activeVisits = service.getActiveVisits(visitLocation);
        model.addAttribute("visitSummaries", activeVisits);

        String patientPageUrl = app.getConfig().get("patientPageUrl").getTextValue();
        model.addAttribute("patientPageUrl", patientPageUrl);

        return null;
    }


    public String post(@SpringBean("patientService") PatientService patientService,
                       @SpringBean("visitService") VisitService visitService,
                       @SpringBean("encounterService") EncounterService encounterService,
                       @SpringBean("obsService") ObsService obsService,
                       @SpringBean("conceptService") ConceptService conceptService,
                       @SpringBean("formService") FormService formService,

                       @RequestParam(value = "patientId", required = false) Integer patientId,
                       @RequestParam(value = "visitId", required = false) Integer visitId,
                       @RequestParam(value = "encounterId", required = false) Integer encounterId,
                       @RequestParam(value = "modeId") String modeId,

                       @RequestParam(value = "poids", required = false) Double poids,
                       @RequestParam(value = "temp", required = false) Double temp,
                       @RequestParam(value = "fc", required = false) Double fc,
                       @RequestParam(value = "tasys", required = false) Double tasys,
                       @RequestParam(value = "tadias", required = false) Double tadias,
                       @RequestParam(value = "resp", required = false) Double resp,
                       @RequestParam(value = "taille", required = false) Double taille,
                       @RequestParam(value = "sao2", required = false) Double sao2,
                       @RequestParam(value = "signature") String signature,

                       @RequestParam(value = "poidsId", required = false) Integer poidsId,
                       @RequestParam(value = "tempId", required = false) Integer tempId,
                       @RequestParam(value = "fcId", required = false) Integer fcId,
                       @RequestParam(value = "tasysId", required = false) Integer tasysId,
                       @RequestParam(value = "tadiasId", required = false) Integer tadiasId,
                       @RequestParam(value = "respId", required = false) Integer respId,
                       @RequestParam(value = "tailleId", required = false) Integer tailleId,
                       @RequestParam(value = "sao2Id", required = false) Integer sao2Id,
                       @RequestParam(value = "signatureId", required = false) Integer signatureId,

                       UiSessionContext sessionContext,
                       PageModel model) {

        Patient patient = patientService.getPatient(patientId);
        Visit visit = visitService.getVisit(visitId);
        Encounter encounter;
        Encounter encounterSave = null;

        // Mapping des conceptIds avec les valeurs
        Map<Integer, Object> vitaux = new LinkedHashMap<>();
        vitaux.put(5089, poids);       // Poids
        vitaux.put(5090, taille);      // Taille
        vitaux.put(5088, temp);        // Température
        vitaux.put(5087, fc);          // Fréquence cardiaque
        vitaux.put(5085, tasys);       // Tension systolique
        vitaux.put(5086, tadias);      // Tension diastolique
        vitaux.put(5242, resp);        // Respiration
        vitaux.put(5092, sao2);        // Saturation O2
        vitaux.put(1473, signature);   // Signature inf.

        // Mapping des conceptIds avec les obsIds prêts pour éventuelles modifications
        List<Integer> obsIds = new ArrayList<>();
        obsIds.add(poidsId);
        obsIds.add(tempId);
        obsIds.add(fcId);
        obsIds.add(tasysId);
        obsIds.add(tadiasId);
        obsIds.add(respId);
        obsIds.add(tailleId);
        obsIds.add(sao2Id);
        obsIds.add(signatureId);


        if (!modeId.isEmpty() && modeId.trim().equals("create")) {

            EncounterType encounterType = encounterService.getEncounterTypeByUuid("67a71486-1a54-468f-ac3e-7091a9a79584");
            Form form = formService.getFormByUuid("a000cb34-9ec1-4344-a1c8-f692232f6edd");
            encounter = new Encounter();
            encounter.setEncounterType(encounterType);
            encounter.setForm(form);
            encounter.setPatient(patient);
            encounter.setEncounterDatetime(new Date());
            encounter.setUuid(UUID.randomUUID().toString());
            encounter.setCreator(sessionContext.getCurrentUser());
            encounter.setLocation(sessionContext.getSessionLocation());
            encounter.setDateCreated(new Date());
            encounter.setVoided(false);
            encounter.setVisit(visit);

            encounterSave = encounterService.saveEncounter(encounter);

            createVitals(vitaux, conceptService, obsService, patient, encounterSave, sessionContext, model);

        } else {
            if (!modeId.isEmpty() && modeId.trim().equals("update")) {
                /**
                 * Modification logique (void = 1) d’un signe vital, et d'inserer un nouveau
                 */
                encounter = encounterService.getEncounter(encounterId);

                updateVitals(vitaux, encounter, obsService, obsIds, patient, conceptService, sessionContext);

            } else if (!modeId.isEmpty() && modeId.trim().equals("delete")) {
                /**
                 * Suppression logique (void = 1) d’un signe vital
                 */
                if (encounterId != null) {
                    Encounter deletedEncounter = encounterService.getEncounter(encounterId);
                    encounterService.voidEncounter(deletedEncounter, "Suppresion des signes vitaux...");
                }
            }
        }


        model.addAttribute("encounterId", encounterSave.getId());

        model.addAttribute("visitId", visit.getId());
        model.addAttribute("patientId", patient.getPatientId());

        List<Encounter> encounterList = encounterService.getEncountersByVisit(visit, false);
        Set<Set<Obs>> hashSetObs = new HashSet<>();
        for (Encounter enc : encounterList) {
            if (enc.getEncounterType().getUuid().equals("67a71486-1a54-468f-ac3e-7091a9a79584")) {
                hashSetObs.add(enc.getAllObs(false));
            }
        }
        model.addAttribute("hashSetObs", hashSetObs);

        return null;
    }


    /**
     * Method for create the vitals signs
     *
     */
    public void createVitals(Map<Integer, Object> vitaux, ConceptService conceptService, ObsService obsService,
                             Patient patient, Encounter encounterSave, UiSessionContext sessionContext, PageModel model) {
        try {
            for (Map.Entry<Integer, Object> entry : vitaux.entrySet()) {
                if (entry.getValue() != null) {
                    Obs obs = new Obs();
                    obs.setPerson(patient);
                    obs.setConcept(conceptService.getConcept(entry.getKey()));
                    obs.setEncounter(encounterSave);
                    obs.setObsDatetime(encounterSave.getEncounterDatetime());
                    obs.setLocation(sessionContext.getSessionLocation());
                    obs.setCreator(sessionContext.getCurrentUser());
                    obs.setDateCreated(new Date());
                    obs.setVoided(false);
                    obs.setUuid(UUID.randomUUID().toString());

                    if (entry.getValue() instanceof Double) {
                        obs.setValueNumeric((Double) entry.getValue());
                    } else if (entry.getValue() instanceof String) {
                        obs.setValueText((String) entry.getValue());
                    }

                    obsService.saveObs(obs, null);
                }
            }
        } catch (ValidationException ex) {
            model.addAttribute("visit_closed_message", "Impossible d’enregistrer une rencontre dans une visite déjà clôturée");
        }
    }


    /**
     * Method for update the vitals signs
     *
     */
    public void updateVitals(Map<Integer, Object> vitaux, Encounter encounter, ObsService obsService, List<Integer> obsIds,
                             Patient patient, ConceptService conceptService, UiSessionContext sessionContext) throws ValidationException {
        for (Map.Entry<Integer, Object> entry : vitaux.entrySet()) {
            if (entry.getValue() != null) {
                Obs checkObs;
                boolean trouve = false;

                Obs obs = new Obs();
                obs.setPerson(patient);
                obs.setConcept(conceptService.getConcept(entry.getKey()));
                obs.setEncounter(encounter);
                obs.setObsDatetime(encounter.getEncounterDatetime());
                obs.setLocation(sessionContext.getSessionLocation());
                obs.setCreator(sessionContext.getCurrentUser());
                obs.setDateCreated(new Date());
                obs.setVoided(false);
                obs.setUuid(UUID.randomUUID().toString());

                for (Integer id : obsIds) {
                    if (id != null) {
                        checkObs = obsService.getObs(id);
                        if (checkObs != null && checkObs.getVoided() == false) {
                            if (checkObs.getConcept().getConceptId().equals(entry.getKey())) {
                                if (entry.getValue() instanceof Double) {
                                    if (checkObs.getValueNumeric() != null && checkObs.getValueNumeric() != ((Double) entry.getValue()).doubleValue()) {
                                        obsService.voidObs(checkObs, "Modification des signes vitaux...");
                                        obs.setValueNumeric((Double) entry.getValue());
                                        obsService.saveObs(obs, null);
                                    }
                                } else if (entry.getValue() instanceof String) {
                                    if (checkObs.getValueText() != null && !checkObs.getValueText().trim().equals(entry.getValue().toString().trim())) {
                                        obsService.voidObs(checkObs, "Modification des signes vitaux...");
                                        obs.setValueText((String) entry.getValue());
                                        obsService.saveObs(obs, null);
                                    }
                                }
                                trouve = true;
                                break;
                            }
                        }
                    }
                }
                if (!trouve) {
                    if (entry.getValue() instanceof Double) {
                        obs.setValueNumeric((Double) entry.getValue());
                        obsService.saveObs(obs, null);
                    } else if (entry.getValue() instanceof String) {
                        obs.setValueText((String) entry.getValue());
                        obsService.saveObs(obs, null);
                    }
                }
            }
        }
    }

}










