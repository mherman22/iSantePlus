package org.openmrs.module.isanteplusreports.healthqual.builder;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.ui.framework.UiUtils;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.SAXException;

import com.itextpdf.text.DocumentException;

import j2html.tags.ContainerTag;

public class HealthQualReportBuilder extends UiUtils{

	private final Log LOGGER = LogFactory.getLog(getClass());

	private static final int ROWS = 4;
	
	private static final ContainerTag MALE_LABEL = th(translateLabel("male")).withClass("label");
	
	private static final ContainerTag FEMALE_LABEL = th(translateLabel("female")).withClass("label");
	
	private static final ContainerTag TOTAL_LABEL = th(translateLabel("total")).withClass("label");
	
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
	
	private int numberOfIndicatorsInOneTable = 3; // if there are too many indicators the table will be splitted
	
	private ContainerTag[] rows;
	
	private List<DataSet> dataSets;
	
	private String clinicDepartment;
	
	private String clinic;

	private Date startDate;

	private Date endDate;

	private Long femalePatients;

	private Long malePatients;

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

	private ContainerTag createPdfHeader() {
		return div().withClass("center").with(
			h2(translateLabel("pdf.header")),
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
		return translateLabel("pdf.creationDate") + " " + df.format(new Date());
	}

	private ContainerTag getStyleForPdf() {
		return style().withType("text/css").withText(getStringFromResource("healthQualPdfStyle.css"));
	}
	
	private String convertHtmlToPdfInBase64(String html) throws IOException, ParserConfigurationException, SAXException,
	        DocumentException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//Document doc = builder.parse(new ByteArrayInputStream(html.replaceAll("&nbsp;", "").getBytes()));
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
		buildClinicInfoTable();
		buildPatientsInfo();
		
		for (int i = 0; i < numberOfIndicatorsInOneTable && iterator.hasNext(); ++i) {
			buildIndicator(iterator.next());
		}
		
		return table().with(getRows());
	}
	
	private void buildClinicInfoTable() {
		fillEmptyRow(getRows()[0], 2);
		fillEmptyRow(getRows()[1], 2);
		
		ContainerTag labels = getRows()[2];
		ContainerTag data = getRows()[3];
		
		labels.with(th(translateLabel("department")).withClass("label"), th(translateLabel("clinic")).withClass("label"));
		
		data.with(td(getClinicDepartment()), td(getClinic()));
	}
	
	private void buildPatientsInfo() {
		fillEmptyRow(getRows()[0], 3);
		
		getRows()[1].with(th(translateLabel("activePatients")).attr("colspan", "3").withClass("label"));
		
		buildIndicatorSummary(createSummaryArray(malePatients, femalePatients));
	}
	
	private void buildIndicator(DataSet data) {
		getRows()[0].with(th(translate(data.getDefinition().getName())).attr("colspan", "9").withClass("indicatorLabel"));
		getRows()[1].with(td(translateLabel("numerator")).attr("colspan", "3").withClass("label"),
		    td(translateLabel("denominator")).attr("colspan", "3").withClass("label"), td(translateLabel("percentage"))
		            .attr("colspan", "3").withClass("label"));
		
		Integer[] numerator = createSummaryArray(getDataSetIntegerValue(data, MALE_NUMERATOR_COLUMN_NAME),
		    getDataSetIntegerValue(data, FEMALE_NUMERATOR_COLUMN_NAME));
		String[] numeratorColumns = createSummaryColumnArray(data, MALE_NUMERATOR_COLUMN_NAME, FEMALE_NUMERATOR_COLUMN_NAME, TOTAL_NUMERATOR_COLUMN_NAME);
		buildIndicatorSummary(numerator, numeratorColumns);
		
		Integer[] denominator = createSummaryArray(getDataSetIntegerValue(data, MALE_DENOMINATOR_COLUMN_NAME),
		    getDataSetIntegerValue(data, FEMALE_DENOMINATOR_COLUMN_NAME));
		String[] denominatorColumns = createSummaryColumnArray(data, MALE_DENOMINATOR_COLUMN_NAME, FEMALE_DENOMINATOR_COLUMN_NAME, TOTAL_DENOMINATOR_COLUMN_NAME);
		buildIndicatorSummary(denominator, denominatorColumns);
		
		buildIndicatorSummary(createPercentageArray(numerator, denominator));
	}
	
	private <T> void buildIndicatorSummary(T[] dataArray, String[] columnsArray) {
		getRows()[2].with(MALE_LABEL, FEMALE_LABEL, TOTAL_LABEL);
		
		final int ROW = 3;
		
		String reportName = columnsArray[3];
		String reportUrl = pageLink("isanteplusreports", "healthQualIndicatorReportPatientList");
		String maleIndicatorUrl = String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportUrl, reportName, columnsArray[0]);
		String femaleIndicatorUrl = String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportUrl, reportName, columnsArray[1]);
		String totalUrl = String.format("%s?savedDataSetKey=%s&savedColumnKey=%s&columnKeyType=numerator", reportUrl, reportName, columnsArray[2]);
		getRows()[ROW].with(td(a(dataArray[0].toString()).withHref(maleIndicatorUrl).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
		getRows()[ROW].with(td(a(dataArray[1].toString()).withHref(femaleIndicatorUrl).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
		getRows()[ROW].with(td(a(dataArray[2].toString()).withHref(totalUrl).attr("onclick", "window.open(this.href, 'windowName', 'width=1000, height=700, left=24, top=24, scrollbars, resizable'); return false;")));
	}

	private <T> void buildIndicatorSummary(T[] dataArray) {
		getRows()[2].with(MALE_LABEL, FEMALE_LABEL, TOTAL_LABEL);
		final int ROW = 3;
		getRows()[ROW].with(td(dataArray[0].toString()));
		getRows()[ROW].with(td(dataArray[1].toString()));
		getRows()[ROW].with(td(dataArray[2].toString()).withClass("total"));
	}
	
	private Integer[] createSummaryArray(Integer males, Integer females) {
		return new Integer[] { males, females, males + females };
	}

	private Long[] createSummaryArray(Long males, Long females) {
		return new Long[] { males, females, males + females };
	}
	
	private String[] createSummaryColumnArray(DataSet data, String maleColumn, String femaleColumn, String totalColumn) {
		return new String[] { maleColumn, femaleColumn, totalColumn, data.getDefinition().getName() };
	}
	
	private String[] createPercentageArray(Integer[] dividend, Integer[] factor) {
		DecimalFormat df = new DecimalFormat(PERCENTAGE_STRING_FORMAT_PATTERN);

		final int SIZE = 3;
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
	
	private static String translateLabel(String labelName) {
		return translate("isanteplusreports.healthqual.result." + labelName + ".label");
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
