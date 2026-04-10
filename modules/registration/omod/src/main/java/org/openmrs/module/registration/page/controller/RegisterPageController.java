package org.openmrs.module.registration.page.controller;

import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.registration.LocationAddress;
import org.openmrs.module.registration.LocationAddressMirror;
import org.openmrs.module.registration.RegistrationConstants;
import org.openmrs.module.registration.RegistrationRelationship;
import org.openmrs.module.registration.api.RegistrationService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class RegisterPageController {

    public String get(UiSessionContext sessionContext,
                      PageModel model,
                      @RequestParam("app") AppDescriptor app) {

        Location sessionLocation = sessionContext.getSessionLocation();

        if (sessionLocation == null)
            return "redirect:login.htm";

        RegistrationService registrationService = Context.getService(RegistrationService.class);
        AdministrationService administrationService = Context.getService(AdministrationService.class);

        List<RegistrationRelationship> relationshipList = registrationService.getAllRelationships(sessionContext.getLocale().getLanguage().trim());

        Map<String, List<LocationAddress>> locations =
                registrationService.getLocationAddressesGroupedByNature(
                        Arrays.asList(
                                "state_province",
                                "city_village",
                                "municipal_section",
                                "locality"
                        )
                );

        String patientPageUrl = app.getConfig().get("patientPageUrl").getTextValue();
        model.addAttribute("patientPageUrl", patientPageUrl);

        // Minimal supporting attributes used by fragments
        model.addAttribute("relationshipsList", relationshipList);

        model.addAttribute("serverUrl", administrationService.getGlobalProperty("m2sys-biometrics.server.url"));

        model.addAttribute("stateProvinces", locations.get("state_province"));
        model.addAttribute("cityVillages", locations.get("city_village"));
        model.addAttribute("municipalSections", locations.get("municipal_section"));
        model.addAttribute("localities", locations.get("locality"));

        return null;
    }


    public String post(UiSessionContext sessionContext,
                       PageModel model,
                       @SpringBean("personService") PersonService personService,
                       @SpringBean("patientService") PatientService patientService,
                       @SpringBean("encounterService") EncounterService encounterService,
                       @SpringBean("obsService") ObsService obsService,
                       @SpringBean("conceptService") ConceptService conceptService,

                       @RequestParam(value = "registrationDate", required = false) LocalDate registrationDate,
                       @RequestParam(value = "fingerprint", required = false) String fingerprint,

                       @RequestParam(value = "firstName", required = false) String firstName,
                       @RequestParam(value = "lastName", required = false) String lastName,
                       @RequestParam(value = "gender", required = false) String gender,
                       @RequestParam(value = "birthDate", required = false) LocalDate birthDate,

                       @RequestParam(value = "departement_birthdate", required = false) String departement_birthdate,
                       @RequestParam(value = "commune_birthdate", required = false) String commune_birthdate,
                       @RequestParam(value = "sectionCommunale_birthdate", required = false) String sectionCommunale_birthdate,
                       @RequestParam(value = "localite_birthdate", required = false) String localite_birthdate,
                       @RequestParam(value = "additionalAdresses_birthdate", required = false) String additionalAdresses_birthdate,


                       @RequestParam(value = "codeST", required = false) String codeST,
                       @RequestParam(value = "codeNational", required = false) String codeNational,
                       @RequestParam(value = "codePC", required = false) String codePC,


                       @RequestParam(value = "contactPhone", required = false) String contactPhone,
                       @RequestParam(value = "contactEmail", required = false) String contactEmail,
                       @RequestParam(value = "contactWhatsapp", required = false) String contactWhatsapp,

                       @RequestParam(value = "departement_demo", required = false) String departement_demo,
                       @RequestParam(value = "commune_demo", required = false) String commune_demo,
                       @RequestParam(value = "sectionCommunale_demo", required = false) String sectionCommunale_demo,
                       @RequestParam(value = "localite_demo", required = false) String localite_demo,
                       @RequestParam(value = "additionalAdresses_demo", required = false) String additionalAdresses_demo,


                       @RequestParam(value = "pcontactName", required = false) String pcontactName,
                       @RequestParam(value = "pcontactPhone", required = false) String pcontactPhone,
                       @RequestParam(value = "prelation", required = false) String prelation,

                       @RequestParam(value = "departement_pcontact", required = false) String departement_pcontact,
                       @RequestParam(value = "commune_pcontact", required = false) String commune_pcontact,
                       @RequestParam(value = "sectionCommunale_pcontact", required = false) String sectionCommunale_pcontact,
                       @RequestParam(value = "localite_pcontact", required = false) String localite_pcontact,
                       @RequestParam(value = "additionalAdresses_pcontact", required = false) String additionalAdresses_pcontact,


                       @RequestParam(value = "contactName_presp", required = false) String contactName_presp,
                       @RequestParam(value = "contactPhone_presp", required = false) String contactPhone_presp,
                       @RequestParam(value = "relation_presp", required = false) String relation_presp,

                       @RequestParam(value = "departement_presponsable", required = false) String departement_presponsable,
                       @RequestParam(value = "commune_presponsable", required = false) String commune_presponsable,
                       @RequestParam(value = "sectionCommunale_presponsable", required = false) String sectionCommunale_presponsable,
                       @RequestParam(value = "localite_presponsable", required = false) String localite_presponsable,
                       @RequestParam(value = "additionalAdresses_presponsable", required = false) String additionalAdresses_presponsable) {


        Location sessionLocation = sessionContext.getSessionLocation();

        if (sessionLocation == null)
            return "redirect:login.htm";

        RegistrationService registrationService = Context.getService(RegistrationService.class);
        List<RegistrationRelationship> relationshipList = registrationService.getAllRelationships(sessionContext.getLocale().getLanguage().trim());
        List<LocationAddressMirror> locationAddressMirrors = registrationService.getAllLocationAddressesByCriteria("");

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

            /** Phone number by patient */
            if (!contactPhone.trim().isEmpty()) {
                PersonAttributeType personAttributeType = personService.getPersonAttributeTypeByUuid("14d4f066-15f5-102d-96e4-000c29c2a5d7");
                PersonAttribute personAttribute = new PersonAttribute(personAttributeType, contactPhone.trim());
                personAttribute.setUuid(UUID.randomUUID().toString());
                personAttribute.setCreator(sessionContext.getCurrentUser());
                personAttribute.setDateCreated(now);
                personAttribute.setVoided(false);
                person.addAttribute(personAttribute);
            }

            /** Firstname and lastname by patient */
            PersonName personName = new PersonName(lastName.trim(), null, firstName.trim());
            personName.setPreferred(true);
            personName.setCreator(sessionContext.getCurrentUser());
            personName.setDateCreated(now);
            personName.setVoided(false);
            personName.setUuid(UUID.randomUUID().toString());
            person.addName(personName);

            /** The birth Place by patient */
            if (!departement_birthdate.trim().isEmpty() || !commune_birthdate.trim().isEmpty() || !sectionCommunale_birthdate.trim().isEmpty() || !localite_birthdate.trim().isEmpty()) {
                PersonAddress personAddress = new PersonAddress();
                personAddress.setCreator(sessionContext.getCurrentUser());
                personAddress.setDateCreated(now);
                personAddress.setUuid(UUID.randomUUID().toString());
                personAddress.setVoided(false);
                personAddress.setPreferred(true);
                personAddress.setCountry("Haiti");
                personAddress.setStateProvince(departement_birthdate);
                personAddress.setCityVillage(commune_birthdate);
                personAddress.setAddress3(sectionCommunale_birthdate);
                personAddress.setAddress1(localite_birthdate);
                person.addAddress(personAddress);
            }

            Person personSave = personService.savePerson(person);

            if (personSave != null) {
                PatientIdentifierType patientIdentifierType = patientService.getPatientIdentifierTypeByUuid("05a29f94-c0ed-11e2-94be-8c13b969e334");

                /** Generated the automatic iSantePlus ID by patient */
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

                /** ST code by patient */
                if (!codeST.isEmpty()) {
                    PatientIdentifierType patientIdentifierTypeCodeST = patientService.getPatientIdentifierTypeByUuid("d059f6d0-9e42-4760-8de1-8316b48bc5f1");
                    PatientIdentifier identifier = new PatientIdentifier(codeST, patientIdentifierTypeCodeST, sessionContext.getSessionLocation());
                    identifier.setCreator(sessionContext.getCurrentUser());
                    identifier.setDateCreated(personSave.getDateCreated());
                    identifier.setVoided(false);
                    identifier.setPreferred(true);
                    identifier.setUuid(UUID.randomUUID().toString());
                    patient.addIdentifier(identifier);
                }
                /** PC code by patient */
                if (!codePC.isEmpty()) {
                    PatientIdentifierType patientIdentifierTypeCodeST = patientService.getPatientIdentifierTypeByUuid("b7a154fd-0097-4071-ac09-af11ee7e0310");
                    PatientIdentifier identifier = new PatientIdentifier(codePC, patientIdentifierTypeCodeST, sessionContext.getSessionLocation());
                    identifier.setCreator(sessionContext.getCurrentUser());
                    identifier.setDateCreated(personSave.getDateCreated());
                    identifier.setVoided(false);
                    identifier.setPreferred(true);
                    identifier.setUuid(UUID.randomUUID().toString());
                    patient.addIdentifier(identifier);
                }
                /** National code by patient */
                if (!codeNational.isEmpty()) {
                    PatientIdentifierType patientIdentifierTypeCodeST = patientService.getPatientIdentifierTypeByUuid("9fb4533d-4fd5-4276-875b-2ab41597f5dd");
                    PatientIdentifier identifier = new PatientIdentifier(codeNational, patientIdentifierTypeCodeST, sessionContext.getSessionLocation());
                    identifier.setCreator(sessionContext.getCurrentUser());
                    identifier.setDateCreated(personSave.getDateCreated());
                    identifier.setVoided(false);
                    identifier.setPreferred(true);
                    identifier.setUuid(UUID.randomUUID().toString());
                    patient.addIdentifier(identifier);
                }

                Patient patientSave = patientService.savePatient(patient);

                if (patientSave != null) {

                    if (!fingerprint.isEmpty()) {
                        String res = registrationService.registerPatient(fingerprint, patientSave.getUuid(), sessionContext.getSessionLocationId());
                        System.out.println("Check saved finger ::: " + res);
                    }

                    EncounterType encounterType = encounterService.getEncounterTypeByUuid("873f968a-73a8-4f9c-ac78-9f4778b751b6");

                    Encounter encounter = new Encounter();
                    encounter.setEncounterType(encounterType);
                    encounter.setPatient(patientSave);
                    encounter.setEncounterDatetime(personSave.getDateCreated());
                    encounter.setUuid(UUID.randomUUID().toString());
                    encounter.setCreator(sessionContext.getCurrentUser());
                    encounter.setLocation(sessionContext.getSessionLocation());
                    encounter.setDateCreated(personSave.getDateCreated());
                    encounter.setVoided(false);
                    encounter.setVisit(null);

                    Encounter encounterSave = encounterService.saveEncounter(encounter);

                    System.out.println("Saving encounter::::::::::::::::: " + encounterSave.getUuid());

                    if (encounterSave != null) {

                        /** Parent : Contact d’urgence (concept group) */
                        Concept contactConcept = conceptService.getConcept(RegistrationConstants.CONTACT_CONCEPT_ID);

                        Set<Obs> children = new HashSet<>();

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
                        if (!pcontactName.trim().isEmpty()) {
                            Obs nameObs = new Obs();
                            nameObs.setConcept(conceptService.getConceptByUuid(RegistrationConstants.CONTACT_NAME_CONCEPT_UUID));
                            nameObs.setValueText(pcontactName.trim());
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
                        if (!pcontactPhone.trim().isEmpty()) {
                            Obs phoneObs = new Obs();
                            phoneObs.setConcept(conceptService.getConceptByUuid(RegistrationConstants.CONTACT_PHONE_CONCEPT_UUID));
                            phoneObs.setValueText(pcontactPhone.trim());
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
                        if (!prelation.trim().isEmpty()) {
                            Obs relationObs = new Obs();
                            relationObs.setConcept(conceptService.getConceptByUuid(RegistrationConstants.CONTACT_RELATION_CONCEPT_UUID));
                            relationObs.setValueCoded(conceptService.getConcept(Integer.parseInt(prelation.trim())));
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
                    }
                }
            }
        }

        model.addAttribute("relationshipsList", relationshipList);
        model.addAttribute("locationAddressMirrors", locationAddressMirrors);

        return null;
    }

    // Méthode utilitaire pour convertir LocalDate en Date
    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
