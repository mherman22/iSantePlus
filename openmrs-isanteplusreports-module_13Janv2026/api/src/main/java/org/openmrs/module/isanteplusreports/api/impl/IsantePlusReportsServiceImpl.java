/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.isanteplusreports.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplus.IsantePlusObs;
import org.openmrs.module.isanteplusreports.IsantePlusReportsObs;
import org.openmrs.module.isanteplusreports.Item;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.api.dao.IsantePlusReportsDao;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary;
import org.openmrs.module.isanteplusreports.dataset.definitions.DdpReportByPeriodDataSetDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.definition.DefinitionContext;
import org.openmrs.module.reporting.definition.library.DefinitionLibrary;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.Indicator;
import org.openmrs.module.reporting.indicator.dimension.Dimension;
import org.openmrs.util.HandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class IsantePlusReportsServiceImpl extends BaseOpenmrsService implements IsantePlusReportsService {

    IsantePlusReportsDao dao;

    UserService userService;

    static Parameter id = new Parameter("id", "id", Integer.class);

    //EvaluationContext context = new EvaluationContext();
    //SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();

    @Autowired
    private DbSessionFactory sessionFactory;
	
	/*@Autowired
	private EmrApiProperties emrApiProperties;*/

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setDao(IsantePlusReportsDao dao) {
        this.dao = dao;
    }

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Item getItemByUuid(String uuid) throws APIException {
        return dao.getItemByUuid(uuid);
    }

    @Override
    public Item saveItem(Item item) throws APIException {
        if (item.getOwner() == null) {
            item.setOwner(userService.getUser(1));
        }

        return dao.saveItem(item);
    }

    @Override
    public CohortDefinition getCohortDefinition(String uuid) {
        return getDefinition(uuid, CohortDefinition.class);
    }

    @Override
    public Indicator getIndicator(String uuid) {
        return getDefinition(uuid, Indicator.class);
    }

    @Override
    public Dimension getDimension(String uuid) {
        return getDefinition(uuid, Dimension.class);
    }

    /**
     * @return The definition of the passed uuid and type. This will first check any
     *         DefinitionLibrary that is defined, and if none found, will check the appropriate
     *         reporting definition service
     */
    protected <T extends Definition> T getDefinition(String uuid, Class<T> type) {
        DefinitionLibrary<T> l = HandlerUtil.getPreferredHandler(DefinitionLibrary.class, type);
        if (l != null) {
            return l.getDefinition(uuid);
        }
        return DefinitionContext.getDefinitionByUuid(type, uuid);
    }

    @Override
    public DataSet patientListArvByPeriod(Integer id, String startDate, String endDate) {

        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        String result = null;
        if (id == 35) {
            result = " between 0 AND 35";
        }
        if (id == 90) {
            result = " between 36 AND 89";
        }
        if (id == 120) {
            result = " between 90 AND 120";
        }
        if (id == 180) {
            result = " between 121 AND 180";
        }
        if (id == 181) {
            result = " > 180";
        }
        //PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
        StringBuilder sqlQuery = new StringBuilder("select "
                + "distinct p.st_id as st_id, p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age, p.gender as Sexe");
        sqlQuery.append(" FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, (select pdisp.patient_id, MAX(ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date))) as visit_date FROM isanteplus.patient_dispensing pdisp WHERE pdisp.arv_drug=1065 AND pdisp.voided <> 1 AND (pdisp.rx_or_prophy = 138405 OR pdisp.rx_or_prophy is null) AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date)) BETWEEN '" + startDate + "' AND '" + endDate + "' GROUP BY 1) B  ");
        sqlQuery.append(" WHERE p.patient_id=pdis.patient_id");
        sqlQuery.append(" AND pdis.patient_id=B.patient_id");
        sqlQuery.append(" AND ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date)) = B.visit_date");
        sqlQuery.append(" AND (pdis.rx_or_prophy = 138405 OR pdis.rx_or_prophy is null)");
        sqlQuery.append(" AND B.visit_date < pdis.next_dispensation_date");
        sqlQuery.append(" AND pdis.arv_drug = 1065");
        sqlQuery.append(" AND pdis.voided <> 1");
        sqlQuery.append(" AND DATEDIFF(pdis.next_dispensation_date,ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date))) " + result);
        if (startDate != null) {
            sqlQuery.append(" AND ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date)) >= '" + startDate + "'");
        }
        if (endDate != null) {
            sqlQuery.append(" AND ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date)) <= '" + endDate + "'");
        }

        //SQLQuery query = sessionFactory.getHibernateSessionFactory().getCurrentSession().createSQLQuery(sqlQuery.toString());
        //SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("Age", "Age", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[6]);
            row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[7]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet lastSixForms(Patient p) {
        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        id.setDefaultValue(p.getPatientId());
        StringBuilder sqlQuery = new StringBuilder(
                "select distinct"
                        + " DATE_FORMAT(enc.date_created, '%d-%m-%Y') as 'Date de création',"
                        + " entype.name as Type");
        sqlQuery.append(" FROM openmrs.encounter enc, openmrs.encounter_type entype");
        sqlQuery.append(" WHERE enc.encounter_type = entype.encounter_type_id");
        sqlQuery.append(" AND TIMESTAMPDIFF(MONTH, DATE(enc.date_created),DATE(now())) <= 6");
        sqlQuery.append(" AND entype.uuid NOT IN('17536ba6-dd7c-4f58-8014-08c7cb798ac7',"
                + "'349ae0b4-65c1-4122-aa06-480f186c8350','709610ff-5e39-4a47-9c27-a60e740b0944',"
                + "'5c312603-25c1-4dbe-be18-1a167eb85f97','873f968a-73a8-4f9c-ac78-9f4778b751b6','12f4d7c3-e047-4455-a607-47a40fe32460')");
        sqlQuery.append(" AND enc.patient_id = '" + p.getPatientId() + "'");
        sqlQuery.append(" ORDER BY DATE(enc.date_created) DESC");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("creation", "creation", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("fiches", "fiches", String.class), o[1]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet firstVisitForms(Patient p) {
        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        id.setDefaultValue(p.getPatientId());
        StringBuilder sqlQuery = new StringBuilder(
                "select distinct"
                        + " DATE_FORMAT(enc.date_created, '%d-%m-%Y') as 'Date de création',"
                        + " entype.name as Type");
        sqlQuery.append(" FROM openmrs.encounter enc, openmrs.encounter_type entype");
        sqlQuery.append(" WHERE enc.encounter_type = entype.encounter_type_id");
        sqlQuery.append(" AND entype.uuid IN('17536ba6-dd7c-4f58-8014-08c7cb798ac7',"
                + "'349ae0b4-65c1-4122-aa06-480f186c8350','709610ff-5e39-4a47-9c27-a60e740b0944',"
                + "'5c312603-25c1-4dbe-be18-1a167eb85f97','12f4d7c3-e047-4455-a607-47a40fe32460')");
        sqlQuery.append(" AND enc.patient_id = '" + p.getPatientId() + "'");
        sqlQuery.append(" ORDER BY DATE(enc.date_created) DESC");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("creationDate", "creationDate", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("ficheName", "ficheName", String.class), o[1]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public List<IsantePlusReportsObs> getLabsResult(Patient patient) {
        // TESTS ORDERED = 1271
        List<IsantePlusReportsObs> labHistory = new ArrayList<IsantePlusReportsObs>();
        Integer labConceptId = 1271;
        Concept testsOrdered = Context.getConceptService().getConcept(labConceptId);
        int i = 0;
        for (Obs obs : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), testsOrdered)) {
            if (obs != null) {

                //Integer result = Integer.parseInt(obs.getValueCoded().toString());
                Integer result = obs.getValueCoded().getConceptId();
                Concept resultTest = Context.getConceptService().getConcept(result);

                for (Obs obs1 : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), resultTest)) {
                    if (obs.getEncounter().getEncounterId() == obs1.getEncounter().getEncounterId()) {
                        IsantePlusReportsObs obsres = new IsantePlusReportsObs(obs1);
                        if (i <= 25) {
                            labHistory.add(obsres);
                        }
                        i = i + 1;
                    }

                }
            }
        }
        return labHistory;
    }

    @Override
    public List<Obs> getMotifsConsultattionLastSixMonths(Patient patient) {
        List<Obs> motifsConsultationList = new ArrayList<Obs>();
        Integer motifsConsultation = 159614;
        int i = 0;
        Concept testsOrdered = Context.getConceptService().getConcept(motifsConsultation);
        for (Obs obs : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), testsOrdered)) {
            if (obs != null) {
                if (i <= 25) {
                    motifsConsultationList.add(obs);
                }
                i = i + 1;
            }
        }
        return motifsConsultationList;
    }

    @Override
    public List<Obs> getImpressionsCliniques(Patient patient) {
        List<Obs> impressionsCliniquesList = new ArrayList<Obs>();
        Integer impressionsCliniques = 1284;
        int i = 0;
        Concept testsOrdered = Context.getConceptService().getConcept(impressionsCliniques);
        for (Obs obs : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), testsOrdered)) {
            if (obs != null && obs.getValueGroupId() != null) {
                if (i <= 25) {
                    impressionsCliniquesList.add(obs);
                }
                i = i + 1;
            }
        }
        return impressionsCliniquesList;
    }

    @Override
    public List<Obs> getDispensingDrugs(Patient patient) {
        List<Obs> drugsHistory = new ArrayList<Obs>();
        Integer drugsConceptId = 1282;
        Integer dateDrugsConceptId = 1276;
        Concept drugsDispensed = Context.getConceptService().getConcept(drugsConceptId);
        Concept dateDispensed = Context.getConceptService().getConcept(dateDrugsConceptId);
        int i = 0;
        for (Obs obs0 : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), drugsDispensed)) {

            if (obs0 != null && obs0.getObsGroup().getConcept().getConceptId() == 163711) {
                Obs obs = Obs.newInstance(obs0);
                for (Obs obs1 : Context.getObsService().getObservationsByPersonAndConcept(patient.getPerson(), dateDispensed)) {
                    if (obs1 != null) {
                        if (obs0.getObsGroup() == obs1.getObsGroup()) {
                            obs.setObsDatetime(obs1.getObsDatetime());

                        }
                    }
                }
                if (i <= 25) {
                    drugsHistory.add(obs);
                }

                i = i + 1;
            }
        }
        return drugsHistory;
    }

    @Override
    public DataSet clinicExams(Patient p) {
        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        id.setDefaultValue(p.getPatientId());
        StringBuilder sqlQuery = new StringBuilder(
                "select distinct"
                        + " DATE_FORMAT(DATE(ob.obs_datetime), '%d-%m-%Y') as 'Date examen', cn.name as 'Nom du test',"
                        + " CASE WHEN ob.value_coded=1115 THEN 'Normal'"
                        + " WHEN ob.value_coded=1116 THEN 'Anormal'"
                        + " WHEN ob.value_coded=1118 THEN 'Pas conduit'"
                        + " END as Résultat");
        sqlQuery.append(" FROM openmrs.obs ob, openmrs.concept_name cn");
        sqlQuery.append(" WHERE cn.concept_id = ob.concept_id");
        sqlQuery.append(" AND ob.value_coded IN (1115,1116,1118)");
        sqlQuery.append(" AND cn.locale='fr'");
        sqlQuery.append(" AND ob.person_id = '" + p.getPatientId() + "'");
        sqlQuery.append(" GROUP BY 1,2,3");
        sqlQuery.append(" ORDER BY ob.obs_datetime DESC");
        sqlQuery.append(" LIMIT 20");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("date_exams", "date_exams", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("clinic_exams", "clinic_exams", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("resultat", "resultat", String.class), o[2]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public void setEventScheduler() {
        StringBuilder sqlQuery2 = new StringBuilder(
                "CALL isanteplus.set_scheduler_and_lock_wait_variable()");
        SQLQuery query2 = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery2.toString());
        query2.executeUpdate();

    }

    @Override
    public DataSet alertReport() {
        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        StringBuilder sqlQuery = new StringBuilder(
                "select distinct"
                        + " al.id, al.libelle as Alert, count(a.id_alert) as Total"
                        + " FROM openmrs.alert a, isanteplus.alert_lookup al"
                        + " WHERE a.id_alert = al.id"
                        + " GROUP BY al.id");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("id_alert", "id_alert", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("Alert", "Alert", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("Total", "Total", String.class), o[2]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet alertReportPatientList(Integer id) {
        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();
        StringBuilder sqlQuery = new StringBuilder(
                "select distinct"
                        + " distinct p.st_id as st_id, p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age, p.gender as Sexe"
                        + " FROM isanteplus.patient p, openmrs.alert a"
                        + " WHERE p.patient_id = a.patient_id"
                        + " AND a.id_alert = '" + id + "'");
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        //query.setInteger("primaryIdentifierType", primaryIdentifierType.getId());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("Age", "Age", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[6]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet weeklyMonitoringReportpatientList(Integer id, String startDate, String endDate) {

        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();

        //PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
        StringBuilder sqlQuery = new StringBuilder("select "
                + "distinct p.st_id as st_id, p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age, p.gender as Sexe");
        sqlQuery.append(" FROM isanteplus.patient p, isanteplus.indicators ind");
        sqlQuery.append(" WHERE p.patient_id = ind.patient_id");
        sqlQuery.append(" AND ind.indicator_type_id = " + id);
        if (startDate != null) {
            sqlQuery.append(" AND DATE(ind.indicator_date) >= '" + startDate + "'");
        }
        if (endDate != null) {
            sqlQuery.append(" AND DATE(ind.indicator_date) <= '" + endDate + "'");
        }

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("Age", "Age", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[6]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet patientImmunizationDoses(Patient p) {

        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();

        StringBuilder sqlQuery = new StringBuilder("select "
                + "distinct il.vaccine_name as Nom, id.dose0, id.dose1, id.dose2, id.dose3, id.dose4, id.dose5");
        sqlQuery.append(" FROM isanteplus.immunization_lookup il");
        sqlQuery.append(" LEFT OUTER JOIN isanteplus.immunization_dose id");
        sqlQuery.append(" ON il.vaccine_concept_id = id.vaccine_concept_id");
        sqlQuery.append(" AND id.patient_id = " + p.getPatientId());
        sqlQuery.append(" ORDER BY il.id ASC");

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("Dose0", "Dose0", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("Dose1", "Dose1", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("Dose2", "Dose2", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("Dose3", "Dose3", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("Dose4", "Dose4", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("Dose5", "Dose5", String.class), o[6]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public DataSet psychoSocialSummary(Patient p) {

        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();

        StringBuilder sqlQuery = new StringBuilder("select "
                + "distinct c.patient_id, c.visitDate, c.obstaclesRemarks");
        sqlQuery.append(" FROM isanteplus.comprehension c");
        sqlQuery.append(" WHERE c.obstaclesRemarks IS NOT NULL");
        sqlQuery.append(" AND c.obstaclesRemarks <> ''");
        sqlQuery.append(" AND c.patient_id = " + p.getPatientId());
        sqlQuery.append(" ORDER BY c.visitDate ASC");

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("visit_date", "visit_date", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("remarks", "remarks", String.class), o[2]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DataSet patientListDdpByPeriod(int id, String datA, String datB) {

        EvaluationContext context = new EvaluationContext();
        SqlDataSetDefinition dataSetDefinition = new SqlDataSetDefinition();

        StringBuilder sqlQuery = new StringBuilder(
                "select "
                        + "distinct p.st_id as st_id, p.patient_id, p.national_id as national_id, p.given_name as Prénom, p.family_name as Nom, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Age, p.gender as Sexe"
                        + " FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp,"
                        + " (select pdi.patient_id,max(ifnull(DATE(pdi.dispensation_date),DATE(pdi.visit_date))) as visit_date from isanteplus.patient_dispensing pdi where pdi.arv_drug = 1065 AND pdi.voided <> 1 AND ifnull(DATE(pdi.dispensation_date),DATE(pdi.visit_date)) BETWEEN '" + datA + "' AND '" + datB + "' group by 1)B"
                        + " WHERE p.patient_id = pdisp.patient_id"
                        + " AND pdisp.patient_id = B.patient_id"
                        + " AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date)) = B.visit_date"
                        + " AND pdisp.arv_drug = 1065"
                        + " AND pdisp.voided <> 1"
                        + " AND pdisp.ddp = '" + id + "'"
                        + " AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date)) BETWEEN '" + datA + "' AND '" + datB + "'");

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sqlQuery.toString());
        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("ID_National", "ID_National", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("Nom", "Nom", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("Prenom", "Prenom", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("Age", "Age", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("Sexe", "Sexe", String.class), o[6]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public List<org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary> getComorbidityReport(String sqlFile, Date startDate, Date endDate) {

        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary> patientList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary patient = new org.openmrs.module.isanteplusreports.comorbidity.model.PatientSummary();

            if (row.get("patient_id") != null)
                patient.setId(Integer.parseInt(row.get("patient_id").toString()));

            patientList.add(patient);
        }

        return patientList;
    }

    @Override
    public List<org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary> getPsychosocialReport(String sqlFile, Date startDate, Date endDate) {

        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary> patientList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary patient = new org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary();

            if (row.get("patient_id") != null)
                patient.setId(Integer.parseInt(row.get("patient_id").toString()));

            patientList.add(patient);
        }

        return patientList;
    }

    @Override
    public List<org.openmrs.module.isanteplusreports.derlSurveillance.model.Indicator> getDerlSurveillanceReport(String sqlFile, Date startDate, Date endDate) {
        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<org.openmrs.module.isanteplusreports.derlSurveillance.model.Indicator> indicatorList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            org.openmrs.module.isanteplusreports.derlSurveillance.model.Indicator indicator = new org.openmrs.module.isanteplusreports.derlSurveillance.model.Indicator();

            if (row.get("indicatorId") != null)
                indicator.setIndicatorId(((Number) row.get("indicatorId")).intValue());

            if (row.get("indicatorTypeId") != null)
                indicator.setIndicatorTypeId(((Number) row.get("indicatorTypeId")).intValue());

            if (row.get("patientId") != null)
                indicator.setPatientId(((Number) row.get("patientId")).intValue());

            if (row.get("locationId") != null)
                indicator.setLocationId(((Number) row.get("locationId")).intValue());

            if (row.get("encounterId") != null)
                indicator.setEncounterId(((Number) row.get("encounterId")).intValue());

            if (row.get("indicatorDate") != null)
                indicator.setIndicatorDate(getDateTimeFormat(row.get("indicatorDate").toString()));

            indicatorList.add(indicator);
        }

        return indicatorList;
    }


    @Override
    public List<PatientSummary> getPatientsByIdentifiersComorbidity(String sqlFile, String ids) {

        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        query.setParameterList("ids", idList);

        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<PatientSummary> patientList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            PatientSummary patient = new PatientSummary();

            if (row.get("identifier") != null)
                patient.setIdentifier(row.get("identifier").toString());
            if (row.get("st_id") != null)
                patient.setStId(row.get("st_id").toString());
            if (row.get("national_id") != null)
                patient.setNationalId(row.get("national_id").toString());
            if (row.get("isante_id") != null)
                patient.setIsanteId(row.get("isante_id").toString());
            if (row.get("patient_id") != null)
                patient.setId(Integer.parseInt(row.get("patient_id").toString()));
            if (row.get("location_id") != null)
                patient.setLocationId(Integer.parseInt(row.get("location_id").toString()));
            if (row.get("site_code") != null)
                patient.setSiteCode(row.get("site_code").toString());
            if (row.get("given_name") != null)
                patient.setGivenName(row.get("given_name").toString());
            if (row.get("family_name") != null)
                patient.setFamilyName(row.get("family_name").toString());
            if (row.get("gender") != null)
                patient.setGender(row.get("gender").toString());
            if (row.get("birthdate") != null)
                patient.setBirthdate(getDateFormat(row.get("birthdate").toString()));
            if (row.get("telephone") != null)
                patient.setTelephone(row.get("telephone").toString());
            if (row.get("last_address") != null)
                patient.setLastAddress(row.get("last_address").toString());
            if (row.get("vih_status") != null)
                patient.setVihStatus(Integer.parseInt(row.get("vih_status").toString()));
            if (row.get("arv_status") != null)
                patient.setArvStatus(Integer.parseInt(row.get("arv_status").toString()));

            patientList.add(patient);
        }

        return patientList;
    }

    @Override
    public List<org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary> getPatientsByIdentifiersPsychosocial(String sqlFile, String ids) {

        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        query.setParameterList("ids", idList);

        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary> patientList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary patient = new org.openmrs.module.isanteplusreports.psychosocial.model.PatientSummary();

            if (row.get("identifier") != null)
                patient.setIdentifier(row.get("identifier").toString());
            if (row.get("st_id") != null)
                patient.setStId(row.get("st_id").toString());
            if (row.get("national_id") != null)
                patient.setNationalId(row.get("national_id").toString());
            if (row.get("isante_id") != null)
                patient.setIsanteId(row.get("isante_id").toString());
            if (row.get("patient_id") != null)
                patient.setId(Integer.parseInt(row.get("patient_id").toString()));
            if (row.get("location_id") != null)
                patient.setLocationId(Integer.parseInt(row.get("location_id").toString()));
            if (row.get("site_code") != null)
                patient.setSiteCode(row.get("site_code").toString());
            if (row.get("given_name") != null)
                patient.setGivenName(row.get("given_name").toString());
            if (row.get("family_name") != null)
                patient.setFamilyName(row.get("family_name").toString());
            if (row.get("gender") != null)
                patient.setGender(row.get("gender").toString());
            if (row.get("birthdate") != null)
                patient.setBirthdate(getDateFormat(row.get("birthdate").toString()));
            if (row.get("telephone") != null)
                patient.setTelephone(row.get("telephone").toString());
            if (row.get("last_address") != null)
                patient.setLastAddress(row.get("last_address").toString());
            if (row.get("vih_status") != null)
                patient.setVihStatus(Integer.parseInt(row.get("vih_status").toString()));
            if (row.get("arv_status") != null)
                patient.setArvStatus(Integer.parseInt(row.get("arv_status").toString()));

            patientList.add(patient);
        }

        return patientList;
    }

    @Override
    public List<org.openmrs.module.isanteplusreports.derlSurveillance.model.PatientSummary> getPatientsByIdentifiersDerlSurveillance(String sqlFile, String ids) {

        String sql = loadSqlFile(sqlFile);

        SQLQuery query = dao.getSessionFactoryResult().createSQLQuery(sql);

        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        query.setParameterList("ids", idList);

        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

        List<Map<String, Object>> list = query.list();
        List<org.openmrs.module.isanteplusreports.derlSurveillance.model.PatientSummary> patientList = new ArrayList<>();

        for (Map<String, Object> row : list) {
            org.openmrs.module.isanteplusreports.derlSurveillance.model.PatientSummary patient = new org.openmrs.module.isanteplusreports.derlSurveillance.model.PatientSummary();

            if (row.get("identifier") != null)
                patient.setIdentifier(row.get("identifier").toString());
            if (row.get("st_id") != null)
                patient.setStId(row.get("st_id").toString());
            if (row.get("national_id") != null)
                patient.setNationalId(row.get("national_id").toString());
            if (row.get("isante_id") != null)
                patient.setIsanteId(row.get("isante_id").toString());
            if (row.get("patient_id") != null)
                patient.setId(Integer.parseInt(row.get("patient_id").toString()));
            if (row.get("location_id") != null)
                patient.setLocationId(Integer.parseInt(row.get("location_id").toString()));
            if (row.get("site_code") != null)
                patient.setSiteCode(row.get("site_code").toString());
            if (row.get("given_name") != null)
                patient.setGivenName(row.get("given_name").toString());
            if (row.get("family_name") != null)
                patient.setFamilyName(row.get("family_name").toString());
            if (row.get("gender") != null)
                patient.setGender(row.get("gender").toString());
            if (row.get("birthdate") != null)
                patient.setBirthdate(getDateFormat(row.get("birthdate").toString()));
            if (row.get("telephone") != null)
                patient.setTelephone(row.get("telephone").toString());
            if (row.get("last_address") != null)
                patient.setLastAddress(row.get("last_address").toString());
            if (row.get("vih_status") != null)
                patient.setVihStatus(Integer.parseInt(row.get("vih_status").toString()));
            if (row.get("arv_status") != null)
                patient.setArvStatus(Integer.parseInt(row.get("arv_status").toString()));

            patientList.add(patient);
        }

        return patientList;
    }

    private LocalDate getDateFormat(String date) {
        if (date == null)
            return null;
        return LocalDate.parse(date);
    }

    private LocalDate getDateTimeFormat(String date) {
        if (date == null) return null;
        if (date.contains(" ")) {
            date = date.split(" ")[0];
        }
        return LocalDate.parse(date);
    }

    public String loadSqlFile(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("Fichier SQL introuvable : " + path);
            }
            return IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture fichier SQL", e);
        }
    }
}
