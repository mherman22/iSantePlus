package org.openmrs.module.isanteplusreports.page.controller;

import javax.servlet.http.HttpServletRequest;

public class ReportsMonitoringPageController {
	
	public Object get(HttpServletRequest request) {
		//String linkUrl = "../window.location.hostname/isante";
		String linkUrl = "/isanteplusreports/reportsMonitoring.gsp";
		return "redirect:" + linkUrl;
	}
	
}
