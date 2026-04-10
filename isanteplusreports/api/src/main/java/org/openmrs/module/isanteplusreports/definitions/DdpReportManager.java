package org.openmrs.module.isanteplusreports.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.dataset.definitions.DdpReportByPeriodDataSetDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DdpReportManager extends BaseReportManager  {
private final Log log = LogFactory.getLog(getClass());
	
	public final static String DATA_SET_NAME = "ddpReport";
	
	public IsantePlusReportsProperties props = new IsantePlusReportsProperties();
	
	//***** PROPERTIES *****
	
	@Autowired
	EmrApiProperties emrApiProperties;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public String getUuid() {
		return props.DDPREPORTBYPERIOD_UUID;
	}
	
	@Override
	public String getName() {
		return "ddpReport";
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	public void setEmrApiProperties(EmrApiProperties emrApiProperties) {
		this.emrApiProperties = emrApiProperties;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//***** INSTANCE METHODS
	
	@Override
	public List<Parameter> getParameters() {
		List<Parameter> l = new ArrayList<Parameter>();
		l.add(new Parameter("startDate", MessageUtil.translate("isanteplusreports.parameters.startdate"), Date.class));
		l.add(new Parameter("endDate", MessageUtil.translate("isanteplusreports.parameters.enddate"), Date.class));
		return l;
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		log.info("Constructing " + getName());
		ReportDefinition rd = new ReportDefinition();
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(getParameters());
		
		DdpReportByPeriodDataSetDefinition dsd = new DdpReportByPeriodDataSetDefinition();
		dsd.addParameters(getParameters());
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		
		rd.addDataSetDefinition(DATA_SET_NAME, dsd, mappings);
		
		return rd;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		return Arrays.asList(csvReportDesign(reportDefinition));
	}
}
