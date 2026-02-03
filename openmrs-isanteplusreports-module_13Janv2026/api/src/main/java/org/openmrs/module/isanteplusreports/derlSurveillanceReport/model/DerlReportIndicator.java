package org.openmrs.module.isanteplusreports.derlSurveillanceReport.model;

import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class DerlReportIndicator {

    private String nameId;

    private String uuid;

    private DerlIndicatorOption option;

    public DerlReportIndicator(ReportDefinition report, DerlIndicatorOption option) {
        this.option = option;
        this.nameId = report.getName();
        this.uuid = report.getUuid();
    }

    public String getName() {
        return MessageUtil.translate(nameId);
    }

    public String getUuid() {
        return uuid;
    }

    public DerlIndicatorOption getOption() {
        return option;
    }
}
