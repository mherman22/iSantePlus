package org.openmrs.module.coreapps.page.controller;

import org.openmrs.*;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.coreapps.CoreAppsConstants;
import org.openmrs.module.coreapps.triage.Triage;
import org.openmrs.module.coreapps.triage.TriageConstants;
import org.openmrs.module.coreapps.triage.Vitals;
import org.openmrs.module.coreapps.triage.PediatricTriageService;
import org.openmrs.module.coreapps.utils.VisitTypeHelper;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

import java.util.*;

public class ActivePatientVisitsTriagedPageController {

    private PediatricTriageService triageService = new PediatricTriageService();

    public String get(UiSessionContext sessionContext, PageModel model,
                      @SpringBean AdtService service,
                      @SpringBean("visitService") VisitService visitService,
                      @SpringBean("visitTypeHelper") VisitTypeHelper visitTypeHelper) {

        Location sessionLocation = sessionContext.getSessionLocation();
        if (sessionLocation == null) {
            return "redirect:login.htm";
        }

        Location visitLocation = service.getLocationThatSupportsVisits(sessionLocation);
        if (visitLocation == null) {
            throw new IllegalStateException("Configuration required: no visit location found");
        }

        model.addAttribute("canViewVisits",
                Context.hasPrivilege(CoreAppsConstants.PRIVILEGE_PATIENT_VISITS));

        List<VisitDomainWrapper> activeVisits = service.getActiveVisits(visitLocation);
        model.addAttribute("visitSummaries", activeVisits);

        Map<Patient, List<Obs>> obsParPatient = collectPatientObservations(activeVisits);

        Map<Patient, List<Obs>> filteredObs = keepLatestObsPerConcept(obsParPatient);

        List<Triage> triageList = buildTriageList(filteredObs);

        model.addAttribute("triageList", triageList);

        Map<Integer, Object> visitTypesWithAttr = new HashMap<>();
        for (VisitType type : visitService.getAllVisitTypes()) {
            visitTypesWithAttr.put(
                    type.getVisitTypeId(),
                    visitTypeHelper.getVisitTypeColorAndShortName(type)
            );
        }

        model.addAttribute("visitTypesWithAttr", visitTypesWithAttr);

        return null;
    }

    /*
     Collecter les observations de triage
     */
    private Map<Patient, List<Obs>> collectPatientObservations(List<VisitDomainWrapper> visits) {

        Map<Patient, List<Obs>> map = new HashMap<>();

        for (VisitDomainWrapper visit : visits) {

            for (Encounter encounter : visit.getSortedEncounters()) {

                if ("77f833ac-79bd-4822-991d-533fcccaf996"
                        .equals(encounter.getEncounterType().getUuid())) {

                    Patient patient = encounter.getPatient();

                    map.putIfAbsent(patient, new ArrayList<>());

                    map.get(patient).addAll(encounter.getAllObs(false));
                }
            }
        }

        return map;
    }

    /*
     Garder la dernière observation par concept
     */
    private Map<Patient, List<Obs>> keepLatestObsPerConcept(Map<Patient, List<Obs>> input) {

        Map<Patient, List<Obs>> result = new HashMap<>();

        for (Map.Entry<Patient, List<Obs>> entry : input.entrySet()) {

            Map<Integer, Obs> latest = new HashMap<>();

            for (Obs obs : entry.getValue()) {

                Integer conceptId = obs.getConcept().getConceptId();

                if (!latest.containsKey(conceptId)
                        || latest.get(conceptId).getObsDatetime()
                        .before(obs.getObsDatetime())) {

                    latest.put(conceptId, obs);
                }
            }

            result.put(entry.getKey(), new ArrayList<>(latest.values()));
        }

        return result;
    }

    /*
     Construire les objets triage
     */
    private List<Triage> buildTriageList(Map<Patient, List<Obs>> obsMap) {

        List<Triage> triageList = new ArrayList<>();

        for (Map.Entry<Patient, List<Obs>> entry : obsMap.entrySet()) {

            Patient patient = entry.getKey();
            List<Obs> obsList = entry.getValue();

            Vitals vitals = extractVitals(obsList);

            Triage triage = new Triage(patient, vitals);

            String level = triageService.determineTriageLevel(triage);

            triage.setTriageLevel(level);

            triageList.add(triage);
        }

        return triageList;
    }

    /**
     Extraire les signes vitaux
     */
    private Vitals extractVitals(List<Obs> obsList) {
        Vitals vitals = new Vitals();

        if (obsList == null) return vitals;

        for (Obs obs : obsList) {
            if (obs == null || obs.getConcept() == null || obs.getConcept().getUuid() == null)
                continue;

            String conceptUuid = obs.getConcept().getUuid();
            vitals.setEncounter(obs.getEncounter());

            switch (conceptUuid) {

                /* ----------------- MESURES ----------------- */
                case TriageConstants.WEIGHT_CONCEPT:
                    vitals.setWeight(obs.getValueNumeric());
                    break;

                case TriageConstants.HEIGHT_CONCEPT:
                    vitals.setHeight(obs.getValueNumeric());
                    break;

                case TriageConstants.ARM_CONCEPT:
                    vitals.setArm(obs.getValueNumeric());
                    break;

                case TriageConstants.HEAD_CONCEPT:
                    vitals.setHead(obs.getValueNumeric());
                    break;

                case TriageConstants.WAIST_CONCEPT:
                    vitals.setWaist(obs.getValueNumeric());
                    break;

                case TriageConstants.THORACIC_CONCEPT:
                    vitals.setThoracic(obs.getValueNumeric());
                    break;

                /* ----------------- SIGNES VITAUX ----------------- */
                case TriageConstants.PULSE_CONCEPT:
                    vitals.setPulse(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.HEART_RATE_CONCEPT:
                    vitals.setHeartRate(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.TEMPERATURE_CONCEPT:
                    vitals.setTemperature(obs.getValueNumeric());
                    break;

                case TriageConstants.RESPIRATORY_RATE_CONCEPT:
                    vitals.setRespiratoryRate(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.SYSTOLIC_CONCEPT:
                    vitals.setSystolic(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.DIASTOLIC_CONCEPT:
                    vitals.setDiastolic(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.SPO2_CONCEPT:
                    vitals.setSpO2(obs.getValueNumeric().intValue());
                    break;

                /* ----------------- RESPIRATION ----------------- */
                case TriageConstants.RESPIRATORY_DISTRESS_CONCEPT:
                    vitals.setRespiratoryDistress(obs.getValueBoolean());
                    break;

                case TriageConstants.STRIDOR_CONCEPT:
                    vitals.setStridor(obs.getValueBoolean());
                    break;

                case TriageConstants.WHEEZING_CONCEPT:
                    vitals.setWheezing(obs.getValueBoolean());
                    break;

                case TriageConstants.APNEA_CONCEPT:
                    vitals.setApnea(obs.getValueBoolean());
                    break;

                case TriageConstants.GROGNEMENT_CONCEPT:
                    vitals.setGrognement(obs.getValueBoolean());
                    break;

                /* ----------------- CIRCULATION ----------------- */
                case TriageConstants.TRC_CONCEPT:
                    vitals.setTrc(obs.getValueNumeric());
                    break;

                case TriageConstants.CYANOSIS_CONCEPT:
                    vitals.setCyanosis(obs.getValueBoolean());
                    break;

                case TriageConstants.PURPURA_CONCEPT:
                    vitals.setPurpura(obs.getValueBoolean());
                    break;

                case TriageConstants.PALE_CONCEPT:
                    vitals.setPale(obs.getValueBoolean());
                    break;

                case TriageConstants.MOTTLED_CONCEPT:
                    vitals.setMottledSkin(obs.getValueBoolean());
                    break;

                case TriageConstants.PETECHIE_CONCEPT:
                    vitals.setPetechie(obs.getValueBoolean());
                    break;

                /* ----------------- NEUROLOGIE ----------------- */
                case TriageConstants.EYES_CONCEPT:
                    vitals.setEyes(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.VERBAL_CONCEPT:
                    vitals.setVerbal(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.MOTOR_CONCEPT:
                    vitals.setMotor(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.AVPU_CONCEPT:
                    vitals.setAvpu(obs.getValueText());
                    break;

                /* ----------------- DOULEUR ----------------- */
                case TriageConstants.PAIN_SCORE_CONCEPT:
                    vitals.setPainScore(obs.getValueNumeric().intValue());
                    break;

                case TriageConstants.PAIN_TYPE_CONCEPT:
                    vitals.setPainType(obs.getValueText());
                    break;

                default:
                    // ignore les autres concepts
                    break;
            }
        }

        return vitals;
    }

}