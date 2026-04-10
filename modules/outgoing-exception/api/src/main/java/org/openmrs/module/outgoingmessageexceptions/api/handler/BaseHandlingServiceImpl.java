package org.openmrs.module.outgoingmessageexceptions.api.handler;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public abstract class BaseHandlingServiceImpl extends BaseOpenmrsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseHandlingServiceImpl.class);
	
	private static final int ADMIN_USER_ID = 1;
	
	@Autowired
	private OutgoingMessageExceptionsService outgoingMessageExceptionsService;
	
	public void handle(String messageBody, String destination,
			boolean failure, String failureReason) {
		
		LOGGER.info("Invoked error handler.%n"
				+ "Error messageType: {},%n"
				+ "MessageBody: {},%n"
				+ "Destination: {},%n"
				+ "Failure: {},%n"
				+ "FailureReason {},%n",
				new Object[]{ getMessageType(), messageBody, destination, failure, failureReason });
		
		OutgoingMessage message = new OutgoingMessage();
		message.setOwner(getOwner());
		message.setDestination(destination);
		message.setFailure(failure);
		message.setFailureReason(failureReason);
		message.setMessageBody(messageBody);
		message.setType(getMessageType().name());
		message.setTimestamp(new Date());
		message.setRetried(false);
		message.setRetryResult("");

		outgoingMessageExceptionsService.saveItem(message);
	}
	
	abstract public MessageType getMessageType();
	
	private User getOwner() {
		return Context.getAuthenticatedUser() != null
				? Context.getAuthenticatedUser()
				: Context.getUserService().getUser(ADMIN_USER_ID);
	}
}
