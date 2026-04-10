package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.definitions.WeeklyMonitoringReportManager;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class WeeklyMonitoringReportPageController {
	
	/*public void get(@SpringBean WeeklyMonitoringReportManager reportManager,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws EvaluationException,
	        IOException {*/
		public void get(@SpringBean WeeklyMonitoringReportManager reportManager,
		        @SpringBean ReportDefinitionService reportDefinitionService, @SpringBean CoreAppsProperties coreAppsProperties,
		        @RequestParam(required = false, value = "startDate") Date startDate,
		        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws EvaluationException,
		        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -7);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		/*
			startDate = DateUtil.getStartOfDay(startDate);
			endDate = DateUtil.getEndOfDay(endDate);
			
			model.addAttribute("nonCodedRows", null);
			model.addAttribute("reportManager", reportManager);
			model.addAttribute("startDate", null);
			model.addAttribute("endDate", null);
			
			DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			model.addAttribute("date_debut", inputFormat.format(startDate));
			model.addAttribute("date_fin", inputFormat.format(endDate));
			
		*/
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		EvaluationContext context = reportManager.initializeContext(params);
		ReportDefinition reportDefinition = reportManager.constructReportDefinition();
		ReportData reportData = reportDefinitionService.evaluate(reportDefinition, context);
		
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("nonCodedRows", reportData.getDataSets().get(WeeklyMonitoringReportManager.DATA_SET_NAME));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", DateUtil.getStartOfDay(endDate));
		startDate.getTime();
		model.addAttribute("dashboardUrl", coreAppsProperties.getDashboardUrl());
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		model.addAttribute("date_debut", inputFormat.format(startDate));
		model.addAttribute("date_fin", inputFormat.format(endDate));
		
	}
	
	public void post(@SpringBean WeeklyMonitoringReportManager reportManager,
	        @SpringBean ReportDefinitionService reportDefinitionService, @SpringBean CoreAppsProperties coreAppsProperties,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws EvaluationException,
	        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -7);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		EvaluationContext context = reportManager.initializeContext(params);
		ReportDefinition reportDefinition = reportManager.constructReportDefinition();
		ReportData reportData = reportDefinitionService.evaluate(reportDefinition, context);
		
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("nonCodedRows", reportData.getDataSets().get(WeeklyMonitoringReportManager.DATA_SET_NAME));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", DateUtil.getStartOfDay(endDate));
		startDate.getTime();
		model.addAttribute("dashboardUrl", coreAppsProperties.getDashboardUrl());
		
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		model.addAttribute("date_debut", inputFormat.format(startDate));
		model.addAttribute("date_fin", inputFormat.format(endDate));
	}

}
