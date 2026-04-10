package org.openmrs.module.isanteplus;

import java.io.Serializable;

public class PatientSearchInfos implements Serializable {

    private Integer patientId;
    private Integer patientAge;

    private String fullName;
    private String gender;
    private String birthDate;
    private String stId;
    private String pcId;
    private String nationalId;
    private String isanteId;
    private String identifier;
    private String adresses;

    public PatientSearchInfos() {
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getIsanteId() {
        return isanteId;
    }

    public void setIsanteId(String isanteId) {
        this.isanteId = isanteId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAdresses() {
        return adresses;
    }

    public void setAdresses(String adresses) {
        this.adresses = adresses;
    }

    @Override
    public String toString() {
        return "PatientSearchInfos{" +
                "patientId=" + patientId +
                ", patientAge=" + patientAge +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", stId='" + stId + '\'' +
                ", pcId='" + pcId + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", isanteId='" + isanteId + '\'' +
                ", identifier='" + identifier + '\'' +
                ", adresses='" + adresses + '\'' +
                '}';
    }
}
