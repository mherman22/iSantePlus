package org.openmrs.module.isanteplusreports.page.controller;

import static org.openmrs.module.isanteplusreports.derlSurveillanceReport.utils.DerlSurveillanceReportUtils.getReportData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.alertprecoce.model.AlertPrecoceSelectedIndicator;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlReportConstants;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlSurveillanceReportBuilder;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.DerlSurveillanceReportManager;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class DerlSurveillanceReportPageController {

    public final static String LOCATION_SESSION_ATTRIBUTE = "emrContext.sessionLocationId";

    public void get(@SpringBean DerlSurveillanceReportManager derlManager,
                    @RequestParam(required = false, value = "startDate") Date startDate,
                    @RequestParam(required = false, value = "endDate") Date endDate, PageModel model) throws IOException {

        if (startDate == null) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 01);
            startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (endDate == null) {
            endDate = new Date();
        }
        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        model.addAttribute("manager", derlManager);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("htmlResult", null);
        model.addAttribute("pdfResult", null);
    }

    /*
    public void get(@SpringBean DerlSurveillanceReportManager derlManager,
            @RequestParam(value = "indicatorList") List<AlertPrecoceSelectedIndicator> indicators,
            @RequestParam(required = false, value = "startDate") Date startDate,
            @RequestParam(required = false, value = "endDate") Date endDate,
            PageModel model, HttpSession session) throws IOException {


        if (startDate == null) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 01);
            startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            //startDate = DateUtils.addDays(new Date(), -21);
        }
        if (endDate == null) {
            endDate = new Date();
        }

        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        DerlSurveillanceReportBuilder builder = new DerlSurveillanceReportBuilder();
        Location location = getSessionLocation(session);
        builder.setClinic(location.getDisplayString());
        builder.setClinicDepartment(location.getStateProvince());
        builder.setStartDate(startDate);
        builder.setEndDate(endDate);

        List<ReportData> allReportData = new ArrayList<ReportData>();
        for (AlertPrecoceSelectedIndicator indicator : indicators) {
            ReportData reportData = getReportData(indicator.getUuid(), startDate, endDate, indicator.getOptions());
            allReportData.add(reportData);
        }
        builder.buildHtmlTables(allReportData);

        session.setAttribute(ReportingConstants.OPENMRS_REPORT_DATA + DerlReportConstants.DERL_GENERAL_PURPOSE_SUFFIX, allReportData);
        model.addAttribute("manager", derlManager);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("htmlResult", builder.getTablesHtml());
        model.addAttribute("pdfResult", builder.buildPdf());
    }
    */

    public void post(@SpringBean DerlSurveillanceReportManager derlManager,
                     @RequestParam(value = "indicatorList") List<AlertPrecoceSelectedIndicator> indicators,
                     @RequestParam(required = false, value = "startDate") Date startDate,
                     @RequestParam(required = false, value = "endDate") Date endDate,
                     PageModel model, HttpSession session) throws IOException {

        if (startDate == null) {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 01);
            startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            //startDate = DateUtils.addDays(new Date(), -21);
        }

        if (endDate == null) {
            endDate = new Date();
        }

        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        DerlSurveillanceReportBuilder builder = new DerlSurveillanceReportBuilder();
        Location location = getSessionLocation(session);

        builder.setClinic(location.getDisplayString());
        builder.setClinicDepartment(location.getStateProvince());
        builder.setStartDate(startDate);
        builder.setEndDate(endDate);

        List<ReportData> allReportData = new ArrayList<>();

        for (AlertPrecoceSelectedIndicator indicator : indicators) {
            ReportData reportData = getReportData(indicator.getUuid(), startDate, endDate, indicator.getOptions());
            allReportData.add(reportData);
        }

        builder.buildHtmlTables(allReportData);
        session.setAttribute(ReportingConstants.OPENMRS_REPORT_DATA + DerlReportConstants.DERL_GENERAL_PURPOSE_SUFFIX, allReportData);

        model.addAttribute("manager", derlManager);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("htmlResult", builder.getTablesHtml());
        model.addAttribute("pdfResult", builder.buildPdf());
    }

    private Location getSessionLocation(HttpSession session) {
        Location location = Context.getUserContext().getLocation();
        if (location == null) {
            return Context.getLocationService().getDefaultLocation();
        }
        return location; // to get clinic data
    }


}
