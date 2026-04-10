package org.openmrs.module.isanteplusreports.pnlsReport.model;

import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class PnlsReportIndicator {
	
	private String nameId;
	
	private String uuid;
	
	private PnlsIndicatorOption option;
	
	public PnlsReportIndicator(ReportDefinition report, PnlsIndicatorOption option) {
		this.option = option;
		this.nameId = report.getName();
		this.uuid = report.getUuid();
	}
	
	public String getName() {
		return MessageUtil.translate(nameId);
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public PnlsIndicatorOption getOption() {
		return option;
	}
}
