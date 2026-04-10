package org.openmrs.module.outgoingmessageexceptions.api.handler;

import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.xdssender.api.errorhandling.XdsBErrorHandlingService;
import org.springframework.stereotype.Component;

@Component("outgoingmessageexceptions.XdsBErrorHandlingService")
public class XdsBErrorHandlingServiceImpl extends BaseHandlingServiceImpl
		implements XdsBErrorHandlingService {
	
	@Override
	public MessageType getMessageType() {
		return MessageType.XDSB;
	}
}
