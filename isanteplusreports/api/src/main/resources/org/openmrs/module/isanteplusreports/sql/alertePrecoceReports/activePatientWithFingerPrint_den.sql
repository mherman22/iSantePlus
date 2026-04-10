SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) <= :endDate GROUP BY 1) B
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id 
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND DATE(psa.date_started_status) <= :endDate;