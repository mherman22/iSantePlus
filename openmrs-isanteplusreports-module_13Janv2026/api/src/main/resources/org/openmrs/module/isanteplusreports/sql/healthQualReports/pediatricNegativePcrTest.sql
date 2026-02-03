SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
            AND p.patient_id IN (
                SELECT plab.patient_id
                FROM isanteplus.patient_laboratory plab
                WHERE
                    plab.test_done = 1
                    AND plab.test_id = 844
                    AND plab.test_result = 1302
                    AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
            )
        ) THEN p.patient_id else null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
            AND p.patient_id IN (
                SELECT plab.patient_id
                FROM isanteplus.patient_laboratory plab
                WHERE
                    plab.test_done = 1
                    AND plab.test_id = 844
                    AND plab.test_result = 1302
                    AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
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
    p.patient_id IN (
        SELECT phv.patient_id
        FROM
        isanteplus.health_qual_patient_visit phv
        LEFT JOIN isanteplus.patient_prescription pp
        ON phv.patient_id = pp.patient_id
        WHERE
            (
	            DATE(phv.visit_date) BETWEEN :startDate AND :endDate
	            OR 
	            DATE(pp.visit_date) BETWEEN :startDate AND :endDate
            )
		    AND TIMESTAMPDIFF(MONTH, p.birthdate, phv.visit_date) <= 18
		    AND TIMESTAMPDIFF(WEEK, p.birthdate, phv.visit_date) >= 4
    )
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159, 1667, 159492)
    );
