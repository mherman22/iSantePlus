package org.openmrs.module.registration;

public class RegistrationRelationship {
	
	private Integer answerConcept;
	
	private String name;
	
	private String locale;
	
	private Integer familyMember;
	
	public RegistrationRelationship() {
	}
	
	public RegistrationRelationship(Integer answerConcept, String name, String locale, Integer familyMember) {
		this.answerConcept = answerConcept;
		this.name = name;
		this.locale = locale;
		this.familyMember = familyMember;
	}
	
	public Integer getAnswerConcept() {
		return answerConcept;
	}
	
	public void setAnswerConcept(Integer answerConcept) {
		this.answerConcept = answerConcept;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public Integer getFamilyMember() {
		return familyMember;
	}
	
	public void setFamilyMember(Integer familyMember) {
		this.familyMember = familyMember;
	}
}
