SELECT psa.patient_id
   FROM isanteplus.patient_status_arv psa,
   (select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B
	    WHERE psa.patient_id = B.patient_id
	    AND DATE(psa.date_started_status) = DATE(B.date_status)
	    AND psa.patient_id IN 
	    (SELECT psa.patient_id FROM isanteplus.patient_status_arv psa 
	    WHERE TIMESTAMPDIFF(MONTH,psa.date_started_status ,:startDate) <=3 
	    AND psa.id_status IN (6,8)) 
       AND psa.id_status = 2
       AND psa.date_started_status  BETWEEN :startDate AND :endDate;