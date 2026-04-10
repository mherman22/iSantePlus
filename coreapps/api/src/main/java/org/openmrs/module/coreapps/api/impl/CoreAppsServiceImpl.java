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
package org.openmrs.module.coreapps.api.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.coreapps.IsantePlusRelationship;
import org.openmrs.module.coreapps.api.CoreAppsService;
import org.openmrs.module.coreapps.api.db.CoreAppsDAO;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * It is a default implementation of {@link CoreAppsService}.
 */
@Transactional
public class CoreAppsServiceImpl extends BaseOpenmrsService implements CoreAppsService {

    protected final Log log = LogFactory.getLog(this.getClass());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CoreAppsDAO dao;

    /**
     * @param dao the dao to set
     */
    public void setDao(CoreAppsDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public CoreAppsDAO getDao() {
        return dao;
    }


    @Override
    public List<IsantePlusRelationship> getAllRelationships(String locale) {
        return dao.getRelationships(locale);
    }

}
