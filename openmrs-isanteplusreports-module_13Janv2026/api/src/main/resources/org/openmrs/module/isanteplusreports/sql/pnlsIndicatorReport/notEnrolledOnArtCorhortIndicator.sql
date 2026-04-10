SELECT p.patient_id
FROM isanteplus.patient p, isanteplus.patient_on_art pat
WHERE p.patient_id = pat.patient_id
  AND (p.date_started_arv IS NULL
    OR (p.date_started_arv >= :startDate and p.date_started_arv >= :endDate))
  AND p.vih_status = 1
  AND p.voided = 0
  AND pat.date_tested_hiv_postive IS NOT NULL
  AND pat.date_tested_hiv_postive  BETWEEN :startDate AND :endDate;












