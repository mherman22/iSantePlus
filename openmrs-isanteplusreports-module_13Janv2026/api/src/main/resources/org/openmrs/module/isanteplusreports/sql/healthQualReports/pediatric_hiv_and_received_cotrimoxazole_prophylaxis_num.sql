SELECT
	p.patient_id
FROM isanteplus.patient p
LEFT JOIN isanteplus.patient_prescription pp
ON p.patient_id = pp.patient_id
WHERE
  p.vih_status = '1' -- HIV+ patient
  AND p.patient_id IN (
    SELECT phv.patient_id
    FROM isanteplus.health_qual_patient_visit phv
    WHERE (
        DATE(phv.visit_date) BETWEEN :startDate AND :endDate AND encounter_type IN (9,10) -- Paeds initial and followup encounter types
        OR (DATE(pp.visit_date) BETWEEN :startDate AND :endDate)
      )
      AND phv.age_in_years <= 14 -- Paediatric
      AND TIMESTAMPDIFF(WEEK, p.birthdate, phv.visit_date) > 4 -- Older than 4 weeks of age
  )
  AND p.patient_id NOT IN (
    SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492)
  )
  AND p.patient_id NOT IN ( -- Negative PCR Result after 18 Months of Age
	SELECT plab.patient_id
	FROM isanteplus.patient_laboratory plab
	WHERE
		plab.test_done = 1
		AND plab.test_id = 844
		AND plab.test_result = 1302
		AND TIMESTAMPDIFF(MONTH, p.birthdate,:endDate) >= 18 
  )
  AND p.patient_id
  AND pp.drug_id = 105281
  AND (pp.visit_date BETWEEN :startDate AND :endDate);