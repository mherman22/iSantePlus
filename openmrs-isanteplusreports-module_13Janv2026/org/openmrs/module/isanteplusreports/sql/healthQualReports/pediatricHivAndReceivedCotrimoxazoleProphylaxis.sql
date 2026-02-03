SELECT
  COUNT( DISTINCT CASE WHEN (
      p.gender = 'F'
      AND p.patient_id
      AND pp.rx_or_prophy = 163768
      AND pp.drug_id = 105281
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
    ) THEN p.patient_id else null END
  ) AS 'femaleNumerator',
  COUNT( DISTINCT CASE WHEN (
      p.gender = 'M'
      AND p.patient_id
      AND pp.rx_or_prophy = 163768
      AND pp.drug_id = 105281
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
    ) THEN p.patient_id else null END
  ) AS 'maleNumerator',
  COUNT( DISTINCT CASE WHEN (
      p.gender = 'F'
    ) THEN p.patient_id else null END
  ) AS 'femaleDenominator',
  COUNT( DISTINCT CASE WHEN (
      p.gender = 'M'
    ) THEN p.patient_id else null END
  ) AS 'maleDenominator'
FROM isanteplus.patient p
LEFT JOIN isanteplus.patient_prescription pp
ON p.patient_id = pp.patient_id
WHERE
  p.patient_id IN (
    SELECT phv.patient_id
    FROM isanteplus.health_qual_patient_visit phv
    WHERE (
        DATE(phv.visit_date) BETWEEN :startDate AND :endDate
        OR (DATE(pp.visit_date) BETWEEN :startDate AND :endDate AND pp.rx_or_prophy = 138405)
      )
      AND phv.age_in_years <= 14
  )
  AND p.patient_id NOT IN (
    SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492)
  )
  AND (
    TIMESTAMPDIFF(MONTH, p.birthdate,:endDate) <= 18
    OR p.patient_id NOT IN (
      SELECT plab.patient_id
      FROM isanteplus.patient_laboratory plab
      WHERE
        plab.test_done = 1
        AND plab.test_id = 844
        AND plab.test_result = 1302
    )
  )
  AND TIMESTAMPDIFF(WEEK,p.birthdate,:endDate) >= 4;