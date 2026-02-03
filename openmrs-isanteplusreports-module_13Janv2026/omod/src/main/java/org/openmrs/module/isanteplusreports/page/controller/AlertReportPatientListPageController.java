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
import org.openmrs.module.isanteplusreports.definitions.ArvReportManager;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

public class AlertReportPatientListPageController {
	
private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean ArvReportManager reportManager, @SpringBean CoreAppsProperties coreAppsProperties,
	        HttpServletRequest request, PageModel model) throws EvaluationException, IOException, NumberFormatException,
	        ParseException {
		//DataSet dataSet = arv.patientListArvByPeriod(Integer.parseInt(request.getParameter("id")), startDate, endDate);
		DataSet dataSet = Context.getService(IsantePlusReportsService.class).alertReportPatientList(Integer.parseInt(request.getParameter("id")));

		model.addAttribute("columns", dataSet.getMetaData().getColumns());
		model.addAttribute("columnsValues", dataSet.iterator());
		model.addAttribute("id", request.getParameter("id"));
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
		model.addAttribute("privilegePatientDashboard", IsantePlusReportsProperties.PRIVILEGE_PATIENT_DASHBOARD);
	}

}
