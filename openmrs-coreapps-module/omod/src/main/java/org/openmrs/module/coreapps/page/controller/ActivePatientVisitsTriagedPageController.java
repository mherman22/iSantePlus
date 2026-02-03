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
package org.openmrs.module.coreapps.page.controller;

import org.openmrs.*;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.coreapps.CoreAppsConstants;
import org.openmrs.module.coreapps.Triage;
import org.openmrs.module.coreapps.Vitals;
import org.openmrs.module.coreapps.utils.VisitTypeHelper;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

public class ActivePatientVisitsTriagedPageController {

    public String get(UiSessionContext sessionContext, PageModel model,
                      @SpringBean AdtService service,
                      @SpringBean("visitService") VisitService visitService,
                      @RequestParam("app") AppDescriptor app,
                      @SpringBean("visitTypeHelper") VisitTypeHelper visitTypeHelper) {

        Location sessionLocation = sessionContext.getSessionLocation();

        if (sessionLocation == null) {
            return "redirect:login.htm";
        }

        Location visitLocation = service.getLocationThatSupportsVisits(sessionLocation);
        if (visitLocation == null) {
            throw new IllegalStateException("Configuration required: no visit location found based on session location");
        }

        model.addAttribute("canViewVisits", Context.hasPrivilege(CoreAppsConstants.PRIVILEGE_PATIENT_VISITS));


        List<VisitDomainWrapper> activeVisits = service.getActiveVisits(visitLocation);
        model.addAttribute("visitSummaries", activeVisits);

        Map<Patient, List<Obs>> obsParPatient = new HashMap<>();
        List<List<Encounter>> encounterList = new ArrayList<>();

        for (VisitDomainWrapper visit : activeVisits)
            encounterList.add(visit.getSortedEncounters());

        for (VisitDomainWrapper visit : activeVisits) {
            List<Encounter> encounters = visit.getSortedEncounters();
            for (Encounter encounter : encounters) {
                // Filtrer par type d'encounter (urgences)
                if (encounter.getEncounterType().getUuid().equals("77f833ac-79bd-4822-991d-533fcccaf996")) {
                    Set<Obs> obsSet = encounter.getAllObs(false);

                    Patient patient = encounter.getPatient(); // récupérer le patient
                    obsParPatient.putIfAbsent(patient, new ArrayList<>());
                    obsParPatient.get(patient).addAll(obsSet); // ajouter toutes les Obs
                }
            }
        }

        for (List<Obs> obsList : obsParPatient.values())
            obsList.sort(Comparator.comparing(Obs::getObsDatetime));

        Map<Patient, List<Obs>> obsPatientAfterTrie = new HashMap<>();

        for (Map.Entry<Patient, List<Obs>> entry : obsParPatient.entrySet()) {
            Patient patient = entry.getKey();
            List<Obs> obsList = entry.getValue();

            obsPatientAfterTrie.putIfAbsent(patient, new ArrayList<>());
            List<Obs> existingObsList = obsPatientAfterTrie.get(patient);

            for (Obs obs : obsList) {
                // Chercher l'index d'un obs avec le même concept
                int index = -1;
                for (int i = 0; i < existingObsList.size(); i++) {
                    if (existingObsList.get(i).getConcept().equals(obs.getConcept())) {
                        index = i;
                        break;
                    }
                }
//                http://localhost:8080/coreapps/clinicianfacing/patient.page?patientId=260
//                http://localhost:8080/openmrs/coreapps/clinicianfacing/patient.page?patientId=260

                if (index >= 0) {
                    // Remplacer l'ancien obs par le nouveau
                    existingObsList.set(index, obs);
                } else {
                    // Sinon on ajoute
                    existingObsList.add(obs);
                }
            }
        }


        List<Triage> triageList = new ArrayList<>();

        for (Map.Entry<Patient, List<Obs>> entry : obsPatientAfterTrie.entrySet()) {
            Vitals vitals = getVitals(entry);
            triageList.add(new Triage(entry.getKey(), vitals));
        }

        for (Triage triage : triageList) {
            Vitals vitals = triage.getVitals();
            int age = triage.getPatient().getAge();

            int frUrgency = (vitals.getRespiratory() != null && age > 0) ? classifyByFR(vitals.getRespiratory(), age) : 4;
            int tempUrgency = (vitals.getTemperature() != null) ? classifyByTemperature(vitals.getTemperature(), age) : 4;
            int avpuUrgency = (vitals.getAvpu() != null) ? classifyByAVPU(vitals.getAvpu()) : 4;

            int glasgowUrgency = (vitals.getGlascowScore() != null) ? classifyByGlascow(vitals.getGlascowScore()) : 4;
            int spO2Urgency = (vitals.getSpO2() != null) ? classifyBySpO2(vitals.getSpO2()) : 4;
            int painUrgency = (vitals.getPainScore() != null && vitals.getPainType() != null) ? classifyByPain(vitals.getPainScore(), vitals.getPainType()) : 5;

            int[] scores = {frUrgency, tempUrgency, avpuUrgency, glasgowUrgency, spO2Urgency, painUrgency};

            String finalUrgency = determineUrgencyLevel(scores);
            triage.setTriageLevel(finalUrgency);

            String currentPriority = triage.getVitals().getPriority();

            if (currentPriority == null || "nope".equals(currentPriority) || getPriorityLevel(finalUrgency) < getPriorityLevel(currentPriority))
                triage.getVitals().setPriority(finalUrgency);
        }

        model.addAttribute("triageList", triageList);

        Map<Integer, Object> visitTypesWithAttr = new HashMap<Integer, Object>();
        List<VisitType> allVisitTypes = visitService.getAllVisitTypes();

        for (VisitType type : allVisitTypes) {
            Map<String, Object> typeAttr = visitTypeHelper.getVisitTypeColorAndShortName(type);
            visitTypesWithAttr.put(type.getVisitTypeId(), typeAttr);
        }

        model.addAttribute("visitTypesWithAttr", visitTypesWithAttr);

        return null;
    }

    private static Vitals getVitals(Map.Entry<Patient, List<Obs>> entry) {
        List<Obs> obsList = entry.getValue();
        Vitals vitals = new Vitals();

        // Vérification de liste vide
        if (obsList == null || obsList.isEmpty()) {
            return vitals; // ou null si tu veux signaler "aucun vital"
        }

        vitals.setEncounter(obsList.get(0).getEncounter());

        for (Obs obs : obsList) {
            switch (obs.getConcept().getConceptId()) {
                case 5089:
                    vitals.setWeight(obs.getValueNumeric());
                    break;
                case 5090:
                    vitals.setHeight(obs.getValueNumeric());
                    break;
                case 5087:
                    vitals.setPouls(obs.getValueNumeric());
                    break;
                case 5085:
                    vitals.setSystol(obs.getValueNumeric());
                    break;
                case 5086:
                    vitals.setDyastol(obs.getValueNumeric());
                    break;
                case 5088:
                    vitals.setTemperature(obs.getValueNumeric());
                    break;
                case 5242:
                    vitals.setRespiratory(obs.getValueNumeric());
                    break;
                case 5092:
                    vitals.setSpO2(obs.getValueNumeric());
                    break;
            }
            if (vitals.getEncounter().getEncounterDatetime().before(obs.getEncounter().getEncounterDatetime()))
                vitals.setEncounter(obs.getEncounter());
        }
        return vitals;
    }


    private String determineUrgencyLevel(int[] scores) {
        int res = Arrays.stream(scores).min().orElse(5);
        switch (res) {
            case 1:
                return "resuscitation";
            case 2:
                return "critical";
            case 3:
                return "potential";
            case 4:
                return "semi";
            default:
                return "nope";
        }
    }


    private int getPriorityLevel(String priority) {
        switch (priority) {
            case "resuscitation":
                return 1;
            case "critical":
                return 2;
            case "potential":
                return 3;
            case "semi":
                return 4;
            case "nope":
                return 5;
            default:
                return 0;
        }
    }

    /**
     * Classification ETG basée sur la FR en fonction du Z-score standard mondial.
     */
    public double calculateZScore(double fr, int age) {
        double moyeFR;
        double ecartypeFR;

        switch (getAgeCategory(age)) {
            case "infant":
                moyeFR = 30.0;
                ecartypeFR = 5.0;
                break;
            case "child":
                moyeFR = 25.0;
                ecartypeFR = 4.0;
                break;
            case "preteen":
                moyeFR = 20.0;
                ecartypeFR = 3.0;
                break;
            case "adult":
            default:
                moyeFR = 16.0;
                ecartypeFR = 3.0;
                break;
        }
        return (fr - moyeFR) / ecartypeFR;
    }

    /**
     * Classification ETG basée sur la FR en fonction du Z-score.
     */
    public Integer classifyByFR(Double fr, int age) {
        if (fr == null) return 4;
        double zScore = calculateZScore(fr, age);
        if (zScore >= 3) return 1;
        if (zScore >= 2) return 2;
        if (zScore >= 1) return 3;
        return 4;
    }


    private String getAgeCategory(int age) {
        if (age <= 1)
            return "infan";
        if (age <= 5)
            return "child";
        if (age <= 12)
            return "preteen";
        return "adult";
    }

    private int classifyByTemperature(Double temperature, int age) {
        if (age == 0 || age <= 3) {
            if (temperature > 38.0 || temperature < 36.0) {
                return 2;
            }
        } else if (age > 3 && temperature > 38.5) {
            return 2;
        }
        return 4;
    }


    private int classifyByAVPU(String AVPU) {
        switch (AVPU) {
            case "alerte":
                return 4;
            case "verbal":
                return 2;
            case "pain":
                return 1;
            case "unresponsive":
                return 1;
            default:
                return 5;
        }
    }

    private int classifyByGlascow(int glasgowScore) {
        if (glasgowScore >= 3 && glasgowScore <= 9) {
            return 1;
        } else if (glasgowScore >= 10 && glasgowScore <= 13) {
            return 2;
        } else if (glasgowScore >= 14 && glasgowScore <= 15) {
            return 4;
        }
        return 5;
    }

    private int classifyByPain(int painScore, String painType) {
        painType = painType.toLowerCase();

        if (painScore >= 8 && painScore <= 10) {
            if (painType.equals("aigue"))
                return 2;
            else if (painType.equals("chronique"))
                return 3;
        } else if (painScore >= 4 && painScore <= 7) {
            if (painType.equals("aigue"))
                return 3;
            else if (painType.equals("chronique"))
                return 4;
        } else if (painScore >= 1 && painScore <= 3) {
            if (painType.equals("aigue"))
                return 4;
            else if (painType.equals("chronique"))
                return 5;
        }
        return 0;
    }

    private Integer classifyBySpO2(Double spo2) {
        if (spo2 == null) {
            return 4;
        }

        if (spo2 <= 90) {
            return 1;
        } else if (spo2 > 90 && spo2 <= 92) {
            return 2;
        } else if (spo2 > 92 && spo2 <= 94) {
            return 3;
        }
        return 4;
    }

}
