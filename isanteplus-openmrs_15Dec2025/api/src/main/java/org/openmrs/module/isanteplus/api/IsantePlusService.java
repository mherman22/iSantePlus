/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.isanteplus.api;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.*;
import org.openmrs.api.*;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.isanteplus.*;
import org.openmrs.module.isanteplus.liquibase.InitialiseFormsHistory;
import org.openmrs.module.isanteplus.mapped.FormHistory;
import org.openmrs.module.reporting.dataset.DataSet;

import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

/**
 * This service exposes module's core functionality. It is a Spring managed bean
 * which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(IsantePlusPatientDashboardService.class).someMethod();
 * </code>
 *
 * @see org.openmrs.api.context.Context
 */
public interface IsantePlusService extends OpenmrsService {

    Obs getLastViralLoadTestResultObsForPatient(Patient patient);

    JSONArray getPatientWeights(Patient patient);

    JSONArray getPatientHeights(Patient patient);

    Integer getPatientAgeInMonths(Patient patient);

    Integer getPatientAgeInDays(Patient patient);

    JSONArray getHeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);

    JSONArray getWeightsAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);

    JSONArray getPatientHeadCircumferences(Patient patient);

    JSONArray getHeadCircumferenceAtGivenPatientAges(Patient patient, ChartJSAgeAxis ageAxis);

    /**
     * Should only be run by {@link InitialiseFormsHistory}
     *
     * @throws SQLException
     * @throws DatabaseException
     */
    void runInitialHistoryCreatorAgainstDB(JdbcConnection connection) throws DatabaseException, SQLException;

    FormHistory getFormHistory(Integer formHistoryId);

    FormHistory getFormHistoryByUuid(String formHistoryUuid);

    void deleteFormHistory(FormHistory formHistory);

    List<FormHistory> getAllFormHistory();

    /* this method was added to resolve slow issue*/
    List<FormHistory> getAllFormHistory(Patient patient);
    List<FormHistory> getAllFormHistory(Visit visit);

    FormHistory saveFormHistory(FormHistory formHistory);

    boolean formHistoryExist(FormHistory formHistory, List<FormHistory> formHistoryLookup);

    List<FormHistory> getOnlyIsanteFormHistories();
    List<FormHistory> getOnlyIsanteFormHistories(Visit visit);

    List<FormHistory> getOnlyIsanteFormHistoriesByVisit(Visit visit);

    List<FormHistory> getAllFormHistoryByVisit(Visit visit);

    FormHistory createBasicFormHistoryObject(Encounter encounter, boolean contextUp);

    List<FormHistory> getAllFormHistoryForAPatient(Patient patient);

    List<IsantePlusObs> getLabsHistory(Patient patient);

    ComponentState getAppframeworkComponentState(String componentSateId);

    void updateComponentStates(JSONObject extensions);

    ComponentState saveOrUpdateComponentState(ComponentState componentState);

    Obs getLatestHeightForPatient(Patient patient);

    Obs getLatestWeightForPatient(Patient patient);

    Obs getLatestPulseForPatient(Patient patient);

    Obs getLatestRespiratoryRateForPatient(Patient patient);

    Obs getLatestDiastolicBloodPressureForPatient(Patient patient);

    Obs getLatestBloodOxygenSaturationForPatient(Patient patient);

    Obs getLatestTemperatureForPatient(Patient patient);

    double roundAbout(double value, int places);

    Obs getLatestSystolicBloodPressureForPatient(Patient patient);

    Obs getLatestMidUpperArmCircumferenceForPatient(Patient patient);

    void toggleRecentVitalsSection(Boolean enableIsanteVitals);

    List<Obs> getDrugsHistory(Patient patient);

    JSONArray getPatientBmi(Patient patient);

    JSONArray getPatientBMIsAcrossAnAgeDifference(Patient patient, ChartJSAgeAxis ageAxis);

    List<FormHistory> getFormHistoryByEncounterId(Integer encounterId);

    JSONArray getPatientViralLoad(Patient patient);

    Obs getLatestNextVisitDate(Patient patient);

    Encounter getFirstEncounterDate(Patient patient);

    Obs getArtInitiationDate(Patient patient);

    DataSet getPatientStatusArv(Patient patient);

    DataSet getDateStartedArv(Patient patient);

    List<IsantePlusObs> getViralLoadHistory(Patient patient);

    List<IsantePlusObs> getAllDiagnosis(Patient patient);

    Obs getLastViralLoadQualitativeObsForPatient(Patient patient);

    Obs getLatestNextOtherVisitDate(Patient patient);

    Obs getLatestNextOrdonanceVisitDate(Patient patient);

    List<Obs> getAllergiesForPatient(Patient patient);

    String getEncounterImmunizationUuidByPatient(Patient patient);

    List<Obs> getAllDiagnosisByPatient(Patient patient);

    List<IsantePlusRelationship> getAllRelationships(String locale);

    List<LocationAddressMirror> getAllLocationAddressesByCriteria(String criteria);

	List<PatientSearchInfos> getAllPatientSearchInfos(String var1);
    Map<String, List<LocationAddress>> getLocationAddressesGroupedByNature(List<String> natures);

    Person createPerson(PersonService personService,
                        UiSessionContext sessionContext,
                        Date now,
                        String firstName,
                        String lastName,
                        String gender,
                        LocalDate birthDate);

    Patient createPatient(PatientService patientService,
                          UiSessionContext sessionContext,
                          Person person);

    Visit createVisit(VisitService visitService,
                      UiSessionContext sessionContext,
                      Patient patient);

    Encounter createEncounter(EncounterService encounterService,
                              FormService formService,
                              UiSessionContext sessionContext,
                              Patient patient,
                              Visit visit);

    void saveNumericObs(ObsService obsService,
                        ConceptService conceptService,
                        UiSessionContext sessionContext,
                        Person person,
                        Encounter encounter,
                        Integer conceptId,
                        Double value);

    void saveVitalSigns(ObsService obsService,
                        ConceptService conceptService,
                        UiSessionContext sessionContext,
                        Person person,
                        Encounter encounter,
                        Double poids, Double taille, Double fr, Double sao2,
                        Double fc, Double tasys, Double tadias,
                        Double glycemie, Double temperature,
                        Double pc, Double pb,
                        Double ge, Double gv, Double gm,
                        String typeDouleur, Double scoreDouleur);

    void saveEmergencyContact(ObsService obsService,
                              ConceptService conceptService,
                              UiSessionContext sessionContext,
                              Person person,
                              Encounter encounter,
                              String contactName,
                              String contactPhone,
                              String relation);

    void savePatientInformation(ObsService obsService,
                                ConceptService conceptService,
                                UiSessionContext sessionContext,
                                Person person,
                                Encounter encounter,
                                String ageGroup,
                                String arrivalModes,
                                List<String> evaluations);

    void saveMedicalDecision(ObsService obsService,
                             ConceptService conceptService,
                             UiSessionContext sessionContext,
                             Person person,
                             Encounter encounter,
                             String disposition,
                             String intervention);

    void saveSignature(ObsService obsService,
                       ConceptService conceptService,
                       UiSessionContext sessionContext,
                       Person person,
                       Encounter encounter,
                       String signature);
}