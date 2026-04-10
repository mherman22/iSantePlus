package org.openmrs.module.isanteplus;

import java.io.Serializable;


public class LocationAddressMirror implements Serializable {

    private Integer id;
    private String locality;
    private String sectionCommunale;
    private String commune;
    private String department;
    private String country;

    public LocationAddressMirror(Integer id, String locality, String sectionCommunale, String commune, String department, String country) {
        this.id = id;
        this.locality = locality;
        this.sectionCommunale = sectionCommunale;
        this.commune = commune;
        this.department = department;
        this.country = country;
    }

    public LocationAddressMirror() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSectionCommunale() {
        return sectionCommunale;
    }

    public void setSectionCommunale(String sectionCommunale) {
        this.sectionCommunale = sectionCommunale;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress() {
        return locality + ", " + sectionCommunale + ", " + commune + ", " + department + ", " + country;
    }
}
