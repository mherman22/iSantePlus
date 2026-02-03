SELECT CASE 
            WHEN pl.test_id = 856 THEN
				  CASE
				  WHEN CAST(pl.test_result AS UNSIGNED) < 1000 THEN pl.patient_id 							
				  END
		      WHEN pl.test_id = 1305 THEN
		          CASE 
                  WHEN pl.test_result = 1306 THEN pl.patient_id
		          END
		      END
            								            
FROM isanteplus.patient_status_arv ps,isanteplus.patient_laboratory pl,
isanteplus.patient_pregnancy pp,isanteplus.patient_on_art pat,
(SELECT psar.patient_id,
      MAX(psar.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psar WHERE psar.date_started_status 
      BETWEEN :startDate AND :endDate GROUP BY 1) B
    WHERE ps.patient_id = pl.patient_id
    AND pl.patient_id = pp.patient_id
    AND pl.patient_id = pat.patient_id
    AND ps.patient_id = B.patient_id
   	AND DATE(ps.date_started_status) = DATE(B.date_status)
    AND pp.end_date >= :endDate
	AND ps.id_status IN (6,8)
    AND ps.date_started_status BETWEEN :startDate AND :endDate 
	 AND pl.test_done =1 
	 AND TIMESTAMPDIFF(MONTH, pl.date_test_done ,:endDate) >= 12
	 AND pl.viral_load_target_or_routine = 1
	 AND ((pl.test_id = 856 AND pl.test_result < 1000)
	 		OR (pl.test_id = 1305 AND pl.test_result = 1306));