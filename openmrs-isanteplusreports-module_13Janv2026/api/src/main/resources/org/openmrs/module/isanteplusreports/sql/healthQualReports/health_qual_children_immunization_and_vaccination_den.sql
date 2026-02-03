SELECT
	p.patient_id
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
         WHERE DATE(phv.visit_date) BETWEEN :startDate AND :endDate
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
    )
    AND p.patient_id IN (
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE
            pv.age_in_years <= 14
            AND :age =
            CASE
              WHEN ( TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 0 AND 45
            ) THEN 45
              WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 46 AND 75
            THEN 75
              WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 76 AND 105
            THEN 105
              WHEN TIMESTAMPDIFF(DAY, p.birthdate, pv.visit_date) BETWEEN 106 AND 270
            THEN 270
              ELSE null
            END
    );