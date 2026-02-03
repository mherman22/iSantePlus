SELECT
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
            AND pv.adherence_evaluation IN (90, 100)
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
            AND pv.adherence_evaluation IN (90, 100)
        ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'F'
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT(DISTINCT CASE WHEN (
            p.gender = 'M'
        ) THEN p.patient_id ELSE null END
        ) AS 'maleDenominator'
FROM isanteplus.patient p
INNER JOIN isanteplus.health_qual_patient_visit pv
ON p.patient_id = pv.patient_id
WHERE p.vih_status = '1'
    AND p.patient_id IN (	-- on ART for 3 months or more
        SELECT pd.patient_id
        FROM isanteplus.patient_dispensing pd
        WHERE
        pd.drug_id IN (SELECT arvd.drug_id FROM isanteplus.arv_drugs arvd)
        AND pd.dispensation_date <= DATE_SUB(:currentDate, INTERVAL 3 MONTH)
    )
    AND pv.visit_date BETWEEN SUBDATE(:currentDate, INTERVAL 3 MONTH) AND :currentDate
    AND pv.adherence_evaluation IS NOT NULL 
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN ('159', '159492')
    )
    AND pv.age_in_years > 14; -- An adult
