package org.openmrs.module.isanteplusreports.comorbidity.model;

public class ComorbidityDatasetDefinition {

    private String label;
    private String sqlNum;
    private String sqlDeno;
    private String uuid;

    public ComorbidityDatasetDefinition(String label, String sqlNum, String sqlDeno, String uuid) {
        this.label = label;
        this.sqlNum = sqlNum;
        this.sqlDeno = sqlDeno;
        this.uuid = uuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSqlNum() {
        return sqlNum;
    }

    public void setSqlNum(String sqlNum) {
        this.sqlNum = sqlNum;
    }

    public String getSqlDeno() {
        return sqlDeno;
    }

    public void setSqlDeno(String sqlDeno) {
        this.sqlDeno = sqlDeno;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "PsychosocialDatasetDefinition{" +
                "label='" + label + '\'' +
                ", sqlNum='" + sqlNum + '\'' +
                ", sqlDeno='" + sqlDeno + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
