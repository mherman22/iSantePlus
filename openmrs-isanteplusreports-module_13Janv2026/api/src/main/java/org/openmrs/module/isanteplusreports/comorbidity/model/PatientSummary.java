package org.openmrs.module.isanteplusreports.comorbidity.model;


import java.time.LocalDate;

public class PatientSummary {

    private Integer id;

    private String identifier;

    private String stId;

    private String nationalId;

    private String isanteId;

    private String siteCode;

    private Integer locationId;

    private String givenName;

    private String familyName;

    private String gender;

    private LocalDate birthdate;

    private String telephone;

    private String lastAddress;

    private Integer vihStatus;

    private Integer arvStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
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

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLastAddress() {
        return lastAddress;
    }

    public void setLastAddress(String lastAddress) {
        this.lastAddress = lastAddress;
    }

    public Integer getVihStatus() {
        return vihStatus;
    }

    public void setVihStatus(Integer vihStatus) {
        this.vihStatus = vihStatus;
    }

    public Integer getArvStatus() {
        return arvStatus;
    }

    public void setArvStatus(Integer arvStatus) {
        this.arvStatus = arvStatus;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "PatientSummary{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", stId='" + stId + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", isanteId='" + isanteId + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", locationId=" + locationId +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", telephone='" + telephone + '\'' +
                ", lastAddress='" + lastAddress + '\'' +
                ", vihStatus=" + vihStatus +
                ", arvStatus=" + arvStatus +
                '}';
    }
}