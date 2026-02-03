/*Ind. 34.2*/

SELECT psa.patient_id
FROM isanteplus.patient_status_arv psa
WHERE DATE_SUB(:endDate, INTERVAL 3 MONTH) < psa.date_started_status
  AND psa.date_started_status < :endDate
  AND psa.id_status = 9
GROUP BY 1;