SELECT pat.patient_id 
   FROM isanteplus.patient_on_art pat,isanteplus.patient_status_arv ps,
   (SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status 
	FROM isanteplus.patient_status_arv psa WHERE 
 psa.date_started_status BETWEEN :startDate AND :endDate GROUP BY 1) B
   WHERE pat.patient_id = ps.patient_id
   AND ps.patient_id = B.patient_id
   AND DATE(ps.date_started_status) = DATE(B.date_status)
   AND ps.id_status IN (6,8)
   AND ps.date_started_status BETWEEN :startDate AND :endDate
   AND pat.date_sample_sent_for_diagnositic_tb IS NOT NULL
	AND pat.date_sample_sent_for_diagnositic_tb  BETWEEN :startDate AND :endDate ; 