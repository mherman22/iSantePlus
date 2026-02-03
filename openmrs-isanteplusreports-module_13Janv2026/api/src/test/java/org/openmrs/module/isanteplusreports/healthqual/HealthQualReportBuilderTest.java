package org.openmrs.module.isanteplusreports.healthqual;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.isanteplusreports.healthqual.builder.HealthQualReportBuilder;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.EncounterDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import j2html.tags.ContainerTag;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HealthQualReportBuilder.class, MessageUtil.class, HealthQualUtils.class, Context.class})
public class HealthQualReportBuilderTest {

    private HealthQualReportBuilder healthQualReportBuilder;

    @Mock
    ReportDefinitionService reportDefinitionService;

    @Mock
    SimpleDataSet dataSet;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockStatic(HealthQualUtils.class);
        when(HealthQualUtils.replaceNonBreakingSpaces(anyString())).thenReturn("label");
        mockStatic(MessageUtil.class);
        when(MessageUtil.translate(anyString())).thenReturn("label");

        healthQualReportBuilder = new HealthQualReportBuilder();
    }

    @Test
    public void getDataSetsTest() {
        healthQualReportBuilder.setDataSets(null);
        List<DataSet> result = healthQualReportBuilder.getDataSets();
        assertNotNull(result);

        List<DataSet> dataSetList = new LinkedList<DataSet>();
        healthQualReportBuilder.setDataSets(dataSetList);
        result = healthQualReportBuilder.getDataSets();
        assertEquals(dataSetList, result);
    }

    @Test
    public void getRowsTest() {
        healthQualReportBuilder.setRows(null);
        ContainerTag[] result = healthQualReportBuilder.getRows();
        assertNotNull(result);

        ContainerTag[] rows = new ContainerTag[4];
        healthQualReportBuilder.setRows(rows);
        result = healthQualReportBuilder.getRows();
        assertArrayEquals(rows, result);
    }

    @Test
    public void getClinicDepartmentTest() {
        healthQualReportBuilder.setClinicDepartment("Clinic Department");
        String result = healthQualReportBuilder.getClinicDepartment();
        assertNotNull(result);
        assertEquals(result, "Clinic Department");

        healthQualReportBuilder.setClinicDepartment("");
        result = healthQualReportBuilder.getClinicDepartment();
        assertNotNull(result);
        assertEquals(result, "-");
    }

    @Test
    public void getClinicTest() {
        healthQualReportBuilder.setClinic("Clinic");
        String result = healthQualReportBuilder.getClinic();
        assertNotNull(result);
        assertEquals(result, "Clinic");

        healthQualReportBuilder.setClinic("");
        result = healthQualReportBuilder.getClinic();
        assertNotNull(result);
        assertEquals(result, "-");
    }

    @Test
    public void addReportDataTest() {
        ReportData reportData = new ReportData();
        Map<String, DataSet> dataSets = new HashMap<>();
        dataSets.put("first", dataSet);
        reportData.setDataSets(dataSets);

        healthQualReportBuilder.addReportData(reportData);

        assertTrue(healthQualReportBuilder.getDataSets().contains(dataSet));
    }

    @Test
    public void buildHtmlTablesTest() {
        //Mock Behavior
        List<DataSet> dataSets = mockDataSet();

        //Variables
        healthQualReportBuilder.setDataSets(dataSets);
        healthQualReportBuilder.setFemalePatients(2L);
        healthQualReportBuilder.setMalePatients(3L);

        //Execute
        String result = healthQualReportBuilder.buildHtmlTables();

        //Verify
        assertNotNull(result);
        assertTrue(result.contains("<table>"));
        assertTrue(result.contains("<td>2</td>"));
        assertTrue(result.contains("<td>3</td>"));
        assertTrue(result.contains("<td class=\"total\">5</td>"));
    }

    @Test
    public void buildHtmlTablesWhenNoColumnTest() {
        //Mock Behavior
        mockStatic(Context.class);
        when(Context.getService(ReportDefinitionService.class)).thenReturn(reportDefinitionService);

        //Variables
        EvaluationContext evaluationContext = new EvaluationContext();
        DataSetDefinition dataSetDefinition = new EncounterDataSetDefinition();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, evaluationContext);
        DataSetRow row = new DataSetRow();
        dataSet.addRow(row);

        List<DataSet> dataSets = new LinkedList<DataSet>();
        dataSets.add(dataSet);
        healthQualReportBuilder.setDataSets(dataSets);
        healthQualReportBuilder.setFemalePatients(2L);
        healthQualReportBuilder.setMalePatients(3L);

        //Expected
        expectedException.expect(HealthQualException.class);
        expectedException.expectMessage("`null` report - column `maleNumerator` " +
                "doesn't exist in dataSet. Probably there is a bug in report SQL");

        //Execute
        healthQualReportBuilder.buildHtmlTables();
    }

    @Test
    public void buildPdfTest() {
        //Mock Behavior
        List<DataSet> dataSets = mockDataSet();

        //Variables
        healthQualReportBuilder.setDataSets(dataSets);
        healthQualReportBuilder.setFemalePatients(2L);
        healthQualReportBuilder.setMalePatients(3L);
        Calendar cal = Calendar.getInstance();
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endDate = cal.getTime();
        healthQualReportBuilder.setStartDate(startDate);
        healthQualReportBuilder.setEndDate(endDate);

        //Execute
        String result = healthQualReportBuilder.buildPdf();

        //Verify
        assertNotNull(result);
    }

    private List<DataSet> mockDataSet() {
        mockStatic(Context.class);
        when(Context.getService(ReportDefinitionService.class)).thenReturn(reportDefinitionService);

        EvaluationContext evaluationContext = new EvaluationContext();
        DataSetDefinition dataSetDefinition = new EncounterDataSetDefinition();

        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, evaluationContext);
        DataSetColumn column = new DataSetColumn();
        column.setName("maleNumerator");
        DataSetRow row = new DataSetRow();
        row.addColumnValue(column, 1);
        dataSet.addRow(row);

        DataSetColumn column2 = new DataSetColumn();
        column2.setName("femaleNumerator");
        row.addColumnValue(column2, 1);

        DataSetColumn column3 = new DataSetColumn();
        column3.setName("maleDenominator");
        row.addColumnValue(column3, 0);

        DataSetColumn column4 = new DataSetColumn();
        column4.setName("femaleDenominator");
        row.addColumnValue(column4, 0);

        List<DataSet> dataSets = new LinkedList<DataSet>();
        dataSets.add(dataSet);
        return dataSets;
    }
}
