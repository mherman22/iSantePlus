package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

public class AlertReportPageController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean CoreAppsProperties coreAppsProperties,
	        HttpServletRequest request, PageModel model) throws EvaluationException, IOException, NumberFormatException,
	        ParseException {
		DataSet dataset = Context.getService(IsantePlusReportsService.class).alertReport();
		model.addAttribute("columns", dataset.getMetaData().getColumns());
		model.addAttribute("columnsValues", dataset.iterator());
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
		model.addAttribute("privilegePatientDashboard", IsantePlusReportsProperties.PRIVILEGE_PATIENT_DASHBOARD);
		
	}
}
