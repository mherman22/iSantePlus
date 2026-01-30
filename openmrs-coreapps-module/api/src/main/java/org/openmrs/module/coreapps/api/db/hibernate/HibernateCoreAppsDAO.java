package org.openmrs.module.coreapps.api.db.hibernate;

import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.coreapps.CoreAppsConstants;
import org.openmrs.module.coreapps.IsantePlusRelationship;
import org.openmrs.module.coreapps.api.db.CoreAppsDAO;

import java.util.ArrayList;
import java.util.List;

public class HibernateCoreAppsDAO implements CoreAppsDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<IsantePlusRelationship> getRelationships(String locale) {
        // ✅ ouvrir la session correctement
        Session session = sessionFactory.getCurrentSession();

        String sql = "SELECT ca.answer_concept, cn.name, cn.locale, ca.concept_id AS family_member " +
                "FROM openmrs.concept_answer ca, openmrs.concept_name cn " +
                "WHERE ca.answer_concept = cn.concept_id " +
                "AND ca.concept_id = :conceptId " +
                "AND cn.locale LIKE :locale";

        List<Object[]> rows = session.createSQLQuery(sql)
                .setParameter("conceptId", CoreAppsConstants.RELATIONSHIPS_CONCEPT_ID)
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
}
