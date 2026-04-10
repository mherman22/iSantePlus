package org.openmrs.module.isanteplusreports.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.context.SessionContext;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.library.indicator.PrenatalReportsIndicatorLibrary;
import org.openmrs.module.isanteplusreports.reporting.reports.MalariaIndicatorReport;
import org.openmrs.module.isanteplusreports.reporting.reports.ObGynIndicatorReport;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.ReportRequest.Status;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.service.ReportService;

public class RegisterAllOtherReports extends SessionContext {

    private static Log log = LogFactory.getLog(RegisterAllOtherReports.class);

    public static void registerOtherReports() {
        cleanTables();
        cleanReportsRequest();
//        nextVisitSevenDays();
//        nextVisitFourteenDays();
        patientAgeGroup();
        firstVisitAge();
        patientsCrachatAnormalWithoutTbDiagnostic();
        patientsWithTbDiagnosticsWithoutTreatment();
        patientsWithCompletedTbTreatment();
        patientsWithTbSymptomsignWithoutCrachat();
        consultationByDay();
        numberVisitsByMonth();
        numberPatientBySex();
        dispensingMedications();
        /*patientsStatusList();*/
        patientsReceivingARVByPeriod();
        drugsPrescription();
        labPrescription();
        labPerfomed();
        patientArvThirtyDay();
        patientStartingArv();
        patientNextArvArrives();
        institutionFrequentingByUser();
        institutionFrequentingByUserAndDate();
        institutionFrequenting();
        institutionFrequentingByDate();
        //saveAlertReport();
        patientWithOnlyRegisterForm();
        hivPatientWithoutFirstVisit();
        hivPatientWithActivityAfterDisc();
        numberPregnancyWomenHivTested();
        numberPregnancyWomenHivPositive();
        communityArvDistribution();
        pregnancyWomenOnHaart();
        pregnancyWomenTestedForSyphilis();
        pregnancyWomenDiagnosedWithSyphilis();
        numberPrenatalVisitBySite();
        numberPregnancyWomenHadFirstPrenatalCons();
        numberHivPregnancyWomenGaveBirthAtHospital();
        exposedInfantsWithMotherInPtmeProgram();
        numberInfantsFromMotherOnProphylaxis();
        frequencyPrenatalVisitsPerPatient();
        numberExposedInfantsTestedByPcr();
        numberExposedInfantsConfirmedHiv();
        numberWomenFirstPrenatalVisitFirstTrimester();
        hivTransmissionRisksFactor();
        numberVisitsByPregnantWomenToClinic();
        listVisitsByPregnantWomenToClinic();
        listPatientsReceivingArtInCommunity();
        listPatientsReceivingArtInHospital();
        listPatientsBeneficiePcr();
        numberPatientsBeneficiePcr();
        listEligibleChildrenPcr();
        numberEligibleChildrenPcr();
        numberChargeViraleByResultDate();
        listPatChargeViraleMoinsMilleByResultDate();
        listPatChargeViralePlusMilleByResultDate();
        numberChargeViraleByDemandDate();
        listPatChargeViraleMoinsMilleByDemandDate();
        listPatChargeViralePlusMilleByDemandDate();
        listOfLabOrders();
        listOfLabOrdersResults();
        listPatientsEligibleForChargeVirale();
        listPatientsEligibleForChargeViraleControl();
        listPatientsWithoutPcOrSt();
        listPatientsWithNoGender();
        patientsWithMultipleARTRegimens();
        possibleDuplicateRegistrations();
        numberWomenEnrolledBecamePregnant();
        numberHivPatient();
        listOfExposedInfants();
        artDispensationFollowUp();
        listPatientWithNextVisitByPeriod();
        MalariaIndicatorReport.registerReport();
        ObGynIndicatorReport.registerReport();
        listPatientWithActivityAfterDiscByPeriod();
        listPatientVaccinatedForCovid19();
        listPatientDiagnoseForCovid19();
        listPatientSuspectedCovid19();
        listPatientConfirmCovid19();
        hivPatientsWithoutPositiveHivTest();
        listActivePatientsWithViralLoadTwelveMonths();
        listActivePatientsScreenedForTb();
        patientWithIncompleteProphylaxisAgainstTb();
        patientScreenedNegativeAgainstTbWithNoProphylaxis();
        listPatientWithFingerPrint();
    }

    private static void cleanTables() {
        List<DataSetDefinition> defService = Context.getService(DataSetDefinitionService.class).getAllDefinitions(true);
        for (DataSetDefinition dataSetDef : defService) {
            //Context.getService(DataSetDefinitionService.class).purgeDefinition(dataSetDef);
            Context.getService(SerializedDefinitionService.class).purgeDefinition(dataSetDef);

        }
        ReportService rs = Context.getService(ReportService.class);
        List<ReportDesign> rDes = rs.getAllReportDesigns(true);
        for (ReportDesign reportDesign : rDes) {
            rs.purgeReportDesign(reportDesign);

        }

        ReportDefinitionService rds = Context.getService(ReportDefinitionService.class);
        List<ReportDefinition> rDefs = rds.getAllDefinitions(true);
        for (ReportDefinition reportDefinition : rDefs) {
            //rds.purgeDefinition(reportDefinition);
            Context.getService(SerializedDefinitionService.class).purgeDefinition(reportDefinition);
        }
        for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED, Status.FAILED)) {
            try {
                rs.purgeReportRequest(request);
            } catch (Exception e) {
                log.warn("Unable to delete old report request: " + request, e);
            }
        }
    }

    public static void cleanReportsRequest() {
        ReportService rs = Context.getService(ReportService.class);
        for (ReportRequest request : rs.getReportRequests(null, null, null, Status.COMPLETED, Status.FAILED)) {
            try {
                rs.purgeReportRequest(request);
            } catch (Exception e) {
                log.warn("Unable to delete old report request: " + request, e);
            }
        }
    }

//    private static void nextVisitSevenDays() {
//        IsantePlusReportsUtil.registerReportsWithoutParams("visitNextSevenDays.sql", MessageUtil.translate("isanteplusreports.septJoursLibelle"),
//                MessageUtil.translate("isanteplusreports.septJoursLibelle"), IsantePlusReportsProperties.SEVEN_DAYS_REPORT_DEFINITION_UUID);
//
//    }

//    private static void nextVisitFourteenDays() {
//        IsantePlusReportsUtil.registerReportsWithoutParams("visitNextFourteenDays.sql", MessageUtil.translate("isanteplusreports.quatorzeJoursLibelle"),
//                MessageUtil.translate("isanteplusreports.quatorzeJoursLibelle"), IsantePlusReportsProperties.FOURTEEN_DAYS_REPORT_DEFINITION_UUID);
//    }

    private static void patientAgeGroup() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("patientAgeGroup.sql", "isanteplusreports.patByAge",
                MessageUtil.translate("isanteplusreports.patByAge"), IsantePlusReportsProperties.PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID);
    }

    private static void firstVisitAge() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("firstVisitAge.sql", "isanteplusreports.firstVisitAge",
                MessageUtil.translate("isanteplusreports.firstVisitAge"), IsantePlusReportsProperties.FIRSTVISITAGE);
    }

    private static void patientsCrachatAnormalWithoutTbDiagnostic() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsCrachatAnormalWithoutTbDiagnostic.sql", "isanteplusreports.patientsCrachatWithoutTbDiagnostic",
                MessageUtil.translate("isanteplusreports.patientsCrachatWithoutTbDiagnostic"), IsantePlusReportsProperties.PATIENTCRACHATANORMALWITHOUTTBDIAGNOSTIC);
    }

    private static void patientsWithCompletedTbTreatment() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientWithCompleteTbTreatment.sql", "isanteplusreports.patientsWithCompletedTbTreatment",
                MessageUtil.translate("isanteplusreports.patientsWithCompletedTbTreatment"), IsantePlusReportsProperties.PATIENTWITHCOMPLETEDTBTREATMENT);
    }

    private static void patientsWithTbDiagnosticsWithoutTreatment() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsWithTbDiagnosticWithoutTreatment.sql", "isanteplusreports.patientsWithTbDiagnosticWithoutTreatment",
                MessageUtil.translate("isanteplusreports.patientsWithTbDiagnosticWithoutTreatment"), IsantePlusReportsProperties.PATIENTSWITHTBDIAGNOSTICWITHOUTTREATMENT);
    }

    private static void patientsWithTbSymptomsignWithoutCrachat() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientsWithTbSymptomWithoutCrachat.sql", "isanteplusreports.patientsWithTbDiagnosticWithoutCrachat",
                MessageUtil.translate("isanteplusreports.patientsWithTbDiagnosticWithoutCrachat"), IsantePlusReportsProperties.PATIENTSWITHTBDIAGNOSTICWITHOUTCRACHAT);
    }

    private static void consultationByDay() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("consultationByDay.sql", "isanteplusreports.consultationByDay",
                MessageUtil.translate("isanteplusreports.consultationByDay"), IsantePlusReportsProperties.CONSULTATIONBYDAY);

    }

    private static void numberVisitsByMonth() {
        IsantePlusReportsUtil.registerOtherReportsWithoutParams("numberVisitsByMonth.sql", "isanteplusreports.numberVisitByMonth",
                MessageUtil.translate("isanteplusreports.numberVisitByMonth"), IsantePlusReportsProperties.NUMBERVISITSBYMONTH);

    }

    private static void numberPatientBySex() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParamsOtherRenderer("numberPatientsBySex.sql", "isanteplusreports.numberPatientBySex",
                MessageUtil.translate("isanteplusreports.numberPatientBySex"), IsantePlusReportsProperties.NUMBERPATIENTBYSEX);
    }

    private static void dispensingMedications() {
        IsantePlusReportsUtil.registerOtherReportsWithoutParams("dispensingMedications.sql", "isanteplusreports.dispensingMedications",
                MessageUtil.translate("isanteplusreports.dispensingMedications"), IsantePlusReportsProperties.DISPENSINGMEDICATIONS);
    }

    /*private static void patientsStatusList() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patient_status.sql","isanteplusreports.patientStatus",
                MessageUtil.translate("isanteplusreports.patientStatus"), IsantePlusReportsProperties.PATIENTSTATUS);
    }
    */
    private static void patientStartingArv() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientStartingArv.sql", "isanteplusreports.patientStartedArv",
                MessageUtil.translate("isanteplusreports.patientStartedArv"), IsantePlusReportsProperties.PATIENT_STARTED_ARV_REGIMEN_UUID);

    }

    private static void patientArvThirtyDay() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patientNextArvInThirtyDay.sql", "isanteplusreports.patientArvExpectedDateInThirtyDays",
                MessageUtil.translate("isanteplusreports.patientArvExpectedDateInThirtyDays"), IsantePlusReportsProperties.PATIENT_ARV_EXPECTATION_IN_THIRTY_DAYS_UUID);
    }

    private static void patientNextArvArrives() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patientArvEnd.sql", "isanteplusreports.patientArvEnd",
                MessageUtil.translate("isanteplusreports.patientArvEnd"), IsantePlusReportsProperties.PATIENT_ARV_END);
    }

    private static void patientsReceivingARVByPeriod() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("numberPatientReceivingARVByPeriod.sql", "isanteplusreports.patientArvByPeriod",
                MessageUtil.translate("isanteplusreports.patientArvByPeriod"), IsantePlusReportsProperties.PATIENTRECEIVINGARVBYPERIOD);
    }

    private static void drugsPrescription() {
        IsantePlusReportsUtil.registerOtherReportsWithoutParams("medicamentPrescrit.sql", "isanteplusreports.drugsPrescriptionAmount",
                MessageUtil.translate("isanteplusreports.drugsPrescriptionAmount"), IsantePlusReportsProperties.DRUGS_PRESCRIPTION_UUID);
    }

    private static void labPrescription() {
        IsantePlusReportsUtil.registerOtherReportsWithoutParams("analyses_laboratoire_prescrites.sql", "isanteplusreports.labPrescription",
                MessageUtil.translate("isanteplusreports.labPrescription"), IsantePlusReportsProperties.LAB_PRESCRIPTION_UUID);
    }

    private static void labPerfomed() {
        IsantePlusReportsUtil.registerOtherReportsWithoutParams("analyses_laboratoire_effectues.sql", "isanteplusreports.labDone",
                MessageUtil.translate("isanteplusreports.labDone"), IsantePlusReportsProperties.LAB_DONE_UUID);
    }

    private static void institutionFrequentingByUser() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_user.sql", "isanteplusreports.institution_frequenting_by_user",
                MessageUtil.translate("isanteplusreports.institution_frequenting_by_user"), IsantePlusReportsProperties.INSTITUTION_FREQUENTING_OTHER_BY_USER_UUID);
    }

    private static void institutionFrequentingByUserAndDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_user_and_date.sql", "isanteplusreports.institution_frequenting_by_user_and_date",
                MessageUtil.translate("isanteplusreports.institution_frequenting_by_user_and_date"), IsantePlusReportsProperties.INSTITUTION_FREQUENTING_OTHER_BY_USER_AND_DATE_UUID);
    }

    private static void institutionFrequenting() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting.sql", "isanteplusreports.institution_frequenting",
                MessageUtil.translate("isanteplusreports.institution_frequenting"), IsantePlusReportsProperties.INSTITUTION_FREQUENTING);
    }

    private static void institutionFrequentingByDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateAndLocationParams("institution_frequenting_by_date.sql", "isanteplusreports.institution_frequenting_by_date",
                MessageUtil.translate("isanteplusreports.institution_frequenting_by_date"), IsantePlusReportsProperties.INSTITUTION_FREQUENTING_ORDER_BY_DATE);
    }
	
	/*private static void saveAlertReport() {
		IsantePlusReportsUtil.registerOtherReportsWithoutParams("alert.sql","isanteplusreports.alert",
				MessageUtil.translate("isanteplusreports.alert"), IsantePlusReportsProperties.ALERT_REPORT_DEFINITION_UUID);
	}*/

    private static void patientWithOnlyRegisterForm() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patient_with_only_register_form.sql", "isanteplusreports.patient_with_only_register_form",
                MessageUtil.translate("isanteplusreports.patient_with_only_register_form"), IsantePlusReportsProperties.PATIENT_WITH_ONLY_REGISTER_FORM);
    }

    private static void hivPatientWithoutFirstVisit() {
        IsantePlusReportsUtil.registerReportsWithoutParams("hiv_patient_without_first_visit.sql", "isanteplusreports.hiv_patient_without_first_visit",
                MessageUtil.translate("isanteplusreports.hiv_patient_without_first_visit"), IsantePlusReportsProperties.HIV_PATIENT_WITHOUT_FIRST_VISIT);
    }

    private static void hivPatientWithActivityAfterDisc() {
        IsantePlusReportsUtil.registerReportsWithoutParams("hiv_patient_with_activity_after_disc.sql", "isanteplusreports.hiv_patient_with_activity_after_discontinuation",
                MessageUtil.translate("isanteplusreports.hiv_patient_with_activity_after_discontinuation"), IsantePlusReportsProperties.HIV_PATIENT_WITH_ACTIVITY_AFTER_DISC);
    }

    private static void numberPregnancyWomenHivTested() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnancy_women_hiv_tested.sql", "isanteplusreports.number_pregnancy_women_hiv_tested",
                MessageUtil.translate("isanteplusreports.number_pregnancy_women_hiv_tested"), IsantePlusReportsProperties.NUMBER_PREGNANT_WOMEN_HIV_TESTED_UUID);
    }

    private static void numberPregnancyWomenHivPositive() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_prenancy_women_vih_positive.sql", "isanteplusreports.number_prenancy_women_vih_positive",
                MessageUtil.translate("isanteplusreports.number_prenancy_women_vih_positive"), IsantePlusReportsProperties.NUMBER_PREGNANT_WOMEN_HIV_POSITIVE_UUID);
    }

    private static void communityArvDistribution() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("community_arv_distribution.sql", "isanteplusreports.community_arv_distribution",
                MessageUtil.translate("isanteplusreports.community_arv_distribution"), IsantePlusReportsProperties.COMMUNITY_ARV_DISTRIBUTION_UUID);
    }

    private static void pregnancyWomenOnHaart() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("pregnancy_women_on_haart.sql", "isanteplusreports.pregnancy_women_on_haart",
                MessageUtil.translate("isanteplusreports.pregnancy_women_on_haart"), IsantePlusReportsProperties.PREGNANCY_WOMEN_ON_HAART_UUID);
    }

    private static void pregnancyWomenTestedForSyphilis() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("pregnancy_women_tested_for_syphilis.sql", "isanteplusreports.pregnancy_women_tested_for_syphilis",
                MessageUtil.translate("isanteplusreports.pregnancy_women_tested_for_syphilis"), IsantePlusReportsProperties.PREGNANCY_WOMEN_TESTED_FOR_SYPHILIS_UUID);
    }

    private static void pregnancyWomenDiagnosedWithSyphilis() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnant_women_diagnosed_syphilis.sql", "isanteplusreports.pregnancy_women_diagnosed_with_syphilis",
                MessageUtil.translate("isanteplusreports.pregnancy_women_diagnosed_with_syphilis"), IsantePlusReportsProperties.PREGNANCY_WOMEN_DIAGNOSED_WITH_SYPHILIS_UUID);
    }

    private static void numberPrenatalVisitBySite() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_prenatal_visit_by_site.sql", "isanteplusreports.number_prenatal_visit_by_site",
                MessageUtil.translate("isanteplusreports.number_prenatal_visit_by_site"), IsantePlusReportsProperties.NUMBER_PRENATAL_VISIT_BY_SITE_UUID);
    }

    private static void numberPregnancyWomenHadFirstPrenatalCons() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_pregnancy_women_had_prenatal_cons.sql", "isanteplusreports.number_pregnancy_women_had_first_consultation_prenatal",
                MessageUtil.translate("isanteplusreports.number_pregnancy_women_had_first_consultation_prenatal"), IsantePlusReportsProperties.NUMBER_PREGNANCY_WOMEN_HAD_FIRST_PRENATAL_CONSULTATION);
    }

    private static void numberHivPregnancyWomenGaveBirthAtHospital() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_hiv_women_gave_birth_at_hospital.sql", "isanteplusreports.number_hiv_pregnancy_women_gave_birth_at_hospital",
                MessageUtil.translate("isanteplusreports.number_hiv_pregnancy_women_gave_birth_at_hospital"), IsantePlusReportsProperties.NUMBER_HIV_PREGNANCY_WOMEN_GAVE_BIRTH_AT_HOSP);
    }

    private static void exposedInfantsWithMotherInPtmeProgram() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("exposed_infants_register_in_ptme_program.sql", "isanteplusreports.exposed_infants_register_in_ptme_program",
                MessageUtil.translate("isanteplusreports.exposed_infants_register_in_ptme_program"), IsantePlusReportsProperties.EXPOSED_INFANT_WITH_MOTHER_IN_PTME_UUID);
    }

    private static void numberInfantsFromMotherOnProphylaxis() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_infants_from_mother_on_prophylaxis.sql", "isanteplusreports.number_infants_from_mother_on_prophylaxis",
                MessageUtil.translate("isanteplusreports.number_infants_from_mother_on_prophylaxis"), IsantePlusReportsProperties.NUMBER_INFANTS_FROM_MOTHER_ON_PROPHYLAXIS);
    }

    private static void frequencyPrenatalVisitsPerPatient() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("frequency_prenatal_visits_per_patient.sql", "isanteplusreports.frequency_prenatal_visits_per_patient",
                MessageUtil.translate("isanteplusreports.frequency_prenatal_visits_per_patient"), IsantePlusReportsProperties.FREQUENCY_PRENATAL_VISITS_PER_PATIENT_UUID);
    }

    private static void numberExposedInfantsTestedByPcr() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_exposed_infants_tested_by_pcr.sql", "isanteplusreports.number_exposed_infants_tested_by_pcr",
                MessageUtil.translate("isanteplusreports.number_exposed_infants_tested_by_pcr"), IsantePlusReportsProperties.NUMBER_EXPOSED_INFANTS_TESTED_BY_PCR_UUID);
    }

    private static void numberExposedInfantsConfirmedHiv() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_exposed_infants_confirmed_hiv.sql", "isanteplusreports.number_exposed_infants_confirmed_hiv",
                MessageUtil.translate("isanteplusreports.number_exposed_infants_confirmed_hiv"), IsantePlusReportsProperties.NUMBER_EXPOSED_INFANTS_CONFIRMED_HIV_UUID);
    }

    private static void numberWomenFirstPrenatalVisitFirstTrimester() {
        CohortIndicatorDataSetDefinition dsd = IsantePlusReportsUtil.cohortIndicatorDataSetDefinition(
                "isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester",
                MessageUtil.translate("isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester"),
                Arrays.asList(PrenatalReportsIndicatorLibrary.pregnantWomenFirstVisitAfterFirstTrimester()));

        IsantePlusReportsUtil.registerIndicatorReportsWithStartAndEndDateParams(
                "isanteplusreports.pregnancy_women_diagnosed_with_syphilis",
                MessageUtil.translate("isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester"),
                IsantePlusReportsProperties.NUMBER_WOMEN_SEEN_FIRST_PRENATAL_VISIT_FIRST_SEMESTER_UUID, dsd);
       /* IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_women_seen_first_prenatal_visit_first_trimester.sql","isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester",
				MessageUtil.translate("isanteplusreports.number_women_seen_first_prenatal_visit_first_trimester"), IsantePlusReportsProperties.NUMBER_WOMEN_SEEN_FIRST_PRENATAL_VISIT_FIRST_SEMESTER_UUID);*/
    }

    private static void hivTransmissionRisksFactor() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("hiv_transmission_risks_factor.sql", "isanteplusreports.hiv_transmission_risks_factor",
                MessageUtil.translate("isanteplusreports.hiv_transmission_risks_factor"), IsantePlusReportsProperties.HIV_TRANSMISSION_RISKS_FACTOR_UUID);
    }

    private static void numberVisitsByPregnantWomenToClinic() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_visits_by_pregnant_women_to_the_clinic.sql", "isanteplusreports.number_visits_by_pregnant_women_to_the_clinic",
                MessageUtil.translate("isanteplusreports.number_visits_by_pregnant_women_to_the_clinic"), IsantePlusReportsProperties.NUMBER_VISITS_BY_PREGNANT_WOMEN_UUID);

    }

    private static void listVisitsByPregnantWomenToClinic() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_pregnancy_women_receiving_in_clinic.sql", "isanteplusreports.list_pregnancy_women_receiving_in_clinic",
                MessageUtil.translate("isanteplusreports.list_pregnancy_women_receiving_in_clinic"), IsantePlusReportsProperties.LIST_VISITS_BY_PREGNANT_WOMEN_UUID);
    }

    private static void listPatientsReceivingArtInCommunity() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_receiving_arv_in_community.sql", "isanteplusreports.list_patients_receiving_arv_in_community",
                MessageUtil.translate("isanteplusreports.list_patients_receiving_arv_in_community"), IsantePlusReportsProperties.LIST_PATIENTS_RECEIVING_ARV_IN_COMMUNITY);
    }

    private static void listPatientsReceivingArtInHospital() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_receiving_arv_in_hospital.sql", "isanteplusreports.list_patients_receiving_arv_in_hospital",
                MessageUtil.translate("isanteplusreports.list_patients_receiving_arv_in_hospital"), IsantePlusReportsProperties.LIST_PATIENTS_RECEIVING_ARV_IN_HOSPITAL);
    }

    private static void listPatientsBeneficiePcr() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patient_beneficie_pcr.sql", "isanteplusreports.list_patients_beneficie_pcr",
                MessageUtil.translate("isanteplusreports.list_patients_beneficie_pcr"), IsantePlusReportsProperties.LIST_PATIENTS_BENEFICIE_PCR_UUID);
    }

    private static void numberPatientsBeneficiePcr() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patient_beneficie_pcr.sql", "isanteplusreports.number_patients_beneficie_pcr",
                MessageUtil.translate("isanteplusreports.number_patients_beneficie_pcr"), IsantePlusReportsProperties.NUMBER_PATIENTS_BENEFICIE_PCR_UUID);
    }

    private static void listEligibleChildrenPcr() {
        IsantePlusReportsUtil.registerReportsWithoutParams("list_eligible_children_for_pcr.sql", "isanteplusreports.list_eligible_children_for_pcr",
                MessageUtil.translate("isanteplusreports.list_eligible_children_for_pcr"), IsantePlusReportsProperties.LIST_ELIGIBLE_CHILDREN_FOR_PCR_UUID);

    }

    private static void numberEligibleChildrenPcr() {
        IsantePlusReportsUtil.registerReportsWithoutParams("number_eligible_children_for_pcr.sql", "isanteplusreports.number_eligible_children_for_pcr",
                MessageUtil.translate("isanteplusreports.number_eligible_children_for_pcr"), IsantePlusReportsProperties.NUMBER_ELIGIBLE_CHILDREN_FOR_PCR_UUID);

    }

    private static void numberChargeViraleByResultDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patients_charge_virale_moins_plus_mille.sql", "isanteplusreports.number_charge_virale_by_result_date",
                MessageUtil.translate("isanteplusreports.number_charge_virale_by_result_date"), IsantePlusReportsProperties.CHARGE_VIRALE_SELON_DATE_RESULTAT);
    }

    private static void listPatChargeViraleMoinsMilleByResultDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_moins_mille_by_result_date.sql", "isanteplusreports.list_patients_charge_virale_moins_mille_by_result_date",
                MessageUtil.translate("isanteplusreports.list_patients_charge_virale_moins_mille_by_result_date"), IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_RES_DATE);
    }

    private static void listPatChargeViralePlusMilleByResultDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_plus_mille_by_result_date.sql", "isanteplusreports.list_patients_charge_virale_plus_mille_by_result_date",
                MessageUtil.translate("isanteplusreports.list_patients_charge_virale_plus_mille_by_result_date"), IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_RES_DATE);
    }

    private static void numberChargeViraleByDemandDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_patients_charge_virale_moins_plus_mille_demand.sql", "isanteplusreports.number_charge_virale_by_demand_date",
                MessageUtil.translate("isanteplusreports.number_charge_virale_by_demand_date"), IsantePlusReportsProperties.CHARGE_VIRALE_SELON_DATE_DEMAND);
    }

    private static void listPatChargeViraleMoinsMilleByDemandDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_moins_mille_by_demand_date.sql", "isanteplusreports.list_patients_charge_virale_moins_mille_by_demand_date",
                MessageUtil.translate("isanteplusreports.list_patients_charge_virale_moins_mille_by_demand_date"), IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_DEMAND_DATE);
    }

    private static void listPatChargeViralePlusMilleByDemandDate() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_charge_virale_plus_mille_by_demand_date.sql", "isanteplusreports.list_patients_charge_virale_plus_mille_by_demand_date",
                MessageUtil.translate("isanteplusreports.list_patients_charge_virale_plus_mille_by_demand_date"), IsantePlusReportsProperties.LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_DEMAND_DATE);
    }

    private static void listOfLabOrders() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_of_lab_orders.sql", "isanteplusreports.list_of_lab_orders",
                MessageUtil.translate("isanteplusreports.list_of_lab_orders"), IsantePlusReportsProperties.LIST_OF_LAB_ORDERS);
    }

    private static void listOfLabOrdersResults() {
        IsantePlusReportsUtil.registerLabOrderReportWithResults("list_of_lab_orders_results.sql", "isanteplusreports.list_of_lab_orders_results",
                MessageUtil.translate("isanteplusreports.list_of_lab_orders_results"), IsantePlusReportsProperties.LIST_OF_LAB_ORDERS_RESULTS);
    }

    private static void listPatientsEligibleForChargeVirale() {
        IsantePlusReportsUtil.registerReportsWithoutParams("list_patients_eligible_for_charge_virale.sql", "isanteplusreports.list_patients_eligible_for_charge_virale",
                MessageUtil.translate("isanteplusreports.list_patients_eligible_for_charge_virale"), IsantePlusReportsProperties.LIST_PATIENTS_ELIGIBLE_FOR_CHARGE_VIRALE);
    }

    private static void listPatientsEligibleForChargeViraleControl() {
        IsantePlusReportsUtil.registerReportsWithoutParams("list_patients_eligible_for_charge_virale_controle.sql", "isanteplusreports.list_patients_eligible_for_charge_virale_controle",
                MessageUtil.translate("isanteplusreports.list_patients_eligible_for_charge_virale_controle"), IsantePlusReportsProperties.LIST_PATIENTS_ELIGIBLE_FOR_CHARGE_VIRALE_CONTROL);
    }

    private static void listPatientsWithoutPcOrSt() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patientsWithoutPCST.sql", "isanteplusreports.patientWithoutPCOrST",
                MessageUtil.translate("isanteplusreports.patientWithoutPCOrST"), IsantePlusReportsProperties.PATIENTSWITHOUTPCORST_UUID);
    }

    private static void listPatientsWithNoGender() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patientsWithNoGender.sql", "isanteplusreports.patientsWithNoGender",
                MessageUtil.translate("isanteplusreports.patientsWithNoGender"), IsantePlusReportsProperties.PATIENTSWITHNOGENDER_UUID);
    }

    private static void patientsWithMultipleARTRegimens() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patientsWithMultipleARTRegimens.sql", "isanteplusreports.patientsWithMultipleARTRegimens",
                MessageUtil.translate("isanteplusreports.patientsWithMultipleARTRegimens"), IsantePlusReportsProperties.PATIENTWITHMULTIPLEARTREGIMENS_UUID);
    }

    private static void possibleDuplicateRegistrations() {
        IsantePlusReportsUtil.registerReportsWithoutParams("possibleDuplicateRegistrations.sql", "isanteplusreports.possibleDuplicateRegistrations",
                MessageUtil.translate("isanteplusreports.possibleDuplicateRegistrations"), IsantePlusReportsProperties.POSSIBLEDUPLICATEREGISTRATIONS_UUID);
    }

    private static void numberWomenEnrolledBecamePregnant() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("womenEnrolledBecamePregnant.sql", "isanteplusreports.womenEnrolledBecamePregnant",
                MessageUtil.translate("isanteplusreports.womenEnrolledBecamePregnant"), IsantePlusReportsProperties.WOMENENROLLEDBECAMEPREGNANT_UUID);
    }

    private static void numberHivPatient() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("number_hiv_patient.sql", "isanteplusreports.number_hiv_patient",
                MessageUtil.translate("isanteplusreports.number_hiv_patient"), IsantePlusReportsProperties.NUMBER_HIV_PATIENT_UUID);
    }

    private static void listOfExposedInfants() {
        IsantePlusReportsUtil.registerReportsWithoutParams("list_of_exposed_infants.sql", "isanteplusreports.list_of_exposed_infants",
                MessageUtil.translate("isanteplusreports.list_of_exposed_infants"), IsantePlusReportsProperties.LIST_OF_EXPOSED_INFANTS_UUID);

    }

    private static void artDispensationFollowUp() {
        IsantePlusReportsUtil.registerReportsWithoutParams("ART_dispensation_follow_up.sql", MessageUtil.translate("isanteplusreports.art_dispensation_follow_up"),
                MessageUtil.translate("isanteplusreports.art_dispensation_follow_up"), IsantePlusReportsProperties.ART_DISPENSATION_FOLLOW_UP_UUID);
    }

    private static void listPatientWithNextVisitByPeriod() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("next_visit_by_period.sql", "isanteplusreports.next_visit_by_period",
                MessageUtil.translate("isanteplusreports.next_visit_by_period"), IsantePlusReportsProperties.NEXT_VISIT_BY_PERIOD_UUID);
    }

    private static void listPatientWithActivityAfterDiscByPeriod() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patients_with_activity_after_disc_by_month.sql", "isanteplusreports.activity_after_discontinuation_by_period",
                MessageUtil.translate("isanteplusreports.activity_after_discontinuation_by_period"), IsantePlusReportsProperties.ACTIVITY_AFTER_DISC_BY_PERIOD);
    }

    private static void listPatientVaccinatedForCovid19() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patients_vaccinated_for_covid_19.sql", "Liste des patients vaccinés pour le COVID-19",
                "Liste des patients vaccinés pour le COVID-19", IsantePlusReportsProperties.PATIENTS_VACCINATED_FOR_COVID19);
    }

    private static void listPatientDiagnoseForCovid19() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patients_diagnose_for_covid19.sql", "Liste des patients diagnostiqués pour le COVID-19",
                "Liste des patients diagnostiqués pour le COVID-19", IsantePlusReportsProperties.PATIENTS_DIAGNOSE_FOR_COVID19);
    }

    private static void listPatientConfirmCovid19() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patients_confirm_covid19.sql", "Liste des patients confirmés COVID-19",
                "Liste des patients confirmés COVID-19", IsantePlusReportsProperties.PATIENTS_CONFIRM_COVID19);
    }

    private static void listPatientSuspectedCovid19() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patients_suspected_covid19.sql", "Liste des patients suspectés pour le COVID-19",
                "Liste des patients suspectés pour le COVID-19", IsantePlusReportsProperties.PATIENTS_SUSPECTED_COVID19);
    }

    private static void hivPatientsWithoutPositiveHivTest() {
        IsantePlusReportsUtil.registerReportsWithoutParams("list_hiv_patients_without_hiv_test.sql", "Liste des patients VIH sans test VIH positif",
                "Liste des patients VIH sans test VIH positif", IsantePlusReportsProperties.PATIENTS_HIV_WITHOUT_POSITIVE_HIV_TEST);

    }

    private static void listActivePatientsWithViralLoadTwelveMonths() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("active_patient_viral_load_last_twelve_months.sql", "Patient Actif avec une charge virale pendant les 12 derniers mois",
                "Patient Actif avec une charge virale pendant les 12 derniers mois", IsantePlusReportsProperties.ACTIVE_PATIENTS_VIRALLOAD_TWELVE_MONTHS);
    }

    private static void listActivePatientsScreenedForTb() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("activePatientsScreenedForTb.sql", "Patients VIH+ actifs sous ARV dépistés pour la TB",
                "Patients VIH+ actifs sous ARV dépistés pour la TB", IsantePlusReportsProperties.ACTIVE_PATIENTS_SCREENED_FOR_TB_UUID);
    }

    private static void patientWithIncompleteProphylaxisAgainstTb() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientWithIncompleteProphylaxisAgainstTb.sql", "Patients VIH+ avec Prophylaxie incomplète contre la TB",
                "Patients VIH+ avec Prophylaxie incomplète contre la TB", IsantePlusReportsProperties.PATIENT_WITH_INCOMPLETE_PROPHYLAXIS_AGAINST_TB_UUID);
    }

    private static void patientScreenedNegativeAgainstTbWithNoProphylaxis() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("patientScreenedNegativeAgainstTbWithNoProphylaxis.sql", "Patients VIH+ dépistés négatifs pour la TB n’ayant pas reçu de prophylaxie",
                "Patients VIH+ dépistés négatifs pour la TB n’ayant pas reçu de prophylaxie", IsantePlusReportsProperties.PATIENT_SCREENED_NEGATIVE_AGAINST_TB_WITH_NO_PROPHYLAXIS_UUID);
    }

    private static void listPatientWithFingerPrint() {
        IsantePlusReportsUtil.registerReportsWithoutParams("patient_with_finger_print.sql", "Liste des patients avec empreinte",
                "Liste des patients avec empreinte", IsantePlusReportsProperties.PATIENT_WITH_FINGER_PRINT_UUID);

    }

}
