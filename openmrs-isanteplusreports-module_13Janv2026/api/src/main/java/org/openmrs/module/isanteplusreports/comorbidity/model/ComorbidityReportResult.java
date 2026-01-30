package org.openmrs.module.isanteplusreports.comorbidity.model;

import java.util.List;
import java.util.Objects;

public class ComorbidityReportResult {

    private String indicatorLabel;

    private List<PatientSummary> patientListNume;

    private List<PatientSummary> patientListDeno;

    private Double indicatorPercent;

    public ComorbidityReportResult(String indicatorLabel, List<PatientSummary> patientListNume, List<PatientSummary> patientListDeno) {
        this.indicatorLabel = indicatorLabel;
        this.patientListNume = patientListNume;
        this.patientListDeno = patientListDeno;
        if (patientListNume.isEmpty() || patientListDeno.isEmpty())
            this.indicatorPercent = 0.0;
        else
            this.indicatorPercent = ((double) patientListNume.size() / (double) patientListDeno.size()) * 100;
    }

    public String getIndicatorLabel() {
        return this.indicatorLabel;
    }

    public void setIndicatorLabel(String indicatorLabel) {
        this.indicatorLabel = indicatorLabel;
    }

    public List<PatientSummary> getPatientListNume() {
        return this.patientListNume;
    }

    public void setPatientListNume(List<PatientSummary> patientListNume) {
        this.patientListNume = patientListNume;
    }

    public List<PatientSummary> getPatientListDeno() {
        return this.patientListDeno;
    }

    public void setPatientListDeno(List<PatientSummary> patientListDeno) {
        this.patientListDeno = patientListDeno;
    }

    public Double getIndicatorPercent() {
        return this.indicatorPercent;
    }

    public void setIndicatorPercent(Double indicatorPercent) {
        this.indicatorPercent = indicatorPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ComorbidityReportResult that = (ComorbidityReportResult) o;
        return Objects.equals(indicatorLabel, that.indicatorLabel) && Objects.equals(patientListNume, that.patientListNume) && Objects.equals(patientListDeno, that.patientListDeno) && Objects.equals(indicatorPercent, that.indicatorPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indicatorLabel, patientListNume, patientListDeno, indicatorPercent);
    }

    @Override
    public String toString() {
        return "PsychosocialReportResult{" +
                "indicatorLabel='" + indicatorLabel + '\'' +
                ", patientListNume=" + patientListNume +
                ", patientListDeno=" + patientListDeno +
                ", indicatorPercent=" + indicatorPercent +
                '}';
    }
}
