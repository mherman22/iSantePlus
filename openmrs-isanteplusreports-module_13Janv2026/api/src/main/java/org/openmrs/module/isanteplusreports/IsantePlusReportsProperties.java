package org.openmrs.module.isanteplusreports;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.REPORTS_SQL_PATH;

public class IsantePlusReportsProperties{
	
	//***** PRIVILEGES ******
	public static final String PRIVILEGE_PATIENT_DASHBOARD = "App: coreapps.patientDashboard";
	
	public final String ISANTEPLUS_REPORTS_RESOURCE_PATH = REPORTS_SQL_PATH + "fullDataExports/";
	
	public final String ISANTEPLUS_SUMMARY_RESOURCE_PATH = REPORTS_SQL_PATH + "patientSummary/";
	
	public static final String SEVEN_DAYS_REPORT_DEFINITION_UUID = "1c52d6ee-7cc1-4bae-a303-ffa2bdd0a8e2";
	
	public static final String FOURTEEN_DAYS_REPORT_DEFINITION_UUID = "d5ced5d1-bbc7-4679-9020-51e732b69dfd";
	
	public static final String PATIENT_AGE_GROUP_REPORT_DEFINITION_UUID = "5c40612b-bd9b-4cb7-8c30-1ae89fcdf038";
	
	public static final String PATIENTCRACHATANORMALWITHOUTTBDIAGNOSTIC = "54ecf434-5978-4826-b049-d965fe1f81ed";
	
	public static final String PATIENTWITHCOMPLETEDTBTREATMENT = "5c4201e3-83c1-421a-8bc1-f23ecb67cf60";
	
	public static final String PATIENTSWITHTBDIAGNOSTICWITHOUTTREATMENT = "7acca9bf-ee7c-49bd-9444-faf2e66aaeb9";
	
	public static final String PATIENTSWITHTBDIAGNOSTICWITHOUTCRACHAT = "73173f96-69fc-4c74-b31c-c7ffb9a37d69";
	
	public static final String CONSULTATIONBYDAY = "d5ca9447-c2b2-4625-af65-93fc1ff58843";
	
	public static final String FIRSTVISITAGE = "27882c50-7a45-475b-97c1-b0618135e667";
	
	public static final String NUMBERVISITSBYMONTH = "68023435-7e82-4e95-af6b-c137476f0f02";
	
	public static final String NUMBERPATIENTBYSEX = "892661ed-f096-4580-a2de-abcf8a13658d";
	
	public static final String DISPENSINGMEDICATIONS = "bdd40a99-1727-43a3-b155-71b73bf681b9";
	
	public static final String PATIENTSTATUS = "b5a70371-3ef5-4d6b-a3e1-1a2cfde6a66d";
	
	public static final String DRUGS_PRESCRIPTION_UUID = "184c228b-ff83-41cb-97c3-6bfc7fe5c4c8";
	
	public static final String LAB_PRESCRIPTION_UUID = "84f3fc41-c932-411f-b43f-f51cfe4e44a7";
	
	public static final String LAB_DONE_UUID = "0a290fe4-863b-4137-bbd6-c79899baf694";
	
	public static final String PATIENTRECEIVINGARVBYPERIOD = "ead22ae8-c3ab-4c27-ab8d-e63ec8658e50";
	
	public static final String PATIENT_STARTED_ARV_REGIMEN_UUID = "a34a5b4a-13d7-4909-a2ef-557db15411cc";
	
	public static final String PATIENT_ARV_EXPECTATION_IN_THIRTY_DAYS_UUID = "2bde1a37-dcbc-4e06-8090-72b6d5356abf";
	
	public static final String PATIENT_ARV_END = "fd4f74f0-4e3b-42a3-bec0-be1367a2e2ce";
	
	public static final String INSTITUTION_FREQUENTING_OTHER_BY_USER_UUID = "6cd7ff44-4139-4289-8a1c-125cbb9b305a";
	
	public static final String INSTITUTION_FREQUENTING_OTHER_BY_USER_AND_DATE_UUID = "ce6214e2-08e5-43bc-8b83-c934e712b28c";
	
	public static final String INSTITUTION_FREQUENTING = "1ed1cfcf-38f6-460f-b7c0-7fa3ea0dfd2a";
	
	public static final String INSTITUTION_FREQUENTING_ORDER_BY_DATE = "33c5cee6-8102-43c1-975e-c11b3ce26ea9";
	
	public static final String ALERT_REPORT_DEFINITION_UUID = "bbb6e146-67f3-49fd-be43-7aeb776def9b";
	
	public static final String PATIENT_WITH_ONLY_REGISTER_FORM = "dfa9a7a6-629f-46db-9648-60a8ca9214be";
	
	public static final String HIV_PATIENT_WITHOUT_FIRST_VISIT = "aea43fb1-3bed-4745-a316-4c55cfdd868e";
	
	public static final String HIV_PATIENT_WITH_ACTIVITY_AFTER_DISC = "1d83343e-9467-42e7-b2dd-a69362802d62";
	
	public static final String FORM_RECENTLY_FILLED_UUID = "93845fc4-d16e-4594-8497-2afda9d398d6";
	
	public static final String NUMBER_PREGNANT_WOMEN_HIV_TESTED_UUID = "7a55fec8-6189-4e0f-9742-a462e4dbc189";
	
	public static final String NUMBER_PREGNANT_WOMEN_HIV_POSITIVE_UUID = "f5c6a8db-73cb-4537-b2ce-833cf850fad4";
	
	public static final String COMMUNITY_ARV_DISTRIBUTION_UUID = "437469a1-7112-47a7-b48a-6cec7bf87260";
	
	public static final String PREGNANCY_WOMEN_ON_HAART_UUID = "b3c29c8d-6918-4b16-90b7-aff3d1204860";
	
	public static final String PREGNANCY_WOMEN_TESTED_FOR_SYPHILIS_UUID = "23f61edd-76b1-4e68-a24d-c3c16a91436d";
	
	public static final String PREGNANCY_WOMEN_DIAGNOSED_WITH_SYPHILIS_UUID = "613d0119-cb40-4fed-aa67-56f032a20cc9";
	
	public static final String NUMBER_PRENATAL_VISIT_BY_SITE_UUID = "b7f7ea96-3fe4-4a17-99b5-1ad2cdaadf50";
	
	public static final String NUMBER_PREGNANCY_WOMEN_HAD_FIRST_PRENATAL_CONSULTATION = "60051dfe-85f3-45e1-9df2-a45bbed2918b";
	
	public static final String NUMBER_HIV_PREGNANCY_WOMEN_GAVE_BIRTH_AT_HOSP = "a366a646-382a-4eb1-b2a7-16282579c9df";
	
	public static final String EXPOSED_INFANT_WITH_MOTHER_IN_PTME_UUID = "f786f658-c9a8-4a3b-bfc1-6232807ef8fe";
	
	public static final String NUMBER_INFANTS_FROM_MOTHER_ON_PROPHYLAXIS = "d0ba4eba-dc36-4adc-9c0e-f0eb244fd279";
	
	public static final String FREQUENCY_PRENATAL_VISITS_PER_PATIENT_UUID = "6d2de0f7-2396-4e17-9399-5a704b3ea7f9";
	
	public static final String NUMBER_EXPOSED_INFANTS_TESTED_BY_PCR_UUID = "314972cc-0d5c-45c6-9445-54a942e96b43";
	
	public static final String NUMBER_EXPOSED_INFANTS_CONFIRMED_HIV_UUID = "1c072b8f-2740-4cb4-aff7-6d0095df2437";
	
	public static final String NUMBER_WOMEN_SEEN_FIRST_PRENATAL_VISIT_FIRST_SEMESTER_UUID = "99dd9482-6e97-49be-8e6e-123ba8f689cb";
	
	public static final String NUMBER_PATIENTS_BY_ARV_STATUS_UUID = "28cf5e0c-e1d6-49de-8b24-eb165a242d69";
	
	public static final String HIV_TRANSMISSION_RISKS_FACTOR_UUID = "b66e2895-d766-4fad-b12f-19b979c82d06";
	
	public static final String NUMBER_VISITS_BY_PREGNANT_WOMEN_UUID = "6647de56-ada8-4a01-a5ed-3e8db17d3957";
	
	public static final String LIST_VISITS_BY_PREGNANT_WOMEN_UUID = "a3d743f9-fc42-429e-bd71-e2b9e375cc61";
	
	public static final String LIST_PATIENTS_DEATH_ON_ART = "a473ea21-b974-4bf2-a007-ae276bbaafb8";
	
	public static final String LIST_PATIENTS_STOPPED_ON_ART = "6c248951-30dd-428c-8ced-a5ed69565221";
	
	public static final String LIST_PATIENTS_TRANSFERED_ON_ART = "b9f62f45-0fd5-4888-9dae-f5898c245273";
	
	public static final String LIST_PATIENTS_DEATH_ON_PRE_ART = "d704b580-1cc2-4169-a90b-e506b97ed051";
	
	public static final String LIST_PATIENTS_TRANSFERRED_ON_PRE_ART = "35013e35-f1de-4c3d-b54e-71f423dc8d8b";
	
	public static final String LIST_PATIENTS_REGULAR_ACTIVE_ON_ART = "ced0c096-774a-44df-b6aa-1c487d0b9102";
	
	public static final String LIST_PATIENTS_RECENT_ON_PRE_ART = "0491f954-f4c3-4563-9ab9-ed755cc53c43";
	
	public static final String LIST_PATIENTS_MISSING_APPOINTMENT_ON_ART_UUID = "81de9cea-d8c4-4b7b-b9a6-13120310ce5b";
	
	public static final String LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_ART_UUID = "1034936c-2252-4cbe-a592-502c5c32574e";
	
	public static final String LIST_PATIENTS_LOST_TO_FOLLOW_UP_ON_PRE_ART_UUID = "9b05b088-67eb-4ef1-b54a-de62f6fa4b6e";
	
	public static final String LIST_PATIENTS_ACTIVE_ON_PRE_ART_UUID = "c8f18f18-b69a-4730-a35a-5c9a36ffa247";
	
	public static final String LIST_PATIENTS_RECEIVING_ARV_IN_COMMUNITY = "af628fc5-e83b-443e-aed2-c7ffc29bc4f2";
	
	public static final String LIST_PATIENTS_RECEIVING_ARV_IN_HOSPITAL = "90ef295f-4d58-4b73-b477-f3798093a1f0";
	
	public static final String LIST_PATIENTS_BENEFICIE_PCR_UUID = "8176506e-b027-4735-bcc8-ce39182dbc7e";
	
	public static final String NUMBER_PATIENTS_BENEFICIE_PCR_UUID = "5a5543d5-98d3-480c-b193-2bbe4dcb1c9d";
	
	public static final String LIST_ELIGIBLE_CHILDREN_FOR_PCR_UUID = "164be26a-d7d8-4efd-9f86-2656d781b275";
	
	public static final String NUMBER_ELIGIBLE_CHILDREN_FOR_PCR_UUID = "cb18f36f-befa-4abb-b932-e99dcf7176bb";
	
	public static final String CHARGE_VIRALE_SELON_DATE_RESULTAT = "09816079-aca6-4659-94b9-3ec619328b82";
	
	public static final String LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_RES_DATE = "4e0475de-1c07-4465-a042-ea25c7d699a4";
	
	public static final String LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_RES_DATE =	"bce4c112-6313-401c-a298-38f1d9abc2aa";
	
	public static final String CHARGE_VIRALE_SELON_DATE_DEMAND = "d01f6bb5-28b2-48b6-88bd-0486cf9ff3e7";
	
	public static final String LIST_PAT_CHARGE_VIRALE_MOINS_MILLE_DEMAND_DATE = "dba66308-7358-41f5-8806-001efe60dd13";
	
	public static final String LIST_PAT_CHARGE_VIRALE_PLUS_MILLE_DEMAND_DATE =	"2126a49e-3a7b-48b2-bb92-12292cbb02f8";
	
	public static final String LIST_OF_LAB_ORDERS = "f37fa532-2f50-4d84-a1f0-a7abb2c29417";

	public static final String LIST_OF_LAB_ORDERS_RESULTS = "62f8e200-38de-4faa-a0c9-d807f2c3a2db";
	
	public static final String LIST_PATIENTS_ELIGIBLE_FOR_CHARGE_VIRALE = "af612ab5-9e33-4911-a23d-dd4c7eeaef14";
	
	public static final String LIST_PATIENTS_ELIGIBLE_FOR_CHARGE_VIRALE_CONTROL = "dceb09ed-7f54-4ebb-be45-76ac69a67b59";
	
	public static final String PATIENTSWITHOUTPCORST_UUID = "3f78a306-3755-4960-8bf2-15a7ae4cb262";
	
	public static final String PATIENTSWITHNOGENDER_UUID = "3099cda4-b906-42fc-af4c-0a1d51da53a3";
	
	public static final String POSSIBLEDUPLICATEREGISTRATIONS_UUID = "f344beaa-634f-4ce3-8a9c-dfd1788b5de2";
	
	public static final String PATIENTWITHMULTIPLEARTREGIMENS_UUID = "4910f32f-1dfc-4e8d-a431-269a8b215b93";
	
	public static final String WOMENENROLLEDBECAMEPREGNANT_UUID = "c1c3838d-80bb-44c3-963d-2283078bf274";
	
	public static final String NUMBER_HIV_PATIENT_UUID = "b6973c26-e4a2-46c6-9446-f993cad52efe";
	
	public static final String LIST_OF_EXPOSED_INFANTS_UUID = "3d6ee8cc-cfea-4ce7-b84b-3c6893ce6a2c";
	
	public static final String ART_DISPENSATION_FOLLOW_UP_UUID = "ef282113-32bd-49d7-870f-0ef41443e8de";
	
	public static final String NEXT_VISIT_BY_PERIOD_UUID = "f3bef310-0e62-4b4f-88f1-8b7983ea51cb";
	
	public static final String ACTIVITY_AFTER_DISC_BY_PERIOD = "7414a044-ace9-4140-88f2-5b79d869b5c9";

	public static final String PATIENTS_VACCINATED_FOR_COVID19 = "de5c0d89-4d01-4f1a-99dd-20b3dd778bc";

	public static final String PATIENTS_DIAGNOSE_FOR_COVID19 = "c6381c6e-b9a4-49d3-84bd-210a0bc6b0dd";
	
	public static final String WEEKLYMONITORINGREPORT_UUID = "1c558cf7-d44c-49f2-bf9e-a43be418c990";
	
	public static final String PATIENTS_CONFIRM_COVID19 = "358f6c1f-6751-4ed0-8567-95a3ac227f39";
	
	public static final String PATIENTS_SUSPECTED_COVID19 = "27f98dae-6c6d-4424-87d2-bd41305253f4";
	
	public static final String PATIENTS_HIV_WITHOUT_POSITIVE_HIV_TEST = "3d931155-97ce-4c83-8652-37735bf48e45";
	
	public static final String ACTIVE_PATIENTS_VIRALLOAD_TWELVE_MONTHS = "bb53535b-4d85-4315-8506-8b242e60cfd9";
	
	public static final String ACTIVE_PATIENTS_SCREENED_FOR_TB_UUID = "8a6bf7f0-5a50-4221-83ad-61d29057864e";
	
	public static final String PATIENT_WITH_INCOMPLETE_PROPHYLAXIS_AGAINST_TB_UUID = "f48cf8ec-feb2-4473-89ec-4ab19dc48738";
	
	public static final String PATIENT_SCREENED_NEGATIVE_AGAINST_TB_WITH_NO_PROPHYLAXIS_UUID = "85d9ca70-81d7-4082-93c0-d9eeed450632";

	public static final String DDPREPORTBYPERIOD_UUID = "3510620c-5eb8-4ca0-b680-eebc80502ebc";

	public static final String PATIENT_WITH_FINGER_PRINT_UUID = "2a06491b-9f73-40a6-bed6-8c39fade8d7a";
}
