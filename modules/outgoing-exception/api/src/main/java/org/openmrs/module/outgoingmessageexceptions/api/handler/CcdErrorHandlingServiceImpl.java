package org.openmrs.module.outgoingmessageexceptions.api.handler;

import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.xdssender.api.errorhandling.CcdErrorHandlingService;
import org.springframework.stereotype.Component;

@Component("outgoingmessageexceptions.CcdErrorHandlingService")
public class CcdErrorHandlingServiceImpl extends BaseHandlingServiceImpl
		implements CcdErrorHandlingService {
	
	@Override
	public MessageType getMessageType() {
		return MessageType.CCD;
	}
}
