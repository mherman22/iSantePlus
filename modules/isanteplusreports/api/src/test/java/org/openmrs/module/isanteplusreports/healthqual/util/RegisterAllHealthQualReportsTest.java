package org.openmrs.module.isanteplusreports.healthqual.util;


import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.test.BaseModuleContextSensitiveTest;
//import org.springframework.test.context.ContextConfiguration;
//@ContextConfiguration(locations = "isanteplusreports:webModuleApplicationContext.xml")
//@ContextConfiguration("/moduleApplicationContext.xml")
/*@ContextConfiguration(locations = { "classpath:applicationContext-service.xml", "classpath*:openmrs-servlet.xml",
"classpath*:moduleApplicationContext.xml" })*/
public class RegisterAllHealthQualReportsTest extends BaseModuleContextSensitiveTest {
	@Override
    public Properties getRuntimeProperties() {
        Properties p = super.getRuntimeProperties();
        String url = "jdbc:h2:mem:isanteplus;MODE=MYSQL;DB_CLOSE_DELAY=30;LOCK_TIMEOUT=10000;"
        		+ "INIT=CREATE SCHEMA IF NOT EXISTS isanteplus\\;SET SCHEMA isanteplus\\;"
        		+ "RUNSCRIPT FROM 'classpath:org/openmrs/module/isanteplusreports/sql/init.sql'\\;"
				+ "RUNSCRIPT FROM 'classpath:org/openmrs/module/isanteplusreports/sql/data.sql'\\;";

        p.setProperty(Environment.URL, url);
        
        return p;
    }
    
    /**
	 * @see RegisterAllHealthQualReports#registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(String,String,String,String)
	 * @verifies save report definition and return valid instance
	 */
   
	@Test
	public void registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen_shouldSaveReportDefinitionAndReturnValidInstance()
	        throws Exception {
		String sqlNum = HealthQualReportsConstants.ADULT_1_NUM_INDICATOR_SQL; 
		String sqlDen = HealthQualReportsConstants.ADULT_1_DEN_INDICATOR_SQL;
		String messageProperties = HealthQualReportsConstants.ADULT_1_INDICATOR_MESSAGE;
		String uuid  = HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID;
		
		RegisterAllHealthQualReports.registerHealthEqualReportWithCurrentDateAndPeriodParamsNumDen(sqlNum, sqlDen, messageProperties, uuid);
		
        ReportDefinition rd = Context.getService(SerializedDefinitionService.class).getDefinitionByUuid(ReportDefinition.class, HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID);
		
        Assert.assertNotNull(rd);

        Assert.assertEquals("Should return at last 1 instance of dataset definition", 1, rd.getDataSetDefinitions().size());

	}

}