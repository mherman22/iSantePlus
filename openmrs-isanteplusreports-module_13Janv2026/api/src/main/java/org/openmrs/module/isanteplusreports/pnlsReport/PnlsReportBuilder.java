package org.openmrs.module.isanteplusreports.pnlsReport;

import static j2html.TagCreator.a;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.h5;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.style;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.tr;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.getStringFromResource;
import static org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils.replaceNonBreakingSpaces;
import static org.openmrs.module.isanteplusreports.pnlsReport.library.columns.ColumnsLibrary.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.UiUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

import j2html.tags.ContainerTag;

public class PnlsReportBuilder extends UiUtils {

    private final Log LOGGER = LogFactory.getLog(getClass());

    private static final int ROWS_5 = 5;

    private static final int ROWS_15 = 15;
    private static final int ROWS_18 = 18;

    private static final int ROWS_2 = 2;

    private static final int ROWS_3 = 3;

    private static final int ROWS_4 = 4;

    private static final int ROWS_8 = 8;

    private static final int ROWS_7 = 7;

    private static final int ROWS_9 = 9;

    private static final int ROWS_11 = 11;

    private static final int ROWS_6 = 6;

    private static final int ROWS_13 = 13;

    private static final String PERIOD_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

    private static final String CREATION_DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

    private static final String STRING_IF_EMPTY = "-";

    private int numberOfIndicatorsInOneTable = 1; // if there are too many indicators the table will be splitted

    private ContainerTag[] rows5;

    private ContainerTag[] rows14;
    private ContainerTag[] rows17;

    private ContainerTag[] rows2;

    private ContainerTag[] rows3;

    private ContainerTag[] rows4;

    private ContainerTag[] rows8;

    private ContainerTag[] rows7;

    private ContainerTag[] rows9;

    private ContainerTag[] rows11;

    private ContainerTag[] rows6;

    private ContainerTag[] rows13;

    private String clinicDepartment;

    private String clinic;

    private Date startDate;

    private Date endDate;

    private Long femalePatients;

    private Long malePatients;

    private String tablesHtml;

    private ContainerTag tables;

    public String[] getColumnNamesArray14By3() {
        return COLUMNS_ARRAY_14_BY_3;
    }

    public String[] getColumnNamesArray17By3() {
        return COLUMNS_ARRAY_17_BY_3;
    }

    public List<String> getColumnNamesList14By3() {
        return Arrays.asList(getColumnNamesArray14By3());
    }

    public List<String> getColumnNamesList17By3() {
        return Arrays.asList(getColumnNamesArray17By3());
    }

    public String[] getBreastFeedingColumnNamesArray() {
        return COULUMNS_ARRAY_1_BY_1;
    }

    public List<String> getBreastFeedingColumnNamesList() {
        return Arrays.asList(getBreastFeedingColumnNamesArray());
    }

    public String[] getColumnNamesArray14By17() {
        return COLUMNS_ARRAY_14_BY_17;
    }

    public List<String> getColumnNamesList14By17() {
        return Arrays.asList(getColumnNamesArray14By17());
    }

    public String[] getKeyPopnColumnNamesArray() {
        return KEY_POPN_COLUMNS_ARRAY;
    }

    public String[] getScreeneTbColumnNamesArray() {
        return KEY_SCRN_COLUMNS_ARRAY;
    }

    public List<String> getKeyPopnColumnNamesList() {
        return Arrays.asList(getKeyPopnColumnNamesArray());
    }

    public List<String> getScreeneTbColumnNamesList() {
        return Arrays.asList(getScreeneTbColumnNamesArray());
    }

    public String[] getCtxColumnNamesArray() {
        return CTX_COLUMNS_ARRAY;
    }

    public List<String> getCtxColumnNamesList() {
        return Arrays.asList(getCtxColumnNamesArray());
    }

    public String[] getAgeBy15ColumnNamesArray() {
        return COLUMNS_ARRAY_AGE_BY_15;
    }

    public String[] getAgeSupp15ColumnNamesArray() {
        return COLUMNS_ARRAY_AGE_SUPP_15;
    }

    public String[] getAgeBy2ColumnNamesArray() {
        return COLUMNS_ARRAY_AGE_BY_2;
    }

    public String[] getAgeBy14ColumnNamesArray() {
        return COLUMNS_ARRAY_AGE_BY_14;
    }

    public List<String> getAgeBy15ColumnNamesList() {
        return Arrays.asList(getAgeBy15ColumnNamesArray());
    }

    public List<String> getAgeSupp15ColumnNamesList() {
        return Arrays.asList(getAgeSupp15ColumnNamesArray());
    }

    ///////////////////////////////////////////////////////////////
    public List<String> getAgeBy2ColumnNamesList() {
        return Arrays.asList(getAgeBy2ColumnNamesArray());
    }

    public List<String> getAgeBy14ColumnNamesList() {
        return Arrays.asList(getAgeBy14ColumnNamesArray());
    }
    ///////////////////////////////////////////////////////////

    public String[] getArvPatientsByTbDiagnosisTestColumnNamesArray() {
        return COLUMNS_ARRAY_ARV_PATIENTS_BY_TB_DIAGNOSIS_TEST;
    }

    public List<String> getArvPatientsByTbDiagnosisTestColumnNamesList() {
        return Arrays.asList(getArvPatientsByTbDiagnosisTestColumnNamesArray());
    }

    public String[] getColumnNamesArray17By4() {
        return COLUMNS_ARRAY_17_BY_4;
    }

    public List<String> getColumnNamesList17By4() {
        return Arrays.asList(getColumnNamesArray17By4());
    }

    public String[] getKeyPopnSingleRowColumnNamesArray() {
        return COLUMNS_ARRAY_KEY_POPN_SINGLE_ROW;
    }

    public String[] getSpecimenSingleRowColumnNamesArray() {
        return COLUMNS_ARRAY_SPECIMEN_SINGLE_ROW;
    }

    public String[] getScreeneSingleRowColumnNamesArray() {
        return COLUMNS_ARRAY_KEY_SCREENTB_SINGLE_ROW;
    }

    public List<String> getKeyPopnSingleRowColumnNamesList() {
        return Arrays.asList(getKeyPopnSingleRowColumnNamesArray());
    }

    public List<String> getSpecimenSingleRowColumnNamesList() {
        return Arrays.asList(getSpecimenSingleRowColumnNamesArray());
    }

    public List<String> getScreeneTbSingleRowColumnNamesList() {
        return Arrays.asList(getScreeneSingleRowColumnNamesArray());
    }

    public String[] getColumnNamesArray4By7() {
        return COLUMNS_ARRAY_4By7;
    }

    public List<String> getColumnNamesList4By7() {
        return Arrays.asList(getColumnNamesArray4By7());
    }

    public String[] getColumnNamesArray4By3() {
        return COLUMNS_ARRAY_4BY3;
    }

    public String[] getColumnNamesArray3By3() {
        return COLUMNS_ARRAY_3BY3;
    }

    public String[] getColumnNamesArray2By14() {
        return COLUMNS_ARRAY_2BY14;
    }


    public List<String> getColumnNamesList4By3() {
        return Arrays.asList(getColumnNamesArray4By3());
    }

//	public List<String> getColumnNamesList3By3() {
//		return Arrays.asList(getColumnNamesArray3By3());
//	}
//	public List<String> getColumnNamesList2By14() {
//		return Arrays.asList(getColumnNamesArray2By14());
//	}

    ///////////////////////////////////////////////////

    public String[] getActivePatientRegimenLinesColumnNamesArray() {
        return COLUMNS_ARRAY_ACTIVE_PATIENTS_BY_REGIME;
    }

    public List<String> getActivePatientRegimenLinesColumnNamesList() {
        return Arrays.asList(getActivePatientRegimenLinesColumnNamesArray());
    }

    public String[] getwomenCancerStatusColumnNamesArray() {
        return COLUMNS_ARRAY_CERVICAL_CANCER_STATUS;
    }

    public List<String> getwomenCancerStatusColumnNamesList() {
        return Arrays.asList(getwomenCancerStatusColumnNamesArray());
    }

    public String[] getwomenCancerTreatmentColumnNamesArray() {
        return COLUMNS_ARRAY_CERVICAL_CANCER_TREATMENT;
    }

    public List<String> getwomenCancerTreatmentColumnNamesList() {
        return Arrays.asList(getwomenCancerTreatmentColumnNamesArray());
    }

    public String[] getFamilyPlanningColumnNamesArray() {
        return COLUMNS_ARRAY_FAMILY_PLANNING;
    }

    public List<String> getFamilyPlanningColumnNamesList() {
        return Arrays.asList(getFamilyPlanningColumnNamesArray());
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
        for (ReportData reportData : allReportData) {
            if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY3)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTable14By3(iterator));
                    clearRows();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_17BY3)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTable17By3(iterator));
                    clearRows();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_1BY1)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTable1By1(iterator));
                    clearRows2();
                }

            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY17)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTable14By17(iterator));
                    clearRows14();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_6BY3)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableKeyPopn(iterator));
                    clearRows4();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY6)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneCtxTable(iterator));
                    clearRows8();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY6_II)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneScreeneTable(iterator));
                    clearRows8();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_4BY5)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTbTable(iterator));
                    clearRows7();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_3BY1)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTbDiagnosisTestTable(iterator));
                    clearRows2();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY4)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOne14By4Table(iterator));
                    clearRows7();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_6BY2)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableKeyPopnBySingleRow(iterator));
                    clearRows3();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_8BY2)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableScpecimenBySingleRow(iterator));
                    clearRows3();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_4BY2)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableKeyScreenBySingleRow(iterator));
                    clearRows3();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_4BY7)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableForActivePatientsOverMonths(iterator));
                    clearRows9();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_4BY7_II)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableForActivePatientsWithAtleastOneFollowUpVist(iterator));
                    clearRows9();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY9)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableForActivePatientsByRegimenLines(iterator));
                    clearRows11();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_14BY9_II)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableForActivePatientsByScreeneTbLines(iterator));
                    clearRows11();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_10BY4)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableforCervicalCancerStatus(iterator));
                    clearRows6();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_10BY4_II)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableforCervicalCancerTreatment(iterator));
                    clearRows6();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_10BY11)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTableFamillyPlanning(iterator));
                    clearRows13();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_4BY3)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTable4By3(iterator));
                    clearRows();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_3BY3)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTbTable2(iterator));
                    clearRows7();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_2BY14)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTbTable3(iterator));
                    clearRows7();
                }
            } else if (StringUtils.equals(reportData.getDefinition().getDescription(),
                    PnlsReportConstants.REPORT_DESCRIPTION_BY15)) {
                List<DataSet> data = new LinkedList<DataSet>();
                data.addAll(reportData.getDataSets().values());
                Iterator<DataSet> iterator = data.iterator();
                while (iterator.hasNext()) {
                    this.tables.with(buildOneTbTable5(iterator));
                    clearRows7();
                }
            }
        }
        return this.tables;
    }

    private ContainerTag buildOneTable14By3(Iterator<DataSet> iterator) {
        buildGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator14By3(iterator.next());
        }
        return table().with(getRows5());
    }

    private ContainerTag buildOneTable17By3(Iterator<DataSet> iterator) {
        buildGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator17By3(iterator.next());
        }
        return table().with(getRows5());
    }

    private ContainerTag buildOneTable4By3(Iterator<DataSet> iterator) {
        buildGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator4By3(iterator.next());
        }
        return table().with(getRows5());
    }

    //////////////////////////////////////////////////////////////////////

    private ContainerTag buildOneTable1By1(Iterator<DataSet> iterator) {
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator1By1(iterator.next());
        }
        return table().with(getRows2());
    }

    private ContainerTag buildOneTable14By17(Iterator<DataSet> iterator) {
        buildNotEnrolledReasonSummaryTable();
        buildNotEnrolledGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator14By17(iterator.next());
        }
        return table().with(getRows17());
    }

    private ContainerTag buildOneTableKeyPopn(Iterator<DataSet> iterator) {
        buildPopulationSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyPopn(iterator.next());
        }
        return table().with(getRows4());
    }

    private ContainerTag buildOneTableScreeneTb(Iterator<DataSet> iterator) {
        buildPopulationSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyScreeneTb(iterator.next());
        }
        return table().with(getRows4());
    }

    private ContainerTag buildOneTableKeyPopnBySingleRow(Iterator<DataSet> iterator) {
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorKeyPopnSingleRow(iterator.next());
        }
        return table().with(getRows3());
    }

    private ContainerTag buildOneTableScpecimenBySingleRow(Iterator<DataSet> iterator) {
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorSpecimenSingleRow(iterator.next());
        }
        return table().with(getRows3());
    }

    private ContainerTag buildOneTableKeyScreenBySingleRow(Iterator<DataSet> iterator) {
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorScreeneSingleRow(iterator.next());
        }
        return table().with(getRows3());
    }

    private ContainerTag buildOneCtxTable(Iterator<DataSet> iterator) {
        buildCtxSummaryTable();
        buildCtxGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildCtxIndicator(iterator.next());
        }
        return table().with(getRows8());
    }

    private ContainerTag buildOneScreeneTable(Iterator<DataSet> iterator) {
        buildScreeneSummaryTable();
        buildCtxGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildCtxIndicator(iterator.next());
        }
        return table().with(getRows8());
    }

    private ContainerTag buildOneTableForActivePatientsByRegimenLines(Iterator<DataSet> iterator) {
        buildRegimeLinesSummaryTable();
        buildRegimensLineGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildRegimensLineIndicator(iterator.next());
        }
        return table().with(getRows11());
    }

    private ContainerTag buildOneTableForActivePatientsByScreeneTbLines(Iterator<DataSet> iterator) {
        buildScreeneTbLinesSummaryTable();
        buildScreenesTbLineGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildRegimensLineIndicator(iterator.next());
        }
        return table().with(getRows11());
    }

    private ContainerTag buildOneTbTable(Iterator<DataSet> iterator) {
        buildTbSummaryTable();
        buildTbGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildTbIndicator(iterator.next());
        }
        return table().with(getRows7());
    }

    private ContainerTag buildOneTbTable2(Iterator<DataSet> iterator) {
        buildTbSummaryTable();
        buildTbGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildTbIndicator2(iterator.next());
        }
        return table().attr("class", "table table-striped table-hover").with(getRows7());
    }

    private ContainerTag buildOneTbTable3(Iterator<DataSet> iterator) {
        buildTbSummaryTable();
        buildTbGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildTbIndicator3(iterator.next());
        }
        return table().attr("class", "table table-striped table-hover").with(getRows7());
    }

    private ContainerTag buildOneTbTable5(Iterator<DataSet> iterator) {
        buildTbSummaryTable();
        buildTbGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildTbIndicator5(iterator.next());
        }
        return table().attr("class", "table table-striped table-hover").with(getRows7());
    }

    private ContainerTag buildOneTableForActivePatientsOverMonths(Iterator<DataSet> iterator) {
        buildPatientSummaryOverMonthsTable();
        buildTbGenderSummaryTableOverMonhs();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorFotArvPatientsOverMonths(iterator.next());
        }
        return table().with(getRows9());
    }

    private ContainerTag buildOneTableForActivePatientsWithAtleastOneFollowUpVist(Iterator<DataSet> iterator) {
        buildPatientSummaryTableForFollowUpVist();
        buildTbGenderSummaryTableOverMonhs();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorFotArvPatientsOverMonths(iterator.next());
        }
        return table().with(getRows9());
    }

    private ContainerTag buildOne14By4Table(Iterator<DataSet> iterator) {
        buildTbSummaryTable();
        buildTbGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator14By4(iterator.next());
        }
        return table().with(getRows7());
    }

    private ContainerTag buildOneTbDiagnosisTestTable(Iterator<DataSet> iterator) {
        buildTbDiagnosisTestSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildTbDagnosisTestIndicator(iterator.next());
        }
        return table().with(getRows2());
    }

    private ContainerTag buildOneTableforCervicalCancerStatus(Iterator<DataSet> iterator) {
        buildCancerSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorCervicalCancerStatus(iterator.next());
        }
        return table().with(getRows6());
    }

    private ContainerTag buildOneTableforCervicalCancerTreatment(Iterator<DataSet> iterator) {
        buildCancerTreatmentSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorCervicalCancerTreatment(iterator.next());
        }
        return table().with(getRows6());
    }

    private ContainerTag buildOneTableFamillyPlanning(Iterator<DataSet> iterator) {
        buildFpMethodSummaryTable();
        buildFpGenderSummaryTable();
        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicatorFamilyPlanning(iterator.next());
        }
        return table().with(getRows13());
    }

    private void buildGenderSummaryTable() {
        fillEmptyRow(getRows5()[0], 1);
        fillEmptyRow(getRows5()[1], 1);
        ContainerTag maleLabel = getRows5()[2];
        ContainerTag femaleLable = getRows5()[3];
        ContainerTag Subtotal = getRows5()[4];
        maleLabel.with(th(translateLabel("Males")).withClass("label"));
        femaleLable.with(th(translateLabel("FeMales")).withClass("label"));
        Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));
    }

    private void buildCancerSummaryTable() {
        fillEmptyRow(getRows6()[0], 1);
        fillEmptyRow(getRows6()[1], 1);
        ContainerTag negative = getRows6()[2];
        ContainerTag postive = getRows6()[3];
        ContainerTag suspected = getRows6()[4];
        ContainerTag Subtotal = getRows6()[5];
        negative.with(th(translateLabel("negative")).withClass("label"));
        postive.with(th(translateLabel("postive")).withClass("label"));
        suspected.with(th(translateLabel("suspected")).withClass("label"));
        Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));
    }

    private void buildCancerTreatmentSummaryTable() {
        fillEmptyRow(getRows6()[0], 1);
        fillEmptyRow(getRows6()[1], 1);
        ContainerTag negative = getRows6()[2];
        ContainerTag postive = getRows6()[3];
        ContainerTag suspected = getRows6()[4];
        ContainerTag Subtotal = getRows6()[5];
        negative.with(th(translateLabel("cryo")).withClass("label"));
        postive.with(th(translateLabel("thermo")).withClass("label"));
        suspected.with(th(translateLabel("leep")).withClass("label"));
        Subtotal.with(th(translateLabel("Subtotal")).withClass("label"));
    }

    private void buildPopulationSummaryTable() {
        fillEmptyRow(getRows4()[0], 1);
        fillEmptyRow(getRows4()[1], 1);
        ContainerTag newEnroll = getRows4()[2];
        ContainerTag reffrenced = getRows4()[3];
        newEnroll.with(th(translateLabel("NewPatients")).withClass("label"));
        reffrenced.with(th(translateLabel("Referenced")).withClass("label"));
    }

    private void buildCtxSummaryTable() {
        fillEmptyRow(getRows8()[0], 1);
        fillEmptyRow(getRows8()[1], 1);
        ContainerTag newCtx = getRows8()[2];
        ContainerTag newTotal = getRows8()[4];
        ContainerTag activeCtx = getRows8()[5];
        ContainerTag activeTotal = getRows8()[7];
        newCtx.with(th(translateLabel("newCtx")).attr("rowspan", "2").withClass("label"));
        activeCtx.with(th(translateLabel("activeCtx")).attr("rowspan", "2").withClass("label"));
        newTotal.with(th(translateLabel("Total")).withClass("label"));
        activeTotal.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildScreeneSummaryTable() {
        fillEmptyRow(getRows8()[0], 1);
        fillEmptyRow(getRows8()[1], 1);
        ContainerTag newCtx = getRows8()[2];
        ContainerTag newTotal = getRows8()[4];
        ContainerTag activeCtx = getRows8()[5];
        ContainerTag activeTotal = getRows8()[7];
        newCtx.with(th(translateLabel("newTb")).attr("rowspan", "2").withClass("label"));
        activeCtx.with(th(translateLabel("alreadyTb")).attr("rowspan", "2").withClass("label"));
        newTotal.with(th(translateLabel("Total")).withClass("label"));
        activeTotal.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildRegimeLinesSummaryTable() {
        fillEmptyRow(getRows11()[0], 1);
        fillEmptyRow(getRows11()[1], 1);
        ContainerTag regime1 = getRows11()[2];
        ContainerTag regime1Total = getRows11()[4];
        ContainerTag regime2 = getRows11()[5];
        ContainerTag regime2Total = getRows11()[7];
        ContainerTag regime3 = getRows11()[8];
        ContainerTag regime3Total = getRows11()[10];
        regime1.with(th(translateLabel("regime1")).attr("rowspan", "2").withClass("label"));
        regime2.with(th(translateLabel("regime2")).attr("rowspan", "2").withClass("label"));
        regime3.with(th(translateLabel("regime3")).attr("rowspan", "2").withClass("label"));
        regime1Total.with(th(translateLabel("Total")).withClass("label"));
        regime2Total.with(th(translateLabel("Total")).withClass("label"));
        regime3Total.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildScreeneTbLinesSummaryTable() {
        fillEmptyRow(getRows11()[0], 1);
        fillEmptyRow(getRows11()[1], 1);
        ContainerTag regime1 = getRows11()[2];
        ContainerTag regime1Total = getRows11()[4];
        ContainerTag regime2 = getRows11()[5];
        ContainerTag regime2Total = getRows11()[7];
        ContainerTag regime3 = getRows11()[8];
        ContainerTag regime3Total = getRows11()[10];
        regime1.with(th(translateLabel("symptomes")).attr("rowspan", "2").withClass("label"));
        regime2.with(th(translateLabel("radiographieThorax")).attr("rowspan", "2").withClass("label"));
        regime3.with(th(translateLabel("pcrMoleculaire")).attr("rowspan", "2").withClass("label"));
        regime1Total.with(th(translateLabel("Total")).withClass("label"));
        regime2Total.with(th(translateLabel("Total")).withClass("label"));
        regime3Total.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildTbSummaryTable() {
        fillEmptyRow(getRows7()[0], 1);
        fillEmptyRow(getRows7()[1], 1);
        ContainerTag newlyTb = getRows7()[2];
        ContainerTag AlreadyTb = getRows7()[4];
        ContainerTag Total = getRows7()[6];
        newlyTb.with(th(translateLabel("newTb")).attr("rowspan", "2").withClass("label"));
        AlreadyTb.with(th(translateLabel("alreadyTb")).attr("rowspan", "2").withClass("label"));
        Total.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildPatientSummaryOverMonthsTable() {
        fillEmptyRow(getRows9()[0], 1);
        fillEmptyRow(getRows9()[1], 1);
        ContainerTag less3Months = getRows9()[2];
        ContainerTag btn3_5Months = getRows9()[4];
        ContainerTag over5Months = getRows9()[6];
        ContainerTag Total = getRows9()[8];
        less3Months.with(th(translateLabel("less3M")).attr("rowspan", "2").withClass("label"));
        btn3_5Months.with(th(translateLabel("btn3_5M")).attr("rowspan", "2").withClass("label"));
        over5Months.with(th(translateLabel("over5M")).attr("rowspan", "2").withClass("label"));
        Total.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildPatientSummaryTableForFollowUpVist() {
        fillEmptyRow(getRows9()[0], 1);
        fillEmptyRow(getRows9()[1], 1);
        ContainerTag qauterly = getRows9()[2];
        ContainerTag semiAnnually = getRows9()[4];
        ContainerTag annually = getRows9()[6];
        ContainerTag Total = getRows9()[8];
        qauterly.with(th(translateLabel("quaterly")).attr("rowspan", "2").withClass("label"));
        semiAnnually.with(th(translateLabel("semiAnualy")).attr("rowspan", "2").withClass("label"));
        annually.with(th(translateLabel("annualy")).attr("rowspan", "2").withClass("label"));
        Total.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildTbDiagnosisTestSummaryTable() {
        fillEmptyRow(getRows2()[0], 1);
        ContainerTag diagnosisTestLable = getRows2()[1];
        diagnosisTestLable
                .with(th(translateString(PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_MESSAGE))
                        .withClass("label"));
    }

    private void buildNotEnrolledReasonSummaryTable() {
        fillEmptyRow(getRows17()[0], 1);
        fillEmptyRow(getRows17()[1], 1);
        ContainerTag A = getRows17()[2];
        ContainerTag B = getRows17()[4];
        ContainerTag C = getRows17()[6];
        ContainerTag D = getRows17()[8];
        ContainerTag E = getRows17()[10];
        ContainerTag F = getRows17()[12];
        ContainerTag Subtotal = getRows17()[14];
        A.with(th(translateLabel("died")).attr("rowspan", "2").withClass("label"));
        B.with(th(translateLabel("voluntary")).attr("rowspan", "2").withClass("label"));
        C.with(th(translateLabel("denial")).attr("rowspan", "2").withClass("label"));
        D.with(th(translateLabel("medical")).attr("rowspan", "2").withClass("label"));
        E.with(th(translateLabel("refered")).attr("rowspan", "2").withClass("label"));
        F.with(th(translateLabel("other")).attr("rowspan", "2").withClass("label"));
        Subtotal.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildFpMethodSummaryTable() {
        fillEmptyRow(getRows13()[0], 1);
        fillEmptyRow(getRows13()[1], 1);
        ContainerTag pills = getRows13()[2];
        ContainerTag inject = getRows13()[3];
        ContainerTag impl = getRows13()[4];
        ContainerTag vagTabs = getRows13()[5];
        ContainerTag lam = getRows13()[6];
        ContainerTag necklace = getRows13()[7];
        ContainerTag condom = getRows13()[8];
        ContainerTag ccv = getRows13()[10];
        ContainerTag Subtotal = getRows13()[12];
        pills.with(th(translateLabel("pills")).attr("rowspan", "1").withClass("label"));
        inject.with(th(translateLabel("inject")).attr("rowspan", "1").withClass("label"));
        impl.with(th(translateLabel("impl")).attr("rowspan", "1").withClass("label"));
        vagTabs.with(th(translateLabel("vagTabs")).attr("rowspan", "1").withClass("label"));
        lam.with(th(translateLabel("lam")).attr("rowspan", "1").withClass("label"));
        necklace.with(th(translateLabel("necklace")).attr("rowspan", "1").withClass("label"));
        condom.with(th(translateLabel("condom")).attr("rowspan", "2").withClass("label"));
        ccv.with(th(translateLabel("ccv")).attr("rowspan", "2").withClass("label"));
        Subtotal.with(th(translateLabel("Total")).withClass("label"));
    }

    private void buildNotEnrolledGenderSummaryTable() {
        fillEmptyRow(getRows17()[0], 1);
        fillEmptyRow(getRows17()[1], 1);
        ContainerTag MA = getRows17()[2];
        ContainerTag FA = getRows17()[3];
        ContainerTag MB = getRows17()[4];
        ContainerTag FB = getRows17()[5];
        ContainerTag MC = getRows17()[6];
        ContainerTag FC = getRows17()[7];
        ContainerTag MD = getRows17()[8];
        ContainerTag FD = getRows17()[9];
        ContainerTag ME = getRows17()[10];
        ContainerTag FE = getRows17()[11];
        ContainerTag MF = getRows17()[12];
        ContainerTag FF = getRows17()[13];
        ContainerTag T = getRows17()[14];
        MA.with(th("M").withClass("label"));
        FA.with(th("F").withClass("label"));
        MB.with(th("M").withClass("label"));
        FB.with(th("F").withClass("label"));
        MC.with(th("M").withClass("label"));
        FC.with(th("F").withClass("label"));
        MD.with(th("M").withClass("label"));
        FD.with(th("F").withClass("label"));
        ME.with(th("M").withClass("label"));
        FE.with(th("F").withClass("label"));
        MF.with(th("M").withClass("label"));
        FF.with(th("F").withClass("label"));
        T.with(th(".").withClass("label"));
    }

    private void buildFpGenderSummaryTable() {
        fillEmptyRow(getRows13()[0], 1);
        fillEmptyRow(getRows13()[1], 1);
        ContainerTag FA = getRows13()[2];
        ContainerTag MB = getRows13()[8];
        ContainerTag FB = getRows13()[9];
        ContainerTag MC = getRows13()[10];
        ContainerTag FC = getRows13()[11];
        ContainerTag T = getRows13()[12];
        FA.with(th(translateLabel("FeMales")).attr("rowspan", "6").withClass("label"));
        MB.with(th(translateLabel("Males")).withClass("label"));
        FB.with(th(translateLabel("FeMales")).withClass("label"));
        MC.with(th(translateLabel("Males")).withClass("label"));
        FC.with(th(translateLabel("FeMales")).withClass("label"));
        T.with(th(".").withClass("label"));
    }

    private void buildCtxGenderSummaryTable() {
        fillEmptyRow(getRows8()[0], 1);
        fillEmptyRow(getRows8()[1], 1);
        ContainerTag MN = getRows8()[2];
        ContainerTag FN = getRows8()[3];
        ContainerTag E1 = getRows8()[4];
        ContainerTag MA = getRows8()[5];
        ContainerTag FA = getRows8()[6];
        ContainerTag E2 = getRows8()[7];
        MN.with(th("M").withClass("label"));
        FN.with(th("F").withClass("label"));
        MA.with(th("M").withClass("label"));
        FA.with(th("F").withClass("label"));
        E1.with(th(".").withClass("label"));
        E2.with(th(".").withClass("label"));
    }

    private void buildRegimensLineGenderSummaryTable() {
        fillEmptyRow(getRows11()[0], 1);
        fillEmptyRow(getRows11()[1], 1);
        ContainerTag M1 = getRows11()[2];
        ContainerTag F1 = getRows11()[3];
        ContainerTag E1 = getRows11()[4];
        ContainerTag M2 = getRows11()[5];
        ContainerTag F2 = getRows11()[6];
        ContainerTag E2 = getRows11()[7];
        ContainerTag M3 = getRows11()[8];
        ContainerTag F3 = getRows11()[9];
        ContainerTag E3 = getRows11()[10];
        M1.with(th("M").withClass("label"));
        F1.with(th("F").withClass("label"));
        M2.with(th("M").withClass("label"));
        F2.with(th("F").withClass("label"));
        M3.with(th("M").withClass("label"));
        F3.with(th("F").withClass("label"));
        E1.with(th(".").withClass("label"));
        E2.with(th(".").withClass("label"));
        E3.with(th(".").withClass("label"));
    }

    private void buildScreenesTbLineGenderSummaryTable() {
        fillEmptyRow(getRows11()[0], 1);
        fillEmptyRow(getRows11()[1], 1);
        ContainerTag M1 = getRows11()[2];
        ContainerTag F1 = getRows11()[3];
        ContainerTag E1 = getRows11()[4];
        ContainerTag M2 = getRows11()[5];
        ContainerTag F2 = getRows11()[6];
        ContainerTag E2 = getRows11()[7];
        ContainerTag M3 = getRows11()[8];
        ContainerTag F3 = getRows11()[9];
        ContainerTag E3 = getRows11()[10];
        M1.with(th("M").withClass("label"));
        F1.with(th("F").withClass("label"));
        M2.with(th("M").withClass("label"));
        F2.with(th("F").withClass("label"));
        M3.with(th("M").withClass("label"));
        F3.with(th("F").withClass("label"));
        E1.with(th(".").withClass("label"));
        E2.with(th(".").withClass("label"));
        E3.with(th(".").withClass("label"));
    }

    private void buildTbGenderSummaryTable() {
        fillEmptyRow(getRows7()[0], 1);
        fillEmptyRow(getRows7()[1], 1);
        ContainerTag MA = getRows7()[2];
        ContainerTag FA = getRows7()[3];
        ContainerTag MB = getRows7()[4];
        ContainerTag FB = getRows7()[5];
        ContainerTag E2 = getRows7()[6];
        MA.with(th("M").withClass("label"));
        FA.with(th("F").withClass("label"));
        MB.with(th("M").withClass("label"));
        FB.with(th("F").withClass("label"));
        E2.with(th(".").withClass("label"));
    }

    private void buildTbGenderSummaryTableOverMonhs() {
        fillEmptyRow(getRows9()[0], 1);
        fillEmptyRow(getRows9()[1], 1);
        ContainerTag MA = getRows9()[2];
        ContainerTag FA = getRows9()[3];
        ContainerTag MB = getRows9()[4];
        ContainerTag FB = getRows9()[5];
        ContainerTag MC = getRows9()[6];
        ContainerTag FC = getRows9()[7];
        ContainerTag E2 = getRows9()[8];
        MA.with(th("M").withClass("label"));
        FA.with(th("F").withClass("label"));
        MB.with(th("M").withClass("label"));
        FB.with(th("F").withClass("label"));
        MC.with(th("M").withClass("label"));
        FC.with(th("F").withClass("label"));
        E2.with(th(".").withClass("label"));
    }

    private void buildIndicator14By17(DataSet data) {
        getRows17()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows17()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList14By17(), data);
        String reportName = data.getDefinition().getName();
        buildSummary14By17(dataSet, getColumnNamesArray14By17(), reportName);
        final int ROW = 14;
        populateTable17WithSum(ROW, dataSet[0], dataSet[17], dataSet[34], dataSet[51], dataSet[68], dataSet[85], dataSet[102], dataSet[119], dataSet[136], dataSet[153], dataSet[170], dataSet[187]);
        populateTable17WithSum(ROW, dataSet[1], dataSet[18], dataSet[35], dataSet[52], dataSet[69], dataSet[86], dataSet[103], dataSet[120], dataSet[137], dataSet[154], dataSet[171], dataSet[188]);
        populateTable17WithSum(ROW, dataSet[2], dataSet[19], dataSet[36], dataSet[53], dataSet[70], dataSet[87], dataSet[104], dataSet[121], dataSet[138], dataSet[155], dataSet[172], dataSet[189]);
        populateTable17WithSum(ROW, dataSet[3], dataSet[20], dataSet[37], dataSet[54], dataSet[71], dataSet[88], dataSet[105], dataSet[122], dataSet[139], dataSet[156], dataSet[173], dataSet[190]);
        populateTable17WithSum(ROW, dataSet[4], dataSet[21], dataSet[38], dataSet[55], dataSet[72], dataSet[89], dataSet[106], dataSet[123], dataSet[140], dataSet[157], dataSet[174], dataSet[191]);
        populateTable17WithSum(ROW, dataSet[5], dataSet[22], dataSet[39], dataSet[56], dataSet[73], dataSet[90], dataSet[107], dataSet[124], dataSet[141], dataSet[158], dataSet[175], dataSet[192]);
        populateTable17WithSum(ROW, dataSet[6], dataSet[23], dataSet[40], dataSet[57], dataSet[74], dataSet[91], dataSet[108], dataSet[125], dataSet[142], dataSet[159], dataSet[176], dataSet[193]);
        populateTable17WithSum(ROW, dataSet[7], dataSet[24], dataSet[41], dataSet[58], dataSet[75], dataSet[92], dataSet[109], dataSet[126], dataSet[143], dataSet[160], dataSet[177], dataSet[194]);
        populateTable17WithSum(ROW, dataSet[8], dataSet[25], dataSet[42], dataSet[59], dataSet[76], dataSet[93], dataSet[110], dataSet[127], dataSet[144], dataSet[161], dataSet[178], dataSet[195]);
        populateTable17WithSum(ROW, dataSet[9], dataSet[26], dataSet[43], dataSet[60], dataSet[77], dataSet[94], dataSet[111], dataSet[128], dataSet[145], dataSet[162], dataSet[179], dataSet[196]);
        populateTable17WithSum(ROW, dataSet[10], dataSet[27], dataSet[44], dataSet[61], dataSet[78], dataSet[95], dataSet[112], dataSet[129], dataSet[146], dataSet[163], dataSet[180], dataSet[197]);
        populateTable17WithSum(ROW, dataSet[11], dataSet[28], dataSet[45], dataSet[62], dataSet[79], dataSet[96], dataSet[113], dataSet[130], dataSet[147], dataSet[164], dataSet[181], dataSet[198]);
        populateTable17WithSum(ROW, dataSet[12], dataSet[29], dataSet[46], dataSet[63], dataSet[80], dataSet[97], dataSet[114], dataSet[131], dataSet[148], dataSet[165], dataSet[182], dataSet[199]);
        populateTable17WithSum(ROW, dataSet[13], dataSet[30], dataSet[47], dataSet[64], dataSet[81], dataSet[98], dataSet[115], dataSet[132], dataSet[149], dataSet[166], dataSet[183], dataSet[200]);
        populateTable17WithSum(ROW, dataSet[14], dataSet[31], dataSet[48], dataSet[65], dataSet[82], dataSet[99], dataSet[116], dataSet[133], dataSet[150], dataSet[167], dataSet[184], dataSet[201]);
        populateTable17WithSum(ROW, dataSet[15], dataSet[32], dataSet[49], dataSet[66], dataSet[83], dataSet[100], dataSet[117], dataSet[134], dataSet[151], dataSet[168], dataSet[185], dataSet[202]);
        populateTable17WithSum(ROW, dataSet[16], dataSet[33], dataSet[50], dataSet[67], dataSet[84], dataSet[101], dataSet[118], dataSet[135], dataSet[152], dataSet[169], dataSet[186], dataSet[203]);
    }

    private void buildIndicator14By3(DataSet data) {
        getRows5()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows5()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList17By3(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary17By3(dataSet, getColumnNamesArray17By3(), reportName);
    }

    private void buildIndicator17By3(DataSet data) {
        getRows5()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows5()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList17By3(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary17By3(dataSet, getColumnNamesArray17By3(), reportName);
    }

    private void buildIndicatorCervicalCancerStatus(DataSet data) {
        getRows6()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "13").withClass("indicatorLabel"));
        getRows6()[1].with(td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getwomenCancerStatusColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary13By4(dataSet, getwomenCancerStatusColumnNamesArray(), reportName);
    }

    private void buildIndicatorCervicalCancerTreatment(DataSet data) {
        getRows6()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "13").withClass("indicatorLabel"));
        getRows6()[1].with(td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getwomenCancerTreatmentColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorTreatmentSummary(dataSet, getwomenCancerTreatmentColumnNamesArray(), reportName);
        final int ROW = 5;
        populateTable6WithSum(ROW, dataSet[0], dataSet[13], dataSet[26]);
        populateTable6WithSum(ROW, dataSet[1], dataSet[14], dataSet[27]);
        populateTable6WithSum(ROW, dataSet[2], dataSet[15], dataSet[28]);
        populateTable6WithSum(ROW, dataSet[3], dataSet[16], dataSet[29]);
        populateTable6WithSum(ROW, dataSet[4], dataSet[17], dataSet[30]);
        populateTable6WithSum(ROW, dataSet[5], dataSet[18], dataSet[31]);
        populateTable6WithSum(ROW, dataSet[6], dataSet[19], dataSet[32]);
        populateTable6WithSum(ROW, dataSet[7], dataSet[20], dataSet[33]);
        populateTable6WithSum(ROW, dataSet[8], dataSet[21], dataSet[34]);
        populateTable6WithSum(ROW, dataSet[9], dataSet[22], dataSet[35]);
        populateTable6WithSum(ROW, dataSet[10], dataSet[23], dataSet[36]);
        populateTable6WithSum(ROW, dataSet[11], dataSet[24], dataSet[37]);
        populateTable6WithSum(ROW, dataSet[12], dataSet[25], dataSet[38]);
    }

    private void buildIndicatorFamilyPlanning(DataSet data) {
        getRows13()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "13").withClass("indicatorLabel"));
        getRows13()[1].with(td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getFamilyPlanningColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorFamillyPlanningSummary(dataSet, getFamilyPlanningColumnNamesArray(), reportName);
        final int ROW = 12;
        populateTable13WithSum(ROW, dataSet[0], dataSet[13], dataSet[26], dataSet[39], dataSet[52], dataSet[65], dataSet[78], dataSet[91], dataSet[104], dataSet[117]);
        populateTable13WithSum(ROW, dataSet[1], dataSet[14], dataSet[27], dataSet[40], dataSet[53], dataSet[66], dataSet[79], dataSet[92], dataSet[105], dataSet[118]);
        populateTable13WithSum(ROW, dataSet[2], dataSet[15], dataSet[28], dataSet[41], dataSet[54], dataSet[67], dataSet[80], dataSet[93], dataSet[106], dataSet[119]);
        populateTable13WithSum(ROW, dataSet[3], dataSet[16], dataSet[29], dataSet[42], dataSet[55], dataSet[68], dataSet[81], dataSet[94], dataSet[107], dataSet[120]);
        populateTable13WithSum(ROW, dataSet[4], dataSet[17], dataSet[30], dataSet[43], dataSet[56], dataSet[69], dataSet[82], dataSet[95], dataSet[108], dataSet[121]);
        populateTable13WithSum(ROW, dataSet[5], dataSet[18], dataSet[31], dataSet[44], dataSet[57], dataSet[70], dataSet[83], dataSet[96], dataSet[109], dataSet[122]);
        populateTable13WithSum(ROW, dataSet[6], dataSet[19], dataSet[32], dataSet[45], dataSet[58], dataSet[71], dataSet[84], dataSet[97], dataSet[110], dataSet[123]);
        populateTable13WithSum(ROW, dataSet[7], dataSet[20], dataSet[33], dataSet[46], dataSet[59], dataSet[72], dataSet[85], dataSet[98], dataSet[111], dataSet[124]);
        populateTable13WithSum(ROW, dataSet[8], dataSet[21], dataSet[34], dataSet[47], dataSet[60], dataSet[73], dataSet[86], dataSet[99], dataSet[112], dataSet[125]);
        populateTable13WithSum(ROW, dataSet[9], dataSet[22], dataSet[35], dataSet[48], dataSet[61], dataSet[74], dataSet[87], dataSet[100], dataSet[113], dataSet[126]);
        populateTable13WithSum(ROW, dataSet[10], dataSet[23], dataSet[36], dataSet[49], dataSet[62], dataSet[75], dataSet[88], dataSet[101], dataSet[114], dataSet[127]);
        populateTable13WithSum(ROW, dataSet[11], dataSet[24], dataSet[37], dataSet[50], dataSet[63], dataSet[76], dataSet[89], dataSet[102], dataSet[115], dataSet[128]);
        populateTable13WithSum(ROW, dataSet[12], dataSet[25], dataSet[38], dataSet[51], dataSet[64], dataSet[77], dataSet[90], dataSet[103], dataSet[116], dataSet[129]);
    }

    private void buildIndicator14By4(DataSet data) {
        getRows7()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows7()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList17By4(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary17By4(dataSet, getColumnNamesArray17By4(), reportName);
        final int ROW = 6;
        populateTable7WithSum(ROW, dataSet[0], dataSet[17], dataSet[34], dataSet[51]);
        populateTable7WithSum(ROW, dataSet[1], dataSet[18], dataSet[35], dataSet[52]);
        populateTable7WithSum(ROW, dataSet[2], dataSet[19], dataSet[36], dataSet[53]);
        populateTable7WithSum(ROW, dataSet[3], dataSet[20], dataSet[37], dataSet[54]);
        populateTable7WithSum(ROW, dataSet[4], dataSet[21], dataSet[38], dataSet[55]);
        populateTable7WithSum(ROW, dataSet[5], dataSet[22], dataSet[39], dataSet[56]);
        populateTable7WithSum(ROW, dataSet[6], dataSet[23], dataSet[40], dataSet[57]);
        populateTable7WithSum(ROW, dataSet[7], dataSet[24], dataSet[41], dataSet[58]);
        populateTable7WithSum(ROW, dataSet[8], dataSet[25], dataSet[42], dataSet[59]);
        populateTable7WithSum(ROW, dataSet[9], dataSet[26], dataSet[43], dataSet[60]);
        populateTable7WithSum(ROW, dataSet[10], dataSet[27], dataSet[44], dataSet[61]);
        populateTable7WithSum(ROW, dataSet[11], dataSet[28], dataSet[45], dataSet[62]);
        populateTable7WithSum(ROW, dataSet[12], dataSet[29], dataSet[46], dataSet[63]);
        populateTable7WithSum(ROW, dataSet[13], dataSet[30], dataSet[47], dataSet[64]);
        populateTable7WithSum(ROW, dataSet[14], dataSet[31], dataSet[48], dataSet[65]);
        populateTable7WithSum(ROW, dataSet[15], dataSet[32], dataSet[49], dataSet[66]);
        populateTable7WithSum(ROW, dataSet[16], dataSet[33], dataSet[50], dataSet[67]);
    }

    private void buildCtxIndicator(DataSet data) {
        getRows8()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows8()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getCtxColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary17By6(dataSet, getCtxColumnNamesArray(), reportName);
    }

    private void buildRegimensLineIndicator(DataSet data) {
        getRows11()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "17").withClass("indicatorLabel"));
        getRows11()[1].with(td(translateLabel("1-0")).attr("colspan", "1").withClass("label"),
                td(translateLabel("1-4")).attr("colspan", "1").withClass("label"),
                td(translateLabel("5-9")).attr("colspan", "1").withClass("label"),
                td(translateLabel("10-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("15-19")).attr("colspan", "1").withClass("label"),
                td(translateLabel("20-24")).attr("colspan", "1").withClass("label"),
                td(translateLabel("25-29")).attr("colspan", "1").withClass("label"),
                td(translateLabel("30-34")).attr("colspan", "1").withClass("label"),
                td(translateLabel("35-39")).attr("colspan", "1").withClass("label"),
                td(translateLabel("40-44")).attr("colspan", "1").withClass("label"),
                td(translateLabel("45-49")).attr("colspan", "1").withClass("label"),
                td(translateLabel("50-54")).attr("colspan", "1").withClass("label"),
                td(translateLabel("55-59")).attr("colspan", "1").withClass("label"),
                td(translateLabel("60-64")).attr("colspan", "1").withClass("label"),
                td(translateLabel("65")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getActivePatientRegimenLinesColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary17By9(dataSet, getActivePatientRegimenLinesColumnNamesArray(), reportName);
    }

    private void buildTbIndicator(DataSet data) {
        getRows7()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows7()[1].with(
                td(translateLabel("<15")).attr("colspan", "1").withClass("label"),
                td(translateLabel(">15")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getAgeBy15ColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary4By5(dataSet, getAgeBy15ColumnNamesArray(), reportName);
        final int ROW = 6;
        populateTable7WithSum(ROW, dataSet[0], dataSet[4], dataSet[8], dataSet[12]);
        populateTable7WithSum(ROW, dataSet[1], dataSet[5], dataSet[9], dataSet[13]);
        populateTable7WithSum(ROW, dataSet[2], dataSet[6], dataSet[10], dataSet[14]);
        populateTable7WithSum(ROW, dataSet[3], dataSet[7], dataSet[11], dataSet[15]);
    }

    private void buildTbIndicator2(DataSet data) {
        getRows7()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "2").withClass("indicatorLabel"));
        getRows7()[1].with(
                td(translateLabel("<2")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getAgeBy2ColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary3By3(dataSet, getAgeBy2ColumnNamesArray(), reportName);
        final int ROW = 6;
        populateTable7WithSum(ROW, dataSet[0], dataSet[1], dataSet[2], dataSet[3]);
    }

    private void buildTbIndicator3(DataSet data) {
        getRows7()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows7()[1].with(
                td(translateLabel("2-14")).attr("colspan", "1").withClass("label"),
                td(translateLabel(">14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getAgeBy14ColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary2By14(dataSet, getAgeBy14ColumnNamesArray(), reportName);
        final int ROW = 6;
        populateTable7WithSum(ROW, dataSet[0], dataSet[4], dataSet[8], dataSet[12]);
        populateTable7WithSum(ROW, dataSet[1], dataSet[5], dataSet[9], dataSet[13]);
        populateTable7WithSum(ROW, dataSet[2], dataSet[6], dataSet[10], dataSet[14]);
        populateTable7WithSum(ROW, dataSet[3], dataSet[7], dataSet[11], dataSet[15]);
    }

    private void buildTbIndicator5(DataSet data) {
        getRows7()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "3").withClass("indicatorLabel"));
        getRows7()[1].with(
                td(translateLabel(">14")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getAgeSupp15ColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummarySupp15(dataSet, getAgeSupp15ColumnNamesArray(), reportName);
        final int ROW = 6;
        populateTable7WithSum(ROW, dataSet[0], dataSet[3], dataSet[6], dataSet[9]);
        populateTable7WithSum(ROW, dataSet[1], dataSet[4], dataSet[7], dataSet[10]);
        populateTable7WithSum(ROW, dataSet[2], dataSet[5], dataSet[8], dataSet[11]);
    }

    private void buildIndicatorFotArvPatientsOverMonths(DataSet data) {
        getRows9()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows9()[1].with(td(translateLabel("<15")).attr("colspan", "1").withClass("label"),
                td(translateLabel(">15")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList4By7(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary4By7(dataSet, getColumnNamesArray4By7(), reportName);
        final int ROW = 8;
        populateTable9WithSum(ROW, dataSet[0], dataSet[4], dataSet[8], dataSet[12], dataSet[16], dataSet[20]);
        populateTable9WithSum(ROW, dataSet[1], dataSet[5], dataSet[9], dataSet[13], dataSet[17], dataSet[21]);
        populateTable9WithSum(ROW, dataSet[2], dataSet[6], dataSet[10], dataSet[14], dataSet[18], dataSet[22]);
        populateTable9WithSum(ROW, dataSet[3], dataSet[7], dataSet[11], dataSet[15], dataSet[19], dataSet[23]);
    }

    private void buildIndicator4By3(DataSet data) {
        getRows5()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows5()[1].with(td(translateLabel("<15")).attr("colspan", "1").withClass("label"),
                td(translateLabel(">15")).attr("colspan", "1").withClass("label"),
                td(translateLabel("UnKnown")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getColumnNamesList4By3(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummary4By3(dataSet, getColumnNamesArray4By3(), reportName);
    }


    private void buildTbDagnosisTestIndicator(DataSet data) {
        getRows2()[0].with(td(translateLabel("CrachatTest")).attr("colspan", "1").withClass("label"),
                td(translateLabel("geneExpertTest")).attr("colspan", "1").withClass("label"),
                td(translateLabel("OtherTest")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getArvPatientsByTbDiagnosisTestColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        final int ROW1 = 1;
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
        String row1 = ConstructUrl(reportUrl, reportName, getArvPatientsByTbDiagnosisTestColumnNamesArray()[0]);
        String row2 = ConstructUrl(reportUrl, reportName, getArvPatientsByTbDiagnosisTestColumnNamesArray()[1]);
        String row3 = ConstructUrl(reportUrl, reportName, getArvPatientsByTbDiagnosisTestColumnNamesArray()[2]);

        populateTable2(ROW1, dataSet[0], row1);
        populateTable2(ROW1, dataSet[1], row2);
        populateTable2(ROW1, dataSet[2], row3);
        getRows2()[ROW1].with(td(Integer.toString(dataSet[0] + dataSet[1] + dataSet[2])));
    }

    private void buildIndicatorKeyPopn(DataSet data) {
        getRows4()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "6").withClass("indicatorLabel"));
        getRows4()[1].with(td(translateLabel("msm")).attr("colspan", "1").withClass("label"),
                td(translateLabel("sexP")).attr("colspan", "1").withClass("label"),
                td(translateLabel("trans")).attr("colspan", "1").withClass("label"),
                td(translateLabel("captv")).attr("colspan", "1").withClass("label"),
                td(translateLabel("drug")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getKeyPopnColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummaryKeyPopn(dataSet, getKeyPopnColumnNamesArray(), reportName);

        /* Adding by Feshner */
        int d1 = Integer.parseInt(data.iterator().next().getColumnValue("MSMN").toString());
        int d2 = Integer.parseInt(data.iterator().next().getColumnValue("SPN").toString());
        int d3 = Integer.parseInt(data.iterator().next().getColumnValue("TSGN").toString());
        int d4 = Integer.parseInt(data.iterator().next().getColumnValue("CPN").toString());
        int d5 = Integer.parseInt(data.iterator().next().getColumnValue("DRUGN").toString());
        populateTable4WithSum(2, d1, d2, d3, d4, d5);

        int d6 = Integer.parseInt(data.iterator().next().getColumnValue("MSMR").toString());
        int d7 = Integer.parseInt(data.iterator().next().getColumnValue("SPR").toString());
        int d8 = Integer.parseInt(data.iterator().next().getColumnValue("TSGR").toString());
        int d9 = Integer.parseInt(data.iterator().next().getColumnValue("CPR").toString());
        int d10 = Integer.parseInt(data.iterator().next().getColumnValue("DRUGR").toString());
        populateTable4WithSum(3, d6, d7, d8, d9, d10);
    }

    private void buildIndicatorKeyScreeneTb(DataSet data) {
        getRows4()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows4()[1].with(td(translateLabel("symptomes")).attr("colspan", "1").withClass("label"),
                td(translateLabel("radiographieThorax")).attr("colspan", "1").withClass("label"),
                td(translateLabel("pcrMoleculaire")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));

        int[] dataSet = createSummaryArray(getScreeneTbColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildIndicatorSummaryScreeneTb(dataSet, getScreeneTbColumnNamesArray(), reportName);

        /* Adding by Jeejen */
        int d1 = Integer.parseInt(data.iterator().next().getColumnValue("MSMN").toString());
        int d2 = Integer.parseInt(data.iterator().next().getColumnValue("SPN").toString());
        int d3 = Integer.parseInt(data.iterator().next().getColumnValue("DRUGN").toString());
        populateTable3WithSum(2, d1, d2, d3);

        int d4 = Integer.parseInt(data.iterator().next().getColumnValue("MSMR").toString());
        int d5 = Integer.parseInt(data.iterator().next().getColumnValue("SPR").toString());
        int d6 = Integer.parseInt(data.iterator().next().getColumnValue("DRUGR").toString());
        populateTable3WithSum(3, d4, d5, d6);
    }

    private void buildIndicatorKeyPopnSingleRow(DataSet data) {
        getRows3()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "6").withClass("indicatorLabel"));
        getRows3()[1].with(td(translateLabel("msm")).attr("colspan", "1").withClass("label"),
                td(translateLabel("sexP")).attr("colspan", "1").withClass("label"),
                td(translateLabel("trans")).attr("colspan", "1").withClass("label"),
                td(translateLabel("captv")).attr("colspan", "1").withClass("label"),
                td(translateLabel("drug")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));
        int[] dataSet = createSummaryArray(getKeyPopnSingleRowColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildSummaryKeyPopnSingleRow(dataSet, getKeyPopnSingleRowColumnNamesArray(), reportName);
        populateTable(2, dataSet[0] + dataSet[1] + dataSet[2] + dataSet[3] + dataSet[4]);
    }

    private void buildIndicatorSpecimenSingleRow(DataSet data) {
        getRows3()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "8").withClass("indicatorLabel"));
        getRows3()[1].with(td(translateLabel("msm")).attr("colspan", "1").withClass("label"),
                td(translateLabel("sexP")).attr("colspan", "1").withClass("label"),
                td(translateLabel("trans")).attr("colspan", "1").withClass("label"),
                td(translateLabel("captv")).attr("colspan", "1").withClass("label"),
                td(translateLabel("drug")).attr("colspan", "1").withClass("label"),
                td(translateLabel("fmeenc")).attr("colspan", "1").withClass("label"),
                td(translateLabel("fmealt")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));
        int[] dataSet = createSummaryArray(getSpecimenSingleRowColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildSummarySpecimenSingleRow(dataSet, getSpecimenSingleRowColumnNamesArray(), reportName);
        populateTable(2, dataSet[0] + dataSet[1] + dataSet[2] + dataSet[3] + dataSet[4] + dataSet[5] + dataSet[6]);
    }

    private void buildIndicatorScreeneSingleRow(DataSet data) {
        getRows3()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "4").withClass("indicatorLabel"));
        getRows3()[1].with(td(translateLabel("symptomes")).attr("colspan", "1").withClass("label"),
                td(translateLabel("radiographieThorax")).attr("colspan", "1").withClass("label"),
                td(translateLabel("pcrMoleculaire")).attr("colspan", "1").withClass("label"),
                td(translateLabel("Total")).attr("colspan", "1").withClass("label"));
        int[] dataSet = createSummaryArray(getScreeneTbSingleRowColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        buildSummaryScreeneTbSingleRow(dataSet, getScreeneSingleRowColumnNamesArray(), reportName);
        populateTable(2, dataSet[0] + dataSet[1] + dataSet[2]);
    }

    private void buildIndicator1By1(DataSet data) {
        getRows2()[0]
                .with(th(translate(data.getDefinition().getName())).attr("colspan", "1").withClass("indicatorLabel"));

        int[] dataSet = createSummaryArray(getBreastFeedingColumnNamesList(), data);
        String reportName = data.getDefinition().getName();
        final int ROW1 = 1;
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        String row1 = ConstructUrl(reportUrl, reportName, getBreastFeedingColumnNamesArray()[0]);
        populateTable2(ROW1, dataSet[0], row1);
    }

    private void buildIndicatorSummary14By3(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 4; ROW++) {
            for (int col = 0; col <= 13; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary17By3(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 4; ROW++) {
            for (int col = 0; col <= 16; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary13By4(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
//        System.out.println("\n");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col <= 12; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable6(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorTreatmentSummary(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 4; ROW++) {
            for (int col = 0; col <= 12; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable6(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorFamillyPlanningSummary(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 11; ROW++) {
            for (int col = 0; col <= 12; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable13(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary17By4(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col <= 16; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable7(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary17By6(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 7; ROW++) {
            for (int col = 0; col <= 16; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable8(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary17By9(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 10; ROW++) {
            for (int col = 0; col <= 16; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable11(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary4By5(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col <= 3; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable7(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary3By3(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col < 1; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable7(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary2By14(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col <= 3; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable7(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummarySupp15(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 5; ROW++) {
            for (int col = 0; col <= 2; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable7(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary4By7(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 7; ROW++) {
            for (int col = 0; col <= 3; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable9(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummary4By3(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 4; ROW++) {
            for (int col = 0; col <= 3; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummaryKeyPopn(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 3; ROW++) {
            for (int col = 0; col <= 4; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable4(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildIndicatorSummaryScreeneTb(int[] dataArray, String[] columnsArray, String reportName) {

        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");

        int colCount = 0;
        for (int ROW = 2; ROW <= 3; ROW++) {
            for (int col = 0; col <= 2; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable4(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildSummary14By17(int[] dataArray, String[] columnsArray, String reportName) {
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
        int colCount = 0;
        for (int ROW = 2; ROW <= 13; ROW++) {
            for (int col = 0; col <= 16; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable14By17(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }

    }

    private void buildSummaryKeyPopnSingleRow(int[] dataArray, String[] columnsArray, String reportName) {
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
        int colCount = 0;
        for (int ROW = 2; ROW <= 2; ROW++) {
            for (int col = 0; col <= 4; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable3(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildSummarySpecimenSingleRow(int[] dataArray, String[] columnsArray, String reportName) {
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
        int colCount = 0;
        for (int ROW = 2; ROW <= 2; ROW++) {
            for (int col = 0; col <= 6; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable3(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private void buildSummaryScreeneTbSingleRow(int[] dataArray, String[] columnsArray, String reportName) {
        String reportUrl = pageLink("isanteplusreports", "pnlsReportPatientList");
        int colCount = 0;
        for (int ROW = 2; ROW <= 2; ROW++) {
            for (int col = 0; col <= 2; col++) {
                if (colCount < dataArray.length) {
                    String row = ConstructUrl(reportUrl, reportName, columnsArray[colCount]);
                    populateTable3(ROW, dataArray[colCount], row);
                    colCount++;
                }
            }
        }
    }

    private String ConstructUrl(String reportBaseUrl, String reportName, String columnName) {
        return String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportBaseUrl,
                reportName, columnName);
    }

    private void populateTable14By17(Integer ROW, int data, String rowLink) {
        getRows17()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable(Integer ROW, int data, String rowLink) {
        getRows5()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable6(Integer ROW, int data, String rowLink) {
        getRows6()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable8(Integer ROW, int data, String rowLink) {
        getRows8()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable11(Integer ROW, int data, String rowLink) {
        getRows11()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable7(Integer ROW, int data, String rowLink) {
        getRows7()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable9(Integer ROW, int data, String rowLink) {
        getRows9()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable2(Integer ROW, int data, String rowLink) {
        getRows2()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable3(Integer ROW, int data, String rowLink) {
        getRows3()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable4(Integer ROW, int data, String rowLink) {
        getRows4()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable13(Integer ROW, int data, String rowLink) {
        getRows13()[ROW].with(td(a(Integer.toString(data)).withHref(rowLink).attr("onclick",
                "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private void populateTable7WithSum(Integer ROW, int data1, int data2, int data3, int data4) {
        getRows7()[ROW].with(td(Integer.toString(data1 + data2 + data3 + data4)));
    }

    private void populateTable9WithSum(Integer ROW, int data1, int data2, int data3, int data4, int data5, int data6) {
        getRows9()[ROW].with(td(Integer.toString(data1 + data2 + data3 + data4 + data5 + data6)));
    }

    private void populateTable6WithSum(Integer ROW, int data1, int data2, int data3) {
        getRows6()[ROW].with(td(Integer.toString(data1 + data2 + data3)));
    }

    private void populateTable13WithSum(Integer ROW, int data1, int data2, int data3, int data4, int data5, int data6,
                                        int data7, int data8, int data9, int data10) {
        getRows13()[ROW].with(
                td(Integer.toString(data1 + data2 + data3 + data4 + data5 + data6 + data7 + data8 + data9 + data10)));
    }

    private void populateTable17WithSum(Integer ROW, int data1, int data2, int data3, int data4, int data5, int data6,
                                        int data7, int data8, int data9, int data10, int data11, int data12) {
        getRows17()[ROW].with(td(Integer.toString(
                data1 + data2 + data3 + data4 + data5 + data6 + data7 + data8 + data9 + data10 + data11 + data12)));
    }

    /*Adding by Feshner*/
    private void populateTable4WithSum(Integer ROW, int data1, int data2, int data3, int data4, int data5) {
        getRows4()[ROW].with(td(Integer.toString(data1 + data2 + data3 + data4 + data5)));

        //getRows4()[ROW].withValue(td(Integer.toString(data1 + data2 + data3 + data4 + data5)));
        //getRows4()[ROW].withValue(Integer.toString(data1 + data2 + data3 + data4 + data5));

    }

    private void populateTable3WithSum(Integer ROW, int data1, int data2, int data3) {
        getRows4()[ROW].with(td(Integer.toString(data1 + data2 + data3)));

        //getRows4()[ROW].withValue(td(Integer.toString(data1 + data2 + data3 + data4 + data5)));
        //getRows4()[ROW].withValue(Integer.toString(data1 + data2 + data3 + data4 + data5));

    }


    /*End adding by Feshner*/
    private void populateTable(Integer ROW, int data) {
        getRows3()[ROW].with(td(Integer.toString(data)));
    }

    private void populateTable1(Integer ROW, int data) {
        getRows3()[ROW].with(td(Integer.toString(data)));
    }

    private int[] createSummaryArray(List<String> columNames, DataSet dataSet) {
        List<Integer> columnValues = new ArrayList<Integer>();
        for (String column : columNames) {
            columnValues.add(getDataSetIntegerValue(dataSet, column));
        }
        return columnValues.stream().mapToInt(Integer::intValue).toArray();
    }

    // filling by empty <td>
    private void fillEmptyRow(ContainerTag row, Integer length) {
        row.with(td().attr("colspan", length.toString()));
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

    public ContainerTag[] getRows5() {
        if (rows5 == null) {
            rows5 = new ContainerTag[ROWS_5];
            for (int i = 0; i < ROWS_5; ++i) {
                rows5[i] = tr();
            }
        }
        return rows5;
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

    public ContainerTag[] getRows17() {
        if (rows17 == null) {
            rows17 = new ContainerTag[ROWS_18];
            for (int i = 0; i < ROWS_18; ++i) {
                rows17[i] = tr();
            }
        }
        return rows17;
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

    public ContainerTag[] getRows3() {
        if (rows3 == null) {
            rows3 = new ContainerTag[ROWS_3];
            for (int i = 0; i < ROWS_3; ++i) {
                rows3[i] = tr();
            }
        }
        return rows3;
    }


    public ContainerTag[] getRows8() {
        if (rows8 == null) {
            rows8 = new ContainerTag[ROWS_8];
            for (int i = 0; i < ROWS_8; ++i) {
                rows8[i] = tr();
            }
        }
        return rows8;
    }

    public ContainerTag[] getRows7() {
        if (rows7 == null) {
            rows7 = new ContainerTag[ROWS_7];
            for (int i = 0; i < ROWS_7; ++i) {
                rows7[i] = tr();
            }
        }
        return rows7;
    }

    public ContainerTag[] getRows9() {
        if (rows9 == null) {
            rows9 = new ContainerTag[ROWS_9];
            for (int i = 0; i < ROWS_9; ++i) {
                rows9[i] = tr();
            }
        }
        return rows9;
    }

    public ContainerTag[] getRows11() {
        if (rows11 == null) {
            rows11 = new ContainerTag[ROWS_11];
            for (int i = 0; i < ROWS_11; ++i) {
                rows11[i] = tr();
            }
        }
        return rows11;
    }

    public ContainerTag[] getRows6() {
        if (rows6 == null) {
            rows6 = new ContainerTag[ROWS_6];
            for (int i = 0; i < ROWS_6; ++i) {
                rows6[i] = tr();
            }
        }
        return rows6;
    }

    public ContainerTag[] getRows13() {
        if (rows13 == null) {
            rows13 = new ContainerTag[ROWS_13];
            for (int i = 0; i < ROWS_13; ++i) {
                rows13[i] = tr();
            }
        }
        return rows13;
    }

    private void clearRows() {
        setRows(null);
    }

    private void clearRows7() {
        setRows7(null);
    }

    private void clearRows2() {
        setRows2(null);
    }

    private void clearRows3() {
        setRows3(null);
    }

    private void clearRows9() {
        setRows9(null);
    }

    private void clearRows11() {
        setRows11(null);
    }

    private void clearRows6() {
        setRows6(null);
    }

    private void clearRows13() {
        setRows13(null);
    }

    private void clearRows4() {
        setRows4(null);
    }

    private void clearRows14() {
        setRows14(null);
    }

    private void clearRows8() {
        setRows8(null);
    }

    public void setRows(ContainerTag[] rows) {
        this.rows5 = rows;
    }

    public void setRows2(ContainerTag[] rows) {
        this.rows2 = rows;
    }

    public void setRows3(ContainerTag[] rows) {
        this.rows3 = rows;
    }

    public void setRows7(ContainerTag[] rows) {
        this.rows7 = rows;
    }

    public void setRows9(ContainerTag[] rows) {
        this.rows9 = rows;
    }

    public void setRows11(ContainerTag[] rows) {
        this.rows11 = rows;
    }

    public void setRows6(ContainerTag[] rows) {
        this.rows6 = rows;
    }

    public void setRows13(ContainerTag[] rows) {
        this.rows13 = rows;
    }

    public void setRows4(ContainerTag[] rows) {
        this.rows4 = rows;
    }

    public void setRows14(ContainerTag[] rows) {
        this.rows14 = rows;
    }

    public void setRows8(ContainerTag[] rows) {
        this.rows8 = rows;
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

    private static String translateLabel(String labelName) {
        return translate("isanteplusreports.pnls.result." + labelName + ".label");
    }

    private static String translateString(String labelName) {
        return translate(labelName);
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
