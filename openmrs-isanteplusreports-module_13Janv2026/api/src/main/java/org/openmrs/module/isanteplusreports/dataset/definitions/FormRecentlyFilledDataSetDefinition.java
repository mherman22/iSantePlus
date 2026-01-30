package org.openmrs.module.isanteplusreports.dataset.definitions;

import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;

public class FormRecentlyFilledDataSetDefinition extends BaseDataSetDefinition {
	
	public static final long serialVersionUID = 1L;
	
	private Integer total;
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
