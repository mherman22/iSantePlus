SELECT DISTINCT p.patient_id  
FROM isanteplus.patient p , isanteplus.patient_on_art pat    
     WHERE pat.date_screened_cervical_cancer BETWEEN :startDate AND :endDate
	 AND p.date_started_arv IS NOT NULL
	 AND p.patient_id = pat.patient_id
    AND p.gender = 'F'
     AND p.vih_status = 1
     AND pat.screened_cervical_cancer = 1
     AND p.voided = 0;