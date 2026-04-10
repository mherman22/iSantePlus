package org.openmrs.module.isanteplusreports;

import org.openmrs.scheduler.tasks.AbstractTask;

public abstract class SessionTask extends AbstractTask {
	
	/**
	 * @see org.openmrs.scheduler.tasks.AbstractTask#execute()
	 */
	@Override
	public void execute() {
		//execute(true);
	}
	
	/**
	 * Executes this data aggregation task
	 * 
	 * @param newSession true to create a new OpenMRS session
	 */
	/*@SuppressWarnings("deprecation")
	public void execute(boolean newSession) {
		if (!isExecuting) {
	        isExecuting = true;
	        
	        if (newSession)
	        	Context.openSession();
	        
			try {
				if (!Context.isAuthenticated())
					authenticate();
				
				onExecute();
				
			} catch (Exception e) {
			} finally {
				if (newSession)
					Context.closeSession();
				isExecuting = false;
			}
		}
	}*/
	
	/**
	 * Does the actual task work
	 * 
	 * @throws Exception
	 */
	abstract protected void onExecute() throws Exception;
	
}
