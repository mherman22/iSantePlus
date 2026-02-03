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
import org.openmrs.module.isanteplusreports.dataset.definitions.PatientsStatusDataSetDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientsStatusReportManager extends BaseReportManager {
	
private final Log log = LogFactory.getLog(getClass());
	
	public final static String DATA_SET_NAME = "patientStatusReport";
	
	//public IsantePlusReportsProperties props = new IsantePlusReportsProperties();
	
	//***** PROPERTIES *****
	
	@Autowired
	EmrApiProperties emrApiProperties;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public String getUuid() {
		return IsantePlusReportsProperties.PATIENTSTATUS;
	}
	
	@Override
	public String getName() {
		return "patientsStatusReport";
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
		l.add(new Parameter("regularOnArt", MessageUtil.translate("isanteplusreports.parameters.hiv_status.regular_on_art"), Integer.class));
		l.add(new Parameter("missingAppointment", MessageUtil.translate("isanteplusreports.parameters.hiv_status.missing_appointment"), Integer.class));
		l.add(new Parameter("lostOfFollowUp", MessageUtil.translate("isanteplusreports.parameters.hiv_status.lost_of_follow_up_on_art"), Integer.class));
		l.add(new Parameter("deathOnArt", MessageUtil.translate("isanteplusreports.parameters.hiv_status.death_on_art"), Integer.class));
		l.add(new Parameter("stoppedOnArt", MessageUtil.translate("isanteplusreports.parameters.hiv_status.stopped_on_art"), Integer.class));
		l.add(new Parameter("tranferedOnArt", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transfered"), Integer.class));
		l.add(new Parameter("transitionRecent", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionRecent"), Integer.class));
		l.add(new Parameter("transitionActive", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionActive"), Integer.class));
		l.add(new Parameter("transitionLostFollowUp", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionLostFollowUp"), Integer.class));
		l.add(new Parameter("transitionDeath", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionDeath"), Integer.class));
		l.add(new Parameter("transitionTranfered", MessageUtil.translate("isanteplusreports.parameters.hiv_status.transitionTranfered"), Integer.class));
		
		return l;
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		
		log.info("Constructing " + getName());
		ReportDefinition rd = new ReportDefinition();
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(getParameters());
		
		PatientsStatusDataSetDefinition dsd = new PatientsStatusDataSetDefinition();
		dsd.addParameters(getParameters());
		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("regularOnArt", "${regularOnArt}");
		mappings.put("missingAppointment", "${missingAppointment}");
		mappings.put("lostOfFollowUp", "${lostOfFollowUp}");
		mappings.put("deathOnArt", "${deathOnArt}");
		mappings.put("stoppedOnArt", "${stoppedOnArt}");
		mappings.put("tranferedOnArt", "${tranferedOnArt}");
		mappings.put("transitionRecent", "${transitionRecent}");
		mappings.put("transitionActive", "${transitionActive}");
		mappings.put("transitionLostFollowUp", "${transitionLostFollowUp}");
		mappings.put("transitionDeath", "${transitionDeath}");
		mappings.put("transitionTranfered", "${transitionTranfered}");
		rd.addDataSetDefinition(DATA_SET_NAME, dsd, mappings);
		
		return rd;
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		return Arrays.asList(csvReportDesign(reportDefinition));
	}

}
