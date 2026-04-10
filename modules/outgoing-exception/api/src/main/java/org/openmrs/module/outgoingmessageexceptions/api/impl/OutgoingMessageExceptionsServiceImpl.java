/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.outgoingmessageexceptions.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.module.outgoingmessageexceptions.api.dao.OutgoingMessageExceptionsDao;
import org.openmrs.module.outgoingmessageexceptions.api.exceptions.BadRequestException;
import org.openmrs.module.outgoingmessageexceptions.api.exceptions.NotFoundException;
import org.openmrs.module.outgoingmessageexceptions.api.model.OutgoingMessageList;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingFieldName;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingOrder;
import org.openmrs.module.outgoingmessageexceptions.api.utils.OutgoingMessageExceptionsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("outgoingmessageexceptions.OutgoingMessageExceptionsService")
public class OutgoingMessageExceptionsServiceImpl extends BaseOpenmrsService implements OutgoingMessageExceptionsService {
	
	@Autowired
	private OutgoingMessageExceptionsDao dao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public OutgoingMessage getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public OutgoingMessage saveItem(OutgoingMessage outgoingMessage) throws APIException {
		if (outgoingMessage.getOwner() == null) {
			outgoingMessage.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(outgoingMessage);
	}
	
	@Override
	public OutgoingMessage getMessageById(Integer id) throws APIException {
		return dao.getMessageById(id);
	}
	
	@Override
	public String getSerializedMessageById(Integer id) throws APIException, JsonProcessingException {
		
		OutgoingMessage outgoingMessage = dao.getMessageById(id);
		ObjectMapper mapper = new ObjectMapper();
		
		SimpleModule module = new SimpleModule();
		module.addSerializer(OutgoingMessage.class, new OutgoingMessage.OutgoingMessageSerializer());
		mapper.registerModule(module);
		
		return mapper.writeValueAsString(outgoingMessage);
	}
	
	@Override
	public void retryMessage(Integer id, LocalDate retryLocalDate, String retryReason) throws NotFoundException,
	        BadRequestException {
		OutgoingMessage outgoingMessage = dao.getMessageById(id);
		if (outgoingMessage == null) {
			throw new NotFoundException();
		} else if (outgoingMessage.getRetried()) {
			throw new BadRequestException();
		}
		outgoingMessage.setRetried(true);
		outgoingMessage.setRetryTimestamp(convertLocalDateToDate(retryLocalDate));
		outgoingMessage.setRetryResult(retryReason);
		saveItem(outgoingMessage);
	}
	
	@Override
	public void updateMessage(Integer id, LocalDate retryLocalDate, String retryReason) throws NotFoundException {
		OutgoingMessage outgoingMessage = dao.getMessageById(id);
		if (outgoingMessage == null) {
			throw new NotFoundException();
		}
		outgoingMessage.setRetryTimestamp(convertLocalDateToDate(retryLocalDate));
		outgoingMessage.setTimestamp(convertLocalDateToDate(retryLocalDate));
		outgoingMessage.setRetryResult(retryReason);
		saveItem(outgoingMessage);
	}
	
	@Override
	public List<OutgoingMessage> getAllMessagesFrom(LocalDate from) {
		return dao.getAllMessagesFrom(from);
	}
	
	@Override
	public List<OutgoingMessage> getAllMessages() {
		return dao.getAllMessages();
	}
	
	@Override
	public List<OutgoingMessage> getFailedMessagesByTypeChronologically(MessageType type) {
		return dao.getFailedMessagesByTypeChronologically(type);
	}
	
	@Override
	public String getPaginatedMessages(Integer page, Integer pageSize, LocalDate from, String v,
	        SortingFieldName sortingFieldName, SortingOrder order, MessageType type, Boolean failed) {
		List<OutgoingMessage> results;
		if (v != null && v.toLowerCase().equals(OutgoingMessageExceptionsConstants.FULL)) {
			results = (from != null) ? getAllMessagesFrom(from) : getAllMessages();
		} else {
			results = dao.getPaginatedMessages(page, pageSize, from, sortingFieldName, order, type, failed, true);
		}
		OutgoingMessageList composedResults = new OutgoingMessageList(dao.getCountOfMessages(from, sortingFieldName, order,
		    type, failed, true), page, pageSize, from, results);
		return serializeResults(composedResults).toString();
	}
	
	private String serializeResults(OutgoingMessageList results) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(OutgoingMessage.class, new OutgoingMessage.OutgoingMessageGsonSerializer());
		Gson gson = gsonBuilder.create();
		
		gson.getAdapter(OutgoingMessage.OutgoingMessageGsonSerializer.class);
		return gson.toJson(results).toString();
	}
	
	private Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
