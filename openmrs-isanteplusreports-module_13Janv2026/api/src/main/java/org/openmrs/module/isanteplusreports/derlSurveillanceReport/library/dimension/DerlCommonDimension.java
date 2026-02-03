package org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.dimension;

import java.util.Date;

import org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.cohort.DerlReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;

public class DerlCommonDimension {

    private static final String DATE_PARAMS = "startDate=${startDate},endDate=${endDate}";

    private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
            Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);


    public CohortDefinitionDimension derlWeeklySurveillance() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
		//dim.addParameter(new Parameter("weekNumber", "Week Number", Integer.class));
        dim.setName("weekNumber");
        dim.addCohortDefinition("1", ReportUtils.map(DerlReportCohortLibrary.weekly1DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("2", ReportUtils.map(DerlReportCohortLibrary.weekly2DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("3", ReportUtils.map(DerlReportCohortLibrary.weekly3DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("4", ReportUtils.map(DerlReportCohortLibrary.weekly4DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("5", ReportUtils.map(DerlReportCohortLibrary.weekly5DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("6", ReportUtils.map(DerlReportCohortLibrary.weekly6DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("7", ReportUtils.map(DerlReportCohortLibrary.weekly7DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("8", ReportUtils.map(DerlReportCohortLibrary.weekly8DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("9", ReportUtils.map(DerlReportCohortLibrary.weekly9DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("10", ReportUtils.map(DerlReportCohortLibrary.weekly10DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("11", ReportUtils.map(DerlReportCohortLibrary.weekly11DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("12", ReportUtils.map(DerlReportCohortLibrary.weekly12DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("13", ReportUtils.map(DerlReportCohortLibrary.weekly13DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("14", ReportUtils.map(DerlReportCohortLibrary.weekly14DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("15", ReportUtils.map(DerlReportCohortLibrary.weekly15DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("16", ReportUtils.map(DerlReportCohortLibrary.weekly16DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("17", ReportUtils.map(DerlReportCohortLibrary.weekly17DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("18", ReportUtils.map(DerlReportCohortLibrary.weekly18DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("19", ReportUtils.map(DerlReportCohortLibrary.weekly19DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("20", ReportUtils.map(DerlReportCohortLibrary.weekly20DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("21", ReportUtils.map(DerlReportCohortLibrary.weekly21DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("22", ReportUtils.map(DerlReportCohortLibrary.weekly22DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("23", ReportUtils.map(DerlReportCohortLibrary.weekly23DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("24", ReportUtils.map(DerlReportCohortLibrary.weekly24DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("25", ReportUtils.map(DerlReportCohortLibrary.weekly25DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("26", ReportUtils.map(DerlReportCohortLibrary.weekly26DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("27", ReportUtils.map(DerlReportCohortLibrary.weekly27DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("28", ReportUtils.map(DerlReportCohortLibrary.weekly28DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("29", ReportUtils.map(DerlReportCohortLibrary.weekly29DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("30", ReportUtils.map(DerlReportCohortLibrary.weekly30DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("31", ReportUtils.map(DerlReportCohortLibrary.weekly31DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("32", ReportUtils.map(DerlReportCohortLibrary.weekly32DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("33", ReportUtils.map(DerlReportCohortLibrary.weekly33DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("34", ReportUtils.map(DerlReportCohortLibrary.weekly34DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("35", ReportUtils.map(DerlReportCohortLibrary.weekly35DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("36", ReportUtils.map(DerlReportCohortLibrary.weekly36DerlSurveillanceCohort(), ""));

        dim.addCohortDefinition("37", ReportUtils.map(DerlReportCohortLibrary.weekly37DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("38", ReportUtils.map(DerlReportCohortLibrary.weekly38DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("39", ReportUtils.map(DerlReportCohortLibrary.weekly39DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("40", ReportUtils.map(DerlReportCohortLibrary.weekly40DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("41", ReportUtils.map(DerlReportCohortLibrary.weekly41DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("42", ReportUtils.map(DerlReportCohortLibrary.weekly42DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("43", ReportUtils.map(DerlReportCohortLibrary.weekly43DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("44", ReportUtils.map(DerlReportCohortLibrary.weekly44DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("45", ReportUtils.map(DerlReportCohortLibrary.weekly45DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("46", ReportUtils.map(DerlReportCohortLibrary.weekly46DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("47", ReportUtils.map(DerlReportCohortLibrary.weekly47DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("48", ReportUtils.map(DerlReportCohortLibrary.weekly48DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("49", ReportUtils.map(DerlReportCohortLibrary.weekly49DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("50", ReportUtils.map(DerlReportCohortLibrary.weekly50DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("51", ReportUtils.map(DerlReportCohortLibrary.weekly51DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("52", ReportUtils.map(DerlReportCohortLibrary.weekly52DerlSurveillanceCohort(), ""));
        dim.addCohortDefinition("53", ReportUtils.map(DerlReportCohortLibrary.weekly53DerlSurveillanceCohort(), ""));
        return dim;
    }


    public CohortDefinitionDimension derlMonthlySurveillance() {
        CohortDefinitionDimension monthDim = new CohortDefinitionDimension();
		//dim.addParameter(new Parameter("weekNumber", "Week Number", Integer.class));
        monthDim.setName("monthNumber");
        monthDim.addCohortDefinition("1", ReportUtils.map(DerlReportCohortLibrary.monthly1DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("2", ReportUtils.map(DerlReportCohortLibrary.monthly2DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("3", ReportUtils.map(DerlReportCohortLibrary.monthly3DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("4", ReportUtils.map(DerlReportCohortLibrary.monthly4DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("5", ReportUtils.map(DerlReportCohortLibrary.monthly5DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("6", ReportUtils.map(DerlReportCohortLibrary.monthly6DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("7", ReportUtils.map(DerlReportCohortLibrary.monthly7DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("8", ReportUtils.map(DerlReportCohortLibrary.monthly8DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("9", ReportUtils.map(DerlReportCohortLibrary.monthly9DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("10", ReportUtils.map(DerlReportCohortLibrary.monthly10DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("11", ReportUtils.map(DerlReportCohortLibrary.monthly11DerlSurveillanceCohort(), ""));
        monthDim.addCohortDefinition("12", ReportUtils.map(DerlReportCohortLibrary.monthly12DerlSurveillanceCohort(), ""));

        return monthDim;
    }

}
