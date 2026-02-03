SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F' AND
            p.patient_id IN (
        SELECT hqpv.patient_id FROM isanteplus.health_qual_patient_visit hqpv
        WHERE hqpv.nutritional_assessment_completed IS TRUE
          AND hqpv.visit_date BETWEEN :startDate AND :endDate
      )
        ) THEN p.patient_id else null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M' AND
            p.patient_id IN (
        SELECT hqpv.patient_id FROM isanteplus.health_qual_patient_visit hqpv
        WHERE hqpv.nutritional_assessment_completed IS TRUE
          AND hqpv.visit_date BETWEEN :startDate AND :endDate
      )
        ) THEN p.patient_id else null END
    ) AS 'maleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
        ) THEN p.patient_id else null END
    ) AS 'femaleDenominator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
        ) THEN p.patient_id else null END
    ) AS 'maleDenominator'
FROM
    isanteplus.patient p
WHERE
  (p.vih_status = 1 -- Include HIV patients
    OR p.patient_id IN ( -- Include exposed children
      SELECT DISTINCT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE
        pv.patient_id NOT IN (    -- Condition E
          SELECT DISTINCT plab.patient_id
          FROM isanteplus.patient_laboratory plab
          WHERE
            plab.test_done = 1
            AND plab.test_id = 844  -- PCR
            AND plab.test_result = 1301 -- positive
            AND plab.date_test_done BETWEEN :startDate AND :endDate)
        AND pv.patient_id NOT IN ( -- Condition E positive virology test
            SELECT DISTINCT pvt.patient_id
            FROM isanteplus.virological_tests pvt
            WHERE answer_concept_id = 1030
            AND test_result = 703
            AND pvt.encounter_date BETWEEN :startDate AND :endDate)
        AND pv.age_in_years < 14 -- An child
        AND ( pv.patient_id IN (  -- Condition D
            SELECT DISTINCT pp.patient_id
            FROM isanteplus.patient_prescription pp
            WHERE pp.rx_or_prophy = 163768 -- prophylaxis
            AND drug_id IN (SELECT drug_id FROM isanteplus.arv_drugs))
          OR pv.patient_id IN ( -- Condition B
            SELECT DISTINCT patient_id
            FROM isanteplus.pediatric_hiv_visit
            WHERE actual_vih_status = 1405) -- HIV EXPOSED
          OR pv.patient_id IN ( -- Condition A PCR result
            SELECT DISTINCT plab.patient_id
            FROM isanteplus.patient_laboratory plab
            WHERE plab.test_done = 1
              AND plab.test_id = 844  -- PCR
              AND plab.test_result = 1302 -- negative
              AND plab.date_test_done < :endDate)
          OR pv.patient_id IN ( -- Condition A virology test
            SELECT DISTINCT pvt.patient_id
            FROM isanteplus.virological_tests pvt
            WHERE answer_concept_id = 1030
            AND test_result = 664
            AND pvt.encounter_date  < :endDate)
      )
    )
  )
    AND p.patient_id IN (
        SELECT phv.patient_id
        FROM isanteplus.health_qual_patient_visit phv
    WHERE (
        DATE(phv.visit_date) BETWEEN :startDate AND :endDate AND phv.encounter_type IN (9,10) -- Paeds initial and followup encounter types
      )
      AND phv.age_in_years <= 14
    )
    AND p.patient_id NOT IN ( -- excludes
        SELECT discon.patient_id
    FROM isanteplus.discontinuation_reason discon
    WHERE discon.reason IN (159,1667,159492)
    )
    AND p.patient_id NOT IN ( -- negative PCR result
        SELECT plab.patient_id
        FROM isanteplus.patient_laboratory plab
        WHERE
            plab.test_done = 1
            AND plab.test_id = 844
            AND plab.test_result = 1302
    );
