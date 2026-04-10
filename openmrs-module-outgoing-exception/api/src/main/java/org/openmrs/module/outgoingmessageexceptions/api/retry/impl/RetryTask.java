package org.openmrs.module.outgoingmessageexceptions.api.retry.impl;

import org.openmrs.api.context.Context;
import org.openmrs.module.outgoingmessageexceptions.api.retry.RetryService;
import org.openmrs.scheduler.tasks.AbstractTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryTask extends AbstractTask {
	
	public static final String TASK_NAME = "Outgoing-Message-Exception Retry Task";
	
	public static final String TASK_DESCRIPTION = "Retry task for iSantePlus failed exception";
	
	public static final long DEFAULT_INTERVAL_SECONDS = 3600;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetryTask.class);
	
	@Override
	public void execute() {
		LOGGER.info("Executing " + TASK_NAME  + "...");
		// FIXME Disabled in 1.1.1 as stopping the scheduled task doesn't stop this mechanism
		// getRetryService().retryAll();
	}
	
	private RetryService getRetryService() {
		return Context.getRegisteredComponent("outgoingmessageexceptions.RetryService",
				RetryService.class);
	}
}
