SELECT p.patient_id  
FROM isanteplus.patient p , isanteplus.patient_on_art pat , (SELECT p.patient_id  , MIN(pat.date_screened_cervical_cancer) AS min_screen_date FROM isanteplus.patient_on_art pat ,isanteplus.patient p WHERE p.patient_id = pat.patient_id  AND p.gender = 'F'  AND p.date_started_arv IS NOT NULL GROUP BY 1)A 
     WHERE pat.date_screened_cervical_cancer BETWEEN :startDate AND :endDate
	 AND p.date_started_arv IS NOT NULL
	 AND p.patient_id = pat.patient_id
	  AND A.patient_id = p.patient_id
	 AND pat.date_screened_cervical_cancer = A. min_screen_date 
    AND p.gender = 'F'
     AND p.vih_status = 1
     AND pat.screened_cervical_cancer = 1
     AND p.voided = 0
	  HAVING COUNT(pat.screened_cervical_cancer) = 1;