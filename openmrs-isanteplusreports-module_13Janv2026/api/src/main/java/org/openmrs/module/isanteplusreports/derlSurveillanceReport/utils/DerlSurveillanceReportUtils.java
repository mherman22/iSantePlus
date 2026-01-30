package org.openmrs.module.isanteplusreports.derlSurveillanceReport.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlReportConstants;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.ReportData;

public class DerlSurveillanceReportUtils {
	
	public static ReportData getReportData(String reportUuid, Date startDate, Date endDate,
	        Map<String, Object> additionalOptions) {
		return HealthQualUtils.getReportData(reportUuid, startDate, endDate, additionalOptions);
	}
	
	public static String replaceNonBreakingSpaces(String stringToClean) {
		return HealthQualUtils.replaceNonBreakingSpaces(stringToClean);
	}
	
	public static CohortIndicator cohortIndicatorFromSqlResource(String sql, String name, List<Parameter> parameters) {
		return EmrReportingUtils.cohortIndicator(name, parameters,
		    Mapped.mapStraightThrough(cohortFromSqlResource(sql, name, parameters)));
	}
	
	public static CohortDefinition cohortFromSqlResource(String sqlResourceName, String name, List<Parameter> parameters) {
		String sql = IsantePlusReportsUtil
		        .getStringFromResource(DerlReportConstants.DERL_REPORTS_RESOURCE_PATH + sqlResourceName);
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName(name);
		cd.setDescription(MessageUtil.translate(name));
		for (Parameter parameter : parameters) {
			cd.addParameter(parameter);
		}
		
		cd.setQuery(sql);
		return cd;
	}
	
	public static void addWeeklyColums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator, String column) {
		constructColumn("1" + column, "1", CohortIndicator, "weekNumber=1", dsd);
		constructColumn("2" + column, "2", CohortIndicator, "weekNumber=2", dsd);
		constructColumn("3" + column, "3", CohortIndicator, "weekNumber=3", dsd);
		constructColumn("4" + column, "4", CohortIndicator, "weekNumber=4", dsd);
		constructColumn("5" + column, "5", CohortIndicator, "weekNumber=5", dsd);
		constructColumn("6" + column, "6", CohortIndicator, "weekNumber=6", dsd);
		constructColumn("7" + column, "7", CohortIndicator, "weekNumber=7", dsd);
		constructColumn("8" + column, "8", CohortIndicator, "weekNumber=8", dsd);
		constructColumn("9" + column, "9", CohortIndicator, "weekNumber=9", dsd);
		constructColumn("10" + column, "10", CohortIndicator, "weekNumber=10", dsd);

		constructColumn("11" + column, "11", CohortIndicator, "weekNumber=11", dsd);
		constructColumn("12" + column, "12", CohortIndicator, "weekNumber=12", dsd);
		constructColumn("13" + column, "13", CohortIndicator, "weekNumber=13", dsd);
		constructColumn("14" + column, "14", CohortIndicator, "weekNumber=14", dsd);
		constructColumn("15" + column, "15", CohortIndicator, "weekNumber=15", dsd);
		constructColumn("16" + column, "16", CohortIndicator, "weekNumber=16", dsd);
		constructColumn("17" + column, "17", CohortIndicator, "weekNumber=17", dsd);
		constructColumn("18" + column, "18", CohortIndicator, "weekNumber=18", dsd);
		constructColumn("19" + column, "19", CohortIndicator, "weekNumber=19", dsd);
		constructColumn("20" + column, "20", CohortIndicator, "weekNumber=20", dsd);
		
		
		constructColumn("21" + column, "21", CohortIndicator, "weekNumber=21", dsd);
		constructColumn("22" + column, "22", CohortIndicator, "weekNumber=22", dsd);
		constructColumn("23" + column, "23", CohortIndicator, "weekNumber=23", dsd);
		constructColumn("24" + column, "24", CohortIndicator, "weekNumber=24", dsd);
		constructColumn("25" + column, "25", CohortIndicator, "weekNumber=25", dsd);
		constructColumn("26" + column, "26", CohortIndicator, "weekNumber=26", dsd);
		constructColumn("27" + column, "27", CohortIndicator, "weekNumber=27", dsd);
		constructColumn("28" + column, "28", CohortIndicator, "weekNumber=28", dsd);
		constructColumn("29" + column, "29", CohortIndicator, "weekNumber=29", dsd);

		constructColumn("30" + column, "30", CohortIndicator, "weekNumber=30", dsd);
		constructColumn("31" + column, "31", CohortIndicator, "weekNumber=31", dsd);
		constructColumn("32" + column, "32", CohortIndicator, "weekNumber=32", dsd);
		constructColumn("33" + column, "33", CohortIndicator, "weekNumber=33", dsd);
		constructColumn("34" + column, "34", CohortIndicator, "weekNumber=34", dsd);
		constructColumn("35" + column, "35", CohortIndicator, "weekNumber=35", dsd);
		constructColumn("36" + column, "36", CohortIndicator, "weekNumber=36", dsd);
		constructColumn("37" + column, "37", CohortIndicator, "weekNumber=37", dsd);
		constructColumn("38" + column, "38", CohortIndicator, "weekNumber=38", dsd);
		constructColumn("39" + column, "39", CohortIndicator, "weekNumber=39", dsd);
		constructColumn("40" + column, "40", CohortIndicator, "weekNumber=40", dsd);
		
		constructColumn("41" + column, "41", CohortIndicator, "weekNumber=41", dsd);
		constructColumn("42" + column, "42", CohortIndicator, "weekNumber=42", dsd);
		constructColumn("43" + column, "43", CohortIndicator, "weekNumber=43", dsd);
		constructColumn("44" + column, "44", CohortIndicator, "weekNumber=44", dsd);
		constructColumn("45" + column, "45", CohortIndicator, "weekNumber=45", dsd);
		constructColumn("46" + column, "46", CohortIndicator, "weekNumber=46", dsd);
		constructColumn("47" + column, "47", CohortIndicator, "weekNumber=47", dsd);
		constructColumn("48" + column, "48", CohortIndicator, "weekNumber=48", dsd);
		constructColumn("49" + column, "49", CohortIndicator, "weekNumber=49", dsd);
		constructColumn("50" + column, "50", CohortIndicator, "weekNumber=50", dsd);

		constructColumn("51" + column, "51", CohortIndicator, "weekNumber=51", dsd);
		constructColumn("52" + column, "52", CohortIndicator, "weekNumber=52", dsd);
		constructColumn("53" + column, "53", CohortIndicator, "weekNumber=53", dsd);
		constructColumn("Total" + column, "Total", CohortIndicator, "", dsd);
	}
	
	public static void addMonthlyColums(CohortIndicatorDataSetDefinition dsd, CohortIndicator CohortIndicator,
	        String column) {
		constructColumn("1" + column, "JANVIER", CohortIndicator, "monthNumber=1", dsd);
		constructColumn("2" + column, "FEVRIER", CohortIndicator, "monthNumber=2", dsd);
		constructColumn("3" + column, "MARS", CohortIndicator, "monthNumber=3", dsd);
		constructColumn("4" + column, "AVRIL", CohortIndicator, "monthNumber=4", dsd);
		constructColumn("5" + column, "MAI", CohortIndicator, "monthNumber=5", dsd);
		constructColumn("6" + column, "JUIN", CohortIndicator, "monthNumber=6", dsd);
		constructColumn("7" + column, "JUILLET", CohortIndicator, "monthNumber=7", dsd);
		constructColumn("8" + column, "AOUT", CohortIndicator, "monthNumber=8", dsd);
		constructColumn("9" + column, "SEPTEMBRE", CohortIndicator, "monthNumber=9", dsd);
		constructColumn("10" + column, "OCTOBRE", CohortIndicator, "monthNumber=10", dsd);
		constructColumn("11" + column, "NOVEMBRE", CohortIndicator, "monthNumber=11", dsd);
		constructColumn("12" + column, "DECEMBRE", CohortIndicator, "monthNumber=12", dsd);
	
		constructColumn("Total" + column, "Total", CohortIndicator, "", dsd);
	}
	
	public static void constructColumn(String columnName, String ColumnDescription, CohortIndicator CohortIndicator,
	        String dimension, CohortIndicatorDataSetDefinition dsd) {
		dsd.addColumn(columnName, ColumnDescription, Mapped.mapStraightThrough(CohortIndicator), dimension);
	}

}
