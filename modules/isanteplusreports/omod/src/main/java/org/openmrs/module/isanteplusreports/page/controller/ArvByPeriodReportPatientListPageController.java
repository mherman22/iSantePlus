package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.dataset.definition.evaluator.ArvByPeriodDataSetEvaluator;
import org.openmrs.module.isanteplusreports.dataset.definition.evaluator.ArvByPeriodPatientList;
import org.openmrs.module.isanteplusreports.definitions.ArvReportManager;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestParam;

public class ArvByPeriodReportPatientListPageController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean ArvReportManager reportManager, @SpringBean CoreAppsProperties coreAppsProperties,
	        HttpServletRequest request, PageModel model) throws EvaluationException, IOException, NumberFormatException,
	        ParseException {
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		
		String datA = inputFormat.format(inputFormat.parse(request.getParameter("startDate").toString()));
		String datB = inputFormat.format(inputFormat.parse(request.getParameter("endDate").toString()));
		Date startDate = inputFormat.parse(datA);
		Date endDate = inputFormat.parse(datB);
		//DataSet dataSet = arv.patientListArvByPeriod(Integer.parseInt(request.getParameter("id")), startDate, endDate);
		DataSet dataSet = Context.getService(IsantePlusReportsService.class).patientListArvByPeriod(
		    Integer.parseInt(request.getParameter("id")), datA, datB);
		
		model.addAttribute("columns", dataSet.getMetaData().getColumns());
		model.addAttribute("columnsValues", dataSet.iterator());
		model.addAttribute("startDate", request.getParameter("startDate"));
		model.addAttribute("endDate", request.getParameter("endDate"));
		model.addAttribute("id", request.getParameter("id"));
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
		model.addAttribute("privilegePatientDashboard", IsantePlusReportsProperties.PRIVILEGE_PATIENT_DASHBOARD);
		model.addAttribute("i", 0);
		
	}
	
}
