/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.isanteplusreports;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.FULL_DATA_EXPORTS_RESOURCE_PATH;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.report.renderer.IsantePlusOtherHtmlReportRenderer;
import org.openmrs.module.isanteplusreports.report.renderer.IsantePlusSimpleHtmlReportRenderer;
import org.openmrs.module.isanteplusreports.report.renderer.IsantePlusSimpleOtherHtmlReportRenderer;
import org.openmrs.module.isanteplusreports.reporting.utils.ColumnParameters;
import org.openmrs.module.isanteplusreports.reporting.utils.EmrReportingUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.ContentType;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportDesignResource;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.ExcelTemplateRenderer;
import org.openmrs.module.reporting.report.renderer.RenderingMode;
import org.openmrs.module.reporting.report.renderer.ReportRenderer;
import org.openmrs.module.reporting.report.renderer.TextTemplateRenderer;
import org.openmrs.module.reporting.report.service.ReportService;
import org.openmrs.util.OpenmrsClassLoader;

/**
 * Utility methods used by the module
 */

public class IsantePlusReportsUtil {

	static IsantePlusReportsProperties props = new IsantePlusReportsProperties();

	
	static Parameter startDate = new Parameter("startDate", MessageUtil.translate("isanteplusreports.parameters.startdate"), Date.class);
	
	static Parameter endDate = new Parameter("endDate", MessageUtil.translate("isanteplusreports.parameters.enddate"), Date.class);
	
	static Parameter location = new Parameter("location", MessageUtil.translate("isanteplusreports.parameters.location"), Location.class);
	
/*static Parameter startDate = new Parameter("startDate", "isanteplusreports.parameters.startdate", Date.class);

	static Parameter endDate = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);

	static Parameter location = new Parameter("location", "isanteplusreports.parameters.location", Location.class);*/


	/**
	 * Given a location on the classpath, return the contents of this resource as a
	 * String
	 */
	public static String getStringFromResource(String resourceName) {
		InputStream is = null;
		try {
			is = OpenmrsClassLoader.getInstance().getResourceAsStream(resourceName);
			return IOUtils.toString(is, "UTF-8");
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load resource: " + resourceName, e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Copied from the reporting module ReportUtil class. Using this one due to bug
	 * fix that will be available in 0.7.8 Also added ability to specify properties
	 * Also, needed to remove the character encoding specification here... :/
	 * 
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static RenderingMode renderingModeFromResource(String label, String resourceName, Properties properties) {
		InputStreamReader reader;

		try {
			reader = new InputStreamReader(OpenmrsClassLoader.getInstance().getResourceAsStream(resourceName));
		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading template from stream", e);
		}

		final ReportDesign design = new ReportDesign();
		ReportDesignResource resource = new ReportDesignResource();
		resource.setName("template");
		String extension = resourceName.substring(resourceName.lastIndexOf(".") + 1);
		resource.setExtension(extension);
		String contentType = "text/plain";
		for (ContentType type : ContentType.values()) {
			if (type.getExtension().equals(extension)) {
				contentType = type.getContentType();
			}
		}
		resource.setContentType(contentType);
		ReportRenderer renderer = null;
		try {
			resource.setContents(IOUtils.toByteArray(reader));
		} catch (Exception e) {
			throw new RuntimeException("Error reading template from stream", e);
		}

		design.getResources().add(resource);
		design.setProperties(properties);
		if ("xls".equals(extension)) {
			renderer = new ExcelTemplateRenderer() {

				@Override
                public ReportDesign getDesign(String argument) {
					return design;
				}
			};
		} else {
			renderer = new TextTemplateRenderer() {

				@Override
                public ReportDesign getDesign(String argument) {
					return design;
				}
			};
		}
		return new RenderingMode(renderer, label, extension, null);
	}

	public static SqlDataSetDefinition sqlDataSetDefinition(String resourceName, String name, String description) {
		return sqlDataSetDefinitionWithResourcePath(resourceName, name, description, FULL_DATA_EXPORTS_RESOURCE_PATH);
	}

	public static SqlDataSetDefinition sqlDataSetDefinitionWithResourcePath(String resourceName, String name,
			String description, String resourcePath) {
		String sql = IsantePlusReportsUtil.getStringFromResource(resourcePath + resourceName);
		SqlDataSetDefinition definition = new SqlDataSetDefinition();
		definition.setSqlQuery(sql);
		definition.setName(name);
		definition.setDescription(description);

		return definition;
	}

	public static ReportDefinition reportDefinition(String name, String description, String uuid) {
		ReportDefinition reportDefinition = new ReportDefinition();
		reportDefinition.setName(name);
		reportDefinition.setDescription(description);
		reportDefinition.setUuid(uuid);
		return reportDefinition;
	}

	public static ReportDesign reportDesign(String name, ReportDefinition rDefinition,
			Class<? extends ReportRenderer> rendererType) {
		ReportDesign rDesign = new ReportDesign();
		rDesign.setName(name);
		rDesign.setReportDefinition(rDefinition);
		rDesign.setRendererType(rendererType);
		return rDesign;
	}

	public static void registerReportsWithoutParams(String sql, String messageProperties, String messagePropertiesFr,
			String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

	public static void registerReportsWithStartAndEndDateParams(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

    public static void registerIndicatorReportsWithStartAndEndDateParams(String name, String description, String uuid,
            DataSetDefinition dataSetDefinition) {

        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        
        DataSetDefinition dsd = dataSetDefinition;
        dsd.addParameter(startDate);
        dsd.addParameter(endDate);
        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        
        ReportDefinition repDefinition = reportDefinition(name, description, uuid);
        repDefinition.addParameter(startDate);
        repDefinition.addParameter(endDate);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }
    
	public static void registerReportsWithStartAndEndDateParamsOtherRenderer(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

	public static void registerReportsWithStartAndEndDateAndLocationParams(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		sqlData.addParameter(location);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("location", "${location}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addParameter(location);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

	public static void registerLabOrderReportWithResults(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		Parameter resultStatus = createLabOrderResultParameter();
		Parameter orderByDate = createLabOrderSortByDateParameter();
		Parameter testType = createLabOrderTestTypeParameter();

		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		sqlData.addParameter(resultStatus);
		sqlData.addParameter(orderByDate);
		sqlData.addParameter(testType);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		mappings.put("result", "${result}");
		mappings.put("sortByDate", "${sortByDate}");
		mappings.put("testType", "${testType}");

		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addParameter(resultStatus);
		repDefinition.addParameter(orderByDate);
		repDefinition.addParameter(testType);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

	private static Parameter createLabOrderTestTypeParameter() {
		Properties widgetConfiguration = new Properties();
		widgetConfiguration.put("uiframeworkFragmentProvider", "isanteplusreports");
		widgetConfiguration.put("uiframeworkFragment", "laborder/parameters/testTypeDropDown");
		return new Parameter("testType", "isanteplusreports.parameters.lab_order.test_type", String.class,
				widgetConfiguration);
	}

	private static Parameter createLabOrderResultParameter() {
		Properties widgetConfiguration = new Properties();
		widgetConfiguration.put("uiframeworkFragmentProvider", "isanteplusreports");
		widgetConfiguration.put("uiframeworkFragment", "laborder/parameters/orderResultDropDown");
		return new Parameter("result", "isanteplusreports.parameters.lab_order.result", String.class,
				widgetConfiguration);
	}

	private static Parameter createLabOrderSortByDateParameter() {
		Properties widgetConfiguration = new Properties();
		widgetConfiguration.put("uiframeworkFragmentProvider", "isanteplusreports");
		widgetConfiguration.put("uiframeworkFragment", "laborder/parameters/sortByDateDropDown");
		return new Parameter("sortByDate", "isanteplusreports.parameters.lab_order.order_by", String.class,
				widgetConfiguration);
	}

	// has been moved to ReportUtil in reporting module, use the one there

	/*@Deprecated
	public static List<Map<String, Object>> simplify(DataSet dataSet) {
		List<Map<String, Object>> simplified = new ArrayList<Map<String, Object>>();
		for (DataSetRow row : dataSet) {
			simplified.add(row.getColumnValuesByKey());
		}
		return simplified;
	}*/
	
	/*public static void registerReportsWithOtherStartAndEndDateParams(String sql, String messageProperties, String messagePropertiesFr, String uuid) {
        SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr, messagePropertiesFr,props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
        sqlData.addParameter(startDate);
        sqlData.addParameter(endDate);
        Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
        
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
        repDefinition.addParameter(startDate);
        repDefinition.addParameter(endDate);
        repDefinition.addDataSetDefinition(sqlData, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
        
        ReportService rs = Context.getService(ReportService.class);*/

	/*
	 * @Deprecated public static List<Map<String, Object>> simplify(DataSet dataSet)
	 * { List<Map<String, Object>> simplified = new ArrayList<Map<String,
	 * Object>>(); for (DataSetRow row : dataSet) {
	 * simplified.add(row.getColumnValuesByKey()); } return simplified; }
	 */

	public static void registerReportsWithOtherStartAndEndDateParams(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		sqlData.addParameter(startDate);
		sqlData.addParameter(endDate);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("startDate", "${startDate}");
		mappings.put("endDate", "${endDate}");
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addParameter(startDate);
		repDefinition.addParameter(endDate);
		repDefinition.addDataSetDefinition(sqlData, mappings);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

	public static void registerOtherReportsWithoutParams(String sql, String messageProperties,
			String messagePropertiesFr, String uuid) {
		SqlDataSetDefinition sqlData = sqlDataSetDefinitionWithResourcePath(sql, messagePropertiesFr,
				messagePropertiesFr, props.ISANTEPLUS_REPORTS_RESOURCE_PATH);
		Context.getService(DataSetDefinitionService.class).saveDefinition(sqlData);
		ReportDefinition repDefinition = reportDefinition(messageProperties, messageProperties, uuid);
		repDefinition.addDataSetDefinition(sqlData, null);
		Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);

		ReportService rs = Context.getService(ReportService.class);
		ReportDesign rDesign = reportDesign("Html", repDefinition, IsantePlusSimpleOtherHtmlReportRenderer.class);
		rs.saveReportDesign(rDesign);
		ReportDesign rDes = reportDesign("Excel", repDefinition, ExcelTemplateRenderer.class);
		rs.saveReportDesign(rDes);
	}

    public static SqlCohortDefinition sqlCohortDefinition(String sql, String name, String description) {
        SqlCohortDefinition cd = new SqlCohortDefinition();
        cd.setName(name);
        cd.setDescription(description);
        cd.addParameter(startDate);
        cd.addParameter(endDate);
        cd.addParameter(location);
        cd.setQuery(sql);
        
        return cd;
    }
    
    public static CohortIndicatorDataSetDefinition cohortIndicatorDataSetDefinition(String name, String description,
            List<CohortIndicator> cohortIndicators) {
        ColumnParameters col = new ColumnParameters("1", "", "");
        
        List<ColumnParameters> columnParameters = Arrays.asList(col);
        
        return cohortIndicatorDataSetDefinition(name, description, cohortIndicators, columnParameters, Arrays.asList("01"));
    }
    
    public static CohortIndicatorDataSetDefinition cohortIndicatorDataSetDefinition(String name, String description,
            List<CohortIndicator> cohortIndicators, List<ColumnParameters> columnParameters, List<String> columnNames) {
        
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        
        dsd.setName(name);
        dsd.setDescription(description);
        
        for (CohortIndicator indicator : cohortIndicators) {
            EmrReportingUtils.addRow(dsd, indicator.getName(), indicator.getDescription(),
                ReportUtils.map(indicator, "startDate=${startDate},endDate=${endDate},location=${location}"),
                columnParameters, columnNames);
        }
        
        return dsd;
    }
}
