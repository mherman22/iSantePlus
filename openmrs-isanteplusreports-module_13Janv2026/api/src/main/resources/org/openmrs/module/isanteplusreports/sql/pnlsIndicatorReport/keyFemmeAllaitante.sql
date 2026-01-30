SELECT p.patient_id
FROM isanteplus.patient p ,isanteplus.patient_on_art pa
WHERE p.patient_id = pa.patient_id
  AND p.gender = "F"
  AND pa.breast_feeding = 1
  /*AND pa.date_started_breast_feeding BETWEEN :startDate AND :endDate*/
  AND p.voided = 0;
