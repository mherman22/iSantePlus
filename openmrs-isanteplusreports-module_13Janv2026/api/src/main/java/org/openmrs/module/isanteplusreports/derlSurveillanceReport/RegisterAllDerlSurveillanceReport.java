package org.openmrs.module.isanteplusreports.derlSurveillanceReport;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;

import j2html.tags.ContainerTag;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.library.dimension.DerlCommonDimension;
import org.openmrs.module.isanteplusreports.derlSurveillanceReport.utils.DerlSurveillanceReportUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllDerlSurveillanceReport {

//private Log log = LogFactory.getLog(this.getClass());

    private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate", Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);

    //private final static Parameter WEEK_NUMBER = new Parameter("weekNumber", "weekNumber", Integer.class);

    //private final static Parameter YEAR = new Parameter("year", "isanteplusreports.parameters.year", Integer.class);

    public static void registerAll() {
        immediateSurveillanceReport();
        weeklySurveillanceReport();
        monthlySurveillanceReport();
    }

    private static void immediateSurveillanceReport() {
        registerImmediateSurveillanceWithStartAndEndDateParams(
                DerlReportConstants.ANIMAL_SUSPECTED_RABBIES_SQL,
                DerlReportConstants.COVID_SUSPECTED_SQL,
                DerlReportConstants.COVID_CONFIRM_SQL,
                DerlReportConstants.CHOLERA_SUSPECT_SQL,
                DerlReportConstants.COQUELUCHE_SUSPECTE_SQl,
                DerlReportConstants.DIPHTERIE_SUSPECT_SQL,
                DerlReportConstants.MENINGITE_SUSPECTE_SQL,
                DerlReportConstants.PALUDISME_CONFIRME_SQL,
                DerlReportConstants.PARALYSIE_FLASQUE_AIGUE_SQL,
                DerlReportConstants.PESTE_SUSPECTE_SQL,
                DerlReportConstants.ROUGEOLE_RUBEOLE_SUSPECTE_SQL,
                DerlReportConstants.SYNDROME_FIEVRE_HEMORRAGIQUE_AIGUE_SQL,
                DerlReportConstants.SYNDROME_RUBEOLE_CONGENITALE_SQL,
                DerlReportConstants.ESAVI_MAJEUR_SQL,
                DerlReportConstants.MORTALITE_NEONATALE_SQL,
                DerlReportConstants.TETANOS_NEONATAL_SQL,
                DerlReportConstants.TIAC_SQL,
                DerlReportConstants.TOUT_PHENOMENE_INHABITUEL_SQL,
                DerlReportConstants.IMMEDIATE_SURVEILLANCE_MESSAGE,
                DerlReportConstants.IMMEDIATE_SURVEILLANCE_UUID
        );

    }

    private static void weeklySurveillanceReport() {
        registerWeeklySurveillanceWithStartAndEndDateParams(
                DerlReportConstants.AUTRE_FIEVRE_ORIGINE_INDETERMINE_SQL,
                DerlReportConstants.CHARBON_CUTANE_SUSPECT_SQL,
                DerlReportConstants.DECES_MATERNEL_SQL,
                DerlReportConstants.DIARRHEE_AIGUE_SQL,
                DerlReportConstants.DIARRHEE_AIGUE_SANGLANTE_SQL,
                DerlReportConstants.ESAVI_MINEURE_SQL,
                DerlReportConstants.FIEVRE_TYPHOIDE_SUSPECT_SQL,
                DerlReportConstants.INFECTION_RESPIRATOIRE_AIGUE_SQL,
                DerlReportConstants.TETANOS_SQL,
                DerlReportConstants.DENGUE_SUSTECTE_SQL,
                DerlReportConstants.FILARIOSE_PROBABLE_SQL,
                DerlReportConstants.RAGE_HUMAINE_SQL,
                DerlReportConstants.SYNDROME_ICTERIQUE_FEBRILE_SQL,
                DerlReportConstants.VIOLENCES_SEXUELLES_SQL,
                DerlReportConstants.WEEKLY_SURVEILLANCE_MESSAGE,
                DerlReportConstants.WEEKLY_SURVEILLANCE_UUID
        );

    }

    private static void monthlySurveillanceReport() {
        registerMonthlySurveillanceWithStartAndEndDateParams(
                DerlReportConstants.PUBLIC_ACCIDENT_SQL,
                DerlReportConstants.DOMESTIC_ACCIDENT_SQL,
                DerlReportConstants.BREAST_CANCER_SQL,
                DerlReportConstants.PROSTATE_CANCER_SQL,
                DerlReportConstants.CANCER_COL_SQL,
                DerlReportConstants.DIABETE_SQL,
                DerlReportConstants.EPILEPSIE_SQL,
                DerlReportConstants.HYPERTENSION_ARTERIELLE_SQL,
                DerlReportConstants.LEPRE_SQL,
                DerlReportConstants.MALNUTRITION_SQL,
                DerlReportConstants.SYPHILLIS_CONGENITALE_SQL,
                DerlReportConstants.IST_SQL,
                DerlReportConstants.VIOLENCES_PHYSIQUES_SQL,
                DerlReportConstants.MONTHLY_SURVEILLANCE_MESSAGE,
                DerlReportConstants.MONTHLY_SURVEILLANCE_UUID
        );

    }

    private static void registerImmediateSurveillanceWithStartAndEndDateParams(
            String sqlRageA,
            String sqlCovidSuspect,
            String sqlCovidConfirm,
            String sqlCholeraSuspect,
            String sqlCoquelucheSuspect,
            String sqlDiphterieSuspect,
            String sqlMeningiteSuspect,
            String sqlPaludismeConfirme,
            String sqlParalysieFlasqueAigue,
            String sqlPesteSuspect,
            String sqlRougeoleRubeoleSuspecte,
            String sqlSyndrFievreHemAigue,
            String sqlSyndrRubeoleCogen,
            String sqlEsaviMageure,
            String sqlMortaliteNeonatale,
            String sqlTetanosNeonatal,
            String sqlTiac,
            String sqlToutPhenomeneInhab,
            String messageProperties,
            String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("weekNumber", ReportUtils.map(new DerlCommonDimension().derlWeeklySurveillance(), ""));

        CohortIndicator cohortRageAn = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlRageA, "name", getParameters());
        CohortIndicator cohortCovidSupect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCovidSuspect, "name", getParameters());
        CohortIndicator cohortCovidConfirm = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCovidConfirm, "name", getParameters());
        CohortIndicator cohortCholeraSuspect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCholeraSuspect, "name", getParameters());

        CohortIndicator cohortCoquelucheSuspect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCoquelucheSuspect, "name", getParameters());
        CohortIndicator cohortDiphterieSuspect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDiphterieSuspect, "name", getParameters());
        CohortIndicator cohortMeningiteSuspect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlMeningiteSuspect, "name", getParameters());
        CohortIndicator cohortPaludismeConfirme = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlPaludismeConfirme, "name", getParameters());
        CohortIndicator cohortParalysieFlasqueAigue = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlParalysieFlasqueAigue, "name", getParameters());
        CohortIndicator cohortPesteSuspect = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlPesteSuspect, "name", getParameters());

        CohortIndicator cohortRougeoleRubeoleSuspecte = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlRougeoleRubeoleSuspecte, "name", getParameters());
        CohortIndicator cohortSyndrFievreHemAigue = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlSyndrFievreHemAigue, "name", getParameters());
        CohortIndicator cohortSyndrRubeoleCogen = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlSyndrRubeoleCogen, "name", getParameters());
        CohortIndicator cohortEsaviMageure = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlEsaviMageure, "name", getParameters());
        CohortIndicator cohortMortaliteNeonatale = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlMortaliteNeonatale, "name", getParameters());
        CohortIndicator cohortTetanosNeonatal = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlTetanosNeonatal, "name", getParameters());
        CohortIndicator cohortTiac = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlTiac, "name", getParameters());
        CohortIndicator cohortToutPhenomeneInhab = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlToutPhenomeneInhab, "name", getParameters());

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortRageAn, "N");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortCovidSupect, "R");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortCovidConfirm, "C");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortCholeraSuspect, "D");

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortCoquelucheSuspect, "E");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortDiphterieSuspect, "F");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortMeningiteSuspect, "G");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortPaludismeConfirme, "H");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortParalysieFlasqueAigue, "I");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortPesteSuspect, "J");

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortRougeoleRubeoleSuspecte, "K");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortSyndrFievreHemAigue, "L");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortSyndrRubeoleCogen, "M");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortEsaviMageure, "O");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortMortaliteNeonatale, "P");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortTetanosNeonatal, "Q");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortTiac, "S");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortToutPhenomeneInhab, "T");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, DerlReportConstants.REPORT_DESCRIPTION_1, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerWeeklySurveillanceWithStartAndEndDateParams(
            String sqlAutreFievreIndetermine,
            String sqlCharbonCutane,
            String sqlDecesMaternel,
            String sqlDiarrheeAigue,
            String sqlDiarrheeAigueS,
            String sqlEsaviMineure,
            String sqlFievreTyphoideSuspecte,
            String sqlInfectionRespiratoireAigue,
            String sqlTetanos,
            String sqlDengueSupecte,
            String sqlFilarioseProbable,
            String sqlRageHumaine,
            String sqlSyndromeIcteriqueFebrile,
            String sqlViolencesSexuelles,
            String messageProperties,
            String uuid) {

        System.out.println("\nsqlTetanos = "+sqlTetanos.toString());

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("weekNumber", ReportUtils.map(new DerlCommonDimension().derlWeeklySurveillance(), ""));

        CohortIndicator cohortAutreFievreIndetermine = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlAutreFievreIndetermine, "name", getParameters());
        CohortIndicator cohortCharbonCutane = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCharbonCutane, "name", getParameters());
        CohortIndicator cohortDecesMaternel = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDecesMaternel, "name", getParameters());
        CohortIndicator cohortDiarrheeAigue = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDiarrheeAigue, "name", getParameters());

        CohortIndicator cohortDiarrheeAigueS = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDiarrheeAigueS, "name", getParameters());
        CohortIndicator cohortEsaviMineure = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlEsaviMineure, "name", getParameters());
        CohortIndicator cohortFievreTyphoideSuspecte = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlFievreTyphoideSuspecte, "name", getParameters());
        CohortIndicator cohortInfectionRespiratoireAigue = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlInfectionRespiratoireAigue, "name", getParameters());

        CohortIndicator cohortTetanos = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlTetanos, "name", getParameters());
        CohortIndicator cohortDengueSupecte = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDengueSupecte, "name", getParameters());
        CohortIndicator cohortFilarioseProbable = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlFilarioseProbable, "name", getParameters());
        CohortIndicator cohortRageHumaine = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlRageHumaine, "name", getParameters());
        CohortIndicator cohortSyndromeIcteriqueFebrile = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlSyndromeIcteriqueFebrile, "name", getParameters());
        CohortIndicator cohortViolencesSexuelles = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlViolencesSexuelles, "name", getParameters());

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortAutreFievreIndetermine, "WN");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortCharbonCutane, "WR");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortDecesMaternel, "WC");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortDiarrheeAigue, "WD");

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortDiarrheeAigueS, "WE");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortEsaviMineure, "WF");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortFievreTyphoideSuspecte, "WG");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortInfectionRespiratoireAigue, "WH");

        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortTetanos, "WI");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortDengueSupecte, "WJ");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortFilarioseProbable, "WK");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortRageHumaine, "WL");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortSyndromeIcteriqueFebrile, "WM");
        DerlSurveillanceReportUtils.addWeeklyColums(dsd, cohortViolencesSexuelles, "WO");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, DerlReportConstants.REPORT_DESCRIPTION_WEEKLY, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerMonthlySurveillanceWithStartAndEndDateParams(
            String sqlPublicAcc,
            String sqlDomesticAcc,
            String sqlBreastCancer,
            String sqlProstateCancer,
            String sqlCancerCol,
            String sqlDiabete,
            String sqlEpilepsie,
            String sqlHypertensionAt,
            String sqlLepre,
            String sqlMalnutrition,
            String sqlSyphilisCong,
            String sqlIst,
            String sqlViolencePhys,
            String messageProperties,
            String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("monthNumber", ReportUtils.map(new DerlCommonDimension().derlMonthlySurveillance(), ""));

        CohortIndicator cohortPublicAcc = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlPublicAcc, "name", getParameters());
        CohortIndicator cohortDomesticAcc = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDomesticAcc, "name", getParameters());
        CohortIndicator cohortBreastCancer = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlBreastCancer, "name", getParameters());
        CohortIndicator cohortProstateCancer = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlProstateCancer, "name", getParameters());

        CohortIndicator cohortCancerCol = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlCancerCol, "name", getParameters());
        CohortIndicator cohortDiabete = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlDiabete, "name", getParameters());
        CohortIndicator cohortEpilepsie = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlEpilepsie, "name", getParameters());
        CohortIndicator cohortHypertensionAt = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlHypertensionAt, "name", getParameters());

        CohortIndicator cohortLepre = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlLepre, "name", getParameters());
        CohortIndicator cohortMalnutrition = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlMalnutrition, "name", getParameters());
        CohortIndicator cohortSyphilisCong = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlSyphilisCong, "name", getParameters());
        CohortIndicator cohortIst = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlIst, "name", getParameters());
        CohortIndicator cohortViolencePhys = DerlSurveillanceReportUtils.cohortIndicatorFromSqlResource(sqlViolencePhys, "name", getParameters());

        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortPublicAcc, "MN");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortDomesticAcc, "MR");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortBreastCancer, "MC");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortProstateCancer, "MD");

        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortCancerCol, "ME");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortDiabete, "MF");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortEpilepsie, "MG");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortHypertensionAt, "MH");

        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortLepre, "MI");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortMalnutrition, "MJ");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortSyphilisCong, "MK");
        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortIst, "ML");

        DerlSurveillanceReportUtils.addMonthlyColums(dsd, cohortViolencePhys, "MM");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);

        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");

        ReportDefinition repDefinition = reportDefinition(messageProperties, DerlReportConstants.REPORT_DESCRIPTION_2, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);

        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    public static List<Parameter> getParameters() {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class));
    }


}
