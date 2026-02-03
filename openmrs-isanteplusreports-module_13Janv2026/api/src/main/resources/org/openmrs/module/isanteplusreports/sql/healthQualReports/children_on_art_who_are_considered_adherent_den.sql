SELECT
	p.patient_id
FROM
isanteplus.patient p
INNER JOIN isanteplus.health_qual_patient_visit pv
  ON p.patient_id = pv.patient_id
LEFT JOIN isanteplus.patient_prescription pp
  ON p.patient_id = pp.patient_id
WHERE
  pv.adherence_evaluation IS NOT NULL
  AND (
      DATE(pv.visit_date) BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate AND pv.encounter_type IN (9,10) -- Paeds initial and followup encounter types
    OR (
      DATE(pp.visit_date) BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate
    )
  )
  AND p.patient_id IN (	-- on ART for 3 months or more
    SELECT pd.patient_id
    FROM isanteplus.patient_dispensing pd
    WHERE
      pd.drug_id IN (SELECT arvd.drug_id FROM isanteplus.arv_drugs arvd)
      AND pd.dispensation_date <= DATE_SUB(:currentDate, INTERVAL 3 MONTH)
  )
  AND p.patient_id NOT IN (
    SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492))
  AND p.patient_id NOT IN (
    SELECT plab.patient_id
    FROM isanteplus.patient_laboratory plab
    WHERE
      plab.test_done = 1
      AND plab.test_id = 844
      AND plab.test_result = 1302
  )
  AND pv.age_in_years <= 14; -- An child
  