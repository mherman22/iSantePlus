SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
            AND plab.test_id = 1305 -- qualitative (1305)
                AND plab.test_result = 1306
                AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
        ) THEN p.patient_id else null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
            AND plab.test_id = 1305 -- qualitative (1305)
            AND plab.test_result = 1306
            AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
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
FROM isanteplus.patient p
INNER JOIN isanteplus.patient_laboratory plab
ON p.patient_id = plab.patient_id
WHERE
    p.vih_status = 1 -- HIV+ patient
    AND p.patient_id IN (
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE pv.age_in_years > 14
    )
    AND (	-- viral load test completed
        plab.test_done = 1
        AND (plab.test_id = 1305 OR plab.test_id = 856) -- qualitative (1305) quantitative (856)
        AND plab.test_result IS NOT NULL
        AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
    ) AND p.patient_id IN (	-- on ART for at least 6 months
        SELECT pd.patient_id
        FROM isanteplus.patient_dispensing pd
        WHERE
            pd.drug_id IN ( SELECT arvd.drug_id FROM isanteplus.arv_drugs arvd)
            AND pd.dispensation_date <= DATE_SUB(:startDate, INTERVAL 6 MONTH)
    ) AND (
        p.patient_id IN (
            SELECT pv.patient_id
            FROM isanteplus.health_qual_patient_visit pv
            WHERE DATE(pv.visit_date) BETWEEN :startDate AND :endDate
        ) OR p.patient_id IN (
            SELECT pp.patient_id
            FROM isanteplus.patient_prescription pp
            WHERE
                DATE(pp.visit_date) BETWEEN :startDate AND :endDate
                AND pp.rx_or_prophy = 138405
        )
    ) AND p.patient_id NOT IN ( -- Exclude deceased (159), transfer (159492)
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159, 159492)
	);
