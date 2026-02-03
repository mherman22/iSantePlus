SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, isanteplus.patient_laboratory pl,
	(SELECT pla.patient_id, MAX(DATE(pla.visit_date)) as visit_date
	FROM isanteplus.patient_laboratory pla 
	WHERE DATE(pla.visit_date) between :startDate AND :endDate
	AND pla.test_id IN (856,1305) GROUP BY 1) C,
	(SELECT DISTINCT en.patient_id, MAX(DATE(en.encounter_datetime)) AS date_visite,
	COUNT(en.encounter_id) FROM openmrs.encounter en, 
		openmrs.encounter_type etype, openmrs.obs ob, openmrs.concept c 
		WHERE en.encounter_type = etype.encounter_type_id
		AND en.encounter_id = ob.encounter_id
		AND ob.value_coded = c.concept_id
		AND ob.concept_id = 163145
		AND c.uuid = 'e0520711-73e5-4490-ba34-9dc3d7e959c3'
		AND etype.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 HAVING COUNT(en.encounter_id) = 3)D
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND pl.patient_id = C.patient_id
 AND pl.patient_id = D.patient_id
 AND DATE(pl.visit_date) = DATE(C.visit_date)
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND DATE(pl.visit_date) <= DATE(D.date_visite)
 AND TIMESTAMPDIFF(DAY, DATE(D.date_visite),DATE(pl.visit_date)) BETWEEN 0 AND 90
 AND psa.id_status IN (6,8)
 AND (pl.test_id = 856 AND pl.test_result > 1000)
 AND DATE(psa.date_started_status) between :startDate AND :endDate;