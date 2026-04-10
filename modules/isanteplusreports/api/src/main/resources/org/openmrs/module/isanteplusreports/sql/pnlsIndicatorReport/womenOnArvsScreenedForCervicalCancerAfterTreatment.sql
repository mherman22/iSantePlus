SELECT p.patient_id  
FROM isanteplus.patient p , isanteplus.patient_on_art pat , (SELECT p.patient_id ,(pat.date_started_cervical_cancer_status) AS second_last_status_date FROM isanteplus.patient_on_art pat ,isanteplus.patient p WHERE p.patient_id = pat.patient_id  AND p.gender = 'F'  AND p.date_started_arv IS NOT NULL AND pat.date_started_cervical_cancer_status NOT IN (SELECT MAX(pat.date_started_cervical_cancer_status) FROM isanteplus.patient_on_art pat ,isanteplus.patient p WHERE  p.patient_id = pat.patient_id  AND p.gender = 'F'  AND p.date_started_arv IS NOT NULL) GROUP BY 1)A , (SELECT p.patient_id  , MAX(pat.date_screened_cervical_cancer) AS max_screen_date FROM isanteplus.patient_on_art pat ,isanteplus.patient p WHERE p.patient_id = pat.patient_id  AND p.gender = 'F'  AND p.date_started_arv IS NOT NULL GROUP BY 1)B
     WHERE pat.date_screened_cervical_cancer BETWEEN :startDate AND :endDate
	 AND p.date_started_arv IS NOT NULL
	 AND p.patient_id = pat.patient_id
	 AND A.patient_id = p.patient_id
	AND B.patient_id = p.patient_id
	 AND pat.date_started_cervical_cancer_status = A.second_last_status_date
	 AND pat.cervical_cancer_status = 'POSTIVE'
	 AND pat.date_screened_cervical_cancer = B.max_screen_date
    AND p.gender = 'F'
     AND p.vih_status = 1
     AND pat.screened_cervical_cancer = 1
     AND p.voided = 0 ;