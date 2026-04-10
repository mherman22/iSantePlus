package org.openmrs.module.isanteplusreports.reporting.utils;

public class IsanteplusIndicatorReportingValue {
    
    private Integer numerator;
    
    private Integer denominator;
    
    private Integer percentage;
    
    private String numeratorDataSetKey;
    
    private String denominatorDataSetKey;
    
    public String getNumeratorDataSetKey() {
        return numeratorDataSetKey;
    }
    
    public void setNumeratorDataSetKey(String numeratorDataSetKey) {
        this.numeratorDataSetKey = numeratorDataSetKey;
    }
    
    public String getDenominatorDataSetKey() {
        return denominatorDataSetKey;
    }
    
    public void setDenominatorDataSetKey(String denominatorDataSetKey) {
        this.denominatorDataSetKey = denominatorDataSetKey;
    }
    
    
    public Integer getNumerator() {
        return numerator;
    }
    
    public void setNumerator(Integer numerator) {
        this.numerator = numerator;
    }
    
    public Integer getDenominator() {
        return denominator;
    }
    
    public void setDenominator(Integer denominator) {
        this.denominator = denominator;
    }
    
    public Integer getPercentage() {
        return percentage;
    }
    
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    
}
