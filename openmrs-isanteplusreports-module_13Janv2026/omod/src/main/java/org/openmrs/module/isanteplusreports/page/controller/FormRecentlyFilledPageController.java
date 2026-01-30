package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.definitions.FormRecentlyFilledReportManager;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class FormRecentlyFilledPageController {
	
	private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean FormRecentlyFilledReportManager reportManager,
	        @SpringBean CoreAppsProperties coreAppsProperties,
	        @RequestParam(required = false, value = "total") String total, PageModel model) throws EvaluationException,
	        IOException {
		Integer tot = 0;
		if (total == null) {
			total = "100";
			tot = Integer.parseInt(total);
		}
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("formRecentlyFilled", null);
		model.addAttribute("total", null);
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
		
	}
	
	public void post(@SpringBean FormRecentlyFilledReportManager reportManager,
	        @SpringBean ReportDefinitionService reportDefinitionService, @SpringBean CoreAppsProperties coreAppsProperties,
	        @RequestParam(required = false, value = "total") String total, PageModel model) throws EvaluationException,
	        IOException {
		
		Integer tot = 0;
		if (total == null || total == "" || Integer.parseInt(total) <= 0) {
			total = "100";
			tot = Integer.parseInt(total);
		} else {
			tot = Integer.parseInt(total);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("total", tot);
		EvaluationContext context = reportManager.initializeContext(params);
		ReportDefinition reportDefinition = reportManager.constructReportDefinition();
		ReportData reportData = reportDefinitionService.evaluate(reportDefinition, context);
		
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("formRecentlyFilled", reportData.getDataSets().get(FormRecentlyFilledReportManager.DATA_SET_NAME));
		model.addAttribute("dashboardUrl", coreAppsProperties.getDashboardUrl());
		model.addAttribute("total", total);
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
	}
	
}
