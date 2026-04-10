package org.openmrs.module.outgoingmessageexceptions.api.retry;

public interface RetrySchedulerService {
	
	void createTaskIfNotExists();

	void stopTaskIfExists();
}
