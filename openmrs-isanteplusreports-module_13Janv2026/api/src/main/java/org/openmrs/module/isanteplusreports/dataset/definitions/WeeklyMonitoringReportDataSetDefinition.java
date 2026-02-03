package org.openmrs.module.isanteplusreports.dataset.definitions;

import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

import java.util.Date;

public class WeeklyMonitoringReportDataSetDefinition extends BaseDataSetDefinition {
	
public static final long serialVersionUID = 1L;
	
	@ConfigurationProperty(group = "when")
	private Date startDate;
	
	@ConfigurationProperty(group = "when")
	private Date endDate;
	
	private Integer id;
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
