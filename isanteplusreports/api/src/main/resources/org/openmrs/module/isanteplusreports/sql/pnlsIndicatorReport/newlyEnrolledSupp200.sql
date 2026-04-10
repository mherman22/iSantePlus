SELECT p.patient_id
FROM isanteplus.patient p, isanteplus.patient_laboratory pl,
    (SELECT patient_id, max(visit_date), test_id, test_result, date_test_done
        FROM isanteplus.patient_laboratory pl
       WHERE pl.test_id = 5497
         AND pl.test_result >= 200
         AND pl.date_test_done BETWEEN :startDate AND :endDate
 GROUP BY pl.patient_id) A
WHERE (p.transferred_in IS NULL OR p.transferred_in = 0)
  AND p.patient_id = pl.patient_id
  AND pl.patient_id = A.patient_id
  AND p.date_started_arv IS NOT NULL
  AND p.arv_status IS NOT NULL
  AND p.date_started_arv BETWEEN :startDate AND :endDate
  AND p.voided = 0
GROUP BY pl.patient_id;



