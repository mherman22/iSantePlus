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
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.isanteplus.IsantePlusConstants;
import org.openmrs.module.isanteplus.IsantePlusRelationship;
import org.openmrs.module.isanteplus.LocationAddressMirror;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
        List<LocationAddressMirror> locationAddressMirrors = isantePlusService.getAllLocationAddressesByCriteria(critere == null ? (sessionLocation.getStateProvince() == null ? "" : sessionLocation.getStateProvince()) : critere);

        List<Encounter> encounterList;
        List<Obs> obsList = null;



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String firstName = null;
        String lastName = null;
        String dateOfBirth = null;
        String gender = null;
        String address = null;
        String phoneNumber = null;

        Integer relationship = null;
        String contactPhone = null;
        String contactName = null;

        if (visitId != null && patientId != null) {

            Visit visit = visitService.getVisit(visitId);
            Patient patient = visit.getPatient();

            firstName = patient.getGivenName();
            lastName = patient.getFamilyName();

            dateOfBirth = sdf.format(patient.getBirthdate());

            gender = patient.getGender();

            address = patient.getPerson().getPersonAddress().getAddress1() + ", "
                    + patient.getPerson().getPersonAddress().getAddress3() + ", "
                    + patient.getPerson().getPersonAddress().getCityVillage() + ", "
                    + patient.getPerson().getPersonAddress().getStateProvince() + ", "
                    + patient.getPerson().getPersonAddress().getCountry();

            List<PersonAttribute> personAttributes = patient.getActiveAttributes();

            for (PersonAttribute personAttribute : personAttributes) {
                if (personAttribute.getAttributeType().getUuid().equals("14d4f066-15f5-102d-96e4-000c29c2a5d7")) {
                    phoneNumber = personAttribute.getValue();
                }
            }

            encounterList = visit.getNonVoidedEncounters();
            obsList = new ArrayList<>();

            for (Encounter encounter : encounterList) {
                if (encounter.getEncounterType().getUuid().equals("77f833ac-79bd-4822-991d-533fcccaf996")) {
                    Set<Obs> obsSet = encounter.getObs();
                    if (obsSet != null && !obsSet.isEmpty())
                        obsList.addAll(obsSet);
                }
            }
        }

        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("dateOfBirth", dateOfBirth);
        model.addAttribute("gender", gender);
        model.addAttribute("address", address);
        model.addAttribute("phoneNumber", phoneNumber);
        model.addAttribute("relationship", relationship);
        model.addAttribute("contactName", contactName);
        model.addAttribute("contactPhone", contactPhone);


        model.addAttribute("patientId", patientId);
        model.addAttribute("visitId", visitId);

        model.addAttribute("obsList", obsList);

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
        model.addAttribute("locationAddressMirrors", locationAddressMirrors);

        List<VisitDomainWrapper> activeVisits = service.getActiveVisits(visitLocation);
        model.addAttribute("visitSummaries", activeVisits);

        String patientPageUrl = app.getConfig().get("patientPageUrl").getTextValue();
        model.addAttribute("patientPageUrl", patientPageUrl);

        return null;
    }

    // Méthode utilitaire pour convertir LocalDate en Date
    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String post(@SpringBean("personService") PersonService personService,
                       @SpringBean("patientService") PatientService patientService,
                       @SpringBean("visitService") VisitService visitService,
                       @SpringBean("encounterService") EncounterService encounterService,
                       @SpringBean("obsService") ObsService obsService,
                       @SpringBean("conceptService") ConceptService conceptService,
                       @SpringBean("formService") FormService formService,
                       @RequestParam(value = "firstName", required = false) String firstName,
                       @RequestParam(value = "lastName", required = false) String lastName,
                       @RequestParam(value = "gender", required = false) String gender,
                       @RequestParam(value = "phone", required = false) String phone,
                       @RequestParam(value = "ageGroup", required = false) String ageGroup,
                       @RequestParam(value = "contactName", required = false) String contactName,
                       @RequestParam(value = "relation", required = false) String relation,
                       @RequestParam(value = "contactPhone", required = false) String contactPhone,
                       @RequestParam(value = "arrivalMode", required = false) String arrivalModes,
                       @RequestParam(value = "evaluation", required = false) List<String> evaluations,
                       @RequestParam(value = "fr", required = false) Double fr,
                       @RequestParam(value = "sao2", required = false) Double sao2,
                       @RequestParam(value = "fc", required = false) Double fc,
                       @RequestParam(value = "tasys", required = false) Double tasys,
                       @RequestParam(value = "tadias", required = false) Double tadias,
                       @RequestParam(value = "glycemie", required = false) Double glycemie,
                       @RequestParam(value = "temp", required = false) Double temperature,
                       @RequestParam(value = "resp", required = false) Double resp,
                       @RequestParam(value = "scoreDouleur", required = false) Double scoreDouleur,
                       @RequestParam(value = "pc", required = false) Double pc,
                       @RequestParam(value = "pb", required = false) Double pb,
                       @RequestParam(value = "ge", required = false) Double ge,
                       @RequestParam(value = "gv", required = false) Double gv,
                       @RequestParam(value = "gm", required = false) Double gm,
                       @RequestParam(value = "poids", required = false) Double poids,
                       @RequestParam(value = "taille", required = false) Double taille,
                       @RequestParam(value = "typeDouleur", required = false) String typeDouleur,
                       @RequestParam(value = "disposition", required = false) String disposition,
                       @RequestParam(value = "intervention", required = false) String intervention,
                       @RequestParam(value = "signature", required = false) String signature,
                       @RequestParam(value = "birthDate", required = false) LocalDate birthDate,
                       UiSessionContext sessionContext,
                       @RequestParam(value = "critere", required = false) String critere,
                       @RequestParam(value = "adresse", required = false) String adresse,
                       PageModel model) {

        Location sessionLocation = sessionContext.getSessionLocation();

        IsantePlusService isantePlusService = Context.getService(IsantePlusService.class);
        List<IsantePlusRelationship> relationshipList = isantePlusService.getAllRelationships(sessionContext.getLocale().getLanguage().trim());
        List<LocationAddressMirror> locationAddressMirrors = isantePlusService.getAllLocationAddressesByCriteria(critere == null ? (sessionLocation.getStateProvince() == null ? "" : sessionLocation.getStateProvince()) : critere);

        Date now = new Date();

        if ((!firstName.trim().isEmpty() && !lastName.trim().isEmpty() && !gender.trim().isEmpty()) || birthDate != null) {
            Person person = new Person();
            person.setGender(gender.trim());
            person.setBirthdateEstimated(false);
            person.setDead(false);
            person.setBirthdate(convertLocalDateToDate(birthDate));
            person.setCreator(sessionContext.getCurrentUser());
            person.setDateCreated(now);
            person.setVoided(false);
            person.setDeathdateEstimated(false);
            person.setUuid(UUID.randomUUID().toString());

            if (!phone.trim().isEmpty()) {
                PersonAttributeType personAttributeType = personService.getPersonAttributeTypeByUuid("14d4f066-15f5-102d-96e4-000c29c2a5d7");
                PersonAttribute personAttribute = new PersonAttribute(personAttributeType, phone.trim());
                personAttribute.setUuid(UUID.randomUUID().toString());
                personAttribute.setCreator(sessionContext.getCurrentUser());
                personAttribute.setDateCreated(now);
                personAttribute.setVoided(false);
                person.addAttribute(personAttribute);
            }

            PersonName personName = new PersonName(lastName.trim(), null, firstName.trim());
            personName.setPreferred(true);
            personName.setCreator(sessionContext.getCurrentUser());
            personName.setDateCreated(now);
            personName.setVoided(false);
            personName.setUuid(UUID.randomUUID().toString());
            person.addName(personName);

            if (!adresse.trim().isEmpty()) {
                String[] adresses = adresse.split(",");
                PersonAddress personAddress = new PersonAddress();
                personAddress.setCreator(sessionContext.getCurrentUser());
                personAddress.setDateCreated(now);
                personAddress.setUuid(UUID.randomUUID().toString());
                personAddress.setVoided(false);
                personAddress.setPreferred(true);
                personAddress.setCountry(adresses[adresses.length - 1].trim());
                personAddress.setStateProvince(adresses[adresses.length - 2].trim());
                personAddress.setCityVillage(adresses[adresses.length - 3].trim());
                personAddress.setAddress3(adresses[adresses.length - 4].trim());
                personAddress.setAddress1(adresses[adresses.length - 5].trim());
                person.addAddress(personAddress);
            }

            Person personSave = personService.savePerson(person);

            if (personSave != null) {
                PatientIdentifierType patientIdentifierType = patientService.getPatientIdentifierTypeByUuid("05a29f94-c0ed-11e2-94be-8c13b969e334");

                IdentifierSourceService iss = Context.getService(IdentifierSourceService.class);
                IdentifierSource source = iss.getIdentifierSource(1);
                String newId = iss.generateIdentifiers(source, 1, null).get(0);

                PatientIdentifier patientIdentifier = new PatientIdentifier(newId, patientIdentifierType, sessionContext.getSessionLocation());
                patientIdentifier.setCreator(sessionContext.getCurrentUser());
                patientIdentifier.setDateCreated(personSave.getDateCreated());
                patientIdentifier.setVoided(false);
                patientIdentifier.setPreferred(true);
                patientIdentifier.setUuid(UUID.randomUUID().toString());

                Patient patient = new Patient(personSave);
                patient.setCreator(sessionContext.getCurrentUser());
                patient.setDateCreated(personSave.getDateCreated());
                patient.setVoided(false);
                patient.setPersonId(personSave.getId());
                patient.addIdentifier(patientIdentifier);

                Patient patientSave = patientService.savePatient(patient);


                if (patientSave != null) {

                    VisitType visitType = visitService.getVisitTypeByUuid("7b0f5697-27e3-40c4-8bae-f4049abfb4ed");
                    Visit visit = new Visit(patientSave, visitType, patientSave.getDateCreated());
                    visit.setCreator(sessionContext.getCurrentUser());
                    visit.setLocation(sessionContext.getSessionLocation());
                    visit.setDateCreated(personSave.getDateCreated());
                    visit.setVoided(false);
                    visit.setUuid(UUID.randomUUID().toString());

                    Visit visitSave = visitService.saveVisit(visit);

                    if (visitSave != null) {

                        System.out.println("Saving visit::::::::::::::: " + visitSave.getUuid());

                        EncounterType encounterType = encounterService.getEncounterTypeByUuid("77f833ac-79bd-4822-991d-533fcccaf996");
                        Form form = formService.getFormByUuid("0cc54a35-e6c8-4f90-9db2-2f24262abbd1");

                        Encounter encounter = new Encounter();
                        encounter.setEncounterType(encounterType);
                        encounter.setForm(form);
                        encounter.setPatient(patientSave);
                        encounter.setEncounterDatetime(visitSave.getDateCreated());
                        encounter.setUuid(UUID.randomUUID().toString());
                        encounter.setCreator(sessionContext.getCurrentUser());
                        encounter.setLocation(sessionContext.getSessionLocation());
                        encounter.setDateCreated(visitSave.getDateCreated());
                        encounter.setVoided(false);
                        encounter.setVisit(visitSave);

                        Encounter encounterSave = encounterService.saveEncounter(encounter);

                        System.out.println("Saving encounter::::::::::::::::: " + encounterSave.getUuid());

                        if (encounterSave != null) {

                            /** Parent : Contact d’urgence (concept group) */
                            Concept contactConcept = conceptService.getConcept(IsantePlusConstants.EMERGENCY_CONTACT_CONCEPT_ID);

                            Set<Obs> children = new HashSet<>();

                            /** Start the emergency contact informations by patient */

                            Obs groupObs = new Obs();
                            groupObs.setPerson(personSave);
                            groupObs.setConcept(contactConcept);
                            groupObs.setEncounter(encounterSave);
                            groupObs.setObsDatetime(encounterSave.getEncounterDatetime());
                            groupObs.setLocation(sessionContext.getSessionLocation());
                            groupObs.setCreator(sessionContext.getCurrentUser());
                            groupObs.setDateCreated(encounterSave.getDateCreated());
                            groupObs.setVoided(false);
                            groupObs.setUuid(UUID.randomUUID().toString());

                            /** Child : Nom du contact */
                            if (!contactName.trim().isEmpty()) {
                                Obs nameObs = new Obs();
                                nameObs.setConcept(conceptService.getConceptByUuid(IsantePlusConstants.EMERGENCY_CONTACT_NAME_CONCEPT_UUID));
                                nameObs.setValueText(contactName.trim());
                                nameObs.setPerson(personSave);
                                nameObs.setEncounter(encounterSave);
                                nameObs.setDateCreated(encounterSave.getDateCreated());
                                nameObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                nameObs.setLocation(sessionContext.getSessionLocation());
                                nameObs.setCreator(sessionContext.getCurrentUser());
                                nameObs.setObsGroup(groupObs);
                                nameObs.setVoided(false);
                                nameObs.setUuid(UUID.randomUUID().toString());

                                children.add(nameObs);
                            }

                            /** Child : Téléphone du contact */
                            if (!contactPhone.trim().isEmpty()) {
                                Obs phoneObs = new Obs();
                                phoneObs.setConcept(conceptService.getConceptByUuid(IsantePlusConstants.EMERGENCY_CONTACT_PHONE_CONCEPT_UUID));
                                phoneObs.setValueText(contactPhone.trim());
                                phoneObs.setPerson(personSave);
                                phoneObs.setDateCreated(encounterSave.getDateCreated());
                                phoneObs.setEncounter(encounterSave);
                                phoneObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                phoneObs.setLocation(sessionContext.getSessionLocation());
                                phoneObs.setCreator(sessionContext.getCurrentUser());
                                phoneObs.setObsGroup(groupObs);
                                phoneObs.setVoided(false);
                                phoneObs.setUuid(UUID.randomUUID().toString());

                                children.add(phoneObs);
                            }

                            /** Child : Relation du contact */
                            if (!relation.trim().isEmpty()) {
                                Obs relationObs = new Obs();
                                relationObs.setConcept(conceptService.getConceptByUuid(IsantePlusConstants.EMERGENCY_CONTACT_RELATION_CONCEPT_UUID));
                                relationObs.setValueCoded(conceptService.getConcept(Integer.parseInt(relation.trim())));
                                relationObs.setPerson(personSave);
                                relationObs.setDateCreated(encounterSave.getDateCreated());
                                relationObs.setEncounter(encounterSave);
                                relationObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                relationObs.setLocation(sessionContext.getSessionLocation());
                                relationObs.setCreator(sessionContext.getCurrentUser());
                                relationObs.setObsGroup(groupObs);
                                relationObs.setVoided(false);
                                relationObs.setUuid(UUID.randomUUID().toString());

                                children.add(relationObs);
                            }

                            groupObs.setGroupMembers(children);

                            obsService.saveObs(groupObs, "Relation avec le patient");
                            /** End contact informations by patient */

                            System.out.println("groupObs:::::::::::: " + groupObs.getObsDatetime());

                            /** Category age for patient */
                            if (!ageGroup.trim().isEmpty()) {
                                Obs ageObs = new Obs();
                                ageObs.setPerson(personSave);
                                ageObs.setConcept(conceptService.getConcept(159614)); // concept a remplacer
                                ageObs.setEncounter(encounterSave);
                                ageObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                ageObs.setLocation(sessionContext.getSessionLocation());
                                ageObs.setCreator(sessionContext.getCurrentUser());
                                ageObs.setDateCreated(encounterSave.getDateCreated());
                                ageObs.setVoided(false);
                                ageObs.setValueCoded(conceptService.getConcept(Integer.parseInt(ageGroup.trim())));
                                ageObs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(ageObs, null);
                            }
                            System.out.println("==================================avant================");
                            /** Arrival mode for the patient */
                            if (!arrivalModes.trim().isEmpty()) {
                                Obs arrivalModeObs = new Obs();
                                arrivalModeObs.setConcept(conceptService.getConcept(159614)); // concept a remplacer
                                arrivalModeObs.setPerson(personSave);
                                arrivalModeObs.setValueCoded(conceptService.getConcept(Integer.parseInt(arrivalModes.trim())));
                                arrivalModeObs.setEncounter(encounterSave);
                                arrivalModeObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                arrivalModeObs.setLocation(sessionContext.getSessionLocation());
                                arrivalModeObs.setCreator(sessionContext.getCurrentUser());
                                arrivalModeObs.setDateCreated(encounterSave.getDateCreated());
                                arrivalModeObs.setVoided(false);
                                arrivalModeObs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(arrivalModeObs, null);
                            }
                            System.out.println("==================================apres================");

                            /** Arrival mode for the patient */
                            if (!evaluations.isEmpty()) {
                                for (String evaluation : evaluations) {
                                    if (evaluation != null && !evaluation.trim().isEmpty()) {
                                        Obs arrivalModeObs = new Obs();
                                        arrivalModeObs.setConcept(conceptService.getConcept(159614)); // concept a remplacer
                                        arrivalModeObs.setPerson(personSave);
                                        arrivalModeObs.setValueCoded(conceptService.getConcept(Integer.parseInt(evaluation.trim())));
                                        arrivalModeObs.setEncounter(encounterSave);
                                        arrivalModeObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                        arrivalModeObs.setLocation(sessionContext.getSessionLocation());
                                        arrivalModeObs.setCreator(sessionContext.getCurrentUser());
                                        arrivalModeObs.setDateCreated(encounterSave.getDateCreated());
                                        arrivalModeObs.setVoided(false);
                                        arrivalModeObs.setUuid(UUID.randomUUID().toString());
                                        obsService.saveObs(arrivalModeObs, null);
                                    }
                                }
                            }

                            /** disposition */
                            if (disposition != null && !disposition.trim().isEmpty()) {
                                Obs dispositionObs = new Obs();
                                dispositionObs.setPerson(personSave);
                                dispositionObs.setConcept(conceptService.getConcept(159614)); // concept a remplacer
                                dispositionObs.setEncounter(encounterSave);
                                dispositionObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                dispositionObs.setLocation(sessionContext.getSessionLocation());
                                dispositionObs.setCreator(sessionContext.getCurrentUser());
                                dispositionObs.setDateCreated(encounterSave.getDateCreated());
                                dispositionObs.setVoided(false);
                                dispositionObs.setValueCoded(conceptService.getConcept(Integer.parseInt(disposition)));
                                dispositionObs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(dispositionObs, null);
                            }

                            /** intervention */
                            if (disposition != null && !disposition.trim().isEmpty()) {
                                Obs interventionObs = new Obs();
                                interventionObs.setPerson(personSave);
                                interventionObs.setConcept(conceptService.getConcept(161011)); // concept a remplacer
                                interventionObs.setEncounter(encounterSave);
                                interventionObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                interventionObs.setLocation(sessionContext.getSessionLocation());
                                interventionObs.setCreator(sessionContext.getCurrentUser());
                                interventionObs.setDateCreated(encounterSave.getDateCreated());
                                interventionObs.setVoided(false);
                                interventionObs.setValueText(intervention.trim());
                                interventionObs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(interventionObs, null);
                            }


                            /** Insertion des signes vitaux */

                            /** Glasgow E */
                            if (ge != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5089));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(ge);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Glasgow V */
                            if (gv != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5089));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(gv);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Glasgow M */
                            if (gm != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5089));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(gm);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** Poids */
                            if (poids != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5089));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(poids);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Taille */
                            if (taille != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5090));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(taille);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Frequence respiratoire */
                            if (fr != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5242));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(fr);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** saturation pulsée en oxygène */
                            if (sao2 != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5092));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(sao2);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Fréquence Cardiaque */
                            if (fc != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5087));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(fc);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Tension Artérielle Systolique */
                            if (tasys != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5085));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(tasys);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Tension Artérielle Diastolique */
                            if (tadias != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5086));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(tadias);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Glycémie */
                            if (glycemie != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5090)); // concept a remplacer
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(glycemie);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }
                            /** Température corporelle */
                            if (temperature != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5088));
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(temperature);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** Type de douleur */
                            if (typeDouleur != null && !typeDouleur.trim().isEmpty()) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(114403)); // concept a remplacer
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(Double.parseDouble(typeDouleur.trim()));
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** Score de douleur */
                            if (scoreDouleur != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(114403)); // concept a remplacer
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(scoreDouleur);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** PC */
                            if (pc != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5090)); // concept a remplacer
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(pc);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** PB */
                            if (pb != null) {
                                Obs obs = new Obs();
                                obs.setPerson(personSave);
                                obs.setConcept(conceptService.getConcept(5090)); // concept a remplacer
                                obs.setEncounter(encounterSave);
                                obs.setObsDatetime(encounterSave.getEncounterDatetime());
                                obs.setLocation(sessionContext.getSessionLocation());
                                obs.setCreator(sessionContext.getCurrentUser());
                                obs.setDateCreated(encounterSave.getDateCreated());
                                obs.setVoided(false);
                                obs.setValueNumeric(pb);
                                obs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(obs, null);
                            }

                            /** signature */
                            if (signature != null && !signature.trim().isEmpty()) {
                                Obs signatureObs = new Obs();
                                signatureObs.setPerson(personSave);
                                signatureObs.setConcept(conceptService.getConcept(1473)); // concept a remplacer
                                signatureObs.setEncounter(encounterSave);
                                signatureObs.setObsDatetime(encounterSave.getEncounterDatetime());
                                signatureObs.setLocation(sessionContext.getSessionLocation());
                                signatureObs.setCreator(sessionContext.getCurrentUser());
                                signatureObs.setDateCreated(encounterSave.getDateCreated());
                                signatureObs.setVoided(false);
                                signatureObs.setValueText(signature.trim());
                                signatureObs.setUuid(UUID.randomUUID().toString());
                                obsService.saveObs(signatureObs, null);
                            }
                        }

                        model.addAttribute("visitId", visitSave.getId());
                    }
                }
            }
        }

        model.addAttribute("relationshipsList", relationshipList);
        model.addAttribute("locationAddressMirrors", locationAddressMirrors);

        return null;
    }

}










