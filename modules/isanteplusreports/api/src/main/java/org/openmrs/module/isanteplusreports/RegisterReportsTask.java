package org.openmrs.module.isanteplusreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.util.RegisterAllOtherReports;

//public class RegisterReportsTask extends SessionTask {
public class RegisterReportsTask extends SessionTask {
	
	private static Log log = LogFactory.getLog(RegisterReportsTask.class);
	
	/**
	 * Does the actual data aggregation
	 */
	//protected void onExecute() {
	public void onExecute() {
		try {
			RegisterAllOtherReports.cleanReportsRequest();
		}
		catch (Exception ex) {
			log.error("One of reports has an error which blocks it and other reports to be registered");
			ex.printStackTrace();
		}
	}
	
}
