package org.openmrs.module.isanteplusreports.alertprecoce.builder;

import com.itextpdf.text.DocumentException;
import j2html.tags.ContainerTag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.UiUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static j2html.TagCreator.*;
import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.getStringFromResource;
import static org.openmrs.module.isanteplusreports.alertprecoce.util.AlertPrecoceUtils.replaceNonBreakingSpaces;

public class AlertPrecoceReportBuilder extends UiUtils {

    private final Log LOGGER = LogFactory.getLog(getClass());

    private static final int ROWS = 4;

    private static final String MALE_NUMERATOR_COLUMN_NAME = "maleNumerator";

    private static final String FEMALE_NUMERATOR_COLUMN_NAME = "femaleNumerator";

    private static final String MALE_DENOMINATOR_COLUMN_NAME = "maleDenominator";

    private static final String FEMALE_DENOMINATOR_COLUMN_NAME = "femaleDenominator";

    private static final String TOTAL_NUMERATOR_COLUMN_NAME = "totalNumerator";

    private static final String TOTAL_DENOMINATOR_COLUMN_NAME = "totalDenominator";

    private static final String PERCENTAGE_STRING_FORMAT_PATTERN = "##0.0";

    private static final String PERIOD_DATE_FORMAT_PATTERN = "yyyy/MM/dd";

    private static final String CREATION_DATE_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

    private static final String STRING_IF_EMPTY = "-";

    private String reportLabel;

    private int numberOfIndicatorsInOneTable = 1; // if there are too many indicators the table will be splitted

    private ContainerTag[] rows;

    private List<DataSet> dataSets;

    private String clinicDepartment;

    private String clinic;

    private Date startDate;

    private Date endDate;

    private Long femalePatients;

    private Long malePatients;

    public String getReportLabel() {
        return this.reportLabel;
    }

    public void setReportLabel(String reportLabel) {
        this.reportLabel = reportLabel;
    }

    public String buildHtmlTables() {
        String tablesHtml = buildTables().render();
        LOGGER.debug("built tables html: " + tablesHtml);
        return tablesHtml;
    }

    public String buildPdf() {
        String htmlForPdf = html(head(getStyleForPdf()), body(createPdfHeader(), buildTables())).render();
        LOGGER.debug("built htmlForPdf: " + htmlForPdf);
        try {
            return convertHtmlToPdfInBase64(htmlForPdf);
        } catch (Exception ex) {
            throw new HealthQualException("PDF cannot be created", ex);
        }
    }

	/*private ContainerTag createPdfHeader() {
		return div().withClass("center").with(
			h2(translate("isanteplusreports.alertprecoce.report.label")),
			h3(createPeriodString()),
			h5(createDateOfCreationString())
		);
	}*/

    private ContainerTag createPdfHeader() {
        return div().withClass("center").with(
                h2(translate(reportLabel)),
                h3(createPeriodString()),
                h5(createDateOfCreationString())
        );
    }

    private String createPeriodString() {
        SimpleDateFormat df = new SimpleDateFormat(PERIOD_DATE_FORMAT_PATTERN);
        return df.format(startDate) + " - " + df.format(endDate);
    }

    private String createDateOfCreationString() {
        SimpleDateFormat df = new SimpleDateFormat(CREATION_DATE_FORMAT_PATTERN);
        return translate("isanteplusreports.healthqual.result.pdf.creationDate.label") + " " + df.format(new Date());
    }

    private ContainerTag getStyleForPdf() {
        return style().withType("text/css").withText(getStringFromResource("healthQualPdfStyle.css"));
    }

    private String convertHtmlToPdfInBase64(String html) throws IOException, ParserConfigurationException, SAXException,
            DocumentException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes("UTF-8")));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);

        renderer.layout();
        renderer.createPDF(out);
        out.flush();
        out.close();

        return DatatypeConverter.printBase64Binary(out.toByteArray());
    }

    private ContainerTag buildTables() {
        ContainerTag tables = div();
        Iterator<DataSet> iterator = getDataSets().iterator();
        while (iterator.hasNext()) {
            tables.with(buildOneTable(iterator));

            clearRows();
        }

        return tables;
    }

    private ContainerTag buildOneTable(Iterator<DataSet> iterator) {

        for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
            buildIndicator(iterator.next());
        }

        return table().with(getRows()).attr("width", "100%");
    }

    private void buildIndicator(DataSet data) {

        if ((translate(data.getDefinition().getName().toString()).equalsIgnoreCase(translate("isanteplusreports.artdistribution.appointmentperiod.message").toString()))
                || (translate(data.getDefinition().getName().toString()).equalsIgnoreCase(translate("isanteplusreports.artdistribution.arvdispense.message").toString()))
        ) {
            getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "3").withClass("indicatorLabel"));
            Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
                    getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));


            String[] numeratorColumns = createSummaryColumnArray(data, TOTAL_NUMERATOR_COLUMN_NAME);
            buildIndicatorSummary(numerator, numeratorColumns);

        } else {
            if (translate(data.getDefinition().getName().toString()).equalsIgnoreCase(translate("isanteplusreports.report.transitionedPatient").toString())) {
                getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "3").withClass("indicatorLabel"));
                getRows()[1].with(td(translate("isanteplusreports.report.transitionedPatient.active")).attr("colspan", "1").withClass("label"),
                        td(translate("isanteplusreports.report.transitionedPatient.inactive")).attr("colspan", "1").withClass("label"), td(translate("isanteplusreports.pnls.result.Subtotal.label"))
                                .attr("colspan", "1").withClass("label"));
                Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
                        getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));

                String[] numeratorColumns = createSummaryColumnArray(data, TOTAL_NUMERATOR_COLUMN_NAME);
                buildIndicatorSummary(numerator, numeratorColumns);

                Integer[] denominator = createSummaryArray(getDataSetIntegerValue(data, MALE_DENOMINATOR_COLUMN_NAME),
                        getDataSetIntegerValue(data, FEMALE_DENOMINATOR_COLUMN_NAME));

                String[] denominatorColumns = createSummaryColumnArray(data, TOTAL_DENOMINATOR_COLUMN_NAME);
                buildIndicatorSummary(denominator, denominatorColumns);

                buildIndicatorSummary(createTotalArray(numerator, denominator));
            } else {

                if (translate(data.getDefinition().getName().toString()).equalsIgnoreCase(translate("isanteplusreports.report.vitalStatistics").toString())) {
                    getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "2").withClass("indicatorLabel"));
                    getRows()[1].with(td(translate("isanteplusreports.report.vitalStatistics.birthdate")).attr("colspan", "1").withClass("label"),
                            td(translate("isanteplusreports.report.vitalStatistics.death")).attr("colspan", "1").withClass("label"));
                    Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
                            getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));

                    String[] numeratorColumns = createSummaryColumnArray(data, TOTAL_NUMERATOR_COLUMN_NAME);
                    buildIndicatorSummary(numerator, numeratorColumns);

                    Integer[] denominator = createSummaryArray(getDataSetIntegerValue(data, MALE_DENOMINATOR_COLUMN_NAME),
                            getDataSetIntegerValue(data, FEMALE_DENOMINATOR_COLUMN_NAME));

                    String[] denominatorColumns = createSummaryColumnArray(data, TOTAL_DENOMINATOR_COLUMN_NAME);
                    buildIndicatorSummary(denominator, denominatorColumns);

                    //buildIndicatorSummary(createTotalArray(numerator, denominator));
                } else {

                    getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "3").withClass("indicatorLabel"));
                    getRows()[1].with(td(translate("isanteplusreports.healthqual.result.numerator.label")).attr("colspan", "1").withClass("label"),
                            td(translate("isanteplusreports.healthqual.result.denominator.label")).attr("colspan", "1").withClass("label"), td(translate("isanteplusreports.healthqual.result.percentage.label"))
                                    .attr("colspan", "1").withClass("label"));
                    Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
                            getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));

                    String[] numeratorColumns = createSummaryColumnArray(data, TOTAL_NUMERATOR_COLUMN_NAME);
                    buildIndicatorSummary(numerator, numeratorColumns);

                    Integer[] denominator = createSummaryArray(getDataSetIntegerValue(data, MALE_DENOMINATOR_COLUMN_NAME),
                            getDataSetIntegerValue(data, FEMALE_DENOMINATOR_COLUMN_NAME));

                    String[] denominatorColumns = createSummaryColumnArray(data, TOTAL_DENOMINATOR_COLUMN_NAME);
                    buildIndicatorSummary(denominator, denominatorColumns);

                    buildIndicatorSummary(createPercentageArray(numerator, denominator));
                }
            }
        }
    }

    private <T> void buildIndicatorSummary(T[] dataArray, String[] columnsArray) {

        final int ROW = 3;
        String reportName = columnsArray[1];
        String reportUrl = pageLink("isanteplusreports", "alertPrecoceIndicatorReportPatientList");
        String totalUrl = String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportUrl, reportName, columnsArray[0]);
        getRows()[ROW].with(td(a(dataArray[0].toString()).withHref(totalUrl).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
    }

    private <T> void buildIndicatorSummary(T[] dataArray) {

        final int ROW = 3;
        getRows()[ROW].with(td(dataArray[0].toString()).withClass("total"));
    }


    private Integer[] createSummaryArray(Integer males, Integer females) {
        return new Integer[]{males + females};
    }


    private String[] createSummaryColumnArray(DataSet data, String totalColumn) {
        return new String[]{totalColumn, data.getDefinition().getName()};
    }

    private String[] createPercentageArray(Integer[] dividend, Integer[] factor) {
        DecimalFormat df = new DecimalFormat(PERCENTAGE_STRING_FORMAT_PATTERN);

        final int SIZE = 1;
        String result[] = new String[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            if (factor[i] != 0) {
                result[i] = df.format(100.0f * dividend[i] / factor[i]);
            } else {
                result[i] = df.format(0);
            }
        }
        return result;
    }

    private String[] createTotalArray(Integer[] nbr1, Integer[] nbr2) {

        final int SIZE = 1;
        int total = 0;
        String result[] = new String[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            total = nbr1[i] + nbr2[i];
            result[i] = String.valueOf(total);
        }
        return result;
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

    public ContainerTag[] getRows() {
        if (rows == null) {
            rows = new ContainerTag[ROWS];
            for (int i = 0; i < ROWS; ++i) {
                rows[i] = tr();
            }
        }
        return rows;
    }

    private void clearRows() {
        setRows(null);
    }

    public void setRows(ContainerTag[] rows) {
        this.rows = rows;
    }

    public List<DataSet> getDataSets() {
        if (dataSets == null) {
            dataSets = new LinkedList<DataSet>();
        }
        return dataSets;
    }

    public void setDataSets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public void addDataSet(DataSet i) {
        getDataSets().add(i);
    }

    public void addReportData(ReportData reportData) {
        getDataSets().addAll(reportData.getDataSets().values());
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

}
