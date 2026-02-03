SELECT
	p.patient_id
FROM
    isanteplus.patient p
    INNER JOIN isanteplus.patient_on_arv poa    -- patient on arv
        ON p.patient_id = poa.patient_id
    INNER JOIN isanteplus.health_qual_patient_visit pv -- pediatric first visit
        ON p.patient_id = pv.patient_id
    LEFT JOIN isanteplus.patient_prescription pp
        ON poa.patient_id = pp.patient_id
WHERE
    p.vih_status = '1' -- HIV+ patient
    AND p.patient_id NOT IN (        -- excluding 
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,1667,159492)
    )
    AND poa.patient_id NOT IN (    -- negative PCR result
        SELECT plab.patient_id
        FROM isanteplus.patient_laboratory plab
        WHERE
            plab.test_done = 1
            AND plab.test_id = 844
            AND plab.test_result = 1302
    )
    AND pv.age_in_years <= 14; -- An child