package org.openmrs.module.isanteplusreports.derlSurveillanceReport;

import static j2html.TagCreator.*;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.getStringFromResource;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils.replaceNonBreakingSpaces;
import static org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.columns.ColumnsDerlLibrary.IMMEDIATE_COLUMNS_ARRAY;
import static org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.columns.ColumnsDerlLibrary.WEEKLY_COLUMNS_ARRAY;
import static org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.columns.ColumnsDerlLibrary.MONTHLY_COLUMNS_ARRAY;

import j2html.tags.ContainerTag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.MapDataSet;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.UiUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

public class DerlSurveillanceReportBuilder extends UiUtils {

    private final Log LOGGER = LogFactory.getLog(getClass());

    private static final int ROWS_3 = 3;

    private static final int ROWS_2 = 16;

    private static final int ROWS_4 = 20;

    private static final int ROWS_5 = 15;

    private static final String PERIOD_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

    private static final String CREATION_DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

    private static final String STRING_IF_EMPTY = "-";

    private int numberOfIndicatorsInOneTable = 1; // if there are too many indicators the table will be splitted

    private ContainerTag[] rows5;

    private ContainerTag[] rows2;

    private ContainerTag[] rows3;

    private ContainerTag[] rows4;


    private String clinicDepartment;

    private String clinic;

    private Date startDate;

    private Date endDate;

    private Long femalePatients;

    private Long malePatients;

    private String tablesHtml;

    private ContainerTag tables;
    private ContainerTag table1;
    private ContainerTag table2;
    private ContainerTag table3;


    public String[] getKeyImmediateColumnNamesArray() {
        return IMMEDIATE_COLUMNS_ARRAY;
    }

    public String[] getKeyWeeklyColumnNamesArray() {
        return WEEKLY_COLUMNS_ARRAY;
    }

    public List<String> getKeyImmediateColumnNamesList() {
        return Arrays.asList(getKeyImmediateColumnNamesArray());
    }

    public List<String> getKeyWeeklyColumnNamesList() {
        return Arrays.asList(getKeyWeeklyColumnNamesArray());
    }

    public String[] getKeyMonthlyColumnNamesArray() {
        return MONTHLY_COLUMNS_ARRAY;
    }

    public List<String> getKeyMonthlyColumnNamesList() {
        return Arrays.asList(getKeyMonthlyColumnNamesArray());
    }

    public String[] getKeyImmediateSingleRowColumnNamesArray() {
        return IMMEDIATE_COLUMNS_ARRAY;
    }

    public String[] getKeyWeeklySingleRowColumnNamesArray() {
        return WEEKLY_COLUMNS_ARRAY;
    }

    public List<String> getKeyWeeklySingleRowColumnNamesList() {
        return Arrays.asList(getKeyImmediateSingleRowColumnNamesArray());
    }

    private void buildIndicatorKeyImmediate(DataSet data) {

        String reportUrl = pageLink("isanteplusreports", "derlReportPatientCriteria");
        int[] dataSet = createSummaryArray(getKeyImmediateColumnNamesList(), data);
        String reportName = data.getDefinition().getName();

        String week01 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[0]);
        String week02 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[1]);
        String week03 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[2]);
        String week04 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[3]);
        String week05 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[4]);
        String week06 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[5]);
        String week07 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[6]);
        String week08 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[7]);
        String week09 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[8]);
        String week10 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[9]);

        String week11 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[10]);
        String week12 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[11]);
        String week13 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[12]);
        String week14 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[13]);
        String week15 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[14]);
        String week16 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[15]);
        String week17 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[16]);
        String week18 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[17]);
        String week19 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[18]);
        String week20 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[19]);

        String week21 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[20]);
        String week22 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[21]);
        String week23 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[22]);
        String week24 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[23]);
        String week25 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[24]);
        String week26 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[25]);
        String week27 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[26]);
        String week28 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[27]);
        String week29 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[28]);
        String week30 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[29]);

        String week31 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[30]);
        String week32 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[31]);
        String week33 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[32]);
        String week34 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[33]);
        String week35 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[34]);
        String week36 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[35]);
        String week37 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[36]);
        String week38 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[37]);
        String week39 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[38]);
        String week40 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[39]);

        String week41 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[40]);
        String week42 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[41]);
        String week43 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[42]);
        String week44 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[43]);
        String week45 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[44]);
        String week46 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[45]);
        String week47 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[46]);
        String week48 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[47]);
        String week49 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[48]);
        String week50 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[49]);

        String week51 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[50]);
        String week52 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[51]);
        String week53 = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[52]);
        String total = ConstructUrl(reportUrl, reportName, getKeyImmediateColumnNamesArray()[53]);

        getRows4()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "54").withClass("indicatorLabel"));
        getRows4()[1].with(
                td(a("01").withHref(week01).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("02").withHref(week02).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("03").withHref(week03).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("04").withHref(week04).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("05").withHref(week05).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("06").withHref(week06).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("07").withHref(week07).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("08").withHref(week08).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("09").withHref(week09).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("10").withHref(week10).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("11").withHref(week11).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("12").withHref(week12).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("13").withHref(week13).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("14").withHref(week14).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("15").withHref(week15).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("16").withHref(week16).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("17").withHref(week17).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("18").withHref(week18).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("19").withHref(week19).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("20").withHref(week20).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("21").withHref(week21).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("22").withHref(week22).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("23").withHref(week23).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("24").withHref(week24).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("25").withHref(week25).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("26").withHref(week26).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("27").withHref(week27).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("28").withHref(week28).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("29").withHref(week29).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("30").withHref(week30).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("31").withHref(week31).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("32").withHref(week32).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("33").withHref(week33).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("34").withHref(week34).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("35").withHref(week35).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("36").withHref(week36).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("37").withHref(week37).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("38").withHref(week38).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("39").withHref(week39).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("40").withHref(week40).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("41").withHref(week41).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("42").withHref(week42).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("43").withHref(week43).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("44").withHref(week44).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("45").withHref(week45).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("46").withHref(week46).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("47").withHref(week47).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("48").withHref(week48).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("49").withHref(week49).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("50").withHref(week50).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("51").withHref(week51).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("52").withHref(week52).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("53").withHref(week53).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a(translateLabel("Total")).withHref(total).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label")
        );

        buildIndicatorSummaryKeyImmediate(dataSet, getKeyImmediateColumnNamesArray(), reportName);
    }

    private void buildIndicatorKeyWeekly(DataSet data) {

        String reportUrl = pageLink("isanteplusreports", "derlReportPatientCriteria");
        int[] dataSet = createSummaryArray(getKeyWeeklyColumnNamesList(), data);
        String reportName = data.getDefinition().getName();

        String week01 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[0]);
        String week02 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[1]);
        String week03 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[2]);
        String week04 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[3]);
        String week05 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[4]);
        String week06 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[5]);
        String week07 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[6]);
        String week08 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[7]);
        String week09 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[8]);
        String week10 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[9]);

        String week11 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[10]);
        String week12 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[11]);
        String week13 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[12]);
        String week14 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[13]);
        String week15 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[14]);
        String week16 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[15]);
        String week17 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[16]);
        String week18 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[17]);
        String week19 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[18]);
        String week20 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[19]);

        String week21 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[20]);
        String week22 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[21]);
        String week23 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[22]);
        String week24 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[23]);
        String week25 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[24]);
        String week26 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[25]);
        String week27 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[26]);
        String week28 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[27]);
        String week29 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[28]);
        String week30 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[29]);

        String week31 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[30]);
        String week32 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[31]);
        String week33 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[32]);
        String week34 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[33]);
        String week35 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[34]);
        String week36 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[35]);
        String week37 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[36]);
        String week38 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[37]);
        String week39 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[38]);
        String week40 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[39]);

        String week41 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[40]);
        String week42 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[41]);
        String week43 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[42]);
        String week44 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[43]);
        String week45 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[44]);
        String week46 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[45]);
        String week47 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[46]);
        String week48 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[47]);
        String week49 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[48]);
        String week50 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[49]);

        String week51 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[50]);
        String week52 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[51]);
        String week53 = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[52]);
        String total = ConstructUrl(reportUrl, reportName, getKeyWeeklyColumnNamesArray()[53]);

        getRows2()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "54").withClass("indicatorLabel"));
        getRows2()[1].with(
                td(a("01").withHref(week01).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("02").withHref(week02).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("03").withHref(week03).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("04").withHref(week04).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("05").withHref(week05).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("06").withHref(week06).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("07").withHref(week07).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("08").withHref(week08).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("09").withHref(week09).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("10").withHref(week10).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("11").withHref(week11).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("12").withHref(week12).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("13").withHref(week13).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("14").withHref(week14).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("15").withHref(week15).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("16").withHref(week16).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("17").withHref(week17).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("18").withHref(week18).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("19").withHref(week19).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("20").withHref(week20).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("21").withHref(week21).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("22").withHref(week22).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("23").withHref(week23).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("24").withHref(week24).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("25").withHref(week25).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("26").withHref(week26).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("27").withHref(week27).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("28").withHref(week28).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("29").withHref(week29).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("30").withHref(week30).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("31").withHref(week31).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("32").withHref(week32).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("33").withHref(week33).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("34").withHref(week34).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("35").withHref(week35).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("36").withHref(week36).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("37").withHref(week37).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("38").withHref(week38).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("39").withHref(week39).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("40").withHref(week40).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("41").withHref(week41).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("42").withHref(week42).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("43").withHref(week43).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("44").withHref(week44).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("45").withHref(week45).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("46").withHref(week46).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("47").withHref(week47).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("48").withHref(week48).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("49").withHref(week49).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("50").withHref(week50).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),

                td(a("51").withHref(week51).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("52").withHref(week52).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("53").withHref(week53).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a(translateLabel("Total")).withHref(total).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label")
        );

        buildIndicatorSummaryKeyWeekly(dataSet, getKeyWeeklyColumnNamesArray(), reportName);
    }



    private void buildIndicatorKeyMonthly(DataSet data) {

        String reportUrl = pageLink("isanteplusreports", "derlReportPatientCriteria");
        int[] dataSet = createSummaryArray(getKeyMonthlyColumnNamesList(), data);
        String reportName = data.getDefinition().getName();

        String jan = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[0]);
        String feb = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[1]);
        String mar = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[2]);
        String apr = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[3]);
        String may = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[4]);
        String jun = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[5]);
        String jul = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[6]);
        String aug = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[7]);
        String sep = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[8]);
        String oct = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[9]);
        String nov = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[10]);
        String dec = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[11]);
        String total = ConstructUrl(reportUrl, reportName, getKeyMonthlyColumnNamesArray()[12]);

        getRows5()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "13").withClass("indicatorLabel"));
        getRows5()[1].with(
                td(a("JANVIER").withHref(jan).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("FEVRIER").withHref(feb).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=250, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("MARS").withHref(mar).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("AVRIL").withHref(apr).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("MAI").withHref(may).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("JUIN").withHref(jun).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("JUILLET").withHref(jul).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("AOUT").withHref(aug).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("SEPTEMBRE").withHref(sep).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("OCTOBRE").withHref(oct).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("NOVEMBRE").withHref(nov).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("DECEMBRE").withHref(dec).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label"),
                td(a("Total").withHref(total).attr("onclick", "window.open(this.href, 'windowName', 'width=1400, height=800, left=300, top=100, scrollbars, resizable'); return false;").withStyle("color: #393683"))
                        .attr("colspan", "1").withClass("label")
        );
        buildIndicatorSummaryMonthly(dataSet, getKeyMonthlyColumnNamesArray(), reportName);
    }


    public static ContainerTag indicatorHeaderMonthly(int index) {

        List<ContainerTag> containerTagList = new ArrayList<>();

        containerTagList.add(td(translateLabel("accidentOnPublicRoads")));
        containerTagList.add(td(translateLabel("domesticAccident")));
        containerTagList.add(td(translateLabel("breastCancer")));
        containerTagList.add(td(translateLabel("prostateCancer")));
        containerTagList.add(td(translateLabel("cancerCol")));
        containerTagList.add(td(translateLabel("diabete")));
        containerTagList.add(td(translateLabel("epilepsie")));
        containerTagList.add(td(translateLabel("hypertensionAt")));
        containerTagList.add(td(translateLabel("lepre")));
        containerTagList.add(td(translateLabel("malnutrition")));
        containerTagList.add(td(translateLabel("syphilisCong")));
        containerTagList.add(td(translateLabel("ist")));
        containerTagList.add(td(translateLabel("violencePhys")));

        return containerTagList.get(index);
    }


    public static ContainerTag indicatorHeaderWeekly(int index) {

        List<ContainerTag> containerTagList = new ArrayList<>();

        containerTagList.add(td(translateLabel("AutreFievreIndetermine")));
        containerTagList.add(td(translateLabel("CharbonCutane")));
        containerTagList.add(td(translateLabel("DecesMaternel")));
        containerTagList.add(td(translateLabel("DiarrheeAigue")));
        containerTagList.add(td(translateLabel("diarrheeAigueS")));
        containerTagList.add(td(translateLabel("esaviMineure")));
        containerTagList.add(td(translateLabel("fievreTyphoideSuspecte")));
        containerTagList.add(td(translateLabel("infectionRespiratoireAigue")));
        containerTagList.add(td(translateLabel("tetanos")));
        containerTagList.add(td(translateLabel("dengueSupecte")));
        containerTagList.add(td(translateLabel("filarioseProbable")));
        containerTagList.add(td(translateLabel("rageHumaine")));
        containerTagList.add(td(translateLabel("syndromeIcteriqueFebrile")));
        containerTagList.add(td(translateLabel("violencesSexuelles")));

        return containerTagList.get(index);
    }

    public static ContainerTag indicatorHeaderImmediate(int index) {

        List<ContainerTag> containerTagList = new ArrayList<>();

        containerTagList.add(td(translateLabel("assaultByAnimalSuspiciousOfRabies")));
        containerTagList.add(td(translateLabel("covidSuspect")));
        containerTagList.add(td(translateLabel("covidConfirm")));
        containerTagList.add(td(translateLabel("choleraSuspect")));
        containerTagList.add(td(translateLabel("coquelucheSuspect")));
        containerTagList.add(td(translateLabel("diphterieSuspect")));
        containerTagList.add(td(translateLabel("meningiteSuspect")));
        containerTagList.add(td(translateLabel("paludismeConfirme")));
        containerTagList.add(td(translateLabel("paralysieFlasqueAigue")));
        containerTagList.add(td(translateLabel("pesteSuspect")));
        containerTagList.add(td(translateLabel("rougeoleRubeoleSuspecte")));
        containerTagList.add(td(translateLabel("syndrFievreHemAigue")));
        containerTagList.add(td(translateLabel("syndrRubeoleCogen")));
        containerTagList.add(td(translateLabel("esaviMageure")));
        containerTagList.add(td(translateLabel("mortaliteNeonatale")));
        containerTagList.add(td(translateLabel("tetanosNeonatal")));
        containerTagList.add(td(translateLabel("tiac")));
        containerTagList.add(td(translateLabel("toutPhenomeneInhab")));

        return containerTagList.get(index);
    }

    public static ContainerTag extract(int i, MapDataSet mapDataSet) {
        if (mapDataSet.getDefinition().getName().equalsIgnoreCase("isanteplusreports.derl.surveillance.report.weekly")) {
            return indicatorHeaderWeekly(i);
        } else if (mapDataSet.getDefinition().getName().equalsIgnoreCase("isanteplusreports.derl.surveillance.report.monthlyIndicator")) {
            return indicatorHeaderMonthly(i);
        } else if(mapDataSet.getDefinition().getName().equalsIgnoreCase("isanteplusreports.derl.surveillance.report.immediateIndicator")){
            return indicatorHeaderImmediate(i);
        }
        return null;
    }


    public static ContainerTag pushTableData(Cohort selectedCohort, int i, DataSet patientDataSet, MapDataSet mapDataSet) {

//        String reportUrl = pageLink("isanteplusreports", "derlReportPatientList");
//        String reportName = patientDataSet.getDefinition().getName();

        ContainerTag tr = tr();
        tr.attr("collapse", "collapse").withStyle("height: 40px");

        if (selectedCohort.getSize() == 0) {

            tr.with(extract(i, mapDataSet),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(0)).attr("align", "center").withStyle("color: #007fbf")
            );

        } else if (selectedCohort.getSize() > 0) {

            int countM1 = 0;
            int countM2 = 0;
            int countM3 = 0;
            int countM4 = 0;
            int countM5 = 0;
            int countM6;

            int countF1 = 0;
            int countF2 = 0;
            int countF3 = 0;
            int countF4 = 0;
            int countF5 = 0;
            int countF6;

            for (Iterator<DataSetRow> it = patientDataSet.iterator(); it.hasNext(); ) {
                DataSetRow dataSetRowIterator = it.next();

                String gender = dataSetRowIterator.getColumnValue("gender").toString();
                int age = Integer.parseInt(dataSetRowIterator.getColumnValue("age").toString());

                if (gender.equalsIgnoreCase("M")) {
                    if (age == 0) {
                        countM1++;
                    } else if (age >= 1 && age < 5) {
                        countM2++;
                    } else if (age >= 5 && age <= 14) {
                        countM3++;
                    } else if (age >= 15 && age <= 49) {
                        countM4++;
                    } else if (age >= 50) {
                        countM5++;
                    }
                } else if (gender.equalsIgnoreCase("F")) {
                    if (age == 0) {
                        countF1++;
                    } else if (age >= 1 && age < 5) {
                        countF2++;
                    } else if (age >= 5 && age <= 14) {
                        countF3++;
                    } else if (age >= 15 && age <= 49) {
                        countF4++;
                    } else if (age >= 50) {
                        countF5++;
                    }
                }
            }

            countM6 = countM1 + countM2 + countM3 + countM4 + countM5;
            countF6 = countF1 + countF2 + countF3 + countF4 + countF5;

            tr.with(extract(i, mapDataSet),
                    td(String.valueOf(countM1)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF1)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countM2)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF2)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countM3)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF3)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countM4)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF4)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countM5)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF5)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countM6)).attr("align", "center").withStyle("color: #007fbf"),
                    td(String.valueOf(countF6)).attr("align", "center").withStyle("color: #007fbf")
            );
        }
        return tr;
    }


    private ContainerTag buildOneTableImmediate(Iterator<DataSet> iterator) {
        buildImmediateSurveillanceSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyImmediate(iterator.next());
        }
        return table().with(getRows4());
    }

    private ContainerTag buildOneTableWeekly(Iterator<DataSet> iterator) {
        buildWeeklySurveillanceSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyWeekly(iterator.next());
        }
        return table().with(getRows2());
    }

    private ContainerTag buildOneTableMonthly(Iterator<DataSet> iterator) {
        buildMonthlySurveillanceSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyMonthly(iterator.next());
        }
        return table().with(getRows5());
    }


    private void buildIndicatorSummaryKeyImmediate(int[] dataArray, String[] columnsArray, String reportName) {
        /*The good one*/
        String reportUrl = pageLink("isanteplusreports", "derlReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 19; ROW++) {
            for (int col = 0; col <= 53; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable4(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummaryKeyWeekly(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "derlReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 15; ROW++) {
            for (int col = 0; col <= 53; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable2(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummaryMonthly(int[] dataArray, String[] columnsArray, String reportName) {
        /*The good one*/
        String reportUrl = pageLink("isanteplusreports", "derlReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 14; ROW++) {
            for (int col = 0; col <= 12; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable5(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }


    private String ConstructUrl(String reportBaseUrl, String reportName, String columnName) {
        return String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportBaseUrl, reportName, columnName);
    }

    private void populateTable4(Integer ROW, int data, String rowLink) {
        getRows4()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1200, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable2(Integer ROW, int data, String rowLink) {
        getRows2()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1200, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable5(Integer ROW, int data, String rowLink) {
        getRows5()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1200, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }


    public int getNumberOfIndicatorsInOneTable() {
        return numberOfIndicatorsInOneTable;
    }

    public void setNumberOfIndicatorsInOneTable(int numberOfIndicatorsInOneTable) {
        this.numberOfIndicatorsInOneTable = numberOfIndicatorsInOneTable;
    }

    private Integer getDataSetIntegerValue(DataSet dataSet, String columnName) {
        Object value = dataSet.iterator().next().getColumnValue(columnName);
        if (value == null || StringUtils.isBlank(value.toString())) {
            dataSet.getDefinition().getName();
            throw new HealthQualException("`" + dataSet.getDefinition().getName() + "` report - column `" + columnName
                    + "` doesn't exist in dataSet. Probably there is a bug in report SQL");
        }
        return Integer.valueOf(value.toString());
    }

    public static String translateLabel(String labelName) {
        return translate("isanteplusreports.derl.surveillance.report." + labelName + ".label");
    }

    private int[] createSummaryArray(List<String> columNames, DataSet dataSet) {
        List<Integer> columnValues = new ArrayList<Integer>();
        for (String column : columNames) {
            columnValues.add(getDataSetIntegerValue(dataSet, column));
        }
        return columnValues.stream().mapToInt(Integer::intValue).toArray();
    }

    public String buildHtmlTables(List<ReportData> allReportData) {
        setTablesHtml(buildTables(allReportData).render());
        LOGGER.debug("built tables html: " + tablesHtml);
        return getTablesHtml();
    }

    public String buildPdf() {
        String htmlForPdf = html(head(getStyleForPdf()), body(createPdfHeader(), this.tables)).render();
        LOGGER.debug("built htmlForPdf: " + htmlForPdf);
        try {
            return convertHtmlToPdfInBase64(htmlForPdf);
        } catch (Exception ex) {
            throw new HealthQualException("PDF cannot be created", ex);
        }
    }

    private ContainerTag createPdfHeader() {
        return div().withClass("center").with(h2(translateLabel("pdf.header")), h3(createPeriodString()),
                h5(createDateOfCreationString()));
    }

    private String createPeriodString() {
        SimpleDateFormat df = new SimpleDateFormat(PERIOD_DATE_FORMAT_PATTERN);
        return df.format(startDate) + " - " + df.format(endDate);
    }

    private String createDateOfCreationString() {
        SimpleDateFormat df = new SimpleDateFormat(CREATION_DATE_FORMAT_PATTERN);
        return translateLabel("pdf.creationDate") + " " + df.format(new Date());
    }

    private ContainerTag getStyleForPdf() {
        return style().withType("text/css").withText(getStringFromResource("healthQualPdfStyle.css"));
    }

    private String convertHtmlToPdfInBase64(String html)
            throws IOException, ParserConfigurationException, SAXException, DocumentException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Document doc = builder.parse(new
        // ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes()));
        Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes("UTF-8")));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);

        renderer.layout();
        renderer.createPDF(out);
        out.flush();
        out.close();

        return DatatypeConverter.printBase64Binary(out.toByteArray());
    }


    private ContainerTag buildTables(List<ReportData> allReportData) {

        this.tables = div();
        this.table1 = div();
        this.table2 = div();
        this.table3 = div();

        //01167 - DTG - ALTARAGI
        // 555886
        // Hopital Adventiste d'Haiti

        this.tables.attr("class", "table table-striped table-hover");

        this.table1.attr("id", "immediate");
        this.table1.attr("class", "tabcontent");

        this.table2.attr("id", "hebdomadaire");
        this.table2.attr("class", "tabcontent");

        this.table3.attr("id", "mensuel");
        this.table3.attr("class", "tabcontent");

        for (ReportData reportData : allReportData) {
            if (StringUtils.equals(reportData.getDefinition().getDescription(), DerlReportConstants.REPORT_DESCRIPTION_1)) {
                List<DataSet> data = new LinkedList<>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.table1.with(buildOneTableImmediate(iterator));
                    clearRows3();
                }
                this.tables.with(this.table1);
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(), DerlReportConstants.REPORT_DESCRIPTION_WEEKLY)) {
                List<DataSet> data = new LinkedList<>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.table2.with(buildOneTableWeekly(iterator));
                    clearRows2();
                }
                this.tables.with(this.table2);
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(), DerlReportConstants.REPORT_DESCRIPTION_2)) {
                List<DataSet> data = new LinkedList<>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.table3.with(buildOneTableMonthly(iterator));
                    clearRows5();
                }
                this.tables.with(this.table3);
            }
        }
        return this.tables;
    }

    private void buildImmediateSurveillanceSummaryTable() {
        fillEmptyRow(getRows4()[0], 1);
        fillEmptyRow(getRows4()[1], 1);
        ContainerTag rageAnimal = getRows4()[2];
        ContainerTag covidSuspect = getRows4()[3];
        ContainerTag covidConfirm = getRows4()[4];
        ContainerTag choleraSuspect = getRows4()[5];

        ContainerTag coquelucheSuspect = getRows4()[6];
        ContainerTag diphterieSuspect = getRows4()[7];
        ContainerTag meningiteSuspect = getRows4()[8];
        ContainerTag paludismeConfirme = getRows4()[9];
        ContainerTag paralysieFlasqueAigue = getRows4()[10];
        ContainerTag pesteSuspect = getRows4()[11];

        ContainerTag rougeoleRubeoleSuspecte = getRows4()[12];
        ContainerTag syndrFievreHemAigue = getRows4()[13];
        ContainerTag syndrRubeoleCogen = getRows4()[14];
        ContainerTag esaviMageure = getRows4()[15];
        ContainerTag mortaliteNeonatale = getRows4()[16];
        ContainerTag tetanosNeonatal = getRows4()[17];
        ContainerTag tiac = getRows4()[18];
        ContainerTag toutPhenomeneInhab = getRows4()[19];


        rageAnimal.with(th(translateLabel("assaultByAnimalSuspiciousOfRabies")).withClass("label").withStyle("width:100px"));
        covidSuspect.with(th(translateLabel("covidSuspect")).withClass("label"));
        covidConfirm.with(th(translateLabel("covidConfirm")).withClass("label"));
        choleraSuspect.with(th(translateLabel("choleraSuspect")).withClass("label"));


        coquelucheSuspect.with(th(translateLabel("coquelucheSuspect")).withClass("label").withStyle("width:100px"));
        diphterieSuspect.with(th(translateLabel("diphterieSuspect")).withClass("label"));
        meningiteSuspect.with(th(translateLabel("meningiteSuspect")).withClass("label"));
        paludismeConfirme.with(th(translateLabel("paludismeConfirme")).withClass("label"));
        paralysieFlasqueAigue.with(th(translateLabel("paralysieFlasqueAigue")).withClass("label").withStyle("width:100px"));
        pesteSuspect.with(th(translateLabel("pesteSuspect")).withClass("label"));

        rougeoleRubeoleSuspecte.with(th(translateLabel("rougeoleRubeoleSuspecte")).withClass("label").withStyle("width:100px"));
        syndrFievreHemAigue.with(th(translateLabel("syndrFievreHemAigue")).withClass("label"));
        syndrRubeoleCogen.with(th(translateLabel("syndrRubeoleCogen")).withClass("label"));
        esaviMageure.with(th(translateLabel("esaviMageure")).withClass("label"));
        mortaliteNeonatale.with(th(translateLabel("mortaliteNeonatale")).withClass("label").withStyle("width:100px"));
        tetanosNeonatal.with(th(translateLabel("tetanosNeonatal")).withClass("label"));
        tiac.with(th(translateLabel("tiac")).withClass("label").withStyle("width:100px"));
        toutPhenomeneInhab.with(th(translateLabel("toutPhenomeneInhab")).withClass("label"));

    }

    private void buildWeeklySurveillanceSummaryTable() {
        fillEmptyRow(getRows2()[0], 1);
        fillEmptyRow(getRows2()[1], 1);
        ContainerTag AutreFievreIndetermine = getRows2()[2];
        ContainerTag CharbonCutane = getRows2()[3];
        ContainerTag DecesMaternel = getRows2()[4];
        ContainerTag DiarrheeAigue = getRows2()[5];

        ContainerTag diarrheeAigueS = getRows2()[6];
        ContainerTag esaviMineure = getRows2()[7];
        ContainerTag fievreTyphoideSuspecte = getRows2()[8];
        ContainerTag infectionRespiratoireAigue = getRows2()[9];

        ContainerTag tetanos = getRows2()[10];
        ContainerTag dengueSupecte = getRows2()[11];
        ContainerTag filarioseProbable = getRows2()[12];
        ContainerTag rageHumaine = getRows2()[13];
        ContainerTag syndromeIcteriqueFebrile = getRows2()[14];
        ContainerTag violencesSexuelles = getRows2()[15];

        AutreFievreIndetermine.with(th(translateLabel("AutreFievreIndetermine")).withClass("label").withStyle("width:100px"));
        CharbonCutane.with(th(translateLabel("CharbonCutane")).withClass("label"));
        DecesMaternel.with(th(translateLabel("DecesMaternel")).withClass("label"));
        DiarrheeAigue.with(th(translateLabel("DiarrheeAigue")).withClass("label"));

        diarrheeAigueS.with(th(translateLabel("diarrheeAigueS")).withClass("label").withStyle("width:100px"));
        esaviMineure.with(th(translateLabel("esaviMineure")).withClass("label"));
        fievreTyphoideSuspecte.with(th(translateLabel("fievreTyphoideSuspecte")).withClass("label"));
        infectionRespiratoireAigue.with(th(translateLabel("infectionRespiratoireAigue")).withClass("label"));

        tetanos.with(th(translateLabel("tetanos")).withClass("label").withStyle("width:100px"));
        dengueSupecte.with(th(translateLabel("dengueSupecte")).withClass("label"));
        filarioseProbable.with(th(translateLabel("filarioseProbable")).withClass("label"));
        rageHumaine.with(th(translateLabel("rageHumaine")).withClass("label"));
        syndromeIcteriqueFebrile.with(th(translateLabel("syndromeIcteriqueFebrile")).withClass("label"));
        violencesSexuelles.with(th(translateLabel("violencesSexuelles")).withClass("label"));
    }

    private void buildMonthlySurveillanceSummaryTable() {
        fillEmptyRow(getRows5()[0], 1);
        fillEmptyRow(getRows5()[1], 1);
        ContainerTag publicAcc = getRows5()[2];
        ContainerTag domesticAcc = getRows5()[3];
        ContainerTag breastCancer = getRows5()[4];
        ContainerTag prostateCancer = getRows5()[5];

        ContainerTag cancerCol = getRows5()[6];
        ContainerTag diabete = getRows5()[7];
        ContainerTag epilepsie = getRows5()[8];

        ContainerTag hypertensionAt = getRows5()[9];
        ContainerTag lepre = getRows5()[10];
        ContainerTag malnutrition = getRows5()[11];

        ContainerTag syphilisCong = getRows5()[12];
        ContainerTag ist = getRows5()[13];
        ContainerTag violencePhys = getRows5()[14];

        publicAcc.with(th(translateLabel("accidentOnPublicRoads")).withClass("label"));
        domesticAcc.with(th(translateLabel("domesticAccident")).withClass("label"));
        breastCancer.with(th(translateLabel("breastCancer")).withClass("label"));
        prostateCancer.with(th(translateLabel("prostateCancer")).withClass("label"));

        cancerCol.with(th(translateLabel("cancerCol")).withClass("label"));
        diabete.with(th(translateLabel("diabete")).withClass("label"));
        epilepsie.with(th(translateLabel("epilepsie")).withClass("label"));

        hypertensionAt.with(th(translateLabel("hypertensionAt")).withClass("label"));
        lepre.with(th(translateLabel("lepre")).withClass("label"));
        malnutrition.with(th(translateLabel("malnutrition")).withClass("label"));

        syphilisCong.with(th(translateLabel("syphilisCong")).withClass("label"));
        ist.with(th(translateLabel("ist")).withClass("label"));
        violencePhys.with(th(translateLabel("violencePhys")).withClass("label"));
    }

    public ContainerTag[] getRows3() {
        if (rows3 == null) {
            rows3 = new ContainerTag[ROWS_3];
            for (int i = 0; i < ROWS_3; ++i) {
                rows3[i] = tr();
            }
        }
        return rows3;
    }

    private void fillEmptyRow(ContainerTag row, Integer length) {
        row.with(td().attr("colspan", length.toString()));
    }

    private void clearRows3() {
        setRows3(null);
    }

    public void setRows3(ContainerTag[] rows) {
        this.rows3 = rows;
    }

    private void clearRows5() {
        setRows5(null);
    }

    public void setRows5(ContainerTag[] rows) {
        this.rows5 = rows;
    }


    private void clearRows2() {
        setRows2(null);
    }

    public void setRows2(ContainerTag[] rows) {
        this.rows5 = rows;
    }

    public void setRows(ContainerTag[] rows) {
        this.rows5 = rows;
    }

    public String getClinic() {
        return StringUtils.isNotBlank(clinic) ? clinic : STRING_IF_EMPTY;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getClinicDepartment() {
        return StringUtils.isNotBlank(clinicDepartment) ? clinicDepartment : STRING_IF_EMPTY;
    }

    public void setClinicDepartment(String clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    public ContainerTag[] getRows4() {
        if (rows4 == null) {
            rows4 = new ContainerTag[ROWS_4];
            for (int i = 0; i < ROWS_4; ++i) {
                rows4[i] = tr();
            }
        }
        return rows4;
    }

    public ContainerTag[] getRows2() {
        if (rows2 == null) {
            rows2 = new ContainerTag[ROWS_2];
            for (int i = 0; i < ROWS_2; ++i) {
                rows2[i] = tr();
            }
        }
        return rows2;
    }

    public ContainerTag[] getRows5() {
        if (rows5 == null) {
            rows5 = new ContainerTag[ROWS_5];
            for (int i = 0; i < ROWS_5; ++i) {
                rows5[i] = tr();
            }
        }
        return rows5;
    }

    private static String translate(String code) {
        return replaceNonBreakingSpaces(MessageUtil.translate(code));
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getFemalePatients() {
        return femalePatients;
    }

    public void setFemalePatients(Long femalePatients) {
        this.femalePatients = femalePatients;
    }

    public Long getMalePatients() {
        return malePatients;
    }

    public void setMalePatients(Long malePatients) {
        this.malePatients = malePatients;
    }

    public String getTablesHtml() {
        return tablesHtml;
    }

    public void setTablesHtml(String tablesHtml) {
        this.tablesHtml = tablesHtml;
    }
}
