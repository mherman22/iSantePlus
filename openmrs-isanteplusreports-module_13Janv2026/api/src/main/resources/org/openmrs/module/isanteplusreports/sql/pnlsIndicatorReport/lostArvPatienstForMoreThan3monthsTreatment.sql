/*Ind. 34.3*/

SELECT psa.patient_id
FROM isanteplus.patient_status_arv psa, isanteplus.patient p
WHERE p.patient_id = psa.patient_id
  AND DATE_SUB(:endDate, INTERVAL 3 MONTH) < psa.date_started_status
  AND psa.date_started_status < :endDate
  AND psa.id_status = 9
  AND p.date_started_arv < DATE_SUB(:startDate, INTERVAL 3 MONTH)
  AND p.date_started_arv >= DATE_SUB(:startDate, INTERVAL 5 MONTH)
GROUP BY 1;