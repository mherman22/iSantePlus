/*Ind. 41.1.1*/
SELECT p.patient_id
FROM isanteplus.patient p
WHERE p.date_started_arv >=  DATE_SUB(:startDate, INTERVAL 12 MONTH)
  AND p.date_started_arv <=  DATE_SUB(:endDate, INTERVAL 12 MONTH)
  AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
  AND p.patient_id NOT IN (SELECT p.patient_id FROM isanteplus.patient p WHERE p.arv_status = 2);




/*
SELECT p.patient_id
FROM isanteplus.patient_status_arv psa, isanteplus.patient p,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status, ps.id_status
	FROM isanteplus.patient_status_arv ps 
	WHERE ps.date_started_status <= :endDate GROUP BY 1) B
WHERE p.patient_id = psa.patient_id
AND (p.transferred_in = 0 OR p.transferred_in IS NULL)
AND TIMESTAMPDIFF(MONTH, p.date_started_arv,:endDate) = 12
AND psa.patient_id = B.patient_id
AND DATE(psa.date_started_status) = DATE(B.date_status)
AND psa.id_status = B.id_status
AND B.date_status <= :endDate
AND B.id_status <> 2; 
ate AND :endDate);*/