package org.openmrs.module.isanteplusreports.dataset.definitions;

import java.util.Date;

import org.openmrs.module.reporting.dataset.definition.BaseDataSetDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class PatientsStatusDataSetDefinition extends BaseDataSetDefinition {
public static final long serialVersionUID = 1L;
	
	@ConfigurationProperty(group = "when")
	private Date startDate;
	
	@ConfigurationProperty(group = "when")
	private Date endDate;
	
	private Integer regularOnArt;
	private Integer missingAppointment;
	private Integer lostOfFollowUp;
	private Integer deathOnArt;
	private Integer stoppedOnArt;
	private Integer tranferedOnArt;
	private Integer transitionRecent;
	private Integer transitionActive;
	private Integer transitionLostFollowUp;
	private Integer transitionDeath;
	private Integer transitionTranfered;
	
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
	
	public Integer getRegularOnArt() {
		return regularOnArt;
	}
	
	public void setRegularOnArt(Integer regularOnArt) {
		this.regularOnArt = regularOnArt;
	}
	
	public Integer getMissingAppointment() {
		return missingAppointment;
	}
	
	public void setMissingAppointment(Integer missingAppointment) {
		this.missingAppointment = missingAppointment;
	}
	public Integer getLostOfFollowUp() {
		return lostOfFollowUp;
	}
	
	public void setLostOfFollowUp(Integer lostOfFollowUp) {
		this.lostOfFollowUp = lostOfFollowUp;
	}
	
	public Integer getDeathOnArt() {
		return deathOnArt;
	}
	
	public void setDeathOnArt(Integer deathOnArt) {
		this.deathOnArt = deathOnArt;
	}
	
	public Integer getStoppedOnArt(){
		return stoppedOnArt;
	}
	public void setStoppedOnArt(Integer stoppedOnArt) {
		this.stoppedOnArt = stoppedOnArt;
	}
	
	public Integer getTranferedOnArt(){
		return tranferedOnArt;
	}
	public void setTranferedOnArt(Integer tranferedOnArt) {
		this.tranferedOnArt = tranferedOnArt;
	}
	public Integer getTransitionRecent(){
		return transitionRecent;
	}
	public void setTransitionRecent(Integer transitionRecent) {
		this.transitionRecent = transitionRecent;
	}
	public Integer getTransitionActive(){
		return transitionActive;
	}
	public void setTransitionActive(Integer transitionActive) {
		this.transitionActive = transitionActive;
	}
	public Integer getTransitionLostFollowUp(){
		return transitionLostFollowUp;
	}
	public void setTransitionLostFollowUp(Integer transitionLostFollowUp) {
		this.transitionLostFollowUp = transitionLostFollowUp;
	}
	public Integer getTransitionDeath(){
		return transitionDeath;
	}
	public void setTransitionDeath(Integer transitionDeath) {
		this.transitionDeath = transitionDeath;
	}
	public Integer getTransitionTranfered(){
		return transitionTranfered;
	}
	public void setTransitionTranfered(Integer transitionTranfered) {
		this.transitionTranfered = transitionTranfered;
	}
	

}
