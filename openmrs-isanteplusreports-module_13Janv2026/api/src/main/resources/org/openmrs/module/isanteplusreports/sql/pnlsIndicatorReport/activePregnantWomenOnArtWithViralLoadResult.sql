SELECT pl.patient_id            								            
FROM isanteplus.patient_status_arv ps,isanteplus.patient_laboratory pl,
isanteplus.patient_pregnancy pp,isanteplus.patient_on_art pat,
(SELECT psar.patient_id,
      MAX(psar.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psar WHERE psar.date_started_status 
      BETWEEN :startDate AND :endDate GROUP BY 1) B
    WHERE ps.patient_id = pl.patient_id
     AND pl.patient_id = pp.patient_id
     AND pat.patient_id = pp.patient_id
     AND ps.patient_id = B.patient_id
   	 AND DATE(ps.date_started_status) = DATE(B.date_status)
     AND pp.end_date >= :endDate
	 AND ps.id_status IN (6,8)
	 AND pl.test_id IN (856,1305)
    AND ps.date_started_status BETWEEN :startDate AND :endDate 
	 AND pl.test_done =1 
	 AND TIMESTAMPDIFF(MONTH, pl.date_test_done ,:endDate) >= 12
	  AND pl.viral_load_target_or_routine <> 2
	 AND pl.voided <> 1;
	