package org.openmrs.module.coreapps;

import org.openmrs.Encounter;
import org.openmrs.Visit;

import java.io.Serializable;


public class Vitals implements Serializable {

    private Encounter encounter;

    private Double weight;

    private Double pouls;

    private Double temperature;

    private Double height;

    private Double bmi;

    private Double arm;

    private Double head;

    private Double systol;

    private Double dyastol;

    private Double heartRate;

    private Double spO2;

    private Double respiratory;

    private Double waist;

    private Double thoracic;

    private Integer eyes;

    private Integer verbal;

    private Integer motor;

    private Integer glascowScore;

    private String avpu;

    private Integer painScore;

    private String painType;

    private String priority;


    public Vitals() {
    }


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPouls() {
        return pouls;
    }

    public void setPouls(Double pouls) {
        this.pouls = pouls;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getArm() {
        return arm;
    }

    public void setArm(Double arm) {
        this.arm = arm;
    }

    public Double getSystol() {
        return systol;
    }

    public void setSystol(Double systol) {
        this.systol = systol;
    }

    public Double getDyastol() {
        return dyastol;
    }

    public void setDyastol(Double dyastol) {
        this.dyastol = dyastol;
    }


    public Double getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(Double respiratory) {
        this.respiratory = respiratory;
    }


    public Double getHead() {
        return head;
    }

    public void setHead(Double head) {
        this.head = head;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Double getHeartRate() {
        return this.heartRate;
    }

    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }

    public Double getSpO2() {
        return this.spO2;
    }

    public void setSpO2(Double SpO2) {
        this.spO2 = SpO2;
    }

    public Double getWaist() {
        return this.waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getThoracic() {
        return this.thoracic;
    }

    public void setThoracic(Double thoracic) {
        this.thoracic = thoracic;
    }

    public Integer getEyes() {
        return this.eyes;
    }

    public void setEyes(Integer eyes) {
        this.eyes = eyes;
    }

    public Integer getVerbal() {
        return this.verbal;
    }

    public void setVerbal(Integer verbal) {
        this.verbal = verbal;
    }

    public Integer getMotor() {
        return this.motor;
    }

    public void setMotor(Integer motor) {
        this.motor = motor;
    }


    public void setGlascowScore(Integer glascowScore) {
        this.glascowScore = glascowScore;
    }

    public String getAvpu() {
        return this.avpu;
    }

    public void setAvpu(String avpu) {
        this.avpu = avpu;
    }

    public Integer getGlascowScore() {
        if(glascowScore == null && (eyes != null && eyes > 0) && (verbal != null && verbal > 0) && (motor != null && motor > 0))
            glascowScore = eyes + verbal + motor;
        return glascowScore;
    }

    public Double getBmi() {
        if(bmi==null && weight!=null && height !=null && weight > 0 && height > 0)
            bmi = (double) Math.round(weight*100/(height * height * 0.0001))/100;
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }


    public Integer getPainScore() {
        return this.painScore;
    }

    public void setPainScore(Integer painScore) {
        this.painScore = painScore;
    }

    public String getPainType() {
        return this.painType;
    }

    public void setPainType(String painType) {
        this.painType = painType;
    }

    @Override
    public String toString() {
        return "Vitals{" +
                "encounter=" + encounter +
                ", weight=" + weight +
                ", pouls=" + pouls +
                ", temperature=" + temperature +
                ", height=" + height +
                ", bmi=" + bmi +
                ", arm=" + arm +
                ", head=" + head +
                ", systol=" + systol +
                ", dyastol=" + dyastol +
                ", heartRate=" + heartRate +
                ", spO2=" + spO2 +
                ", respiratory=" + respiratory +
                ", waist=" + waist +
                ", thoracic=" + thoracic +
                ", eyes=" + eyes +
                ", verbal=" + verbal +
                ", motor=" + motor +
                ", glascowScore=" + glascowScore +
                ", avpu='" + avpu + '\'' +
                ", painScore=" + painScore +
                ", painType='" + painType + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}

