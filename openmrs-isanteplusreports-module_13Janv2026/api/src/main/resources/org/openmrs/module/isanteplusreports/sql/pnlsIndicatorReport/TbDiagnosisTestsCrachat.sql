SELECT pat.patient_id 
   FROM isanteplus.patient_on_art pat ,isanteplus.patient_status_arv ps
   WHERE pat.patient_id = ps.patient_id
   AND  ps.id_status IN (6,8)
   AND ps.date_started_status BETWEEN :startDate AND :endDate
   AND pat.date_sample_sent_for_diagnositic_tb IS NOT NULL
   AND pat.tb_crachat_test = 1
	AND pat.date_sample_sent_for_diagnositic_tb  BETWEEN :startDate AND :endDate ; 