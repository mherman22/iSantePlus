package org.openmrs.module.isanteplusreports.derlSurveillance.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Indicator {

    private Integer indicatorId;

    private Integer indicatorTypeId;

    private Integer patientId;

    private Integer locationId;

    private Integer encounterId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate indicatorDate;

    private Boolean voided;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastUpdatedDate;

    public Indicator() {
    }

    public Indicator(Integer indicatorId, Integer indicatorTypeId, Integer patientId, Integer locationId, Integer encounterId, LocalDate indicatorDate, Boolean voided, LocalDate createdDate, LocalDate lastUpdatedDate) {
        this.indicatorId = indicatorId;
        this.indicatorTypeId = indicatorTypeId;
        this.patientId = patientId;
        this.locationId = locationId;
        this.encounterId = encounterId;
        this.indicatorDate = indicatorDate;
        this.voided = voided;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Integer getIndicatorTypeId() {
        return indicatorTypeId;
    }

    public void setIndicatorTypeId(Integer indicatorTypeId) {
        this.indicatorTypeId = indicatorTypeId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public LocalDate getIndicatorDate() {
        return indicatorDate;
    }

    public void setIndicatorDate(LocalDate indicatorDate) {
        this.indicatorDate = indicatorDate;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "indicatorId=" + indicatorId +
                ", indicatorTypeId=" + indicatorTypeId +
                ", patientId=" + patientId +
                ", locationId=" + locationId +
                ", encounterId=" + encounterId +
                ", indicatorDate=" + indicatorDate +
                ", voided=" + voided +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}