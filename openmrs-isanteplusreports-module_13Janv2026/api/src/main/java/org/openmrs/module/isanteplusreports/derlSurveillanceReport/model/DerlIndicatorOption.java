package org.openmrs.module.isanteplusreports.derlSurveillanceReport.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openmrs.module.reporting.common.MessageUtil;

public class DerlIndicatorOption {
	
	private String id;
	
	private String parameterName;

	private List<String> values;
	
	public DerlIndicatorOption(String id, String parametrName, String[] values) {
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

	@Override
	public String toString() {
		return "DerlIndicatorOption{" +
				"id='" + id + '\'' +
				", parameterName='" + parameterName + '\'' +
				", values=" + values +
				'}';
	}
}
