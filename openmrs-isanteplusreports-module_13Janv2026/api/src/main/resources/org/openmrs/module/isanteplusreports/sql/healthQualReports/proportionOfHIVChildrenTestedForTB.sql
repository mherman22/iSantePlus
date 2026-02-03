SELECT
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'F'
        AND p.patient_id IN (
            SELECT pv.patient_id FROM isanteplus.health_qual_patient_visit pv
            WHERE pv.evaluated_of_tb is true
            AND pv.visit_date BETWEEN :startDate AND :endDate
        )
    ) THEN p.patient_id else null END
    ) AS 'femaleNumerator',
    COUNT( DISTINCT CASE WHEN (
            p.gender = 'M'
            AND p.patient_id IN (
            SELECT pv.patient_id FROM isanteplus.health_qual_patient_visit pv
            WHERE pv.evaluated_of_tb is true
            AND pv.visit_date BETWEEN :startDate AND :endDate
        )
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
FROM
    isanteplus.patient p
WHERE
    p.vih_status = 1
    AND p.patient_id IN (
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE
            pv.encounter_type IN ('9') -- pediatric first HIV visit
            AND pv.visit_date BETWEEN :startDate AND :endDate -- the date of first visit
            AND pv.age_in_years <= 14
    		AND TIMESTAMPDIFF(MONTH, p.birthdate, pv.visit_date) >= 6
    )
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,1667,159492) -- 159-deceased, 1667- Discontinuations, 159492- Transfer
    )
    AND p.patient_id NOT IN ( -- negative PCR result
        SELECT plab.patient_id
        FROM isanteplus.patient_laboratory plab
        WHERE
            plab.test_done = 1
            AND plab.test_id = 844
            AND plab.test_result = 1302
    );
