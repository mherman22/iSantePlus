/*Ind. 32*/

SELECT patient_id
FROM (
         SELECT psa.patient_id, MAX(psa.date_started_status) as date_started_status
         FROM isanteplus.patient_status_arv psa
         WHERE psa.date_started_status BETWEEN :startDate AND :endDate
           AND psa.id_status IN (1,2,3,9)
         GROUP BY 1
UNION ALL
SELECT pat.patient_id, B.date_started_status as date_started_status
FROM isanteplus.patient_status_arv ps, isanteplus.patient_on_art pat,
     (SELECT psar.patient_id,
             MAX(psar.date_started_status) as date_started_status
      FROM isanteplus.patient_status_arv psar
      WHERE psar.date_started_status BETWEEN :startDate AND :endDate
      GROUP BY 1) B
WHERE ps.patient_id = pat.patient_id
  AND ps.patient_id = B.patient_id
  AND DATE(ps.date_started_status) = DATE(B.date_started_status)
  AND ps.id_status = 9
  AND ps.date_started_status BETWEEN :startDate AND :endDate
  AND pat.migrated = 1
    ) AS combined_results;






