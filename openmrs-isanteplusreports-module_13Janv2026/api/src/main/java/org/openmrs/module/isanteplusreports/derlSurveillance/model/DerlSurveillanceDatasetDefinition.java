package org.openmrs.module.isanteplusreports.derlSurveillance.model;

import java.util.List;

public class DerlSurveillanceDatasetDefinition {

    private String label;
    private String uuid;
    private String sqlFile;
    private List<Indicator> indicatorList;

    public DerlSurveillanceDatasetDefinition(String label, String uuid, String sqlFile) {
        this.label = label;
        this.uuid = uuid;
        this.sqlFile = sqlFile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSqlFile() {
        return sqlFile;
    }

    public void setSqlFile(String sqlFile) {
        this.sqlFile = sqlFile;
    }

    public List<Indicator> getIndicatorList() {
        return indicatorList;
    }

    public void setIndicatorList(List<Indicator> indicatorList) {
        this.indicatorList = indicatorList;
    }

    @Override
    public String toString() {
        return "DerlSurveillanceDatasetDefinition{" +
                "label='" + label + '\'' +
                ", uuid='" + uuid + '\'' +
                ", sqlFile='" + sqlFile + '\'' +
                ", indicatorList=" + indicatorList +
                '}';
    }
}
