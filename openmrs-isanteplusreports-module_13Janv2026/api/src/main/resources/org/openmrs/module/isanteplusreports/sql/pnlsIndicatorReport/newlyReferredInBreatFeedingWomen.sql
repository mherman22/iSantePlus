
SELECT p.patient_id
 FROM isanteplus.patient p, isanteplus.patient_on_art pa
WHERE p.transferred_in = 1
  AND p.date_started_arv IS NOT NULL
  AND p.date_started_arv IS NOT NULL
  AND (p.birthdate <>'' AND p.birthdate is not null)
  AND p.date_started_arv BETWEEN :startDate AND :endDate
  AND pa.date_started_breast_feeding BETWEEN :startDate AND :endDate
  AND p.gender = "F"
  AND pa.breast_feeding = 1
  AND p.voided = 0;