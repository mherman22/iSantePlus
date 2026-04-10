SELECT
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'F'
        AND p.patient_id IN (
            SELECT patient_id
            FROM isanteplus.patient_on_arv
        )
    ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'M'
        AND p.patient_id IN (
            SELECT patient_id
            FROM isanteplus.patient_on_arv
        )
    ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'F'
    ) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'M'
    ) THEN p.patient_id ELSE null END
    ) AS 'maleDenominator'
FROM isanteplus.patient p
INNER JOIN isanteplus.health_qual_patient_visit pv ON p.patient_id = pv.patient_id
WHERE p.vih_status = '1' -- HIV+ patient
    AND pv.encounter_type IN ('1', '9') -- adult or pediatric first HIV visit
    AND pv.visit_date BETWEEN :startDate AND :endDate -- the date of first visit
    AND p.patient_id NOT IN (
            SELECT discon.patient_id
            FROM isanteplus.discontinuation_reason discon
            WHERE discon.reason IN ('159', '159492') -- not deceased and transferred
        )
    AND pv.age_in_years > 14;
