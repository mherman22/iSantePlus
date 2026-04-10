/*Ind. 37*/
SELECT p.patient_id
FROM isanteplus.patient p
WHERE (p.transferred_in IS NULL OR p.transferred_in = 0)
  AND p.date_started_arv IS NOT NULL
  AND p.arv_status IS NOT NULL
  AND p.date_started_arv BETWEEN :startDate AND :endDate
  AND p.voided = 0
  AND p.patient_id IN (
    SELECT DISTINCT(pla.patient_id)
    FROM isanteplus.patient_laboratory pla
    WHERE pla.test_id IN (856, 1305)
      AND pla.test_result IS NOT NULL
      AND pla.visit_date  BETWEEN :startDate AND :endDate
      AND pla.voided <> 1
  );

