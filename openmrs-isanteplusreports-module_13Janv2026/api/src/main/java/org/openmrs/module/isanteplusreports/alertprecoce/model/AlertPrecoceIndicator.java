package org.openmrs.module.isanteplusreports.alertprecoce.model;

import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class AlertPrecoceIndicator {

    private String nameId;

    private String uuid;

    private AlertPrecoceIndicatorOption option;

    public AlertPrecoceIndicator(ReportDefinition report, AlertPrecoceIndicatorOption option) {
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

    public AlertPrecoceIndicatorOption getOption() {
        return option;
    }

}
