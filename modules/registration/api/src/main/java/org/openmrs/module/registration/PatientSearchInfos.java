package org.openmrs.module.registration;

import java.io.Serializable;

public class PatientSearchInfos implements Serializable {

    private Integer patientId;
    private String fullName;
    private String gender;
    private Integer patientAge;
    private String birthDate;

    public PatientSearchInfos() {
    }

    public PatientSearchInfos(Integer patientId, String fullName, String gender, Integer patientAge, String birthDate) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.patientAge = patientAge;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    @Override
    public String toString() {
        return "PatientSearchInfos{" +
                "patientId=" + patientId +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", patientAge=" + patientAge +
                '}';
    }

}
