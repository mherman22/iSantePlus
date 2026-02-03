SELECT
	p.patient_id
FROM
isanteplus.patient p
WHERE
  p.patient_id IN (
    SELECT phv.patient_id
    FROM isanteplus.health_qual_patient_visit phv
    LEFT JOIN isanteplus.patient_prescription pp
    ON phv.patient_id = pp.patient_id
    WHERE (
        DATE(phv.visit_date) BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate AND phv.encounter_type IN (9,10) -- Paeds initial and followup encounter types
        OR (
          DATE(pp.visit_date) BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate
        )
      )
      AND phv.age_in_years <= 14 -- An child
  )
  AND p.patient_id IN (SELECT poa.patient_id FROM isanteplus.patient_on_arv poa)
  AND p.patient_id NOT IN (
    SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492)
  )
  AND p.patient_id NOT IN (
    SELECT plab.patient_id
    FROM isanteplus.patient_laboratory plab
    WHERE
      plab.test_done = 1
      AND plab.test_id = 844
      AND plab.test_result = 1302
  );