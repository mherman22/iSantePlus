package org.openmrs.module.isanteplusreports.page.controller;

import static org.openmrs.module.isanteplusreports.pnlsReport.utils.pnlsReportUtils.getReportData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportBuilder;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportConstants;
import org.openmrs.module.isanteplusreports.pnlsReport.PnlsReportManager;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsReportSelectedIndicator;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;


public class PnlsReportPageController {
	
	public final static String LOCATION_SESSION_ATTRIBUTE = "emrContext.sessionLocationId";

	public void get(@SpringBean PnlsReportManager pnlsManager,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws IOException {
		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		model.addAttribute("manager", pnlsManager);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("htmlResult", null);
		model.addAttribute("pdfResult", null);
	}
	
	public void post(@SpringBean PnlsReportManager pnlsManager,
	        @RequestParam(value = "indicatorList") List<PnlsReportSelectedIndicator> indicators,
	        @RequestParam(required = false, value = "startDate") Date startDate,
	        @RequestParam(required = false, value = "endDate") Date endDate,
			PageModel model, HttpSession session) throws IOException {

		
		if (startDate == null) {
			startDate = DateUtils.addDays(new Date(), -21);
		}
		if (endDate == null) {
			endDate = new Date();
		}
		
		startDate = DateUtil.getStartOfDay(startDate);
		endDate = DateUtil.getEndOfDay(endDate);
		
		PnlsReportBuilder builder = new PnlsReportBuilder();
		Location location = getSessionLocation(session);
		builder.setClinic(location.getDisplayString());
		builder.setClinicDepartment(location.getStateProvince());
		builder.setStartDate(startDate);
		builder.setEndDate(endDate);
		
		List<ReportData> allReportData = new ArrayList<ReportData>();
		for (PnlsReportSelectedIndicator indicator : indicators) {
			ReportData reportData = getReportData(indicator.getUuid(), startDate, endDate, indicator.getOptions());
			allReportData.add(reportData);		
		}
		builder.buildHtmlTables(allReportData);
				
		session.setAttribute(ReportingConstants.OPENMRS_REPORT_DATA + PnlsReportConstants.PNLS_GENERAL_PURPOSE_SUFFIX, allReportData);
		model.addAttribute("manager", pnlsManager);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("htmlResult", builder.getTablesHtml());
		model.addAttribute("pdfResult", builder.buildPdf()); 
	}



	private Location getSessionLocation(HttpSession session) {
		Location location = Context.getUserContext().getLocation();
		if (location == null) {
			return Context.getLocationService().getDefaultLocation();
		}
		
		return location; // to get clinic data
	}




}
