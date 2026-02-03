package org.openmrs.module.isanteplusreports.derlSurveillanceReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.openmrs.module.isanteplusreports.derlSurveillanceReport.model.DerlIndicatorOption;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.model.DerlReportIndicator;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.MapDataSet;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DerlSurveillanceReportManager {

    private static final String[] indicatorUuids = {
            DerlReportConstants.IMMEDIATE_SURVEILLANCE_UUID,
            DerlReportConstants.WEEKLY_SURVEILLANCE_UUID,
            DerlReportConstants.MONTHLY_SURVEILLANCE_UUID
    };

    private Map<String, DerlIndicatorOption> options = new HashMap<>();

    @Autowired
    private ReportDefinitionService reportDefinitionService;

    public List<DerlReportIndicator> getDerlIndicators() {
        return uuidToReportDefinition(Arrays.asList(indicatorUuids));
    }

    private List<DerlReportIndicator> uuidToReportDefinition(List<String> uuids) {

        List<DerlReportIndicator> indicators = new ArrayList<DerlReportIndicator>();

        for (String uuid : uuids) {

            ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(uuid);
            DerlIndicatorOption option = options.get(uuid);

            if (reportDefinition != null) {
                indicators.add(new DerlReportIndicator(reportDefinition, option));
            }
        }
        return indicators;
    }


}
