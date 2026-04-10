package org.openmrs.module.outgoingmessageexceptions.api.handler;

import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.registrationcore.api.errorhandling.PdqErrorHandlingService;
import org.springframework.stereotype.Component;

@Component("outgoingmessageexceptions.PdqErrorHandlingService")
public class PdqErrorHandlingServiceImpl extends BaseHandlingServiceImpl
		implements PdqErrorHandlingService {
	
	@Override
	public MessageType getMessageType() {
		return MessageType.PDQ;
	}
}
