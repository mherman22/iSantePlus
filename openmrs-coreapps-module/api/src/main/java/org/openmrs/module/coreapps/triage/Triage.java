package org.openmrs.module.coreapps.triage;

import org.openmrs.Patient;

import java.io.Serializable;

public class Triage implements Serializable {

    private Patient patient;
    private Vitals vitals;
    private String triageLevel;

    public Triage() {
        // Constructeur vide pour frameworks / sérialisation
    }

    public Triage(Patient patient, Vitals vitals) {
        this.patient = patient;
        this.vitals = vitals;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Vitals getVitals() {
        return vitals;
    }

    public void setVitals(Vitals vitals) {
        this.vitals = vitals;
    }

    public String getTriageLevel() {
        return triageLevel;
    }

    public void setTriageLevel(String triageLevel) {
        this.triageLevel = triageLevel;
    }

    @Override
    public String toString() {
        return "Triage{" +
                "patient=" + (patient != null ? patient.getPersonName() : "null") +
                ", vitals=" + vitals +
                ", triageLevel='" + triageLevel + '\'' +
                '}';
    }
}