package org.openmrs.module.isanteplusreports.util;

import org.openmrs.module.appframework.context.SessionContext;
import org.openmrs.module.isanteplusreports.IsantePlusReportsProperties;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.reporting.common.MessageUtil;

public class RegisterPatientsArvStatusReports extends SessionContext {

    //IsantePlusReportsProperties props = new IsantePlusReportsProperties();

    //static IsantePlusReportsProperties props;
    //IsantePlusReportsUtil reportsUtil;

    public static void registerAllPatientsArvStatusReports() {
        numberPatientsByArvStatus();
        listPatientsDeathOnArt();
        listPatientsStoppedOnArt();
        listPatientsTransfertOnArt();
        listPatientsDeathOnPreArt();
        listPatientsTransferredOnPreArt();
        listPatientsRegularActiveOnArt();
        listPatientsRecentOnPreArt();
        listPatientsMissingAppointmentOnArt();
        listPatientsLostToFollowUpOnArt();
        listPatientsLostToFollowUpOnPreArt();
        listPatientsActiveOnPreArt();
        /*listPatientsActiveInactive();*/
    }

    private static void numberPatientsByArvStatus() {
        IsantePlusReportsUtil.registerReportsWithOtherStartAndEndDateParams("number_patient_by_status.sql", "isanteplusreports.number_patient_by_arv_status",
                MessageUtil.translate("isanteplusreports.number_patient_by_arv_status"), IsantePlusReportsProperties.NUMBER_PATIENTS_BY_ARV_STATUS_UUID);
    }

    private static void listPatientsDeathOnArt() {
        IsantePlusReportsUtil.registerReportsWithOtherStartAndEndDateParams("list_patients_death_on_art.sql", "isanteplusreports.death_on_art",
                MessageUtil.translate("isanteplusreports.death_on_art"), IsantePlusReportsProperties.LIST_PATIENTS_DEATH_ON_ART);
    }

    private static void listPatientsStoppedOnArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_stopped_on_art.sql", "isanteplusreports.stopped_on_art",
                MessageUtil.translate("isanteplusreports.stopped_on_art"), IsantePlusReportsProperties.LIST_PATIENTS_STOPPED_ON_ART);
    }

    private static void listPatientsTransfertOnArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_transfert_on_art.sql", "isanteplusreports.transfered_on_art",
                MessageUtil.translate("isanteplusreports.transfered_on_art"), IsantePlusReportsProperties.LIST_PATIENTS_TRANSFERED_ON_ART);
    }

    private static void listPatientsDeathOnPreArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_death_on_pre_art.sql", "isanteplusreports.death_on_pre_art",
                MessageUtil.translate("isanteplusreports.death_on_pre_art"), IsantePlusReportsProperties.LIST_PATIENTS_DEATH_ON_PRE_ART);
    }

    private static void listPatientsTransferredOnPreArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_transferred_on_pre_art.sql", "isanteplusreports.transferred_on_pre_art",
                MessageUtil.translate("isanteplusreports.transferred_on_pre_art"), IsantePlusReportsProperties.LIST_PATIENTS_TRANSFERRED_ON_PRE_ART);
    }

    private static void listPatientsRegularActiveOnArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_regular_active_on_arv.sql", "isanteplusreports.regular_active_on_art",
                MessageUtil.translate("isanteplusreports.regular_active_on_art"), IsantePlusReportsProperties.LIST_PATIENTS_REGULAR_ACTIVE_ON_ART);
    }

    private static void listPatientsRecentOnPreArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_recent_on_pre_art.sql", "isanteplusreports.recent_on_pre_art",
                MessageUtil.translate("isanteplusreports.recent_on_pre_art"), IsantePlusReportsProperties.LIST_PATIENTS_RECENT_ON_PRE_ART);
    }

    private static void listPatientsMissingAppointmentOnArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_missing_appointment_on_arv.sql", "isanteplusreports.missing_appointment",
                MessageUtil.translate("isanteplusreports.missing_appointment"), IsantePlusReportsProperties.LIST_PATIENTS_MISSING_APPOINTMENT_ON_ART_UUID);
    }

    private static void listPatientsLostToFollowUpOnArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_lost_to_follow_up_on_art.sql", "isanteplusreports.list_patients_lost_to_follow_up_on_art",
                MessageUtil.translate("isanteplusreports.list_patients_lost_to_follow_up_on_art"), IsantePlusReportsProperties.LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_ART_UUID);
    }

    private static void listPatientsLostToFollowUpOnPreArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_lost_to_follow_up_on_pre_art.sql", "isanteplusreports.list_patients_lost_to_follow_up_on_pre_art",
                MessageUtil.translate("isanteplusreports.list_patients_lost_to_follow_up_on_pre_art"), IsantePlusReportsProperties.LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_PRE_ART_UUID);
    }

    private static void listPatientsActiveOnPreArt() {
        IsantePlusReportsUtil.registerReportsWithStartAndEndDateParams("list_patients_active_on_pre_art.sql", "isanteplusreports.list_patients_active_on_pre_art",
                MessageUtil.translate("isanteplusreports.list_patients_active_on_pre_art"), IsantePlusReportsProperties.LIST_PATIENTS_ACTIVE_ON_PRE_ART_UUID);
    }
	
	/*private static void listPatientsActiveInactive(){
		IsantePlusReportsUtil.registerHivStatus("patient_status.sql","isanteplusreports.patientStatus",
				MessageUtil.translate("isanteplusreports.patientStatus"), IsantePlusReportsProperties.PATIENTSTATUS);
	}
     */


}
