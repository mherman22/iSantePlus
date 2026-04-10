SELECT p.patient_id
FROM isanteplus.patient p
WHERE (p.transferred_in IS NULL OR p.transferred_in = 0)
  AND p.date_started_arv IS NOT NULL
  AND p.patient_id IN (
    SELECT DISTINCT
        CASE
            WHEN pla.test_id = 856 THEN
                CASE
                    WHEN CAST(pla.test_result AS UNSIGNED) < 1000 THEN pla.patient_id
                END
            WHEN pla.test_id = 1305 THEN
                CASE
                    WHEN pla.test_result = 1306 THEN pla.patient_id
                END
        END
    FROM isanteplus.patient_laboratory pla
    WHERE pla.visit_date  BETWEEN :startDate AND :endDate
      AND pla.voided <> 1
  )
  AND p.arv_status IS NOT NULL
  AND p.date_started_arv BETWEEN :startDate AND :endDate
  AND p.voided = 0;

