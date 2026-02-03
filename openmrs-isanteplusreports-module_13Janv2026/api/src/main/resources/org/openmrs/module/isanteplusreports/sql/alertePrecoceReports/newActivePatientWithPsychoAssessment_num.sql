SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, openmrs.encounter e,
	openmrs.encounter_type et
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND e.encounter_type = et.encounter_type_id
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND et.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 AND DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate
 AND DATE(psa.date_started_status) between :startDate AND :endDate
 AND DATE(p.date_started_arv) between :startDate AND :endDate
 AND TIMESTAMPDIFF(DAY, DATE(e.encounter_datetime),DATE(p.date_started_arv)) BETWEEN 0 AND 1;