package org.openmrs.module.isanteplusreports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.isanteplusreports.alertprecoce.util.RegisterAllAlertPrecoceReports;
import org.openmrs.module.isanteplusreports.healthqual.util.RegisterAllHealthQualReports;
import org.openmrs.module.isanteplusreports.pnlsReport.RegisterAllPnlsReports;
import org.openmrs.module.isanteplusreports.util.RegisterAllOtherReports;
import org.openmrs.module.isanteplusreports.util.RegisterPatientsArvStatusReports;

public class RegisterReports {
	
	private static Log log = LogFactory.getLog(RegisterReportsTask.class);
	
	/**
	 * Does the actual data aggregation
	 */
	//protected void onExecute() {
	public void registerReports() {
		try {
			
			String version = ModuleFactory.getModuleById("isanteplusreports").getVersion();
			String oldversion = Context.getAdministrationService().getGlobalProperty("reports.moduleVersion");
			
			if (!version.equals(oldversion)) {
				RegisterAllOtherReports.registerOtherReports();
				RegisterAllHealthQualReports.registerAll();
				RegisterPatientsArvStatusReports.registerAllPatientsArvStatusReports();
				RegisterAllPnlsReports.registerAll();
				RegisterAllAlertPrecoceReports.registerAll();
				RegisterAllHealthQualReports.registerAll();
				Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("reports.moduleVersion", version));
			}
		}
		catch (Exception ex) {
			log.error("One of reports has an error which blocks it and other reports to be registered");
			ex.printStackTrace();
		}
	}
}
