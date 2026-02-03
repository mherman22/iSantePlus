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
package org.openmrs.module.isanteplus.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.ComponentState;
import org.openmrs.module.isanteplus.IsantePlusConstants;
import org.openmrs.module.isanteplus.IsantePlusRelationship;
import org.openmrs.module.isanteplus.LocationAddressMirror;
import org.openmrs.module.isanteplus.api.db.IsantePlusDAO;
import org.openmrs.module.isanteplus.mapped.FormHistory;

/**
 * It is a default implementation of {@link IsantePlusDAO}.
 */
public class HibernateIsantePlusDAO implements IsantePlusDAO {

    protected final Log log = LogFactory.getLog(this.getClass());

    private SessionFactory sessionFactory;

    /**
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public FormHistory getFormHistory(Integer formHistoryId) {
        return (FormHistory) getSessionFactory().getCurrentSession().get(FormHistory.class, formHistoryId);
    }

    @Override
    public FormHistory getFormHistoryByUuid(String formHistoryUuid) {
        return (FormHistory) getSessionFactory().getCurrentSession()
                .createQuery("from FormHistory fh where fh.uuid = :uuid").setString("uuid", formHistoryUuid)
                .uniqueResult();
    }

    @Override
    public void deleteFormHistory(FormHistory formHistory) {
        /*getSessionFactory().getCurrentSession().delete(formHistory);*/
        Encounter encounter = formHistory.getEncounter();
        Context.getEncounterService().voidEncounter(encounter, "delete encounter");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormHistory> getAllFormHistory() {
        return getSessionFactory().getCurrentSession().createCriteria(FormHistory.class).list();
    }

    /* this method was added to resolve slow issue*/
    @Override
    @SuppressWarnings("unchecked")
    public List<FormHistory> getAllFormHistory(Patient patient) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
                .createAlias("patient", "p").add(Restrictions.eq("p.patientId", patient.getPatientId()));
        Criteria crit1 = crit.createCriteria("encounter");
        crit1.addOrder(Order.desc("encounterDatetime"));
        return crit.list();
    }

    /* this method was added to resolve slow issue*/
    @Override
    @SuppressWarnings("unchecked")
    public List<FormHistory> getAllFormHistory(Visit visit) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
                .createAlias("visit", "v").add(Restrictions.eq("v.visitId", visit.getVisitId()));
        Criteria crit1 = crit.createCriteria("encounter");
        crit1.addOrder(Order.desc("encounterDatetime"));
        return crit.list();
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<Encounter> getAllEncounters() {
        return getSessionFactory().getCurrentSession().createCriteria(Encounter.class).list();
    }

    @Override
    public FormHistory saveFormHistory(FormHistory formHistory) {
        sessionFactory.getCurrentSession().saveOrUpdate(formHistory);
        return formHistory;
    }

    @Override
    public ComponentState getAppframeworkComponentState(String componentSateId) {
        ComponentState componentSate = (ComponentState) getSessionFactory().getCurrentSession()
                .createQuery("from ComponentState cs where cs.componentId = :component_id")
                .setString("component_id", componentSateId).uniqueResult();

        return componentSate;
    }

    @Override
    public ComponentState saveOrUpdateComponentState(ComponentState componentState) {
        sessionFactory.getCurrentSession().saveOrUpdate(componentState);
        return componentState;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormHistory> getFormHistoryByEncounterId(Integer encounterId) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(FormHistory.class)
                .createAlias("encounter", "e").add(Restrictions.eq("e.encounterId", encounterId));

        return crit.list();
    }

    @Override
    public SessionFactory getSessionFactoryResult() {
        return sessionFactory;
    }

    @Override
    public List<IsantePlusRelationship> getRelationships(String locale) {
        String sql = "SELECT ca.answer_concept, cn.name, cn.locale, ca.concept_id AS family_member " +
                "FROM openmrs.concept_answer ca, openmrs.concept_name cn " +
                "WHERE ca.answer_concept = cn.concept_id " +
                "AND ca.concept_id = :conceptId " +
                "AND cn.locale LIKE :locale";

        List<Object[]> rows = sessionFactory.getCurrentSession()
                .createSQLQuery(sql)
                .setParameter("conceptId", IsantePlusConstants.RELATIONSHIPS_CONCEPT_ID)
                .setParameter("locale", "%" + locale + "%")
                .list();

        List<IsantePlusRelationship> relationships = new ArrayList<>();

        for (Object[] row : rows) {
            Integer answerConcept = ((Number) row[0]).intValue();
            String name = (String) row[1];
            String localeVal = (String) row[2];
            Integer familyMember = ((Number) row[3]).intValue();
            relationships.add(new IsantePlusRelationship(answerConcept, name, localeVal, familyMember));
        }
        return relationships;
    }

    @Override
    public List<LocationAddressMirror> getLocationAddresses(String criteria) {

        String sql = "SELECT l1.id AS id, " +
                "       l1.name AS locality, " +
                "       l2.name AS section_communale, " +
                "       l3.name AS commune, " +
                "       l4.name AS department, " +
                "       l5.name AS country " +
                "FROM isanteplus.location_address l1 " +
                "INNER JOIN isanteplus.location_address l2 ON l2.id = l1.parent " +
                "INNER JOIN isanteplus.location_address l3 ON l3.id = l2.parent " +
                "INNER JOIN isanteplus.location_address l4 ON l4.id = l3.parent " +
                "INNER JOIN isanteplus.location_address l5 ON l5.id = l4.parent " +
                "WHERE l1.name LIKE :criteria " +
                "   OR l2.name LIKE :criteria " +
                "   OR l3.name LIKE :criteria " +
                "   OR l4.name LIKE :criteria " +
                "   OR l5.name LIKE :criteria";

        List<Object[]> rows = sessionFactory.getCurrentSession()
                .createSQLQuery(sql)
                .setParameter("criteria", "%" + criteria + "%")
                .list();

        List<LocationAddressMirror> locationAddresses = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String locality = (String) row[1];
            String sectionCommunale = (String) row[2];
            String commune = (String) row[3];
            String department = (String) row[4];
            String country = (String) row[5];
            locationAddresses.add(new LocationAddressMirror(id, locality, sectionCommunale, commune, department, country));
        }

        return locationAddresses;
    }

}