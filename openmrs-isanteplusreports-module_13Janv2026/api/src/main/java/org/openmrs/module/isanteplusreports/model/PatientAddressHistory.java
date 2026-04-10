package org.openmrs.module.isanteplusreports.model;

public class PatientAddressHistory {

    private Integer personId;
    private String address2;
    private String address1;
    private String cityVillage;
    private String stateProvince;
    private String country;

    // Constructeur vide
    public PatientAddressHistory() {
    }

    // Constructeur avec paramètres
    public PatientAddressHistory(Integer personId, String address2, String address1,
                         String cityVillage, String stateProvince,
                         String country, Boolean preferred) {
        this.personId = personId;
        this.address2 = address2;
        this.address1 = address1;
        this.cityVillage = cityVillage;
        this.stateProvince = stateProvince;
        this.country = country;
    }

    // Getters et Setters
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "PatientAddressHistory{" +
                "personId=" + personId +
                ", address2='" + address2 + '\'' +
                ", address1='" + address1 + '\'' +
                ", cityVillage='" + cityVillage + '\'' +
                ", stateProvince='" + stateProvince + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}