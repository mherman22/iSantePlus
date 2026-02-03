package org.openmrs.module.isanteplusreports.pnlsReport;

import static org.openmrs.module.isanteplusreports.IsantePlusReportsUtil.reportDefinition;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.library.dimension.CommonDimension;
import org.openmrs.module.isanteplusreports.pnlsReport.utils.pnlsReportUtils;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;

public class RegisterAllPnlsReports {

    private Log log = LogFactory.getLog(this.getClass());

    private final static Parameter START_DATE = new Parameter("startDate", "isanteplusreports.parameters.startdate",
            Date.class);

    private final static Parameter END_DATE = new Parameter("endDate", "isanteplusreports.parameters.enddate", Date.class);

    public static void registerAll() {
        newlyEnrolledPatientsOnArt();
        referredInPatientsOnArt();
        newlyEnrolledBreastFeedingOnArt();
        newlyEnrolledSupp200OnArt();
        newlyEnrolledInf200OnArt();
        newlyEnrolledUnknownOnArt();

        newlyRefferedInBreastFeedingOnArt();
        notEnrolledOnArtByReason();
        patientsBykeyPopulations();
        patientsUnderCtx();
        patientsEnrolledOnArtUnderTbTreatMent();
        patientsEnrolledOnArtUnderTbTreatMentFor6months();
        patientsEnrolledOnArtCompletedTbTreatMentFor6months();
        patientsEnrolledOnArtINHTreatMentDuring();/////////////
        patientsEnrolledOnArINHTreatMentSixMonth();/////////////
        patientsEnrolledOnArtUnderTBTreatMentDuring();/////////////
        patientsEnrolledOnArtUnderRIFAINHTreatMentDuring();/////////////
        patientsEnrolledOnArtUnderRIFAINHTreatMentThreeMonth();/////////////
        patientsEnrolledCompleteOnArtUnderRIFAINHTreatMentThreeMonth();
        patientsEnrolledOnArtUnderRIFAINHTreatMentOneMonth();/////////////
        patientsEnrolledOnArtUnderRIFAINHCompleteTreatMentDuring();/////////////


        patientsEnrolledOnArtTbPostiveScrenneesFor6months();
        patientsEnrolledOnArtTbNegativeScrenneesFor6months();
//        activeArvPatientsByScreeneTbLines();

        activeArvPatientsWithViralLoadByScreeneTb(); // 18
        activeArvPatientsWithViralLoadByScreeneTbCXRXRAY();
        patientsEnrolledOnArtTbPostiveScrenneesFor6monthsCXRXRAYPos();
        patientsEnrolledOnArtTbPostiveScrenneesFor6monthsCXRXRAYNeg();
        activeArvPatientsWithViralLoadByScreeneTbMWRD();
        patientsEnrolledOnArtTbPostiveScrenneesFor6monthsMWRDPos();
        patientsEnrolledOnArtTbPostiveScrenneesFor6monthsMWRDNeg();

        patientsEnrolledOnArtActiveCompletedTbTreatMentFor6months();
        patientsEnrolledOnArtActiveCompletedTbScreeneTreatMentFor6months();
//        totalPatientsCompletedPreventionTbTreatment();

//        totalPatientsUnderPreventionCtx();
//        totalPatientsUnderPreventionTbTreatment();
//        totalPatientsUnderPreventionTbTreatmentForSixMonths();
//        totalPatientsCompletedPreventionTbTreatment();
        totalPatientsWhoAreTbScrenees();
        totalPatientsWithBacteriologySpecimentSampleCollection();
        BreastFeedingNotYetOnArt();
        ArvPatientsWithSamplesSentForTbDiagnosisTest();
        totalPatientsWithPostiveResultsFromTbDiagnosis();
//        totalArvPatientsNewlyPlacedUnderTbTreatment();
        patientsEnrolledOnArtAndTbTreatment();
        patientsEnrolledOnArtVihAntiTbTreatMent();
        patientsEnrolledOnArtVihAndTbTreatment();

//        tbHivPatientsOnAntiTbTreatment();
//        tbHivPatientsOnTbTreamentDintergratedByNelyAndActivelyEnrolledOnArt();
        activeHivPatientsTB_ART();
        activeHivPatientsTX_HIV_HTN();
        activeHivPatients();
        activeArvPatientsBykeyPopulations();
        activeArvPatientsOverMonths();
        activeArvPatientsByRegimeLines();
//        activeArvPatientsByScreeneTbLines();
        totalArvPatientsInactiveAtTheEnd();
        inactiveArvPatientsDead();
        inactiveArvPatientsMedicalOrVountary();
        inactiveArvPatientsLostToForAMonth();
        inactiveArvPatientsLostToForMigrated();
        inactiveArvPatientsTransferred();
        patientsDuplicated();
        totalArvPatientsLostToFollow();
        lostPatientsDied();
        lostPatientsAfterLessThan3MonthsTreatment();
        lostPatientsAfterMoreThan3MonthsTreatment();
        lostPatientsAfterMoreThan6MonthsTreatment();
        lostPatientsConfirmedTransferedTreatment();
        lostPatientsStoppedTreatment();

//        lostPatientsTransferred();
//        lostPatientsStopped();
        deadArvPatientsByTuberclosis();
        deadArvPatientsByOtherInfectiousDiseases();
        deadArvPatientsByCancer();
        deadArvPatientsByHivIllnesses();
        deadArvPatientsByNaturalCauses();
        deadArvPatientsByUnNaturalCauses();
        deadArvPatientsByUnKnownCauses();
        lostArvPatientsResumedTreatment();
        totalArvPatientsInactiveAndResumeActive3Months();
        totalArvPatientsInactiveAndResumeActive5Months();
        totalArvPatientsInactiveAndResumeActive6Months();

        lostArvPatientsResumedTreatmentBykeyPopulations();
        lostArvPatientsResumedCd4Supp200Treatment();
        lostArvPatientsResumedCd4Inf200Treatment();
        lostArvPatientsResumedCd4UnknownTreatment();
        lostArvPatientsSpecimenViralLoadCategories();
        lostArvPatientsResultViralLoadCategories();
        lostArvPatientsResultViralLoadInf1000CopyCategories();

        activeArvPatientsWithViralLoadResult();
        pregnantWomenOnArvWithViralLoadTest();
        breastFeedingWomenOnArvWithViralLoadTest();
        activeArvPatientsWithViralLoadBykeyPopulations();
        activeArvPatientsWithViralLoadResultLessThan1000Copies();
        pregnantWomenOnArvWithViralLoadResultsLessThan100Copies();
        breastFeedingWomenOnArvWithViralLoadResultsLessThan1000Copies();
        activeArvPatientsWithViralLoadLess100CopiesBykeyPopulations();
        activeArvPatientsWithViralLoadResultTargeted();
        pregnantWomenOnArvWithViralLoadTargeted();
        breastFeedingWomenOnArvWithViralLoadTargeted();
        activeArvPatientsWithViralLoadBykeyPopulationsTargeted();
        activeArvPatientsWithViralLoadResultTargetedLess1000Copies();
        pregnantWomenOnArvWithViralLoadTargetedLessThan1000Copies();
        breastFeedingWomenOnArvWithViralLoadTargetedLesstHan1000Copies();
        activeArvPatientsWithViralLoadLessThan1000CopiesBykeyPopulationsTargeted();
        activeArvPatientsFor12Months();
        activeArvPatientsFor12MonthsInSight();
        activeArvPatientsFor12MonthsTransferred();
        activeArvPatientsFor12MonthsAlive();
        womenOnArvScreenedCervicalCancer();
        womenScreenedForCervicalCancerFirstTime();
        womenScreenedForCervicalCancerAfterTestNegative();
        womenScreenedForCervicalCancerAfterTreatment();
        womenScreenedForCervicalCancePostive();
        totalArvPatientsRecomendedPf();
        hivPatientsAcceptingFamilyPlanning();
        hivPatientsUsingFamilyPlanning();
        activeArvPatientsWithAtlestOneClinicalFollowUpVist();
//        activeHivPatientsFullSixMonthsOfInh();

        totalArvPatientsViralLoad();


        patientsVihOnArtDetainedInCare();/////////////
        patientsVihOnArtRequiredTreatmentDuring();/////////////
        patientsArvSpecimenViralLoadTreatmentDuring();
        patientsArvViralLoadTreatmentDuring();
        patientsArvViralLoadTreatmentTakenInf1000Copy();
        totalViralLoadTest();
        arvRetention();

    }

    private static void newlyEnrolledPatientsOnArt() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL,
                PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_MESSAGE,
                PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_UUID);

    }

    private static void referredInPatientsOnArt() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL,
                PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_UUID);
    }

    private static void tbHivPatientsOnAntiTbTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.TB_HIV_PATIENTS_ON_ANTI_TB_TREATMENT_SQL_11,
                PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_UUID);
    }

    private static void inactiveArvPatientsDead() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_SQL_17_1,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_MESSAGE,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_DEAD_UUID);
    }

    private static void inactiveArvPatientsMedicalOrVountary() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_SQL_17_2,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_MESSAGE,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MEDICAL_OR_VOLUNTARY_UUID);
    }


    private static void inactiveArvPatientsLostToForAMonth() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_SQL_17_3,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_MESSAGE,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_LOST_TO_FOR_A_MONTH_UUID);
    }

    private static void inactiveArvPatientsLostToForMigrated() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_SQL_17_3_1,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_MESSAGE,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_MIGRATED_UUID);
    }

    private static void inactiveArvPatientsTransferred() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_SQL_17_4,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_MESSAGE,
                PnlsReportConstants.INACTIVE_ARV_PATIENTS_TRANSFERRED_UUID);
    }

    private static void patientsDuplicated() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_DUPLICATED_SQL_31,
                PnlsReportConstants.PATIENTS_DUPLICATED_MESSAGE,
                PnlsReportConstants.PATIENTS_DUPLICATED_UUID);
    }

    private static void activeHivPatients() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_SQL_13,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_MESSAGE,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_UUID);
    }

    private static void activeHivPatientsTB_ART() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TB_ART_SQL,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TB_ART_MESSAGE,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TB_ART_UUID);
    }

    private static void activeHivPatientsTX_HIV_HTN() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TX_HIV_HTN_SQL,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TX_HIV_HTN_MESSAGE,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_HYPERTENSION_TX_HIV_HTN_UUID);
    }


    private static void lostPatientsDied() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_DIED_SQL_18_1,
                PnlsReportConstants.LOST_ARV_PATIENTS_DIED_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_DIED_UUID);
    }

    private static void lostPatientsAfterLessThan3MonthsTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_SQL_18_2,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_LESS_3MONTHS_UUID);
    }

    private static void lostPatientsAfterMoreThan3MonthsTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_SQL_18_3,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_3MONTHS_UUID);
    }

    private static void lostPatientsAfterMoreThan6MonthsTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_6MONTHS_SQL_32_4,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_6MONTHS_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_AFTER_TREATMENT_MORE_6MONTHS_UUID);
    }

    private static void lostPatientsConfirmedTransferedTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_NEW_INACTIVES_ARV_PATIENTS_CONFIRMED_TRANSFERED_MORE_3MONTHS_SQL_32_5,
                PnlsReportConstants.LOST_NEW_INACTIVES_ARV_PATIENTS_CONFIRMED_TRANSFERED_MORE_3MONTHS_MESSAGE,
                PnlsReportConstants.NEW_INACTIVES_ARV_PATIENTS_CONFIRMED_TRANSFERED_MORE_3MONTHS_UUID);
    }

    private static void lostPatientsStoppedTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.NEW_STOPPED_ARV_PATIENTS_TREATMENT_MORE_3MONTHS_SQL_32_6,
                PnlsReportConstants.NEW_STOPPED_ARV_PATIENTS_TREATMENT_MORE_3MONTHS_MESSAGE,
                PnlsReportConstants.NEW_STOPPED_ARV_PATIENTS_TREATMENT_MORE_3MONTHS_UUID);
    }

    private static void lostPatientsTransferred() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_SQL_18_4,
                PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_TRANSFERRED_UUID);
    }

    private static void lostPatientsStopped() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_SQL_18_5,
                PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_STOPPED_UUID);
    }

    private static void deadArvPatientsByTuberclosis() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_SQL_18_1_1,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_TUBERCLOSIS_UUID);
    }

    private static void deadArvPatientsByOtherInfectiousDiseases() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_SQL_18_1_2,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_OTHER_INFECTIOUS_DISEASES_UUID);
    }

    private static void deadArvPatientsByCancer() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_SQL_18_1_3,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_CANCER_UUID);
    }

    private static void deadArvPatientsByHivIllnesses() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_SQL_18_1_4,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_HIV_ILLNESSES_UUID);
    }

    private static void deadArvPatientsByNaturalCauses() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_SQL_18_1_5,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_NATURAL_CAUSES_UUID);
    }

    private static void deadArvPatientsByUnNaturalCauses() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_SQL_18_1_6,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNNATURAL_CAUSES_UUID);
    }

    private static void deadArvPatientsByUnKnownCauses() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_SQL_18_1_7,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_MESSAGE,
                PnlsReportConstants.DEAD_ARV_PATIENTS_BY_UNKNOWN_CAUSES_UUID);
    }

    private static void lostArvPatientsResumedTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_SQL_19,
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_UUID);
    }

    private static void lostArvPatientsResumedCd4Supp200Treatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_SUPP200_SQL_33_5,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_SUPP200_MESSAGE,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_SUPP200_UUID);
    }

    private static void lostArvPatientsResumedCd4Inf200Treatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_INF200_SQL_33_6,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_INF200_MESSAGE,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_INF200_UUID);
    }

    private static void lostArvPatientsResumedCd4UnknownTreatment() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_UNKNOWN_SQL_33_7,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_UNKNOWN_MESSAGE,
                PnlsReportConstants.LOST_ARV_STOPPED_PATIENTS_RESUMED_TREATMENT_CD4_UNKNOWN_UUID);
    }

    private static void activeArvPatientsWithViralLoadResult() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SQL_20_1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_UUID);
    }

    private static void activeArvPatientsWithViralLoadResultLessThan1000Copies() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL_20_2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID);
    }

    private static void activeArvPatientsWithViralLoadResultTargeted() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL_21_1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID);
    }

    private static void activeArvPatientsWithViralLoadResultTargetedLess1000Copies() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL_21_2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID);
    }

    private static void activeArvPatientsFor12Months() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_SQL_22_1,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_UUID);
    }

    private static void activeArvPatientsFor12MonthsInSight() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_SQL_22_1_1,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_IN_SIGHT_UUID);
    }

    private static void activeArvPatientsFor12MonthsTransferred() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_SQL_22_1_2,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_TRANSFERRED_UUID);
    }


    private static void activeArvPatientsFor12MonthsAlive() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_SQL_22_2,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ARVS_FOR_12_MONTHS_ALIVE_UUID);
    }

    private static void activeArvPatientsWithAtlestOneClinicalFollowUpVist() {

        registerArvActivePatientsWithClinicalFollowUpBy15OverMonthsWithStartAndEndDateParams(

                PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_QUATERLY_SQL_15_1,
                PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_SEMI_ANNUALLY_SQL_15_2,
                PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_ANNUALLY_SQL_15_3,
                PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_MESSAGE,
                PnlsReportConstants.ACTIVE_PATIENTS_WITH_ATLEST_FOLLOW_UP_VIST_UUID
        );
    }

    private static void newlyEnrolledBreastFeedingOnArt() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_SQL_1_1_1,
                PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.NEW_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID
        );
    }
    private static void newlyEnrolledSupp200OnArt() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.NEW_SUPP200_ENROLED_ON_ART_SQL_1_1_1,
                PnlsReportConstants.NEW_SUPP200_ON_ART_MESSAGE,
                PnlsReportConstants.NEW_SUPP200_ENROLED_ON_ART_UUID
        );
    }
    private static void newlyEnrolledInf200OnArt() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.NEW_INF200_ENROLED_ON_ART_SQL_1_1_1,
                PnlsReportConstants.NEW_INF200_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.NEW_INF200_ENROLED_ON_ART_UUID
        );
    }
    private static void newlyEnrolledUnknownOnArt() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.NEW_UNKNOWN_ENROLED_ON_ART_SQL_1_1_1,
                PnlsReportConstants.NEW_UNKNOWN_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.NEW_UNKNOWN_ENROLED_ON_ART_UUID
        );
    }

    private static void newlyRefferedInBreastFeedingOnArt() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_SQL_1_2_1,
                PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.NEW_REFERRED_IN_BREAST_FEEDING_WOMEN_ENROLED_ON_ART_UUID
        );
    }

    private static void BreastFeedingNotYetOnArt() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_SQL_3_1,
                PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_MESSAGE,
                PnlsReportConstants.BREAST_FEEDING_WOMEN_NOT_YET_ENROLED_ON_ART_UUID
        );
    }

    private static void totalPatientsUnderPreventionCtx() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_SQL_4,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID
        );
    }

    private static void totalPatientsUnderPreventionTbTreatment() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_SQL_5,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID
        );
    }


    private static void totalPatientsUnderPreventionTbTreatmentForSixMonths() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_SQL_6,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_FOR_SIX_MONTHS_UUID
        );
    }

    private static void totalPatientsCompletedPreventionTbTreatment() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_SQL_7,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_COMPLETED_PREVENTION_TB_TREATMENT_UUID
        );
    }

    // Ind. 17
    private static void totalPatientsWhoAreTbScrenees() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_SQL_9,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_UUID
        );
    }

    private static void totalPatientsWithBacteriologySpecimentSampleCollection() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_SQL_9_1_1,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_MESSAGE,
                PnlsReportConstants.TOTAL_HIV_PATIENTS_WITH_A_BACTERIOLOGY_SPECIMEN_COLLECTION_UUID
        );
    }

    private static void totalPatientsWithPostiveResultsFromTbDiagnosis() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_SQL_9_1_1_1_1,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_WITH_POSTIVE_RESULT_FROM_TB_DIAGNOSIS_UUID
        );
    }

    private static void totalArvPatientsNewlyPlacedUnderTbTreatment() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_SQL_10,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID
        );
    }

    private static void totalArvPatientsInactiveAtTheEnd() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_SQL_15,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_THE_END_UUID
        );
    }

    private static void totalArvPatientsInactiveAndResumeActive3Months() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AND_RESUME_ACTIVE_3MONTHS_SQL_33_1,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AND_RESUME_ACTIVE_3MONTHS_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AND_RESUME_ACTIVE_3MONTHS_UUID
        );
    }

    private static void totalArvPatientsInactiveAndResumeActive5Months() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_AND_RESUME_ACTIVE_5MONTHS_SQL_33_2,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_AND_RESUME_ACTIVE_5MONTHS_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_AND_RESUME_ACTIVE_5MONTHS_UUID
        );
    }

    private static void totalArvPatientsInactiveAndResumeActive6Months() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_AND_RESUME_ACTIVE_6MONTHS_SQL_33_3,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AT_AND_RESUME_ACTIVE_6MONTHS_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_INACTIVE_AND_RESUME_ACTIVE_6MONTHS_UUID
        );
    }

    private static void totalArvPatientsViralLoad() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_VIRAL_LOAD_SQL_35,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_VIRAL_LOAD_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_VIRAL_LOAD_UUID
        );
    }

    private static void totalArvPatientsLostToFollow() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_SQL_16,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_LOST_TO_FOLLOW_UUID
        );
    }

    private static void totalArvPatientsRecomendedPf() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_SQL,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_PATIENTS_RECOMENDED_PF_UUID
        );
    }

    private static void pregnantWomenOnArvWithViralLoadTest() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_SQL_20_1_1,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_MESSAGE,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID
        );
    }

    private static void breastFeedingWomenOnArvWithViralLoadTest() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_SQL_20_1_2,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_MESSAGE,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_UUID
        );
    }

    private static void totalViralLoadTest() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_VIRAL_LOAD_RESULT_SQL_37,
                PnlsReportConstants.TOTAL_VIRAL_LOAD_RESULT_MESSAGE,
                PnlsReportConstants.TOTAL_VIRAL_LOAD_RESULT_UUID
        );
    }

    private static void pregnantWomenOnArvWithViralLoadResultsLessThan100Copies() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL_20_2_1,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID
        );
    }

    private static void breastFeedingWomenOnArvWithViralLoadResultsLessThan1000Copies() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL_20_2_2,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_UUID
        );
    }

    private static void pregnantWomenOnArvWithViralLoadTargeted() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL_21_1_1,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID
        );
    }

    private static void breastFeedingWomenOnArvWithViralLoadTargeted() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL_21_1_2,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_MESSAGE,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_UUID
        );
    }

    private static void pregnantWomenOnArvWithViralLoadTargetedLessThan1000Copies() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL_21_2_1,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_PREGNANT_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID
        );
    }

    private static void breastFeedingWomenOnArvWithViralLoadTargetedLesstHan1000Copies() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL_21_2_2,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_MESSAGE,
                PnlsReportConstants.ACTIVE_BREAST_FEEDING_WOMEN_ON_ART_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_UUID
        );
    }


    private static void womenOnArvScreenedCervicalCancer() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_SQL_23,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_MESSAGE,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_UUID
        );
    }

    private static void notEnrolledOnArtByReason() {
        patientsNotEnrolledOnArtByReason(
                PnlsReportConstants.NOT_ENROLED_ON_ART_COHORT_INDICATOR_SQL_3,
                PnlsReportConstants.NOT_YET_ENROLED_ON_ART_BY_REASON_MESSAGE,
                PnlsReportConstants.NOT_YET_ENROLED_ON_ART_BY_REASON_UUID
        );
    }

    private static void patientsBykeyPopulations() {
        registerKeyPopulationReportWithStartAndEndDateParams(
                PnlsReportConstants.NEWLY_ENROLLED_PATIENTS_ON_ART_SQL,
                PnlsReportConstants.REFERRED_IN_PATIENTS_ENROLED_ON_ART_SQL,
                PnlsReportConstants.DISINTERGRATION_BY_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.DISINTERGRATION_BY_KEY_POPULATION_UUID
        );

    }

    private static void activeArvPatientsBykeyPopulations() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_SQL_13,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_BY_KEY_POPN_MESSAGE,
                PnlsReportConstants.ACTIVE_HIV_PATIENTS_BY_KEY_POPN_UUID
        );

    }

    private static void lostArvPatientsResumedTreatmentBykeyPopulations() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_SQL_19,
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.LOST_ARV_PATIENTS_RESUMED_TREATMENT_KEY_POPULATION_UUID
        );

    }

    private static void lostArvPatientsSpecimenViralLoadCategories() {
        registerArvSpecimenViralLoadCategoriesReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ARV_SPECIMEN_VIRAL_LOAD_DURING_SQL,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_SPECIMEN_VIRAL_LOAD_CATEGORIES_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_SPECIMEN_VIRAL_LOAD_CATEGORIES_UUID
        );
    }

    private static void lostArvPatientsResultViralLoadCategories() {
        registerArvSpecimenViralLoadCategoriesReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_SQL_35_1_1,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_UUID
        );
    }

    private static void lostArvPatientsResultViralLoadInf1000CopyCategories() {
        registerArvSpecimenViralLoadCategoriesReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_INF1000COPY_SQL_35_2_1,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_INF1000COPY_MESSAGE,
                PnlsReportConstants.TOTAL_ARV_ACTIVES_PATIENTS_RESULT_VIRAL_LOAD_CATEGORIES_INF1000COPY_UUID
        );
    }

    private static void activeArvPatientsWithViralLoadBykeyPopulations() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SQL_20_1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_KEY_POPULATION_UUID
        );
    }

    //Ind 18
    private static void activeArvPatientsWithViralLoadByScreeneTb() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_SQL_9,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_UUID
        );
    }

    private static void activeArvPatientsWithViralLoadByScreeneTbCXRXRAY() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_TOTAL_SQL,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_TOTAL_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_TOTAL_UUID
        );
    }

    private static void activeArvPatientsWithViralLoadByScreeneTbMWRD() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_TOTAL_SQL,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_TOTAL_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_TOTAL_UUID
        );
    }



//    private static void totalPatientsWhoAreTbScrenees() {
//        registerPnlsSingleColumnReportWithStartAndEndDateParams(
//                ComorbidityReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_SQL_9,
//                ComorbidityReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_MESSAGE,
//                ComorbidityReportConstants.TOTAL_HIV_PATIENTS_TB_SCRENEES_UUID
//        );
//    }

    private static void activeArvPatientsWithViralLoadLess100CopiesBykeyPopulations() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_SQL_20_2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_BY_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_LESS_THAN_1000_COPIES_BY_KEY_POPULATION_UUID
        );
    }

    private static void arvRetention() {
        registerPnlsSingleColumnReportWithStartAndEndDateParams(
                PnlsReportConstants.ARV_RETENTION_SQL_38,
                PnlsReportConstants.ARV_RETENTION_MESSAGE,
                PnlsReportConstants.ARV_RETENTION_UUID
        );
    }

    private static void activeArvPatientsWithViralLoadBykeyPopulationsTargeted() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_SQL_21_1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_BY_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_BY_KEY_POPULATION_UUID
        );

    }

    private static void activeArvPatientsWithViralLoadLessThan1000CopiesBykeyPopulationsTargeted() {
        registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_SQL_21_2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_KEY_POPULATION_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_TARGETED_LESS_THAN_1000_COPIES_KEY_POPULATION_UUID
        );

    }

    private static void patientsUnderCtx() {
        registerPatientsUnderCtxWithStartAndEndDateParams(
                PnlsReportConstants.NEWLY_POWERED_CTX_COHORT_INDICATOR_SQL_4_1,
                PnlsReportConstants.ACTIVE_CTX_COHORT_INDICATOR_SQL_4_2,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_CTX_MESSAGE,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_CTX_UUID
        );
    }

    private static void patientsEnrolledOnArtUnderTbTreatMent() {
        registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_TREATMENT_SQL_5_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_TREATMENT_SQL_5_2,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_UUID
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void patientsEnrolledOnArtINHTreatMentDuring() {
        registerArvPatientsByAge2TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_PREVENTION_INH_TREATMENT_DURING_SQL_26_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_PREVENTION_INH_TREATMENT_DURING_SQL_26_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_INH_TREATMENT_DURING_MESSAGE,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_INH_TREATMENT_DURING_UUID
        );
    }

    private static void patientsEnrolledOnArINHTreatMentSixMonth() {
        registerArvPatientsByAge2TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_INH_TREATMENT_6M_SQL_27_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_INH_TREATMENT_6M_SQL_27_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_INH_TREATMENT_MESSAGE_6M,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_INH_TREATMENT_6M_UUID
        );
    }

    private static void patientsEnrolledOnArtUnderTBTreatMentDuring() {
        registerArvPatientsByAge2TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_TREATMENT_DURING_SQL_28_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_TREATMENT_DURING_SQL_28_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_INH_TREATMENT_MESSAGE_DURING,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_TB_TREATMENT_DURING_UUID
        );
    }

    private static void patientsVihOnArtDetainedInCare() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_DETAINED_IN_CARE_SQL,
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_DETAINED_IN_CARE_MESSAGE,
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_DETAINED_IN_CARE_UUID
        );
    }

    private static void patientsVihOnArtRequiredTreatmentDuring() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_REQUIRED_TREATMENT_DURING_SQL,
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_REQUIRED_TREATMENT_DURING_MESSAGE,
                PnlsReportConstants.PATIENTS_VIH_ACTIF_ON_ART_REQUIRED_TREATMENT_DURING_UUID
        );
    }

    private static void patientsArvSpecimenViralLoadTreatmentDuring() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(
                PnlsReportConstants.PATIENTS_ARV_SPECIMEN_VIRAL_LOAD_DURING_SQL,
                PnlsReportConstants.PATIENTS_ARV_SPECIMEN_VIRAL_LOAD_DURING_MESSAGE,
                PnlsReportConstants.PATIENTS_ARV_SPECIMEN_VIRAL_LOAD_DURING_UUID
        );
    }

    private static void patientsArvViralLoadTreatmentDuring() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_SQL,
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_MESSAGE,
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_UUID
        );
    }

    private static void patientsArvViralLoadTreatmentTakenInf1000Copy() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_TAKEN_INF1000COPY_SQL,
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_TAKEN_INF1000COPY_MESSAGE,
                PnlsReportConstants.PATIENTS_ARV_VIRAL_LOAD_DURING_TAKEN_INF1000COPY_UUID
        );
    }

    private static void patientsEnrolledOnArtUnderRIFAINHTreatMentDuring() {
        registerArvPatientsByAge14TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_DURING_SQL_29_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_DURING_SQL_29_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_MESSAGE_DURING,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_DURING_UUID
        );
    }

    private static void patientsEnrolledOnArtUnderRIFAINHTreatMentOneMonth() {
        registerArvPatientsByAgeSupp15TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_1M_SQL_29_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_1M_SQL_29_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_MESSAGE_1M,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_1M_UUID
        );
    }

    /// 13
    private static void patientsEnrolledOnArtUnderRIFAINHTreatMentThreeMonth() {
        registerArvPatientsByAge14TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_3M_SQL_29_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_RIFA_AND_INH_TREATMENT_3M_SQL_29_2,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_MESSAGE_3M,
                PnlsReportConstants.HIV_PATIENTS_PREVENTION_RIFA_AND_INH_TREATMENT_3M_UUID
        );
    }

    /// 14
    private static void patientsEnrolledCompleteOnArtUnderRIFAINHTreatMentThreeMonth() {
        registerArvPatientsByAge14TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_COMPLETE_WITH_RIFA_AND_INH_TREATMENT_3M_SQL_29_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_COMPLETE_WITH_RIFA_AND_INH_TREATMENT_3M_SQL_29_2,
                PnlsReportConstants.HIV_PATIENTS_PROPHY_RIFA_AND_INH_TREATMENT_MESSAGE_3M,
                PnlsReportConstants.HIV_PATIENTS_PROPHY_RIFA_AND_INH_TREATMENT_3M_UUID
        );
    }

    private static void patientsEnrolledOnArtUnderRIFAINHCompleteTreatMentDuring() {
        registerArvPatientsByAgeSupp15TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_COMPLETE_WITH_RIFA_AND_INH_TREATMENT_DURING_SQL_29_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_COMPLETE_WITH_RIFA_AND_INH_TREATMENT_DURING_SQL_29_2,
                PnlsReportConstants.HIV_PATIENTS_COMPLETE_RIFA_AND_INH_TREATMENT_MESSAGE_DURING,
                PnlsReportConstants.HIV_PATIENTS_COMPLETE_RIFA_AND_INH_TREATMENT_DURING_UUID
        );
    }

//    private static void patientsEnrolledOnArtUnderTBPOSITIFTreatMentDuring() {
//        registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
//                ComorbidityReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_AND_POSITIF_TREATMENT_DURING_SQL_29_1,
//                ComorbidityReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_AND_POSITIF_TREATMENT_DURING_SQL_29_2,
//                ComorbidityReportConstants.HIV_PATIENTS_PREVENTION_TB_AND_POSITIF_TREATMENT_MESSAGE_DURING,
//                ComorbidityReportConstants.HIV_PATIENTS_PREVENTION_TB_AND_POSITIF_TREATMENT_DURING_UUID
//        );
//    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void patientsEnrolledOnArtUnderTbTreatMentFor6months() {
        registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_STARTED_WITH_TB_TREATMENT_FOR_SIX_MONTHS_SQL_6_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_STARTED_WITH_TB_TREATMENT_FOR_SIX_MONTHS_SQL_6_2,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_6MONTHS_MESSAGE,
                PnlsReportConstants.HIV_PATIENTS_UNDER_PREVENTION_TB_TREATMENT_6MONTHS_UUID
        );
    }


    private static void patientsEnrolledOnArtCompletedTbTreatMentFor6months() {
        registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_COMPLETED_TB_TREATMENT_SQL_7_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_COMPLETED_TB_TREATMENT_SQL_7_2,
                PnlsReportConstants.PATIENTS_WHO_COMPLETED_TB_TREATMENT__MESSAGE,
                PnlsReportConstants.PATIENTS_WHO_COMPLETED_TB_TREATMENT_UUID
        );
    }

    private static void patientsEnrolledOnArtActiveCompletedTbTreatMentFor6months() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_ACTIVE_COMPLETED_TB_TREATMENT_SQL_19_1,
                PnlsReportConstants.PATIENTS_WHO_ACTIVE_COMPLETED_TB_TREATMENT__MESSAGE,
                PnlsReportConstants.PATIENTS_WHO_ACTIVE_COMPLETED_TB_TREATMENT_UUID
        );
    }

    private static void patientsEnrolledOnArtActiveCompletedTbScreeneTreatMentFor6months() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEW_PATIENTS_ON_ART_ACTIVE_COMPLETED_TB_SCREENE_TREATMENT_SQL,
                PnlsReportConstants.PATIENTS_WHO_ACTIVE_COMPLETED_SCREENE_TB_TREATMENT__MESSAGE,
                PnlsReportConstants.PATIENTS_WHO_ACTIVE_COMPLETED_TB_SCREENE_TREATMENT_UUID
        );
    }

    private static void patientsEnrolledOnArtVihAntiTbTreatMent() {
        registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(
                PnlsReportConstants.COHORT_ARV_VIH_ANTI_TB_TREATMENT_SQL,
                PnlsReportConstants.PATIENTS_ARV_VIH_ANTI_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.PATIENTS_ARV_VIH_ANTI_TB_TREATMENT_UUID
        );
    }

    private static void patientsEnrolledOnArtTbPostiveScrenneesFor6months() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEWLY_ENROLLED_PATIENTS_ON_ART_SCRENEES_POSTIVE_SQL_9_1,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_SCRENEES_POSTIVE_SQL_9_1,
                PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_POSTIVE_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_POSTIVE_UUID
        );
    }

    private static void patientsEnrolledOnArtTbPostiveScrenneesFor6monthsCXRXRAYPos() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_POS_SQL1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_POS_SQL2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_POS_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_POS_UUID
        );
    }

    private static void patientsEnrolledOnArtTbPostiveScrenneesFor6monthsMWRDPos() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_POS_SQL1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_POS_SQL2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_POS_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_POS_UUID
        );
    }

    private static void patientsEnrolledOnArtTbPostiveScrenneesFor6monthsMWRDNeg() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_NEG_SQL1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_NEG_SQL2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_NEG_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_MWRD_NEG_UUID
        );
    }




    private static void patientsEnrolledOnArtTbPostiveScrenneesFor6monthsCXRXRAYNeg() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_NEG_SQL1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_NEG_SQL2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_NEG_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_WITH_VIRAL_LOAD_RESULT_SCREENE_TB_CXRXRAY_NEG_UUID
        );
    }



    private static void patientsEnrolledOnArtTbNegativeScrenneesFor6months() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.COHORT_NEWLY_ENROLLED_PATIENTS_ON_ART_SCRENEES_NEGATIVE_SQL_9_2,
                PnlsReportConstants.COHORT_PATIENTS_ALREADY_ON_ART_SCRENEES_NEGATIVE_SQL_9_2,
                PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_NEGATIVE_MESSAGE,
                PnlsReportConstants.PATIENTS_ON_ART_SCRENEES_NEGATIVE_UUID
        );
    }

    private static void patientsEnrolledOnArtAndTbTreatment() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.NEWLYLY_ENROLLED_ARV_PATIENTS_AND_NEWLY_PLACED_ON_TB_TREATMENT_SQL_10,
                PnlsReportConstants.PATIENTS_ALREADY_ON_ARVS_AND_ALREADY_PLACED_ON_TB_TREATMENT_SQL_10,
                PnlsReportConstants.ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.ARV_PATIENTS_NEWLY_PLACED_ON_TB_TREATMENT_UUID
        );
    }

    private static void patientsEnrolledOnArtVihAndTbTreatment() {
        registerPatientsUnderScreeneWithStartAndEndDateParams(
                PnlsReportConstants.NEWLYLY_ENROLLED_ARV_AND_VIH_PATIENTS_TB_TREATMENT_SQL_24,
                PnlsReportConstants.PATIENTS_ALREADY_ON_ARVS_AND_VIH_TB_TREATMENT_SQL_24,
                PnlsReportConstants.ARV_AND_VIH_PATIENTS_TB_TREATMENT_MESSAGE,
                PnlsReportConstants.ARV_AND_VIH_PATIENTS_TB_TREATMENT_UUID
        );
    }

    private static void ArvPatientsWithSamplesSentForTbDiagnosisTest() {
        registerArvPatientsWithDiagnosisTestsWithStartAndEndDateParams(
                PnlsReportConstants.CRACHAT_MTB_DIAGNOSIS_TEST_SQL_9_1_1_1,
                PnlsReportConstants.GENEEXPERT_MTB_DIAGNOSIS_TEST_SQL_9_1_1_1,
                PnlsReportConstants.OTHER_DIAGNOSIS_TEST_SQL_9_1_1_1,
                PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_MESSAGE,
                PnlsReportConstants.ARV_PATIENTS_WITH_SAMPLES_SENT_TO_DIAGNOSTIC_TEST_UUID
        );
    }

    private static void tbHivPatientsOnTbTreamentDintergratedByNelyAndActivelyEnrolledOnArt() {
        registerPnlsReportWith15By4ColumnsAndWithStartAndEndDateParams(
                PnlsReportConstants.TB_HIV_PATIENTS_NEWLY_ENROLLED_ON_ART_ON_ANTI_TB_TREATMENT_SQL_12,
                PnlsReportConstants.TB_HIV_PATIENTS_ALREADY_ON_ART_ON_ANTI_TB_TREATMENT_SQL_12,
                PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_DISTENGRATED_BY_NEW_AND_ALREADY_ON_ART_MESSAGE,
                PnlsReportConstants.TB_HIV_PATIENTS_ON_TB_TREATMENT_DISTENGRATED_BY_NEW_AND_ALREADY_ON_ART_UUID
        );
    }

    private static void activeArvPatientsOverMonths() {
        registerArvActivePatientsBy15OverMonthsWithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_LESS_3MONTHS_SQL_14_1,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_BTN_3_5MONTHS_SQL_14_2,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_5MONTHS_SQL_14_3,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_MONTHS_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_OVER_MONTHS_UUID
        );
    }


    private static void activeArvPatientsByRegimeLines() {
        registerActivePatientsWithRegimeLinesStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_ARV_FIRST_LINE_REGIMEN_SQL_16_1,
                PnlsReportConstants.ACTIVE_ARV_SECOND_LINE_REGIMEN_SQL_16_2,
                PnlsReportConstants.ACTIVE_ARV_THIRD_LINE_REGIMEN_SQL_16_3,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_REGIME_LINES_MESSAGE,
                PnlsReportConstants.ACTIVE_ARV_PATIENTS_REGIME_LINES_UUID
        );
    }

//    private static void activeArvPatientsByScreeneTbLines() {
//        registerActivePatientsWithScreeneTbLinesStartAndEndDateParams(
//                ComorbidityReportConstants.ACTIVE_ARV_FIRST_LINE_SCREENE_TB_SYMPTOME_SQL_18_1,
//                ComorbidityReportConstants.ACTIVE_ARV_SECOND_LINE_SCREENE_TB_RADIOGRAPHIE_THORAX_SQL_18_2,
//                ComorbidityReportConstants.ACTIVE_ARV_THIRD_LINE_SCREENE_TB_PCR_MOLECULAIRE_SQL_18_3,
//                ComorbidityReportConstants.ACTIVE_ARV_PATIENTS_SCREENE_TB_LINES_MESSAGE,
//                ComorbidityReportConstants.ACTIVE_ARV_PATIENTS_SCREENE_TB_LINES_UUID
//        );
//    }

    private static void womenScreenedForCervicalCancerFirstTime() {
        registerWomenWithCervicalCancerStatusStartAndEndDateParams(
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_SQL_23_1,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_MESSAGE,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_FIRST_TIME_UUID);

    }

    private static void womenScreenedForCervicalCancerAfterTestNegative() {
        registerWomenWithCervicalCancerStatusStartAndEndDateParams(
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_SQL_23_2,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_MESSAGE,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_FIRST_NEGATIVE_UUID);

    }

    private static void womenScreenedForCervicalCancerAfterTreatment() {
        registerWomenWithCervicalCancerStatusStartAndEndDateParams(
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_SQL_23_3,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_MESSAGE,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_AFTER_TREATMENT_UUID);

    }

    private static void womenScreenedForCervicalCancePostive() {
        registerWomenWithCervicalCancerByTreatmentStartAndEndDateParams(
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_CRYO_SQL_24,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_THERMO_SQL_24,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_RECIEVED_LEEP_SQL_24,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_TESTED_POSTIVE_MESSAGE,
                PnlsReportConstants.WOMEN_ON_ARVS_SCREENED_FOR_CERVICAL_CANCER_TESTED_POSTIVE_UUID);

    }

    private static void hivPatientsAcceptingFamilyPlanning() {
        registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_PILLS_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_INJECT_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_IMPLANTS_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_VAGTABS_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_LAM_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_NECKLACE_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_CONDOM_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_CCV_SQL,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_MESSAGE,
                PnlsReportConstants.PATIENTS_ACCEPTING_FAMILY_PLANNING_UUID);

    }

    private static void hivPatientsUsingFamilyPlanning() {
        registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_PILLS_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_INJECT_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_IMPLANTS_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_VAGTABS_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_LAM_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_NECKLACE_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_CONDOM_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_CCV_SQL,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_MESSAGE,
                PnlsReportConstants.PATIENTS_USING_FAMILY_PLANNING_UUID);

    }


    private static void activeHivPatientsFullSixMonthsOfInh() {
        registerArvActivePatientsBy15WithStartAndEndDateParams(
                PnlsReportConstants.ACTIVE_PATIENTS_6_MONTHS_INH_SQL_8,
                PnlsReportConstants.ACTIVE_PATIENTS_6_MONTHS_INH_MESSAGE,
                PnlsReportConstants.ACTIVE_PATIENTS_6_MONTHS_INH_UUID
        );
    }

    private static void registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());

        pnlsReportUtils.addAgeAndGenderColumns(dsd, CohortIndicator, "M");
        pnlsReportUtils.addAgeAndGenderColumns(dsd, CohortIndicator, "F");
        pnlsReportUtils.addTotalColumns(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_17BY3, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerPnlsReportWithAgeAndGenderColumnsAndStartAndEndDateParams1(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone1(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());

        pnlsReportUtils.addAgeAndGenderColumns1(dsd, CohortIndicator, "M");
        pnlsReportUtils.addAgeAndGenderColumns1(dsd, CohortIndicator, "F");
        pnlsReportUtils.addTotalColumns1(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_17BY3, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerPnlsSingleColumnReportWithStartAndEndDateParams(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        dsd.addColumn("BF", "Single Column", Mapped.mapStraightThrough(CohortIndicator), "");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_1BY1, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    private static void patientsNotEnrolledOnArtByReason(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("enrolRsn", ReportUtils.map(new CommonDimension().notEnrollReason(), "endDate=${endDate}"));
        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "A", "DIED");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "A", "DIED");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "B", "VOL");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "B", "VOL");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "C", "DENIAL");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "C", "DENIAL");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "D", "MED");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "D", "MED");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "E", "REF");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "E", "REF");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "M", "F", "OTHER");
        pnlsReportUtils.addAgeAndGenderAndNotEnrollOnArtReasonColumns(dsd, CohortIndicator, "F", "F", "OTHER");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY17, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    private static void registerKeyPopulationReportWithStartAndEndDateParams(String sqlNew, String sqlReferenced, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("keyPopn", ReportUtils.map(new CommonDimension().keyPopulationDimension(), ""));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(sqlNew, "name", getParameters());
        CohortIndicator CohortIndicatorReferenced = pnlsReportUtils.cohortIndicatorFromSqlResource(sqlReferenced, "name", getParameters());

        pnlsReportUtils.addKeyPopulationColums(dsd, CohortIndicatorNewlyEnrolled, "N");
        pnlsReportUtils.addKeyPopulationColums(dsd, CohortIndicatorReferenced, "R");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_6BY3, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerKeyPopulationReportWithSingleRowAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("keyPopn", ReportUtils.map(new CommonDimension().keyPopulationDimension(), ""));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        pnlsReportUtils.addKeyPopulationSingleRowColums(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_6BY2, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerArvSpecimenViralLoadCategoriesReportWithSingleRowAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("specimen", ReportUtils.map(new CommonDimension().keySpecimenDimension(), ""));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        pnlsReportUtils.addKeySpecimenSingleRowColums(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_8BY2, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerScreeneTbReportWithSingleRowAndStartAndEndDateParams(String sql, String messageProperties, String uuid) {
        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("scrnTb", ReportUtils.map(new CommonDimension().keyScreeneTbDimension(), ""));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());
        pnlsReportUtils.addKeyScreeneTbSingleRowColums(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_4BY2, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerPatientsUnderCtxWithStartAndEndDateParams(String newlyPoweredCtxSql, String activeOnCtxSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(newlyPoweredCtxSql, "name", getParameters());
        CohortIndicator CohortIndicatorActiveCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOnCtxSql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyCtx, "M", "N");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyCtx, "F", "N");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorNewlyCtx, "N");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveCtx, "M", "A");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveCtx, "F", "A");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorActiveCtx, "A");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY6, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }
    private static void registerPatientsUnderScreeneWithStartAndEndDateParams(String newlyPoweredCtxSql, String activeOnCtxSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(newlyPoweredCtxSql, "name", getParameters());
        CohortIndicator CohortIndicatorActiveCtx = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOnCtxSql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyCtx, "M", "N");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyCtx, "F", "N");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorNewlyCtx, "N");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveCtx, "M", "A");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveCtx, "F", "A");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorActiveCtx, "A");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY6_II, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerActivePatientsWithRegimeLinesStartAndEndDateParams(String regime1Sql, String regime2Sql, String regime3Sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorRegime1 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime1Sql, "name", getParameters());
        CohortIndicator CohortIndicatorRegime2 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime2Sql, "name", getParameters());
        CohortIndicator CohortIndicatorRegime3 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime3Sql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime1, "M", "1");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime1, "F", "1");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime1, "1");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime2, "M", "2");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime2, "F", "2");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime2, "2");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime3, "M", "3");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime3, "F", "3");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime3, "3");


        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY9, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerActivePatientsWithScreeneTbLinesStartAndEndDateParams(String regime1Sql, String regime2Sql, String regime3Sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorRegime1 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime1Sql, "name", getParameters());
        CohortIndicator CohortIndicatorRegime2 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime2Sql, "name", getParameters());
        CohortIndicator CohortIndicatorRegime3 = pnlsReportUtils.cohortIndicatorFromSqlResource(regime3Sql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime1, "M", "1");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime1, "F", "1");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime1, "1");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime2, "M", "2");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime2, "F", "2");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime2, "2");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime3, "M", "3");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorRegime3, "F", "3");
        pnlsReportUtils.addMultipleTotalColumns(dsd, CohortIndicatorRegime3, "3");


        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY9_II, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerPnlsReportWith15By4ColumnsAndWithStartAndEndDateParams(String newlyEnrolledSql, String activeOnArvSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(newlyEnrolledSql, "name", getParameters());
        CohortIndicator CohortIndicatorActiveOnArv = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOnArvSql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyEnrolled, "M", "N");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorNewlyEnrolled, "F", "N");

        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveOnArv, "M", "A");
        pnlsReportUtils.addMultipleAgeAndGenderColumns(dsd, CohortIndicatorActiveOnArv, "F", "A");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_14BY4, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    private static void registerArvPatientsByAge15TreatmentWithStartAndEndDateParams(String NewlyEnrolledSql, String AlreadyOnArtSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(NewlyEnrolledSql, "name", getParameters());
        CohortIndicator CohortIndicatorAlreadyOnArt = pnlsReportUtils.cohortIndicatorFromSqlResource(AlreadyOnArtSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorNewlyEnrolled, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorNewlyEnrolled, "F", "A");

        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorAlreadyOnArt, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, CohortIndicatorAlreadyOnArt, "F", "B");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_4BY5, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void registerArvPatientsByAge14TreatmentWithStartAndEndDateParams(String NewlyEnrolledSql, String AlreadyOnArtSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy14(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(NewlyEnrolledSql, "name", getParameters());
        CohortIndicator CohortIndicatorAlreadyOnArt = pnlsReportUtils.cohortIndicatorFromSqlResource(AlreadyOnArtSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorNewlyEnrolled, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorNewlyEnrolled, "F", "A");

        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorAlreadyOnArt, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorAlreadyOnArt, "F", "B");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_2BY14, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void registerArvPatientsByAgeSupp15TreatmentWithStartAndEndDateParams(String NewlyEnrolledSql, String AlreadyOnArtSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageSuppBy15(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(NewlyEnrolledSql, "name", getParameters());
        CohortIndicator CohortIndicatorAlreadyOnArt = pnlsReportUtils.cohortIndicatorFromSqlResource(AlreadyOnArtSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorNewlyEnrolled, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorNewlyEnrolled, "F", "A");

        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorAlreadyOnArt, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy14Colums(dsd, CohortIndicatorAlreadyOnArt, "F", "B");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_BY15, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void registerArvPatientsByAge2TreatmentWithStartAndEndDateParams(String NewlyEnrolledSql, String AlreadyOnArtSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy2(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorNewlyEnrolled = pnlsReportUtils.cohortIndicatorFromSqlResource(NewlyEnrolledSql, "name", getParameters());
        CohortIndicator CohortIndicatorAlreadyOnArt = pnlsReportUtils.cohortIndicatorFromSqlResource(AlreadyOnArtSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy2Colums(dsd, CohortIndicatorNewlyEnrolled, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy2Colums(dsd, CohortIndicatorNewlyEnrolled, "F", "A");

        pnlsReportUtils.addGenderAndAgeBy2Colums(dsd, CohortIndicatorAlreadyOnArt, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy2Colums(dsd, CohortIndicatorAlreadyOnArt, "F", "B");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_3BY3, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerArvActivePatientsBy15OverMonthsWithStartAndEndDateParams(String activeLess3MonthsSql, String activeBtn3_5MonthsSql, String activeOver5MonthsSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));

        CohortIndicator activeLess3Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeLess3MonthsSql, "name", getParameters());
        CohortIndicator activeBtn3_5Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeBtn3_5MonthsSql, "name", getParameters());
        CohortIndicator activeOver5Months = pnlsReportUtils.cohortIndicatorFromSqlResource(activeOver5MonthsSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeLess3Months, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeLess3Months, "F", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeBtn3_5Months, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeBtn3_5Months, "F", "B");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeOver5Months, "M", "C");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, activeOver5Months, "F", "C");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_4BY7, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerArvActivePatientsWithClinicalFollowUpBy15OverMonthsWithStartAndEndDateParams(String qauterlySql, String semiAnnualySql, String annualySql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));

        CohortIndicator quaterly = pnlsReportUtils.cohortIndicatorFromSqlResource(qauterlySql, "name", getParameters());
        CohortIndicator semiAnnually = pnlsReportUtils.cohortIndicatorFromSqlResource(semiAnnualySql, "name", getParameters());
        CohortIndicator annualy = pnlsReportUtils.cohortIndicatorFromSqlResource(annualySql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, quaterly, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, quaterly, "F", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, semiAnnually, "M", "B");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, semiAnnually, "F", "B");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, annualy, "M", "C");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, annualy, "F", "C");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_4BY7_II, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    private static void registerArvActivePatientsBy15WithStartAndEndDateParams(String corhotSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZoneBy15(), "effectiveDate=${endDate}"));

        CohortIndicator corhot = pnlsReportUtils.cohortIndicatorFromSqlResource(corhotSql, "name", getParameters());

        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, corhot, "M", "A");
        pnlsReportUtils.addGenderAndAgeBy15Colums(dsd, corhot, "F", "A");
        pnlsReportUtils.addTotalGenderAndAgeBy15Colums(dsd, corhot, "T");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_4BY3, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }


    private static void registerArvPatientsWithDiagnosisTestsWithStartAndEndDateParams(String crachatSql, String genexpertMtbSql, String otherTbDiagnosisTestsSql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        CohortIndicator CrachatTest = pnlsReportUtils.cohortIndicatorFromSqlResource(crachatSql, "name", getParameters());
        CohortIndicator CohortIndicatorGeneExpertTest = pnlsReportUtils.cohortIndicatorFromSqlResource(genexpertMtbSql, "name", getParameters());
        CohortIndicator CohortIndicatorOtherTest = pnlsReportUtils.cohortIndicatorFromSqlResource(otherTbDiagnosisTestsSql, "name", getParameters());

        dsd.addColumn("CT", "GeneExpert MTB Diagnosis Tests", Mapped.mapStraightThrough(CrachatTest), "");
        dsd.addColumn("GE", "GeneExpert MTB Diagnosis Tests", Mapped.mapStraightThrough(CohortIndicatorGeneExpertTest), "");
        dsd.addColumn("OT", "Other Diagnosis Tests", Mapped.mapStraightThrough(CohortIndicatorOtherTest), "");

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_3BY1, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerWomenWithCervicalCancerStatusStartAndEndDateParams(String sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("cervStat", ReportUtils.map(new CommonDimension().CervicalCancerStatusDimension(), DATE_PARAMS));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicator = pnlsReportUtils.cohortIndicatorFromSqlResource(sql, "name", getParameters());

        pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "NEG");
        pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "POS");
        pnlsReportUtils.addCervicalCancerStatusColumns(dsd, CohortIndicator, "SUS");
        pnlsReportUtils.addCervicalTotalCancerStatusColumns(dsd, CohortIndicator);

        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY4, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerWomenWithCervicalCancerByTreatmentStartAndEndDateParams(String treatment1sql, String treatment2sql, String treatment3sql, String messageProperties, String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicator1 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment1sql, "name", getParameters());
        CohortIndicator CohortIndicator2 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment2sql, "name", getParameters());
        CohortIndicator CohortIndicator3 = pnlsReportUtils.cohortIndicatorFromSqlResource(treatment3sql, "name", getParameters());

        pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator1, "1");
        pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator2, "2");
        pnlsReportUtils.addCervicalCancerbyAgeColumns(dsd, CohortIndicator3, "3");


        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY4_II, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    private static void registerReportByFamilyPlaningMethodAndWithStartAndEndDateParams(
            String pillssSql,
            String injectSql,
            String implantSql,
            String vagTabsSql,
            String lamSql,
            String necklaceSql,
            String condomSql,
            String ccvSql,
            String messageProperties,
            String uuid) {

        CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
        dsd.setName(messageProperties);
        dsd.addParameter(START_DATE);
        dsd.addParameter(END_DATE);

        dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
        dsd.addDimension("age", ReportUtils.map(new CommonDimension().ageZone(), "effectiveDate=${endDate}"));

        CohortIndicator CohortIndicatorPills = pnlsReportUtils.cohortIndicatorFromSqlResource(pillssSql, "name", getParameters());
        CohortIndicator CohortIndicatorInject = pnlsReportUtils.cohortIndicatorFromSqlResource(injectSql, "name", getParameters());
        CohortIndicator CohortIndicatorImplant = pnlsReportUtils.cohortIndicatorFromSqlResource(implantSql, "name", getParameters());
        CohortIndicator CohortIndicatorVagtabs = pnlsReportUtils.cohortIndicatorFromSqlResource(vagTabsSql, "name", getParameters());
        CohortIndicator CohortIndicatorLam = pnlsReportUtils.cohortIndicatorFromSqlResource(lamSql, "name", getParameters());
        CohortIndicator CohortIndicatorNecklace = pnlsReportUtils.cohortIndicatorFromSqlResource(necklaceSql, "name", getParameters());
        CohortIndicator CohortIndicatorCondom = pnlsReportUtils.cohortIndicatorFromSqlResource(condomSql, "name", getParameters());
        CohortIndicator CohortIndicatorCcv = pnlsReportUtils.cohortIndicatorFromSqlResource(ccvSql, "name", getParameters());

        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorPills, "F", "A");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorInject, "F", "B");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorImplant, "F", "C");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorVagtabs, "F", "D");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorLam, "F", "E");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorNecklace, "F", "F");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorCondom, "M", "G");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorCondom, "F", "H");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorCcv, "M", "I");
        pnlsReportUtils.addMultipleAgeAndGenderColumnsStaringat15(dsd, CohortIndicatorCcv, "F", "J");


        Context.getService(DataSetDefinitionService.class).saveDefinition(dsd);
        Map<String, Object> mappings = new HashMap<String, Object>();
        mappings.put("startDate", "${startDate}");
        mappings.put("endDate", "${endDate}");
        ReportDefinition repDefinition = reportDefinition(messageProperties, PnlsReportConstants.REPORT_DESCRIPTION_10BY11, uuid);
        repDefinition.addParameter(START_DATE);
        repDefinition.addParameter(END_DATE);
        repDefinition.addDataSetDefinition(dsd, mappings);
        Context.getService(SerializedDefinitionService.class).saveDefinition(repDefinition);
    }

    public static List<Parameter> getParameters() {
        return Arrays.asList(new Parameter("startDate", "Start Date", Date.class),
                new Parameter("endDate", "End Date", Date.class));
    }

    private static final String DATE_PARAMS = "startDate=${startDate},endDate=${endDate}";

}
