SELECT p.patient_id
FROM isanteplus.patient p, isanteplus.patient_pregnancy pp
WHERE p.patient_id = pp.patient_id
  AND p.gender = "F"
  /*AND pp.end_date >= :endDate*/
  AND p.voided = 0;

