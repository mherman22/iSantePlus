package org.openmrs.module.isanteplusreports.healthqual;

import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicator;
import org.openmrs.module.isanteplusreports.healthqual.model.HealthQualIndicatorOption;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualReportsConstants;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HealthQualManager {
	
	private static final String[] adultIndicatorsUuid = {
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_2_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_3_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_4_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_5_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_6_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_7_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_8_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_9_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_10_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_11_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_12_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_13_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_14_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_15_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_ADULT_16_INDICATOR_UUID
	};
	
	private static final String[] pediatricIndicatorsUuid = {
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_1_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_2_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_3_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_4_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_5_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_6_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_7_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_8_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_9_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_10_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_11_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_12_INDICATOR_UUID,
			HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_13_INDICATOR_UUID,
            HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_14_INDICATOR_UUID
	};
	
	private Map<String, HealthQualIndicatorOption> options = new HashMap<>();
	
	@Autowired
	private ReportDefinitionService reportDefinitionService;
	
	public HealthQualManager() {
		createIndicatorOptions();
	}
	
	public List<HealthQualIndicator> getAdultIndicators() {
		return uuidToReportDefinition(Arrays.asList(adultIndicatorsUuid));
	}
	
	public List<HealthQualIndicator> getPediatricIndicators() {
		return uuidToReportDefinition(Arrays.asList(pediatricIndicatorsUuid));
	}
	
	private List<HealthQualIndicator> uuidToReportDefinition(List<String> uuids) {
		List<HealthQualIndicator> indicators = new ArrayList<HealthQualIndicator>();
		for (String uuid : uuids) {
			ReportDefinition reportDefinition = reportDefinitionService.getDefinitionByUuid(uuid);
			HealthQualIndicatorOption option = options.get(uuid);
			if (reportDefinition != null) {
				indicators.add(new HealthQualIndicator(reportDefinition, option));
			}
		}
		return indicators;
	}
	
	private void createIndicatorOptions() {
		String[] periodMonthsValues = { "6", "12", "24", "48", "60" };
		String[] ageDaysValues = { "45", "75", "105", "270"};

		HealthQualIndicatorOption period = new HealthQualIndicatorOption("periodMonths", "period", periodMonthsValues);
		HealthQualIndicatorOption age = new HealthQualIndicatorOption("ageDays", "age", ageDaysValues);

		// adult options
		options.put(HealthQualReportsConstants.HEALTH_QUAL_ADULT_1_INDICATOR_UUID, period);
		// pediatric options
		options.put(HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_1_INDICATOR_UUID, period);

		options.put(HealthQualReportsConstants.HEALTH_QUAL_PEDIATRIC_10_INDICATOR_UUID, age);
	}
}
