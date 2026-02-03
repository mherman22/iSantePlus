package org.openmrs.module.isanteplusreports.library.dimension;


import java.util.Date;

import org.openmrs.module.isanteplusreports.library.cohort.common.CommonCohortLibrary;
import org.openmrs.module.isanteplusreports.pnlsReport.library.cohort.PnlsReportCohortLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;

public class CommonDimension {

    private static final String DATE_PARAMS = "startDate=${startDate},endDate=${endDate}";

    private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
            Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);

    /**
     * Gender dimension
     *
     * @return the {@link org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension}
     */
    public CohortDefinitionDimension gender() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        CommonCohortLibrary genderCohortLibrary = new CommonCohortLibrary();
        dim.setName("gender");
        dim.addCohortDefinition("M", ReportUtils.map(genderCohortLibrary.maleCohort(), ""));
        dim.addCohortDefinition("F", ReportUtils.map(genderCohortLibrary.femaleCohort(), ""));
        return dim;
    }

    public CohortDefinitionDimension ageZone() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
        dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
        dim.setName("age");
        dim.addCohortDefinition("0_1", ReportUtils.map(ageCohortLibrary.ageRangeCohort(0, 1), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("1_4", ReportUtils.map(ageCohortLibrary.ageRangeCohort(1, 4), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("5_9", ReportUtils.map(ageCohortLibrary.ageRangeCohort(5, 9), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("10_14", ReportUtils.map(ageCohortLibrary.ageRangeCohort(10, 14), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("15_19", ReportUtils.map(ageCohortLibrary.ageRangeCohort(15, 19), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("20_24", ReportUtils.map(ageCohortLibrary.ageRangeCohort(20, 24), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("25_29", ReportUtils.map(ageCohortLibrary.ageRangeCohort(25, 29), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("30_34", ReportUtils.map(ageCohortLibrary.ageRangeCohort(30, 34), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("35_39", ReportUtils.map(ageCohortLibrary.ageRangeCohort(35, 39), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("40_44", ReportUtils.map(ageCohortLibrary.ageRangeCohort(40, 44), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("45_49", ReportUtils.map(ageCohortLibrary.ageRangeCohort(45, 49), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("50_54", ReportUtils.map(ageCohortLibrary.ageRangeCohort(50, 54), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("55_59", ReportUtils.map(ageCohortLibrary.ageRangeCohort(55, 59), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("60_64", ReportUtils.map(ageCohortLibrary.ageRangeCohort(60, 64), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("65", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(65), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
        return dim;
    }

    public CohortDefinitionDimension ageZone1() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
        dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
        dim.setName("age");
        dim.addCohortDefinition("0_1", ReportUtils.map(ageCohortLibrary.ageRangeCohort(0, 1), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("1_4", ReportUtils.map(ageCohortLibrary.ageRangeCohort(1, 4), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("5_9", ReportUtils.map(ageCohortLibrary.ageRangeCohort(5, 9), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("10_14", ReportUtils.map(ageCohortLibrary.ageRangeCohort(10, 14), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("15_19", ReportUtils.map(ageCohortLibrary.ageRangeCohort(15, 19), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("20_24", ReportUtils.map(ageCohortLibrary.ageRangeCohort(20, 24), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("25_29", ReportUtils.map(ageCohortLibrary.ageRangeCohort(25, 29), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("30_34", ReportUtils.map(ageCohortLibrary.ageRangeCohort(30, 34), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("35_39", ReportUtils.map(ageCohortLibrary.ageRangeCohort(35, 39), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("40_44", ReportUtils.map(ageCohortLibrary.ageRangeCohort(40, 44), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("45_49", ReportUtils.map(ageCohortLibrary.ageRangeCohort(45, 49), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("50_54", ReportUtils.map(ageCohortLibrary.ageRangeCohort(50, 54), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("55_59", ReportUtils.map(ageCohortLibrary.ageRangeCohort(55, 59), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("60_64", ReportUtils.map(ageCohortLibrary.ageRangeCohort(60, 64), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("65", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(65), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
        return dim;
    }

    public CohortDefinitionDimension notEnrollReason() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        dim.addParameter(new Parameter("endDate", "end Date", Date.class));
        dim.setName("enrolRsn");
        dim.addCohortDefinition("DENIAL", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonDenial(), "endDate=${endDate}"));
        dim.addCohortDefinition("DIED", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonDied(), "endDate=${endDate}"));
        dim.addCohortDefinition("MED", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonMedical(), "endDate=${endDate}"));
        dim.addCohortDefinition("REF", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonRefferd(), "endDate=${endDate}"));
        dim.addCohortDefinition("VOL", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonVoluntary(), "endDate=${endDate}"));
        dim.addCohortDefinition("OTHER", ReportUtils.map(PnlsReportCohortLibrary.cohortByNonEnrollmentReasonOther(), "endDate=${endDate}"));
        return dim;
    }

    public CohortDefinitionDimension keyPopulationDimension() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        dim.setName("keyPopn");
        dim.addCohortDefinition("CAPT", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationCaptive(), ""));
        dim.addCohortDefinition("DRUG", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationDrug(), ""));
        dim.addCohortDefinition("MSM", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationMsm(), ""));
        dim.addCohortDefinition("SEX", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationSex(), ""));
        dim.addCohortDefinition("TRANSG", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationTransgender(), ""));
        return dim;
    }

    public CohortDefinitionDimension keySpecimenDimension() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        dim.setName("specimen");
        dim.addCohortDefinition("CAPT", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationCaptive(), ""));
        dim.addCohortDefinition("DRUG", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationDrug(), ""));
        dim.addCohortDefinition("MSM", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationMsm(), ""));
        dim.addCohortDefinition("SEX", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationSex(), ""));
        dim.addCohortDefinition("TRANSG", ReportUtils.map(PnlsReportCohortLibrary.keyPopulationTransgender(), ""));
        dim.addCohortDefinition("FMEENC", ReportUtils.map(PnlsReportCohortLibrary.keySpecimenFemmeEnceinte(), ""));
        dim.addCohortDefinition("FMEALT", ReportUtils.map(PnlsReportCohortLibrary.keySpecimenFemmeAllaitante(), ""));
        return dim;
    }

    public CohortDefinitionDimension keyScreeneTbDimension() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        dim.setName("scrnTb");
        dim.addCohortDefinition("SCRMSYMPT", ReportUtils.map(PnlsReportCohortLibrary.keyScreeneTbSymptome(), ""));
        dim.addCohortDefinition("CXRXRAY", ReportUtils.map(PnlsReportCohortLibrary.keyScreeneTbRadiographie(), ""));
        dim.addCohortDefinition("PCRMWRD", ReportUtils.map(PnlsReportCohortLibrary.keyScreeneTbPcrMoleculaire(), ""));
        return dim;
    }

    public CohortDefinitionDimension ageZoneBy15() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
        dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
        dim.setName("age");
        dim.addCohortDefinition("<15", ReportUtils.map(ageCohortLibrary.agedAtMostCohort(14), "effectiveDate=${endDate}"));
        dim.addCohortDefinition(">15", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(15), "effectiveDate=${endDate}"));
        dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
        return dim;
    }


	public CohortDefinitionDimension ageZoneBy14() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.setName("age");
		dim.addCohortDefinition("2-14", ReportUtils.map(ageCohortLibrary.agedAtMostCohort(14), "effectiveDate=${endDate}"));
		dim.addCohortDefinition(">14", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(15), "effectiveDate=${endDate}"));
		dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
		return dim;
	}

	public CohortDefinitionDimension ageSuppBy15() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.setName("age");
		dim.addCohortDefinition(">14", ReportUtils.map(ageCohortLibrary.agedAtLeastCohort(15), "effectiveDate=${endDate}"));
		dim.addCohortDefinition("unknown", ReportUtils.map(ageCohortLibrary.unknownAgeCohort(), ""));
		return dim;
	}


	public CohortDefinitionDimension ageZoneBy2() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		CommonCohortLibrary ageCohortLibrary = new CommonCohortLibrary();
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.setName("age");
		dim.addCohortDefinition("<2", ReportUtils.map(ageCohortLibrary.agedAtMostCohort(1), "effectiveDate=${endDate}"));
		return dim;
	}

    public CohortDefinitionDimension CervicalCancerStatusDimension() {
        CohortDefinitionDimension dim = new CohortDefinitionDimension();
        dim.addParameter(START_DATE);
        dim.addParameter(END_DATE);
        dim.setName("cervStat");
        dim.addCohortDefinition("NEG", ReportUtils.map(PnlsReportCohortLibrary.CervicalCancerNegative(), DATE_PARAMS));
        dim.addCohortDefinition("POS", ReportUtils.map(PnlsReportCohortLibrary.CervicalCancerPostive(), DATE_PARAMS));
        dim.addCohortDefinition("SUS", ReportUtils.map(PnlsReportCohortLibrary.CervicalCancerSuspected(), DATE_PARAMS));
        return dim;
    }


}
