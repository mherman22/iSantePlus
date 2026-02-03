SELECT ps.patient_id 
FROM isanteplus.patient_status_arv ps ,isanteplus.patient_on_art pat , isanteplus.patient p
    WHERE ps.patient_id = pat.patient_id
    AND p.patient_id = pat.patient_id
	AND ps.id_status IN (6,8)
	AND p.date_started_arv  < :startDate 
    AND ps.date_started_status BETWEEN :startDate AND :endDate
    AND pat.date_enrolled_on_tb_treatment IS NOT NUlL 
	AND pat.date_enrolled_on_tb_treatment BETWEEN :startDate AND :endDate 
	AND p.voided <> 1;