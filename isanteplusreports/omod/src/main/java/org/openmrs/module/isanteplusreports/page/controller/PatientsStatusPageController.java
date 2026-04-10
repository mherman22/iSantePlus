package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.definitions.ArvReportManager;
import org.openmrs.module.isanteplusreports.definitions.PatientsStatusReportManager;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientsStatusPageController {
	
private final Log log = LogFactory.getLog(getClass());
	
	public void get(@SpringBean PatientsStatusReportManager reportManager,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate,
	        @RequestParam(required = false, value = "regularOnArt") Boolean regularOnArt,
	        @RequestParam(required = false, value = "missingAppointment") Boolean missingAppointment,
	        @RequestParam(required = false, value = "lostOfFollowUp") Boolean lostOfFollowUp,
	        @RequestParam(required = false, value = "deathOnArt") Boolean deathOnArt,
	        @RequestParam(required = false, value = "stoppedOnArt") Boolean stoppedOnArt,
	        @RequestParam(required = false, value = "tranferedOnArt") Boolean tranferedOnArt,
	        @RequestParam(required = false, value = "transitionRecent") Boolean transitionRecent,
	        @RequestParam(required = false, value = "transitionActive") Boolean transitionActive,
	        @RequestParam(required = false, value = "transitionLostFollowUp") Boolean transitionLostFollowUp,
	        @RequestParam(required = false, value = "transitionDeath") Boolean transitionDeath,
	        @RequestParam(required = false, value = "transitionTranfered") Boolean transitionTranfered,
	        PageModel model) throws EvaluationException,
	        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		model.addAttribute("nonCodedRows", null);
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("startDate", null);
		model.addAttribute("endDate", null);
		model.addAttribute("dashboardUrlWithoutQueryParams", null);
		/*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());*/
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		model.addAttribute("date_debut", inputFormat.format(startDate));
		model.addAttribute("date_fin", inputFormat.format(endDate));
		model.addAttribute("i", 0);
		model.addAttribute("message", null);
	}
	
	public void post(@SpringBean PatientsStatusReportManager reportManager,
	        @SpringBean ReportDefinitionService reportDefinitionService, @SpringBean CoreAppsProperties coreAppsProperties,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate,
	        @RequestParam(required = false, value = "regularOnArt") Boolean regularOnArt,
	        @RequestParam(required = false, value = "missingAppointment") Boolean missingAppointment,
	        @RequestParam(required = false, value = "lostOfFollowUp") Boolean lostOfFollowUp,
	        @RequestParam(required = false, value = "deathOnArt") Boolean deathOnArt,
	        @RequestParam(required = false, value = "stoppedOnArt") Boolean stoppedOnArt,
	        @RequestParam(required = false, value = "tranferedOnArt") Boolean tranferedOnArt,
	        @RequestParam(required = false, value = "transitionRecent") Boolean transitionRecent,
	        @RequestParam(required = false, value = "transitionActive") Boolean transitionActive,
	        @RequestParam(required = false, value = "transitionLostFollowUp") Boolean transitionLostFollowUp,
	        @RequestParam(required = false, value = "transitionDeath") Boolean transitionDeath,
	        @RequestParam(required = false, value = "transitionTranfered") Boolean transitionTranfered,
	        PageModel model) throws EvaluationException,
	        IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		Integer regularOnArtStatus;
		Integer missingAppointmentStatus;
		Integer lostOfFollowUpStatus;
		Integer deathOnArtStatus; 
		Integer stoppedOnArtStatus; 
		Integer tranferedOnArtStatus;
		Integer transitionRecentStatus;
		Integer transitionActiveStatus;
		Integer transitionLostFollowUpStatus;
		Integer transitionDeathStatus;
		Integer transitionTranferedStatus;
		String message = "";
		
		if(regularOnArt){
			regularOnArtStatus = 6;
			message += " " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.regular_on_art");
		}
		else{ regularOnArtStatus = 0;}
		
		if(missingAppointment){
			missingAppointmentStatus = 8;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.missing_appointment");
		}
		else{ missingAppointmentStatus = 0;}
		
		if(lostOfFollowUp){
			
			lostOfFollowUpStatus = 9;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.lost_of_follow_up_on_art");
		}
		else{ lostOfFollowUpStatus = 0;}
		
		if(deathOnArt){
			deathOnArtStatus = 1;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.death_on_art");
		}
		else{ deathOnArtStatus = 0;}
		
		if(stoppedOnArt){
			stoppedOnArtStatus = 3;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.stopped_on_art");
		}
		else{ stoppedOnArtStatus = 0;}
		
		if(tranferedOnArt){
			tranferedOnArtStatus = 2;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transfered");
		}
		else{ tranferedOnArtStatus = 0;}
		
		if(transitionRecent){
			transitionRecentStatus = 7;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionRecent");
		}
		else{ transitionRecentStatus = 0;}
		
		if(transitionActive){
			transitionActiveStatus = 11;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionActive");
		}
		else{ transitionActiveStatus = 0;}
		
		if(transitionLostFollowUp){
			transitionLostFollowUpStatus = 10;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionLostFollowUp");
		}
		else{ transitionLostFollowUpStatus = 0;}
		
		if(transitionDeath){
			transitionDeathStatus = 4;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionDeath");
			
		}
		else{ transitionDeathStatus = 0;}
		
		if(transitionTranfered){
			transitionTranferedStatus = 5;
			message += ", " + MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionTranfered");
		}
		else{ transitionTranferedStatus = 0;}
		
		
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("regularOnArt", regularOnArtStatus);
		params.put("missingAppointment", missingAppointmentStatus);
		params.put("lostOfFollowUp", lostOfFollowUpStatus);
		params.put("deathOnArt", deathOnArtStatus);
		params.put("stoppedOnArt", stoppedOnArtStatus);
		params.put("tranferedOnArt", tranferedOnArtStatus);
		params.put("transitionRecent", transitionRecentStatus);
		params.put("transitionActive", transitionActiveStatus);
		params.put("transitionLostFollowUp", transitionLostFollowUpStatus);
		params.put("transitionDeath", transitionDeathStatus);
		params.put("transitionTranfered", transitionTranferedStatus);
		EvaluationContext context = reportManager.initializeContext(params);
		ReportDefinition reportDefinition = reportManager.constructReportDefinition();
		ReportData reportData = reportDefinitionService.evaluate(reportDefinition, context);
		
		model.addAttribute("reportManager", reportManager);
		model.addAttribute("nonCodedRows", reportData.getDataSets().get(PatientsStatusReportManager.DATA_SET_NAME));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", DateUtil.getStartOfDay(endDate));
		startDate.getTime();
		model.addAttribute("dashboardUrl", coreAppsProperties.getDashboardUrl());
		model.addAttribute("dashboardUrlWithoutQueryParams", coreAppsProperties.getDashboardUrlWithoutQueryParams());
		/*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());*/
		DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
		model.addAttribute("date_debut", inputFormat.format(startDate));
		model.addAttribute("date_fin", inputFormat.format(endDate));
		model.addAttribute("i", 0);
		model.addAttribute("message", message);
		
	}

}
