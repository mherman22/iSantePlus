/*Ind. 35.5*/

SELECT A.patient_id FROM
(SELECT p.patient_id as patient_id, MAX(p.date_started_status) as date_started_status
FROM isanteplus.patient_status_arv p,
         (SELECT psa.patient_id, MAX(psa.date_started_status) as date_started_status
          FROM isanteplus.patient_status_arv psa
          WHERE psa.date_started_status < :startDate
            AND psa.id_status IN (3, 9)
          GROUP BY 1) A,
         (SELECT patient_id, max(visit_date), test_id, test_result, date_test_done
          FROM isanteplus.patient_laboratory pl
          WHERE pl.test_id = 5497
            AND pl.test_result >= 200
            AND pl.date_test_done BETWEEN :startDate AND :endDate
            AND pl.voided = 0
          GROUP BY 1) B
WHERE p.patient_id = A.patient_id
  AND p.patient_id = B.patient_id
  AND p.id_status in (6, 8)
  AND p.date_started_status BETWEEN :startDate AND :endDate
GROUP BY 1) A;




