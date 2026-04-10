SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, openmrs.encounter e,
	openmrs.encounter_type et, isanteplus.patient_laboratory pl,
	(SELECT pla.patient_id, MAX(DATE(pla.visit_date)) as visit_date
	FROM isanteplus.patient_laboratory pla 
	WHERE DATE(pla.visit_date) between :startDate AND :endDate
	AND pla.test_id IN (856,1305) GROUP BY 1) C
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND B.patient_id = e.patient_id
 AND e.encounter_type = et.encounter_type_id
 AND pl.patient_id = C.patient_id
 AND DATE(pl.visit_date) = DATE(C.visit_date)
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND et.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 AND TIMESTAMPDIFF(DAY, DATE(e.encounter_datetime), DATE(pl.date_test_done)) BETWEEN 0 AND 30
 AND DATE(e.encounter_datetime) > DATE(pl.visit_date)
 AND (pl.test_id = 856 AND pl.test_result > 1000)
 AND DATE(psa.date_started_status) between :startDate AND :endDate;