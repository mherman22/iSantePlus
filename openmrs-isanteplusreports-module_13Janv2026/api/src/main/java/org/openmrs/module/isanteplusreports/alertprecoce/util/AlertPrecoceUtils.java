package org.openmrs.module.isanteplusreports.alertprecoce.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;

public abstract class AlertPrecoceUtils {

    public static ReportData getReportData(String reportUuid, Date startDate, Date endDate, Map<String, Object> additionalOptions) {
        ReportDefinitionService reportDefinitionService = Context.getService(ReportDefinitionService.class);
        ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(reportUuid);
        Map<String, Object> parameterValues = new HashMap<String, Object>();

        for (Parameter parameter : reportDefinition.getParameters()) {
            if (parameter.getName().equals("startDate")) {
                parameterValues.put("startDate", startDate);
            } else if (parameter.getName().equals("endDate")) {
                parameterValues.put("endDate", endDate);
            } else {
                throw new HealthQualException("Report cannot be evaluated - missing '" + parameter.getName() + "' parameter'");
            }
        }

        EvaluationContext evaluationContext = new EvaluationContext();
        evaluationContext.setParameterValues(parameterValues);
        ReportData reportData = null;
        try {
            reportData = reportDefinitionService.evaluate(reportDefinition, evaluationContext);
        } catch (EvaluationException e) {
            throw new HealthQualException("Report cannot be evaluated", e);
        }
        return reportData;
    }

    public static String replaceNonBreakingSpaces(String stringToClean) {
        return stringToClean.replaceAll("&nbsp;", " ") // to let space-wrapping
                .replace(' ', ' ').trim(); // replace non-breaking space with regular space
    }

}
