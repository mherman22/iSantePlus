SELECT p.patient_id
FROM isanteplus.patient p, isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(ps.date_started_status) AS date_status FROM 
isanteplus.patient_status_arv ps
WHERE DATE(ps.date_started_status) BETWEEN :startDate AND :endDate
GROUP BY 1) B
WHERE p.patient_id = psa.patient_id
AND psa.patient_id = B.patient_id
AND DATE(psa.date_started_status) = DATE(B.date_status)
AND psa.id_status IN (6,8)
AND TIMESTAMPDIFF(YEAR,p.birthdate,DATE(psa.date_started_status)) = 15
AND TIMESTAMPDIFF(YEAR,p.birthdate, DATE_SUB(DATE(psa.date_started_status), INTERVAL 1 MONTH)) = 14
AND DATE(psa.date_started_status) BETWEEN :startDate AND :endDate;