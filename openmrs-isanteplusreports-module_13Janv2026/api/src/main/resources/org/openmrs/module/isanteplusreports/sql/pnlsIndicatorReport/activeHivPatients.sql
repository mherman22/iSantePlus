SELECT ps.patient_id
FROM isanteplus.patient_status_arv ps, 
(SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status 
	FROM isanteplus.patient_status_arv psa WHERE 
 DATE(psa.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B
    WHERE ps.patient_id = B.patient_id
    AND DATE(ps.date_started_status) = B.date_status
    AND ps.id_status IN (6,8)
    AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate;