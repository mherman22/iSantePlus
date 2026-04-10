SELECT
	p.patient_id
FROM
    isanteplus.patient p
WHERE
    p.vih_status = 1 -- HIV+
    AND p.patient_id IN ( -- Pregnant
        SELECT ppreg.patient_id
        FROM isanteplus.patient_pregnancy ppreg
        WHERE (ppreg.end_date IS NULL AND ppreg.start_date <= :endDate) OR (ppreg.end_date BETWEEN :startDate AND :endDate)
    )
    AND p.patient_id NOT IN ( -- Exclude deceased (159), transfer (159492)
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,159492)
    )
    AND p.patient_id IN ( -- An adult in a given period
        SELECT hqpv.patient_id
        FROM isanteplus.health_qual_patient_visit hqpv
        WHERE hqpv.age_in_years > 14
    );