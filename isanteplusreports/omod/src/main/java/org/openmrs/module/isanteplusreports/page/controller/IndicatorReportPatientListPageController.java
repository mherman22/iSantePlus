package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.MapDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.SimplePatientDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.indicator.dimension.CohortIndicatorAndDimensionResult;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class IndicatorReportPatientListPageController {

	private final Log log = LogFactory.getLog(getClass());

	public void get(@RequestParam(required = false, value = "savedDataSetKey") String savedDataSetKey,
			@RequestParam(required = false, value = "savedColumnKey") String savedColumnKey,
			@RequestParam(required = false, value = "applyDataSetId") String applyDataSetId,
			@RequestParam(required = false, value = "limit") Integer limit,
            @RequestParam(required = false, value = "columnKeyType") String columnKeyType,
			@SpringBean CoreAppsProperties coreAppsProperties,
			HttpServletRequest request, PageModel model)
			throws EvaluationException, IOException, NumberFormatException, ParseException {

		ReportData reportData = (ReportData) request.getSession().getAttribute(ReportingConstants.OPENMRS_REPORT_DATA);

		for (Map.Entry<String, DataSet> e : reportData.getDataSets().entrySet()) {
			if (e.getKey().equals(savedDataSetKey)) {

				MapDataSet mapDataSet = (MapDataSet) e.getValue();
				DataSetColumn dataSetColumn = mapDataSet.getMetaData().getColumn(savedColumnKey);
				model.addAttribute("selectedColumn", dataSetColumn);

				Object result = mapDataSet.getData(dataSetColumn);
				Cohort selectedCohort = null;
                log.debug(result.getClass().getName());
				if (result instanceof CohortIndicatorAndDimensionResult) {
					CohortIndicatorAndDimensionResult cidr = (CohortIndicatorAndDimensionResult) mapDataSet
							.getData(dataSetColumn);
                    if (columnKeyType.equals("denominator")) { //Can either be "denominator" or "numerator" (Default is numerator)
                        selectedCohort = cidr.getCohortIndicatorAndDimensionDenominator();
                    } else {
                        selectedCohort = cidr.getCohortIndicatorAndDimensionCohort();
                    }
                } else if (result instanceof Cohort) {
                    selectedCohort = (Cohort) result;
				}

				model.addAttribute("selectedCohort", selectedCohort);

				// Evaluate the default patient dataset definition
				DataSetDefinition dsd = null;
				if (applyDataSetId != null) {
					try {
						dsd = Context.getService(DataSetDefinitionService.class).getDefinition(applyDataSetId, null);
					} catch (Exception ex) {
						log.error("exception getting dataset definition", ex);
					}
				}

				if (dsd == null) {
					SimplePatientDataSetDefinition d = new SimplePatientDataSetDefinition();
					d.addPatientProperty("patientId");
					d.addIdentifierType(Context.getPatientService().getPatientIdentifierTypeByName("Code ST"));
					d.addIdentifierType(Context.getPatientService().getPatientIdentifierTypeByName("Code National"));
					d.addPatientProperty("givenName");
					d.addPatientProperty("familyName");
					d.addPatientProperty("age");
					d.addPatientProperty("gender");
					dsd = d;
				}

				EvaluationContext evalContext = new EvaluationContext();
				if (limit != null && limit > 0)
					evalContext.setLimit(limit);
				evalContext.setBaseCohort(selectedCohort);

				DataSet patientDataSet = Context.getService(DataSetDefinitionService.class).evaluate(dsd, evalContext);
				model.addAttribute("columnsValues", patientDataSet.iterator());
				model.addAttribute("dataSetDefinition", dsd);

				if (reportData != null) {
					DataSet dataSet = null;

					for (String key : reportData.getDataSets().keySet()) {
						dataSet = reportData.getDataSets().get(key);
					}

					model.addAttribute("reportName", dataSet.getDefinition().getName());
					model.addAttribute("columns", dataSet.getMetaData().getColumns());
					model.addAttribute("dataset", dataSet);
					model.addAttribute("startDate", request.getParameter("startDate"));
					model.addAttribute("endDate", request.getParameter("endDate"));
					model.addAttribute("id", request.getParameter("id"));
					model.addAttribute("dashboardUrlWithoutQueryParams",
							coreAppsProperties.getDashboardUrlWithoutQueryParams());
					model.addAttribute("privilegePatientDashboard",
							IsantePlusReportsProperties.PRIVILEGE_PATIENT_DASHBOARD);
				}
			}
		}
	}

}
