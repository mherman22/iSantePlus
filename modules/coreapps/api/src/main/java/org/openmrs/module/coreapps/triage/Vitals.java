package org.openmrs.module.coreapps.triage;

import org.openmrs.Encounter;

import java.io.Serializable;

public class Vitals implements Serializable {

    private Encounter encounter;

    /* -------------------------
       MESURES ANTHROPOMETRIQUES
       ------------------------- */

    private Double weight;
    private Double height;
    private Double bmi;

    private Double arm;
    private Double head;
    private Double waist;
    private Double thoracic;

    /* -------------------------
       SIGNES VITAUX
       ------------------------- */

    private Integer pulse;
    private Integer heartRate;

    private Double temperature;

    private Integer systolic;
    private Integer diastolic;

    private Integer respiratoryRate;

    private Integer spO2;

    /* -------------------------
       RESPIRATION (CLINIQUE)
       ------------------------- */

    private Boolean respiratoryDistress;
    private Boolean stridor;
    private Boolean wheezing;
    private Boolean apnea;
    private Boolean grognement; // ajouté pour grognement

    /* -------------------------
       CIRCULATION
       ------------------------- */

    private Double trc;

    private Boolean cyanosis;
    private Boolean purpura;
    private Boolean pale;
    private Boolean mottledSkin;

    // Autres signes cutanés du formulaire
    private Boolean skinDry;
    private Boolean skinHumid;
    private Boolean skinHot;
    private Boolean skinCold;
    private Boolean petechie;

    /* -------------------------
       NEUROLOGIE
       ------------------------- */

    private Integer eyes;
    private Integer verbal;
    private Integer motor;

    private Integer glasgowScore;

    private String avpu;

    /* -------------------------
       DOULEUR
       ------------------------- */

    private Integer painScore;
    private String painType;

    /* -------------------------
       TRIAGE
       ------------------------- */

    private String priority;

    public Vitals() {
    }

    public Vitals(Encounter encounter) {
        this.encounter = encounter;
    }

    /* -------------------------
       ENCOUNTER
       ------------------------- */

    public Encounter getEncounter() { return encounter; }
    public void setEncounter(Encounter encounter) { this.encounter = encounter; }

    /* -------------------------
       MESURES
       ------------------------- */

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Double getBmi() {
        if (bmi == null && weight != null && height != null && weight > 0 && height > 0) {
            return Math.round((weight / ((height / 100) * (height / 100))) * 100) / 100.0;
        }
        return bmi;
    }
    public void setBmi(Double bmi) { this.bmi = bmi; }

    public Double getArm() { return arm; }
    public void setArm(Double arm) { this.arm = arm; }

    public Double getHead() { return head; }
    public void setHead(Double head) { this.head = head; }

    public Double getWaist() { return waist; }
    public void setWaist(Double waist) { this.waist = waist; }

    public Double getThoracic() { return thoracic; }
    public void setThoracic(Double thoracic) { this.thoracic = thoracic; }

    /* -------------------------
       SIGNES VITAUX
       ------------------------- */

    public Integer getPulse() { return pulse; }
    public void setPulse(Integer pulse) { this.pulse = pulse; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Integer getSystolic() { return systolic; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }

    public Integer getDiastolic() { return diastolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }

    public Integer getRespiratoryRate() { return respiratoryRate; }
    public void setRespiratoryRate(Integer respiratoryRate) { this.respiratoryRate = respiratoryRate; }

    public Integer getSpO2() { return spO2; }
    public void setSpO2(Integer spO2) { this.spO2 = spO2; }

    /* -------------------------
       RESPIRATION
       ------------------------- */

    public Boolean isRespiratoryDistress() { return respiratoryDistress; }
    public void setRespiratoryDistress(Boolean respiratoryDistress) { this.respiratoryDistress = respiratoryDistress; }

    public Boolean isStridor() { return stridor; }
    public void setStridor(Boolean stridor) { this.stridor = stridor; }

    public Boolean isWheezing() { return wheezing; }
    public void setWheezing(Boolean wheezing) { this.wheezing = wheezing; }

    public Boolean isApnea() { return apnea; }
    public void setApnea(Boolean apnea) { this.apnea = apnea; }

    public Boolean isGrognement() { return grognement; }
    public void setGrognement(Boolean grognement) { this.grognement = grognement; }

    /* -------------------------
       CIRCULATION
       ------------------------- */

    public Double getTrc() { return trc; }
    public void setTrc(Double trc) { this.trc = trc; }

    public Boolean isCyanosis() { return cyanosis; }
    public void setCyanosis(Boolean cyanosis) { this.cyanosis = cyanosis; }

    public Boolean isPurpura() { return purpura; }
    public void setPurpura(Boolean purpura) { this.purpura = purpura; }

    public Boolean isPale() { return pale; }
    public void setPale(Boolean pale) { this.pale = pale; }

    public Boolean isMottledSkin() { return mottledSkin; }
    public void setMottledSkin(Boolean mottledSkin) { this.mottledSkin = mottledSkin; }

    public Boolean isSkinDry() { return skinDry; }
    public void setSkinDry(Boolean skinDry) { this.skinDry = skinDry; }

    public Boolean isSkinHumid() { return skinHumid; }
    public void setSkinHumid(Boolean skinHumid) { this.skinHumid = skinHumid; }

    public Boolean isSkinHot() { return skinHot; }
    public void setSkinHot(Boolean skinHot) { this.skinHot = skinHot; }

    public Boolean isSkinCold() { return skinCold; }
    public void setSkinCold(Boolean skinCold) { this.skinCold = skinCold; }

    public Boolean isPetechie() { return petechie; }
    public void setPetechie(Boolean petechie) { this.petechie = petechie; }

    /* -------------------------
       NEUROLOGIE
       ------------------------- */

    public Integer getEyes() { return eyes; }
    public void setEyes(Integer eyes) { this.eyes = eyes; }

    public Integer getVerbal() { return verbal; }
    public void setVerbal(Integer verbal) { this.verbal = verbal; }

    public Integer getMotor() { return motor; }
    public void setMotor(Integer motor) { this.motor = motor; }

    public Integer getGlasgowScore() {
        if (glasgowScore != null) return glasgowScore;
        if (eyes != null && verbal != null && motor != null)
            return eyes + verbal + motor;
        return null;
    }
    public void setGlasgowScore(Integer glasgowScore) { this.glasgowScore = glasgowScore; }

    public Integer getGlasgow() { return getGlasgowScore(); }
    public void setGlasgow(Integer glasgow) { this.glasgowScore = glasgow; }

    public String getAvpu() { return avpu; }
    public void setAvpu(String avpu) { this.avpu = avpu; }

    /* -------------------------
       DOULEUR
       ------------------------- */

    public Integer getPainScore() { return painScore; }
    public void setPainScore(Integer painScore) { this.painScore = painScore; }

    public String getPainType() { return painType; }
    public void setPainType(String painType) { this.painType = painType; }

    /* -------------------------
       TRIAGE
       ------------------------- */

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

}