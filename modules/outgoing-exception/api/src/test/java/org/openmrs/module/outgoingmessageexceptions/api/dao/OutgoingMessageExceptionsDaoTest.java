/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.outgoingmessageexceptions.api.dao;

import org.junit.Test;
import org.junit.Ignore;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * It is an integration test (extends BaseModuleContextSensitiveTest), which verifies DAO methods
 * against the in-memory H2 database. The database is initially loaded with data from
 * standardTestDataset.xml in openmrs-api. All test methods are executed in transactions, which are
 * rolled back by the end of each test method.
 */
public class OutgoingMessageExceptionsDaoTest extends BaseModuleContextSensitiveTest {
	
	@Autowired
	OutgoingMessageExceptionsDao dao;
	
	@Autowired
	UserService userService;
	
	@Test
	@Ignore("Unignore if you want to make the OutgoingMessage class persistable, see also OutgoingMessage and liquibase.xml")
	public void saveItem_shouldSaveAllPropertiesInDb() {
		//Given
		OutgoingMessage outgoingMessage = new OutgoingMessage();
		outgoingMessage.setOwner(userService.getUser(1));
		outgoingMessage.setMessageBody("message");
		outgoingMessage.setType("Hl7");
		outgoingMessage.setDestination("Encounter");
		Date date = new Date(2017, 11, 11);
		outgoingMessage.setTimestamp(date);
		
		//When
		dao.saveItem(outgoingMessage);
		
		//Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
		Context.flushSession();
		Context.clearSession();
		
		//Then
		OutgoingMessage savedOutgoingMessage = dao.getItemByUuid(outgoingMessage.getUuid());
		
		assertThat(savedOutgoingMessage, hasProperty("uuid", is(outgoingMessage.getUuid())));
		assertThat(savedOutgoingMessage, hasProperty("owner", is(outgoingMessage.getOwner())));
	}
}
