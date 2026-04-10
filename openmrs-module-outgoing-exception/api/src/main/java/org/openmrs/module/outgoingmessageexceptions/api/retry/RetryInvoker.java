package org.openmrs.module.outgoingmessageexceptions.api.retry;

import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;

public interface RetryInvoker {
	
	void retry(OutgoingMessage outgoingMessage);
}
