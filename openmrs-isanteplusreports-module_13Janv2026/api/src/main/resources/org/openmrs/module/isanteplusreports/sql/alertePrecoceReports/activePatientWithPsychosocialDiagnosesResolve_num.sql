/*Pourcentage de patients séropositifs actifs traités pour un trouble psychologique durant la période d’analyse*/
SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, openmrs.obs o, openmrs.encounter e,
	openmrs.encounter_type et, 
	(SELECT ob.person_id AS patient_id, DATE(enc.encounter_datetime) AS visit_date
	FROM openmrs.obs ob, openmrs.encounter enc,
	openmrs.encounter_type etype
	WHERE ob.encounter_id = enc.encounter_id
	AND enc.encounter_type = etype.encounter_type_id
 AND etype.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
  AND (ob.concept_id = 1284 AND ob.value_coded IS NOT NULL 
  AND ob.obs_group_id IS NOT NULL))C
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND B.patient_id = o.person_id
 AND o.encounter_id = e.encounter_id
 AND e.encounter_type = et.encounter_type_id
 AND B.patient_id = C.patient_id
 AND DATE(e.encounter_datetime) > DATE(C.visit_date)
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND et.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 AND o.concept_id = 163104 AND (o.value_text IS NOT NULL AND o.value_text <> '')
 AND TIMESTAMPDIFF(MONTH, DATE(e.encounter_datetime),:endDate) BETWEEN 0 AND 12
 AND DATE(psa.date_started_status) between :startDate AND :endDate;