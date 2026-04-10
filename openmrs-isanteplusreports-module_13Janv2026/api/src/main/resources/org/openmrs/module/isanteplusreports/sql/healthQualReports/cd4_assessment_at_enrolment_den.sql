SELECT
	p.patient_id
FROM isanteplus.patient p
INNER JOIN isanteplus.health_qual_patient_visit pv ON p.patient_id = pv.patient_id
LEFT JOIN isanteplus.patient_laboratory pl ON p.patient_id = pl.patient_id -- join by patient_id, so that we can look for the first HIV visit (numerator)
WHERE p.vih_status = '1' -- HIV+ patient
    AND pv.encounter_type IN ('1', '9') -- adult or pediatric first HIV visit
    AND pv.visit_date BETWEEN :startDate AND :endDate -- the date of first visit
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN ('159', '1667', '159492') -- not deceased, discontinued and transferred
    )
    AND pv.age_in_years > 14
    