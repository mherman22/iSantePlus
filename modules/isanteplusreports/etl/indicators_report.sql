USE isanteplus;

/*insertion of all diagnosis in the table patient_diagnosis*/
	INSERT into patient_diagnosis(patient_id,encounter_id,location_id,concept_group,obs_group_id,concept_id,answer_concept_id, encounter_date, voided)
					
	select distinct ob.person_id,ob.encounter_id,
	ob.location_id,ob1.concept_id,ob.obs_group_id,ob.concept_id, ob.value_coded, e.encounter_datetime, ob.voided
	from openmrs.obs ob, openmrs.obs ob1, openmrs.encounter e, openmrs.encounter_type et
	where ob.person_id = ob1.person_id
	AND ob.encounter_id = ob1.encounter_id
	AND ob.obs_group_id = ob1.obs_id
	AND ob.encounter_id = e.encounter_id
	AND e.encounter_type = et.encounter_type_id	
	AND ob.concept_id = 1284
	AND (ob.value_coded <> '' OR ob.value_coded is not null)
	on duplicate key update
	encounter_id = ob.encounter_id,
	voided = ob.voided;
	
	
	INSERT into patient_diagnosis(patient_id,encounter_id,location_id,concept_id,answer_concept_id, encounter_date, voided)
					
	select distinct ob.person_id,ob.encounter_id,
	ob.location_id,ob.concept_id, ob.value_coded, e.encounter_datetime, ob.voided
	from openmrs.obs ob, openmrs.encounter e, openmrs.encounter_type et
	where ob.encounter_id = e.encounter_id
	AND e.encounter_type = et.encounter_type_id	
	AND ob.concept_id = 159614
	AND (ob.value_coded <> '' OR ob.value_coded is not null)
	on duplicate key update
	encounter_id = ob.encounter_id,
	voided = ob.voided;
	
	
/*update patient diagnosis for suspected_confirmed area*/	
					
	update patient_diagnosis pdiag, openmrs.obs ob
	 SET pdiag.suspected_confirmed = ob.value_coded
	 WHERE pdiag.patient_id = ob.person_id
		   AND pdiag.obs_group_id = ob.obs_group_id
		   AND pdiag.encounter_id = ob.encounter_id
		   AND ob.concept_id = 159394
		   AND ob.value_coded IN (159392,159393)
		   AND ob.voided = 0;
		   
/*Update for primary_secondary area*/
	update patient_diagnosis pdiag, openmrs.obs ob
	 SET pdiag.primary_secondary = ob.value_coded
	 WHERE pdiag.patient_id = ob.person_id
		   AND pdiag.obs_group_id = ob.obs_group_id
		   AND pdiag.encounter_id = ob.encounter_id
		   AND ob.concept_id = 159946
		   AND ob.value_coded IN (159943,159944)
		   AND ob.voided = 0;
		   
/*Update encounter date for patient_diagnosis	   
	update patient_diagnosis pdiag, openmrs.encounter enc
    SET pdiag.encounter_date = enc.encounter_datetime
    WHERE pdiag.encounter_id = enc.encounter_id
		  AND enc.encounter_datetime IS NOT NULL
		  AND enc.voided = 0;*/
/*Ending patient_diagnosis*/


 
 
	
/*Indicateur 1 - 1 : Agression par animal suspecte de rage*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 1, 1, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided,now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 160146
	AND pdiag.suspected_confirmed = 159393
	 or pdiag.answer_concept_id = 121605
	AND pdiag.voided <> 1
	AND pdiag.encounter_date IS NOT NULL
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*2 : Coqueluche Suspect*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 2, 2, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 114190
	AND pdiag.suspected_confirmed = 159393
	 OR (pdiag.concept_id = 159614 AND pdiag.answer_concept_id = 509166386)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*3 : Cholera Suspect*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 3, 3, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 122604
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*4 : Deces maternel*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 4, 4, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 134612
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*5 : Diphterie probable*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 5, 5, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 119399
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*5.1 : Cas possible de Diphterie*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id, encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 5, 5, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.patient_id = pdiag1.patient_id
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id in (1284, 159614) and pdiag.answer_concept_id in (116399, 130305, 158843, 131028) /*Laryngite*/
	and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 509166382 /*Amygdales blanchatres*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*6 : Evenement supose etre attribuable a la vaccination et a l’immunisation (esavi)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
									indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 6, 6, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag,
	openmrs.concept c
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.answer_concept_id = c.concept_id
	AND pdiag.concept_id = 1284
	AND c.uuid = '1b4d09df-4f9f-44ff-9e7b-c1eba6514289'
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
		
		
/*7 : Meningite Suspect*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 7, 7, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id in (115835, 121255)
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*7.1 : Cas possible de Meningite Suspect*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 7, 7, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.encounter_date = pdiag1.encounter_date
	AND pdiag.concept_id = 159614 AND pdiag.answer_concept_id = 509166387
	AND pdiag1.concept_id = 159614 AND pdiag.answer_concept_id in (509166388, 509166389, 116334, 139084, 1836, 512)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*8 : Microcephalie congenitale*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
									indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 8,8,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag,
	openmrs.concept c
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.answer_concept_id = c.concept_id
	AND pdiag.concept_id = 1284
	AND c.uuid = '87275706-5e87-4562-8cdc-b9d1e1649f83'
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*9 : Paludisme confirme*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 9, 9, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 116128
	AND pdiag.suspected_confirmed = 159392
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*10 : Paralysie flasque aigue(PFA)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 10, 10, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id in (160426, 5258)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*10.1 : Paralysie flasque aigue(PFA)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT DISTINCT 10, 10, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 159614 AND pdiag.answer_concept_id = 509166415 /*Faiblesse soudaine d'un membre*/
	AND (TIMESTAMPDIFF(YEAR, p.birthdate, CURDATE()) < 15) 
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*11 : Peste suspecte*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 11,11,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 114120
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*12 : Rage humaine*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	SELECT DISTINCT 12, 12, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 160146
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*12.1 : Cas possible de Rage humaine*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date,voided,created_date,last_updated_date)
	
	SELECT DISTINCT DISTINCT 12, 12, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.patient_id = pdiag1.patient_id	
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 6023 /*Irritabilité*/
	and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 121605 /*Morsure par animal*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*13 : Rougeole/rubeole suspecte*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT 13, 13, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id in (134561, 113205)
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*13.1 : Cas possible de Rougeole*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id, encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 13, 13, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.patient_id = pdiag1.patient_id	
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id = 159614 
	and pdiag.answer_concept_id = 509166383 /*Eruption maculo-papulaire*/
	and pdiag1.answer_concept_id = 140238 /*Fièvre*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*14 : Syndrome de guillain barre*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 14,14,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 139233
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*15 : Syndrome de fievre hemorragique aigue*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT 15, 15, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 163392
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*15.1 : Cas possible Syndrome de fievre hemorragique aigue*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 15, 15, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1, isanteplus.patient_diagnosis pdiag2
	WHERE p.patient_id = pdiag.patient_id
	  AND pdiag.encounter_date = pdiag1.encounter_date
	  AND pdiag.encounter_date = pdiag2.encounter_date
	  and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166399 /*Fièvre de moins de 3 semaines*/
	  and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 509166400 /*Altération de l’état général*/
	  and pdiag2.concept_id = 159614 and pdiag2.answer_concept_id in (509166401, 509166384, 130324, 133499, 138905)
	  AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*16 : Syndrome de rubeole congenitale*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 16, 16, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 113205
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*16.1 : Cas possible Syndrome de rubeole congenitale*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 16, 16, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	  and pdiag.concept_id = 159614 and pdiag.answer_concept_id IN (134213, 113478, 509166384, 509166385)
	  AND (TIMESTAMPDIFF(YEAR, p.birthdate, CURDATE()) < 1)
	  AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*17 : Tetanos neonatal (tnn)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 17, 17, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 124957
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*17.1 : Cas possible de Tetanos neonatal (tnn)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 17, 17, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	  AND pdiag.encounter_date = pdiag1.encounter_date
	  AND pdiag.concept_id = 159614 and pdiag.answer_concept_id = 110540 /*Pleurs excessifs*/
	  AND pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 119775 /*Spasme musculaire*/
	  AND (TIMESTAMPDIFF(DAY, p.birthdate, CURDATE()) BETWEEN 3 and 28)
	  AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*18 : Toxi-infection alimentaire collective (tiac)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 18, 18, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag,
	openmrs.concept c
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.answer_concept_id = c.concept_id
	AND pdiag.concept_id = 1284
	AND c.uuid = '50d568a4-2e65-420c-8d9c-8b63f146e2c5'
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*19 : Charbon cutané suspect*/ /*Cutaneous Anthrax 143086*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 19, 19, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 121555
	AND pdiag.suspected_confirmed = 159393
	 or (pdiag.concept_id = 159614
	     AND pdiag.answer_concept_id = 509166393)/*Lesion papulo vesiculaire sur fond noiratre*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*20 : Dengue suspecte*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date, last_updated_date)
	SELECT DISTINCT 20, 20, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 142592
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*20.1 : Cas possible du Dengue*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id, encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 20, 20, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1, isanteplus.patient_diagnosis pdiag2, isanteplus.patient_diagnosis pdiag3,
              			    isanteplus.patient_diagnosis pdiag4
	WHERE p.patient_id = pdiag.patient_id
	  AND pdiag.encounter_date = pdiag1.encounter_date
	  AND pdiag.encounter_date = pdiag2.encounter_date
	  AND pdiag.encounter_date = pdiag3.encounter_date
	  AND pdiag.encounter_date = pdiag4.encounter_date
	  and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166387 /*Fièvre aigue*/
	  and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 139084 /*CÉPHALÉE*/
	  and pdiag2.concept_id = 159614 and pdiag2.answer_concept_id = 162629 /*Douleur Retro-orbitaire*/
	  and pdiag3.concept_id = 159614 and pdiag3.answer_concept_id in (121, 150167) /*Myalgies*/
	  and pdiag4.concept_id = 159614 and pdiag4.answer_concept_id = 148437 /*Arthralgies*/
	  AND pdiag.voided <> 1
	   ON DUPLICATE KEY 
       UPDATE last_updated_date = NOW(),
	      voided = pdiag.voided;
	
	
/*21 : Diabète*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id,location_id, encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 21, 21, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id IN (142473, 142474)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*21.1 : Cas possible de Diabète*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id, encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 21, 21, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1, isanteplus.patient_diagnosis pdiag2
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.encounter_date = pdiag1.encounter_date
	AND pdiag.encounter_date = pdiag2.encounter_date
	and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 129510 /*Polyurie*/
	and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 118483 /*Polyphagie*/
	and pdiag2.concept_id = 159614 and pdiag2.answer_concept_id = 140939 /*Polydispie*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*22 : Diarrhée aigue aqueuse*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 22, 22, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 161887
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*23 : Diarrhée aigue sanglante*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 23, 23, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 138868
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*24 : Fièvre typhoïde suspecte*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 24, 24, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 141
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*24.1 : Cas possible de typhoïde*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 24, 24, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	  FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1, isanteplus.patient_diagnosis pdiag2
	 WHERE p.patient_id = pdiag.patient_id
	   AND pdiag.encounter_date = pdiag1.encounter_date
	   AND pdiag.encounter_date = pdiag2.encounter_date
	   and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166405 /*Fièvre continue >= 3 jours*/
	   and ((pdiag1.concept_id = 159614 and pdiag1.answer_concept_id in (139084, 148437, 6031, 151, 996, 142412))
	   and (pdiag2.concept_id = 159614 and pdiag2.answer_concept_id in (139084, 148437, 6031, 151, 996, 142412)))
	   AND pdiag.voided <> 1
	    ON DUPLICATE KEY 
	UPDATE last_updated_date = NOW(),
	       voided = pdiag.voided;
	
	
/*25 : Filariose probable*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 25, 25, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 119354
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*25.1 : Cas possible de Filariose */
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 25, 25, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	  FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	 WHERE p.patient_id = pdiag.patient_id
	   AND pdiag.encounter_date = pdiag1.encounter_date
	   and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 127740 /*Fièvre récurrente*/
	   and ((pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 118729) /*Oedème des membres inférieurs*/
	    OR (pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 135480)) /*Lymphœdème*/
	   AND pdiag.voided <> 1
	    ON DUPLICATE KEY 
	UPDATE last_updated_date = NOW(),
	       voided = pdiag.voided;
	
	
/*26 : Infection respiratoire aigue*/
		INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 26, 26, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 154983
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*26.1 : Cas possible Infection respiratoire aigue*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 26, 26, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.patient_id = pdiag1.patient_id	
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166394 /*Fièvre (> 38.0°C)*/
	and pdiag1.concept_id in (1284, 159614) and pdiag1.answer_concept_id in (113224, 143264, 158843, 122496)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*27 : Syndrome ictérique fébrile*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT 27, 27, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 163402
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*27.1 : Cas possible de Syndrome ictérique fébrile*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id,location_id,encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 27, 27, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now() 
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.patient_id = pdiag1.patient_id	
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166394 /*Fièvre > 38 ᴼ C*/
	and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 136443 /*Ictère*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*28 : Tétanos*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id, location_id, encounter_id, indicator_date, voided, created_date, last_updated_date)
	
	SELECT DISTINCT 28,28,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 124957
	 OR (pdiag.concept_id = 159614 AND pdiag.answer_concept_id in (114739, 124105, 119775, 119029))
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
/*28.1 : Cas possible de Tétanos*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id, indicator_date,voided,created_date,last_updated_date)
	
	SELECT DISTINCT 28, 28, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date, pdiag.voided, now(), now()
	FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag, isanteplus.patient_diagnosis pdiag1
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.encounter_date = pdiag1.encounter_date
	and pdiag.concept_id = 159614 and pdiag.answer_concept_id = 509166390 /*Contraction musculaire douloureuse*/
	and pdiag1.concept_id = 159614 and pdiag1.answer_concept_id = 509166391 /*Porte d'entree recente du clostridium Tetani*/
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*29 : Accidents (domestiques, voie publique)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 29, 29, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 150452
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*30 : Cancers (seins, col de l’utérus, prostate, autres)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 30, 30, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id IN (113753, 146221, 116023)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*31 : Epilepsie*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 31, 31, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 155
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*32 : Hypertension artérielle (hta)*/
	INSERT INTO isanteplus.indicators (indicator_id, indicator_type_id, patient_id,location_id, encounter_id,
										indicator_date, voided, created_date, last_updated_date)
	SELECT DISTINCT 32, 32, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	  AND pdiag.concept_id in (1284, 159614) AND pdiag.answer_concept_id in (117399, 139084, 111525, 132662, 123588, 509166411, 118938)
	  AND pdiag.voided <> 1
	   ON DUPLICATE KEY UPDATE
	      last_updated_date = NOW(),
	      voided = pdiag.voided;
	
	
/*33 : Infection sexuellement transmissible (ist)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 33, 33, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 112992
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/* 34 : Lèpre suspecte */
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 24, 24, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 116344
	AND pdiag.suspected_confirmed = 159393
	 or (pdiag.concept_id = 159614 AND pdiag.answer_concept_id in (116344, 509166397, 509166398))
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*35 : Malnutrition*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 35,35,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id IN (832, 126598, 134722, 134723)
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*36 : Syphilis congénitale*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 36, 36, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 143672
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*37 : Violences (physique, sexuelle)*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 37,37,pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 158358
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*38 : Covid Suspect - 155762 */
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 38, 38, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 155762
	AND pdiag.suspected_confirmed = 159393
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*39 : Covid Confirme - 155762 */
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 39, 39, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 155762
	AND pdiag.suspected_confirmed = 159392
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	
	
/*40 : CANCER DU COL*/
	INSERT INTO isanteplus.indicators (indicator_id,indicator_type_id,patient_id,location_id,encounter_id,
										indicator_date,voided,created_date,last_updated_date)
	SELECT DISTINCT 40, 40, pdiag.patient_id, pdiag.location_id, pdiag.encounter_id, pdiag.encounter_date,
	pdiag.voided, now(), now() FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
	WHERE p.patient_id = pdiag.patient_id
	AND pdiag.concept_id = 1284
	AND pdiag.answer_concept_id = 116023
	AND pdiag.voided <> 1
	ON DUPLICATE KEY UPDATE
	last_updated_date = NOW(),
	voided = pdiag.voided;
	




