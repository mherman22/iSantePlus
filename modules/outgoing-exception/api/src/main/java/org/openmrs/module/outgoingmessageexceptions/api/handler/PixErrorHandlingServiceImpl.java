package org.openmrs.module.outgoingmessageexceptions.api.handler;

import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.registrationcore.api.errorhandling.PixErrorHandlingService;
import org.springframework.stereotype.Component;

@Component("outgoingmessageexceptions.PixErrorHandlingService")
public class PixErrorHandlingServiceImpl extends BaseHandlingServiceImpl
		implements PixErrorHandlingService {
	
	@Override
	public MessageType getMessageType() {
		return MessageType.PIX;
	}
}
