package org.openmrs.module.isanteplusreports.healthqual.model;

import org.openmrs.module.reporting.common.MessageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthQualIndicatorOption {
	
	private String id;
	
	private String parameterName;
	
	private List<String> values;
	
	public HealthQualIndicatorOption(String id, String parametrName, String[] values) {
		this.id = id;
		this.parameterName = parametrName;
		this.values = new ArrayList<String>();
		this.values.addAll(Arrays.asList(values));
	}
	
	public String getId() {
		return id;
	}
	
	public String getLabel() {
		return MessageUtil.translate("isanteplusreports.healthqual.option.label." + this.id);
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public List<String> getValues() {
		return values;
	}
}
