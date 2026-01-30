package org.openmrs.module.isanteplusreports.page.controller;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.module.coreapps.CoreAppsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlReportConstants;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlSurveillanceReportBuilder;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static j2html.TagCreator.*;
import static org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlSurveillanceReportBuilder.translateLabel;

public class DerlReportPatientCriteriaPageController {

    private final Log log = LogFactory.getLog(getClass());

    public void get(@RequestParam(required = false, value = "savedDataSetKey") String savedDataSetKey,
                    @RequestParam(required = false, value = "savedColumnKey") String savedColumnKey,
                    @RequestParam(required = false, value = "applyDataSetId") String applyDataSetId,
                    @RequestParam(required = false, value = "limit") Integer limit,
                    @RequestParam(required = false, value = "columnKeyType") String columnKeyType,
                    @SpringBean CoreAppsProperties coreAppsProperties,
                    HttpServletRequest request, PageModel model)
            throws EvaluationException, IOException, NumberFormatException, ParseException {

        @SuppressWarnings("unchecked")
        List<ReportData> allReportData = (List<ReportData>) request.getSession().getAttribute(ReportingConstants.OPENMRS_REPORT_DATA + DerlReportConstants.DERL_GENERAL_PURPOSE_SUFFIX);

        try {

            for (ReportData reportData : allReportData) {
                for (Map.Entry<String, DataSet> e : reportData.getDataSets().entrySet()) {
                    if (e.getKey().equals(savedDataSetKey)) {

                        MapDataSet mapDataSet = (MapDataSet) e.getValue();

                        Map<ContainerTag, LinkedHashMap<String, Integer>> mapHashMap = new LinkedHashMap<>();
                        ContainerTag tbody = tbody();
                        tbody.withStyle("font-size: 14px");

                        Object result;
                        DataSetColumn columnSelected = mapDataSet.getMetaData().getColumn(savedColumnKey);

                        System.out.println("DataSets:::" + mapDataSet.getDefinition().getName());

                        String header = null;

                        if (mapDataSet.getDefinition().getName().equalsIgnoreCase("isanteplusreports.derl.surveillance.report.weekly")) {
                            header = "Déclaration Hebdomadaire -   Semaine";
                        } else if (mapDataSet.getDefinition().getName().equalsIgnoreCase("isanteplusreports.derl.surveillance.report.immediateIndicator")) {
                            header = "Déclaration Immediate -   Semaine";
                        } else {
                            header = "Déclaration mensuelle -   ";
                        }

                        int i = 0;
                        for (DataSetColumn dd : mapDataSet.getData().getColumnValues().keySet()) {
                            if (dd.getLabel().equalsIgnoreCase(columnSelected.getLabel())) {

                                DataSetColumn dataSetColumn = mapDataSet.getMetaData().getColumn(dd.getName());
                                result = mapDataSet.getData(dataSetColumn);

                                model.addAttribute("selectedColumn", header + " " + dataSetColumn.toString());


                                Cohort selectedCohort = null;
                                log.debug(result.getClass().getName());

                                if (result instanceof CohortIndicatorAndDimensionResult) {
                                    CohortIndicatorAndDimensionResult cidr = (CohortIndicatorAndDimensionResult) mapDataSet
                                            .getData(dataSetColumn);
                                    if (columnKeyType.equals("denominator")) {
                                        //Can either be "denominator" or "numerator" (Default is numerator)
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

                                tbody.with(DerlSurveillanceReportBuilder.pushTableData(selectedCohort, i, patientDataSet, mapDataSet));

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

                                i++;
                            }
                        }

                        model.addAttribute("tbody", tbody);

                    }
                }
            }
        } catch (NullPointerException npe) {
            model.addAttribute("msg", "Session timeout, refresh to loging !");
        }
    }

}
