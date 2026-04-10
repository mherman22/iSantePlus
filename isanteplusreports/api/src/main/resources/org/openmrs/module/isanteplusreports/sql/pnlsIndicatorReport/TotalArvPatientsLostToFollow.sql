/*Ind. 34*/
SELECT patient_id
FROM (
     SELECT psa.patient_id, MAX(psa.date_started_status) as date_started_status
     FROM isanteplus.patient_status_arv psa
     WHERE DATE_SUB(:endDate, INTERVAL 3 MONTH) < psa.date_started_status
       AND psa.date_started_status < :endDate
       AND psa.id_status IN (1,2,3,9)
     GROUP BY 1
UNION ALL
SELECT distinct pat.patient_id, B.date_started_status as date_started_status
FROM isanteplus.patient_status_arv ps, isanteplus.patient_on_art pat,
 (SELECT psa.patient_id, MAX(psa.date_started_status) as date_started_status
  FROM isanteplus.patient_status_arv psa
  WHERE DATE_SUB(:endDate, INTERVAL 3 MONTH) < psa.date_started_status
    AND psa.date_started_status < :endDate
    AND psa.id_status IN (1,2,3,9)
  GROUP BY 1) B
WHERE ps.patient_id = pat.patient_id
  AND ps.patient_id = B.patient_id
  AND ps.id_status = 9
  AND DATE_SUB(:endDate, INTERVAL 3 MONTH) < ps.date_started_status
  AND ps.date_started_status < :endDate
  AND pat.migrated = 1
    ) AS combined_results;


