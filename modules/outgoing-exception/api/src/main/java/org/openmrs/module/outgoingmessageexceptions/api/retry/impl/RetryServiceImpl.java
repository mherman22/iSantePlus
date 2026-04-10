package org.openmrs.module.outgoingmessageexceptions.api.retry.impl;

import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.module.outgoingmessageexceptions.api.model.enums.MessageType;
import org.openmrs.module.outgoingmessageexceptions.api.retry.RetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("outgoingmessageexceptions.RetryService")
public class RetryServiceImpl implements RetryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetryServiceImpl.class);
	
	@Autowired
	private XdsBRetryInvoker xdsBRetryInvoker;
	
	@Autowired
	private PixRetryInvoker pixRetryInvoker;
	
	@Autowired
	private OutgoingMessageExceptionsService outgoingMessageService;
	
	@Override
	public void retryAll() {
		LOGGER.info("Executing PIX errors retry...");
		outgoingMessageService.getFailedMessagesByTypeChronologically(MessageType.PIX).forEach(m -> {
			pixRetryInvoker.retry(m);
		});
		
		LOGGER.info("Executing XDS.b errors retry...");
		outgoingMessageService.getFailedMessagesByTypeChronologically(MessageType.XDSB).forEach(m -> {
			xdsBRetryInvoker.retry(m);
		});
	}
}
