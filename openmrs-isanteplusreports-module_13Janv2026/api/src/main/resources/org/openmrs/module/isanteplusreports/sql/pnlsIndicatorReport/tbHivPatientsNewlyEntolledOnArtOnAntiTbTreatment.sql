SELECT ps.patient_id 
FROM isanteplus.patient_status_arv ps,isanteplus.patient_on_art pat, 
isanteplus.patient p, (SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status 
	FROM isanteplus.patient_status_arv psa WHERE 
 psa.date_started_status BETWEEN :startDate AND :endDate GROUP BY 1) B
    WHERE ps.patient_id = pat.patient_id
    AND p.patient_id = pat.patient_id
    AND ps.patient_id = B.patient_id
    AND DATE(ps.date_started_status) = DATE(B.date_status)
	AND ps.id_status IN (6,8)
	AND p.date_started_arv  BETWEEN :startDate AND :endDate 
    AND ps.date_started_status BETWEEN :startDate AND :endDate
    AND pat.date_enrolled_on_tb_treatment IS NOT NUlL 
	AND pat.date_enrolled_on_tb_treatment BETWEEN :startDate AND :endDate 
	AND p.voided <> 1;