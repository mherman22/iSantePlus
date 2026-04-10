/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.outgoingmessageexceptions.api;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.converter.OutgoingMessageStringToDateConverter;
import org.openmrs.module.outgoingmessageexceptions.api.dao.OutgoingMessageExceptionsDao;
import org.openmrs.module.outgoingmessageexceptions.api.impl.OutgoingMessageExceptionsServiceImpl;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingFieldName;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingOrder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * This is a unit test, which verifies logic in OutgoingMessageExceptionsService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class OutgoingMessageExceptionsServiceTest {
	
	private static final String OUTGOING_MESSAGE_RESPONSE_JSON = "/testOutgoingMessage.json";
	
	private static final String OUTGOING_PAGINATED_MESSAGE_RESPONSE_JSON = "/testPaginatedOutgoingMessage.json";
	
	@InjectMocks
	private OutgoingMessageExceptionsServiceImpl basicModuleService;
	
	@Mock
	private OutgoingMessageExceptionsDao dao;
	
	@Mock
	private UserService userService;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveItem_shouldSetOwnerIfNotSet() {
		//Given
		OutgoingMessage outgoingMessage = new OutgoingMessage();
		
		when(dao.saveItem(outgoingMessage)).thenReturn(outgoingMessage);
		
		User user = new User();
		when(userService.getUser(1)).thenReturn(user);
		
		//When
		basicModuleService.saveItem(outgoingMessage);
		
		//Then
		assertThat(outgoingMessage, hasProperty("owner", is(user)));
	}
	
	@Test
	public void shouldReturnSerializedMessageById() throws Exception {
		Integer testId = 1;
		
		when(dao.getMessageById(1)).thenReturn(prepareDummyOutgoingMessage());
		
		String expected = readJsonFromFile(OUTGOING_MESSAGE_RESPONSE_JSON);
		String fetched = basicModuleService.getSerializedMessageById(testId);
		
		assertEquals(expected, fetched);
	}
	
	@Test
	public void shouldReturnMessageById() {
		Integer testId = 1;
		OutgoingMessage dummyOutgoingMessage = prepareDummyOutgoingMessage();
		
		when(dao.getMessageById(1)).thenReturn(dummyOutgoingMessage);
		
		OutgoingMessage fetched = basicModuleService.getMessageById(testId);
		
		assertEquals(dummyOutgoingMessage, fetched);
	}
	
	@Test
	public void shouldReturnPaginatedMessages() throws Exception {
		Integer page = 1;
		Integer pageSize = 100;
		String from = "2015-09-09";
		String v = "part";
		SortingFieldName sort = SortingFieldName.TIMESTAMP;
		SortingOrder order = SortingOrder.DESC;
		MessageType type = null;
		Boolean failed = true;
		
		OutgoingMessageStringToDateConverter converter = new OutgoingMessageStringToDateConverter();
		
		when(dao.getPaginatedMessages(page, pageSize, converter.convert(from), sort, order, type, failed, true))
		        .thenReturn(prepareDummyPaginatedOutgoingMessages());
		when(dao.getCountOfMessages(converter.convert(from), sort, order, type, failed, true)).thenReturn(2L);
		
		String expected = readJsonFromFile(OUTGOING_PAGINATED_MESSAGE_RESPONSE_JSON);
		String fetched = basicModuleService.getPaginatedMessages(page, pageSize, converter.convert(from), v, sort, order,
		    type, failed);
		
		assertEquals(expected, fetched);
	}
	
	private OutgoingMessage prepareDummyOutgoingMessage() {
		OutgoingMessage outgoingMessage = new OutgoingMessage(1, 2, "testMessageBody", new Date(2017, 11, 11),
		        "testFailureReason", "testDestination", "testType", false);
		
		return outgoingMessage;
	}
	
	private List<OutgoingMessage> prepareDummyPaginatedOutgoingMessages() {
		List<OutgoingMessage> dummyMessages = new ArrayList<>();

		dummyMessages.add( new OutgoingMessage(1, 1, "Message 1", new Timestamp(1444428000000L),
				"NullPointerException", "Gdansk", "XDSb", true));
		dummyMessages.add( new OutgoingMessage(2, 1, "Message 2", new Timestamp(1449874800000L),
				"IllegalArgumentException", "Seattle", "PDQ", true));

		dummyMessages.get(0).getOwner().setUuid("a7983935-656d-11e7-9259-2047477501aa");
		dummyMessages.get(0).getOwner().setUsername("Tester 1");

		dummyMessages.get(1).getOwner().setUuid("a7983935-656d-11e7-9259-2047477501aa");
		dummyMessages.get(1).getOwner().setUsername("Tester 2");

		return dummyMessages;
	}
	
	private String readJsonFromFile(String filename) throws Exception {
		Resource resource = new ClassPathResource(filename);
		String json;
		try(InputStream is = resource.getInputStream()) {
			json = IOUtils.toString(is);
		}

		return json;
	}
}
