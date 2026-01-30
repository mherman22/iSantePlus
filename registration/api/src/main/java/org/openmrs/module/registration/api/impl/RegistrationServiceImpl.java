/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.registration.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.registration.*;
import org.openmrs.module.registration.api.RegistrationService;
import org.openmrs.module.registration.api.dao.RegistrationDao;
import org.openmrs.module.registration.util.BioPluginResponseParser;
import org.openmrs.module.registration.wsclient.BioPluginSoapClient;

import java.util.*;

public class RegistrationServiceImpl extends BaseOpenmrsService implements RegistrationService {

    RegistrationDao dao;

    UserService userService;

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setDao(RegistrationDao dao) {
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
    public List<RegistrationRelationship> getAllRelationships(String locale) {
        String sql = "SELECT ca.answer_concept, cn.name, cn.locale, ca.concept_id AS family_member " +
                "FROM openmrs.concept_answer ca, openmrs.concept_name cn " +
                "WHERE ca.answer_concept = cn.concept_id " +
                "AND ca.concept_id = :conceptId " +
                "AND cn.locale LIKE :locale";

        List<Object[]> rows = dao.getSession()
                .createSQLQuery(sql)
                .setParameter("conceptId", RegistrationConstants.RELATIONSHIPS_CONCEPT_ID)
                .setParameter("locale", "%" + locale + "%")
                .list();

        List<RegistrationRelationship> relationships = new ArrayList<>();

        for (Object[] row : rows) {
            Integer answerConcept = ((Number) row[0]).intValue();
            String name = (String) row[1];
            String localeVal = (String) row[2];
            Integer familyMember = ((Number) row[3]).intValue();
            relationships.add(new RegistrationRelationship(answerConcept, name, localeVal, familyMember));
        }
        return relationships;
    }

    @Override
    public List<LocationAddressMirror> getAllLocationAddressesByCriteria(String criteria) {
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
                "WHERE l1.name LIKE :criteria AND l1.nature = 'locality'";

        List<Object[]> rows = dao.getSession()
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

    @Override
    public List<LocationAddress> getLocationAddressesByNature(String natureSelected) {
        String sql = "SELECT * FROM isanteplus.location_address la " +
                " WHERE la.nature = :natureSelected ORDER BY la.name ASC";

        List<Object[]> rows = dao.getSession()
                .createSQLQuery(sql)
                .setParameter("natureSelected", natureSelected)
                .list();

        List<LocationAddress> locationAddresses = new ArrayList<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String code = (String) row[1];
            String color = (String) row[2];
            String name = (String) row[3];
            String nature = (String) row[4];
            Integer parent = ((Number) row[5]).intValue();
            locationAddresses.add(new LocationAddress(id, code, color, name, nature, parent));
        }

        return locationAddresses;
    }

//    @Override
//    public List<LocationAddress> getLocationAddressesByNatureAndParent(String natureSelected, Integer parentSelected) {
//        String sql = "SELECT * FROM isanteplus.location_address la " +
//                " WHERE la.nature = :natureSelected " +
//                " AND la.parent = :parentSelected ORDER BY la.name ASC";
//
//        List<Object[]> rows = dao.getSession()
//                .createSQLQuery(sql)
//                .setParameter("natureSelected", natureSelected)
//                .setParameter("parentSelected", parentSelected)
//                .list();
//
//        List<LocationAddress> locationAddresses = new ArrayList<>();
//
//        for (Object[] row : rows) {
//            Integer id = ((Number) row[0]).intValue();
//            String code = (String) row[1];
//            String color = (String) row[2];
//            String name = (String) row[3];
//            String nature = (String) row[4];
//            Integer parent = ((Number) row[5]).intValue();
//            locationAddresses.add(new LocationAddress(id, code, color, name, nature, parent));
//        }
//
//        return locationAddresses;
//    }

    @Override
    public Map<String, List<LocationAddress>> getLocationAddressesGroupedByNature(List<String> natures) {

        String sql = "SELECT la.id, la.code, la.color, la.name, la.nature, la.parent " +
                "FROM isanteplus.location_address la " +
                "WHERE la.nature IN (:natures) " +
                "ORDER BY la.nature ASC, la.name ASC";

        List<Object[]> rows = dao.getSession()
                .createSQLQuery(sql)
                .setParameterList("natures", natures)
                .list();

        Map<String, List<LocationAddress>> result = new HashMap<>();

        for (Object[] row : rows) {
            Integer id = ((Number) row[0]).intValue();
            String code = (String) row[1];
            String color = (String) row[2];
            String name = (String) row[3];
            String nature = (String) row[4];
            Integer parent = row[5] != null ? ((Number) row[5]).intValue() : null;

            LocationAddress location = new LocationAddress(
                    id, code, color, name, nature, parent
            );

            result.computeIfAbsent(nature, k -> new ArrayList<>())
                    .add(location);
        }

        return result;
    }

    private BioPluginSoapClient soapClient;

    private BioPluginSoapClient getClient() {
        if (soapClient == null) {
            soapClient = new BioPluginSoapClient();
        }
        return soapClient;
    }

    //	@Autowired
    //	private BioPluginSoapClient soapClient;

    @Override
    public String getEngineInfo() {
        return getClient().getInfo();
    }

    @Override
    public String identifyPatient(String biometricXml, int locationId) {
        return BioPluginResponseParser.isMatch(getClient().identify(biometricXml, locationId));
    }

    @Override
    public String verifyPatient(String biometricXml, String patientId, int locationId) {
        return getClient().verify(biometricXml, patientId, locationId);
    }

    @Override
    public String registerPatient(String biometricXml, String patientId, int locationId) {
        return BioPluginResponseParser.registerResponse(getClient().register(biometricXml, patientId, locationId));
    }

}
