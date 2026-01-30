/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.registration.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.registration.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface RegistrationService extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an item. Sets the owner to superuser, if it is not set. It can be called by users with
	 * this module's privilege. It is executed in a transaction.
	 * 
	 * @param item
	 * @return
	 * @throws APIException
	 */
	@Authorized(RegistrationConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;

	List<RegistrationRelationship> getAllRelationships(String locale);

	List<LocationAddressMirror> getAllLocationAddressesByCriteria(String criteria);

	List<LocationAddress> getLocationAddressesByNature(String nature);

//	List<LocationAddress> getLocationAddressesByNatureAndParent(String nature, Integer parent);

	Map<String, List<LocationAddress>> getLocationAddressesGroupedByNature(List<String> natures);


	String getEngineInfo();

	String identifyPatient(String biometricXml, int locationId);

	String verifyPatient(String biometricXml, String patientId, int locationId);

	String registerPatient(String biometricXml, String patientId, int locationId);
}
