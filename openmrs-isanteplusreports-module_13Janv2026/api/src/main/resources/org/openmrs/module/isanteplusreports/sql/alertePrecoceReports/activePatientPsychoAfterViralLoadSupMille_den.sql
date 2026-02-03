SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, isanteplus.patient_laboratory pl,
	(SELECT pla.patient_id, MAX(DATE(pla.visit_date)) as visit_date
	FROM isanteplus.patient_laboratory pla 
	WHERE DATE(pla.visit_date) between :startDate AND :endDate
	AND pla.test_id IN (856,1305) GROUP BY 1) C
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND pl.patient_id = C.patient_id
 AND DATE(pl.visit_date) = DATE(C.visit_date)
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND (pl.test_id = 856 AND pl.test_result > 1000)
 AND DATE(psa.date_started_status) between :startDate AND :endDate
 AND DATE(pl.visit_date) between :startDate AND :endDate;