SELECT ps.patient_id
FROM isanteplus.patient_status_arv ps, (SELECT psar.patient_id,
      MAX(psar.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psar WHERE psar.date_started_status 
      BETWEEN :startDate AND :endDate GROUP BY 1) B
    WHERE ps.patient_id = B.patient_id
    AND DATE(ps.date_started_status) = DATE(B.date_status)
    AND ps.id_status = 2
    AND ps.date_started_status BETWEEN :startDate AND :endDate;