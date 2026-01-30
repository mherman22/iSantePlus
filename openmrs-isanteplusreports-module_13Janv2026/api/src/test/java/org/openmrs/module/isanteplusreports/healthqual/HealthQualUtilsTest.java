package org.openmrs.module.isanteplusreports.healthqual;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.api.context.Context;


import org.openmrs.module.isanteplusreports.exception.HealthQualException;
import org.openmrs.module.isanteplusreports.healthqual.util.HealthQualUtils;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;

import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class, EvaluationContext.class, HealthQualUtils.class})
public class HealthQualUtilsTest {

    @Mock
    ReportDefinitionService reportDefinitionService;

    @Mock
    ReportData reportData;

    @Mock
    ReportDefinition reportDefinition;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void replaceNonBreakingSpacesTest() {
        String spaces = "1234 &nbsp;";
        spaces = HealthQualUtils.replaceNonBreakingSpaces(spaces);

        Assert.assertFalse(spaces.contains("&nbsp;"));
        Assert.assertFalse(spaces.contains(" "));
    }

    @Test
    public void getReportDataTest() throws Exception {
        //Variables
        String reportUuid = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endDate = cal.getTime();

        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setName("startDate");
        parameters.add(parameter);
        Parameter parameterSecond = new Parameter();
        parameterSecond.setName("endDate");
        parameters.add(parameterSecond);
        Parameter parameterThird = new Parameter();
        parameterThird.setName("currentDate");
        parameters.add(parameterThird);

        Parameter extraParameter = new Parameter();
        extraParameter.setName("extra");
        parameters.add(extraParameter);

        Map<String, Object> additionalOptions = new HashMap<>();
        additionalOptions.put("extra", extraParameter);

        //Mock Behavior
        when(reportDefinitionService.getDefinitionByUuid(anyString())).thenReturn(reportDefinition);

        when(reportDefinition.getParameters()).thenReturn(parameters);

        mockStatic(Context.class);
        when(Context.getService(ReportDefinitionService.class)).thenReturn(reportDefinitionService);

        EvaluationContext evaluationContext = new EvaluationContext();
        whenNew(EvaluationContext.class).withNoArguments().thenReturn(evaluationContext);
        when(reportDefinitionService.evaluate(eq(reportDefinition), any(EvaluationContext.class)))
                .thenReturn(reportData);

        //Execute
        HealthQualUtils.getReportData(reportUuid, startDate, endDate, additionalOptions);

        //Verify
        verify(reportDefinitionService, times(1)).getDefinitionByUuid(eq(reportUuid));
        verify(reportDefinitionService, times(1))
                .evaluate(eq(reportDefinition), any(EvaluationContext.class));

        assertTrue(evaluationContext.getParameterValues().containsKey("startDate"));
        assertTrue(evaluationContext.getParameterValues().containsKey("endDate"));
        assertTrue(evaluationContext.getParameterValues().containsKey("currentDate"));
        assertTrue(evaluationContext.getParameterValues().containsKey("extra"));

        assertEquals(evaluationContext.getParameterValue("startDate"), startDate);
        assertEquals(evaluationContext.getParameterValue("endDate"), endDate);
        assertEquals(evaluationContext.getParameterValue("currentDate"), startDate);
        assertEquals(evaluationContext.getParameterValue("extra"), extraParameter);
    }

    @Test
    public void getReportDataWithNoParametersTest() throws Exception {
        //Variables
        String reportUuid = UUID.randomUUID().toString();
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setName("bad");
        parameters.add(parameter);

        //Mock Behavior
        when(reportDefinitionService.getDefinitionByUuid(anyString())).thenReturn(reportDefinition);

        when(reportDefinition.getParameters()).thenReturn(parameters);

        mockStatic(Context.class);
        when(Context.getService(ReportDefinitionService.class)).thenReturn(reportDefinitionService);

        EvaluationContext evaluationContext = new EvaluationContext();
        whenNew(EvaluationContext.class).withNoArguments().thenReturn(evaluationContext);
        when(reportDefinitionService.evaluate(eq(reportDefinition), any(EvaluationContext.class)))
                .thenReturn(reportData);

        //Expected
        expectedException.expect(HealthQualException.class);
        expectedException.expectMessage("Report cannot be evaluated - missing 'bad' parameter'");

        //Execute
        HealthQualUtils.getReportData(reportUuid, null, null, null);
    }

    @Test
    public void getReportDataWithEvaluationErrorTest() throws Exception {
        //Variables
        String reportUuid = UUID.randomUUID().toString();
        Calendar cal = Calendar.getInstance();
        Date startDate = cal.getTime();
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setName("startDate");
        parameters.add(parameter);

        //Mock Behavior
        when(reportDefinitionService.getDefinitionByUuid(anyString())).thenReturn(reportDefinition);
        when(reportDefinition.getParameters()).thenReturn(parameters);

        mockStatic(Context.class);
        when(Context.getService(ReportDefinitionService.class)).thenReturn(reportDefinitionService);

        EvaluationContext evaluationContext = new EvaluationContext();
        whenNew(EvaluationContext.class).withNoArguments().thenReturn(evaluationContext);

        when(reportDefinitionService.evaluate(eq(reportDefinition), any(EvaluationContext.class)))
                .thenThrow(new EvaluationException("error"));

        //Expected
        expectedException.expect(HealthQualException.class);
        expectedException.expectMessage("Report cannot be evaluated");

        //Execute
        HealthQualUtils.getReportData(reportUuid, startDate, null, null);
    }

}
