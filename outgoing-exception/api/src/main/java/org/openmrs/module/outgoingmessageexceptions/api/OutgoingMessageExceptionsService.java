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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessageExceptionsConfig;
import org.openmrs.module.outgoingmessageexceptions.api.exceptions.BadRequestException;
import org.openmrs.module.outgoingmessageexceptions.api.exceptions.NotFoundException;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingFieldName;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.SortingOrder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface OutgoingMessageExceptionsService extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized
	@Transactional(readOnly = true)
	OutgoingMessage getItemByUuid(String uuid) throws APIException;
	
	/**
	 * Saves an outgoingMessage. Sets the owner to superuser, if it is not set. It can be called by
	 * users with this module's privilege. It is executed in a transaction.
	 * 
	 * @param outgoingMessage
	 * @return
	 * @throws APIException
	 */
	@Authorized(OutgoingMessageExceptionsConfig.MODULE_PRIVILEGE)
	@Transactional
	OutgoingMessage saveItem(OutgoingMessage outgoingMessage) throws APIException;
	
	/**
	 * Returns an OutgoingMessage by id. It can be called by any authenticated user. It is fetched
	 * in read only transaction.
	 * 
	 * @param id
	 * @return OutgoingMessage
	 * @throws APIException
	 */
	@Authorized
	@Transactional(readOnly = true)
	OutgoingMessage getMessageById(Integer id) throws APIException, JsonProcessingException;
	
	
	/**
	 * Returns an serialized OutgoingMessage by id. It can be called by any authenticated user.
	 * It is fetched in read only transaction.
	 *
	 * @param id
	 * @return OutgoingMessage
	 * @throws APIException
	 */
	@Authorized
	@Transactional(readOnly = true)
	String getSerializedMessageById(Integer id) throws APIException, JsonProcessingException;
	
	/**
	 * Set an OutgoingMessage as retried. It needs message id parameter to find the message, a retry
	 * local date and retry reason are used as a feedback. It can be called by any authenticated
	 * user.
	 * 
	 * @param id
	 * @param retryLocalDate
	 * @param retryReason
	 * @return OutgoingMessage
	 * @throws APIException
	 * @throws JsonProcessingException
	 */
	@Authorized
	void retryMessage(Integer id, LocalDate retryLocalDate, String retryReason) throws NotFoundException,
	        BadRequestException;
	
	/**
	 * Update OutgoingMessage after failed retry. It needs message id parameter to find the message,
	 * a retry local date and retry reason are used as a feedback. It can be called by any
	 * authenticated user.
	 * 
	 * @param id
	 * @param retryLocalDate
	 * @param retryReason
	 * @return OutgoingMessage
	 * @throws APIException
	 * @throws JsonProcessingException
	 */
	@Authorized
	void updateMessage(Integer id, LocalDate retryLocalDate, String retryReason) throws NotFoundException,
	        BadRequestException;
	
	/**
	 * Fetches messages with pagination, given order and type. When v parameter equals "full" the
	 * pagination is ignored and all messages are returned. It can be called by any authenticated
	 * user.
	 * 
	 * @param page number of pagination page
	 * @param pageSize a pagination page size
	 * @param from a date from which the messages will be fetched
	 * @param v a scope parameter. If equals "full" other parameters, except from, will be ignored
	 *            and all matching messages will be fetched.
	 * @param sortingFieldName a field to sort by
	 * @param order the sorting order
	 * @param type a message type
	 * @param failed
	 * @return a list of fetched messages
	 */
	@Authorized
	@Transactional(readOnly = true)
	String getPaginatedMessages(Integer page, Integer pageSize, LocalDate from, String v, SortingFieldName sortingFieldName,
	        SortingOrder order, MessageType type, Boolean failed);
	
	/**
	 * Fetches all messages from given date. It can be called by any authenticated user.
	 * 
	 * @param from a date from which the messages will be fetched
	 * @return a list of fetched messages
	 */
	@Authorized
	@Transactional(readOnly = true)
	List<OutgoingMessage> getAllMessagesFrom(LocalDate from);
	
	/**
	 * Fetches all outgoingMessages.It can be called by any authenticated user.
	 * 
	 * @return list of all messages.
	 */
	@Authorized
	@Transactional(readOnly = true)
	List<OutgoingMessage> getAllMessages();
	
	/**
	 * Fetches failed outgoingMessages by MessageType.It can be called by any authenticated user.
	 *
	 * @return list of the messages.
	 */
	@Authorized
	@Transactional(readOnly = true)
	List<OutgoingMessage> getFailedMessagesByTypeChronologically(MessageType type);
}
