package org.openmrs.module.isanteplusreports.pnlsReport.model;

import java.util.Map;

public class PnlsReportSelectedIndicator {
	
	private String uuid;
	
	private Map<String, Object> options;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Map<String, Object> getOptions() {
		return options;
	}
	
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
}
