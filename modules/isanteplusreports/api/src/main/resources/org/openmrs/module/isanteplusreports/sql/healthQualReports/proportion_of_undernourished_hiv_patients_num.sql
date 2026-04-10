SELECT
	p.patient_id
FROM
  isanteplus.patient p
WHERE
  p.vih_status = 1 -- HIV+
  AND p.patient_id IN (
    SELECT pv.patient_id
    FROM isanteplus.health_qual_patient_visit pv
    WHERE
      pv.visit_date BETWEEN :startDate AND :endDate
      AND pv.age_in_years > 14
      AND pv.patient_bmi IS NOT NULL
  )
  AND p.patient_id NOT IN ( -- Exclude deceased (159), discontinuations (1667), transfer (159492)
      SELECT discon.patient_id
      FROM isanteplus.discontinuation_reason discon
      WHERE discon.reason IN (159,1667,159492)
  );