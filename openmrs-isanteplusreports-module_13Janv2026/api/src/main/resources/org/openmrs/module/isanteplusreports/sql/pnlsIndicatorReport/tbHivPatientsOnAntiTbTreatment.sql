SELECT p.patient_id
FROM isanteplus.patient_on_art pat , isanteplus.patient p
    WHERE p.patient_id = pat.patient_id
	AND p.vih_status =1
    AND pat.date_enrolled_on_tb_treatment IS NOT NUlL 
	AND pat.date_enrolled_on_tb_treatment BETWEEN :startDate AND :endDate ;