

/*INSERT FLAGS*/
/*use openmrs;*/

	SET SQL_SAFE_UPDATES = 0;
	SET FOREIGN_KEY_CHECKS = 0;
	
	truncate table openmrs.patientflags_flag_tag;
	truncate table openmrs.patientflags_tag_displaypoint;
	truncate table openmrs.patientflags_flag;
	truncate table openmrs.patientflags_tag;
	truncate table openmrs.patientflags_priority;
				
	SET SQL_SAFE_UPDATES = 1;
	SET FOREIGN_KEY_CHECKS = 1;

	INSERT INTO openmrs.patientflags_tag VALUES (2,'Tag',NULL,1,'2018-05-28 09:44:50',NULL,NULL,0,NULL,NULL,NULL,'4dbe134d-a67a-44be-871f-5890b05d328c');
 	
 	INSERT INTO openmrs.patientflags_priority VALUES 
	(1,'Liste VL','color:red',1,NULL,1,'2018-05-28 02:17:38',1,'2018-05-28 02:19:27',0,NULL,NULL,NULL,'f2e0e461-170e-4df9-80fc-da2d93663328');
	INSERT INTO openmrs.patientflags_priority VALUES 
	(2,'Liste Medicament','color: red',2,NULL,1,'2018-05-31 15:02:47',NULL,NULL,0,NULL,NULL,NULL,'5d87ef2b-5cc2-4ef5-a241-a122977170d6');
	INSERT INTO openmrs.patientflags_priority VALUES 
	(3,'Liste TB','color: blue',3,NULL,1,'2018-05-31 15:02:47',NULL,NULL,0,NULL,NULL,NULL,'439d2dfa-29ee-4271-9e18-97a80d0eb475');

/* Dernière charge virale de ce patient remonte à au moins 12 mois */
 	INSERT INTO openmrs.patientflags_flag VALUES 
	(2,'Dernière charge virale de ce patient remonte à 12 mois ou plus',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 4',
	'Dernière charge virale de ce patient remonte à 12 mois ou plus',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2018-05-28 02:18:18',1,'2018-05-31 13:43:43',0,NULL,NULL,NULL,
	'8c176fcb-9354-43fa-b13c-c293e6f910dc',1);
				
/*patient sous ARV depuis 6 mois sans un résultat de charge virale*/
				
/*INSERT INTO openmrs.patientflags_flag VALUES 
				(3,'patient sous ARV depuis 6 mois sans un résultat de charge virale',
				'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 1',
				 'Le patient est sous ARV depuis 6 mois sans un résultat de charge virale',1,
				 'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',NULL,
				 1,'2018-05-31 14:58:13',1,'2018-05-31 14:59:31',0,NULL,NULL,NULL,
				 '1d968997-4d6d-41d4-ab91-9b7936030ace',1); */

/* Patient sous ARV et traitement anti tuberculeux */
				
	INSERT INTO openmrs.patientflags_flag VALUES 
	(4,'Coïnfection TB/VIH',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 9',
	'Coïnfection TB/VIH',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',NULL,1,'2018-05-31 15:03:40',
	NULL,NULL,0,NULL,NULL,NULL,'a1d4c4ba-348c-456d-aca1-755190b78b0c',3);
				 
				 
/* Dernière charge virale de ce patient remonte à au moins 3 mois et le résultat était supérieur 1000 copies/ml */
 	INSERT INTO openmrs.patientflags_flag VALUES 
	(5,'Le patient a au moins 3 mois de sa dernière charge virale supérieur à 1000 copies/ml',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 5',
	'Le patient a au moins 3 mois de sa dernière charge virale supérieur à 1000 copies/ml',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2018-05-28 02:18:18',1,'2018-05-31 13:43:43',0,NULL,NULL,NULL,
	'8c176fcb-9354-43fa-b13c-c293e6f910dc',1);
	
/*Le patient doit venir renflouer ses ARV dans les 30 prochains jours*/
	INSERT INTO openmrs.patientflags_flag VALUES 
	(7,'Le patient doit venir renflouer ses ARV dans les 30 prochains jours',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 7',
	'Le patient doit venir renflouer ses ARV dans les 30 prochains jours',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2018-05-28 02:18:18',1,'2018-05-31 13:43:43',0,NULL,NULL,NULL,
	'8c176fcb-9354-43fa-b13c-c293e6f910dc',2);
	
/*Le patient n'a plus de médicaments disponibles*/
	INSERT INTO openmrs.patientflags_flag VALUES 
	(8,'Le patient n\'a plus de médicaments disponibles',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 8',
	'Le patient n\'a plus de médicaments disponibles',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2018-05-28 02:18:18',1,'2018-05-31 13:43:43',0,NULL,NULL,NULL,
	'8c176fcb-9354-43fa-b13c-c293e6f910dc',2);
	
/*Patient sous ARV depuis au moins 3 mois sans un résultat de charge virale*/
	INSERT INTO openmrs.patientflags_flag VALUES 
	(9,'Patient sous ARV depuis au moins 3 mois sans un résultat de charge virale',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 10',
	'Patient sous ARV depuis au moins 3 mois sans un résultat de charge virale',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',NULL,
	1,'2020-02-05 14:58:13',1,'2020-02-05 14:59:31',0,NULL,NULL,NULL,
	'c874aaf5-9e64-4fca-ba49-3f903158fa5f',1);
				 
	INSERT INTO openmrs.patientflags_flag VALUES 
	(10,'Nouveau enrôlé aux ARV sans prophylaxie INH',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 11',
	'Nouveau enrôlé aux ARV sans prophylaxie INH',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2020-02-05 02:18:18',1,'2020-02-05 13:43:43',0,NULL,NULL,NULL,
	'c26c358d-ec66-4588-8546-e39511723ded',2);
				
/*Patient Abonne au DDP*/
	INSERT INTO openmrs.patientflags_flag VALUES 
	(11,'Ce patient est abonné au DDP',
	'select distinct a.patient_id FROM isanteplus.alert a WHERE a.id_alert = 12',
	'Ce patient est abonné au DDP',1,
	'org.openmrs.module.patientflags.evaluator.SQLFlagEvaluator',
	NULL,1,'2021-08-02 13:18:18',1,'2021-08-02 13:18:18',0,NULL,NULL,
	NULL,'38125986-383c-4426-b825-87dc0effa6de',2);
				 
	INSERT INTO openmrs.patientflags_flag_tag VALUES (2,2),(4,2),(5,2),(7,2),(8,2),(9,2),(10,2),(11,2);
	INSERT INTO openmrs.patientflags_tag_displaypoint VALUES (2,1);
		
/*Update global_property to Set where the alert should appear*/			
	UPDATE openmrs.global_property SET property_value = 'false' 
	WHERE property = 'patientflags.patientHeaderDisplay';
	UPDATE openmrs.global_property SET property_value = 'true'
	WHERE property = 'patientflags.patientOverviewDisplay';
		

	SET SQL_SAFE_UPDATES = 0;
	SET FOREIGN_KEY_CHECKS = 0;
			
	truncate table openmrs.patientflags_tag_role;
	
/*Insert patientflags_tag_role*/
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Anonymous');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Administers System');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Configures Appointment Scheduling');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Configures Forms');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Configures Metadata');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Edits Existing Encounters');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Enters ADT Events');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Enters Vitals');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Has Super User Privileges');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Manages Atlas');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Manages Provider Schedules');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Records Allergies');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Registers Patients');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Requests Appointments');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Schedules And Overbooks Appointments');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Schedules Appointments');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Sees Appointment Schedule');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Uses Capture Vitals App');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Uses Patient Summary');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: View Reports');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: Writes Clinical Notes');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Authenticated');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Organizational: Doctor');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Organizational: Hospital Administrator');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Organizational: Nurse');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Organizational: Registration Clerk');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Organizational: System Administrator');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Privilege Level: Full');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Provider');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'System Developer');
	INSERT INTO openmrs.patientflags_tag_role (`tag_id`,`role`) VALUES (2,'Application: View Reports');
	
	SET SQL_SAFE_UPDATES = 1;
	SET FOREIGN_KEY_CHECKS = 1;


	
	
