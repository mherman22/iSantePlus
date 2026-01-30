SELECT
	p.patient_id
FROM
    isanteplus.patient p
    LEFT JOIN isanteplus.patient_prescription pp
        ON p.patient_id = pp.patient_id
WHERE
    p.vih_status = 1
    AND p.patient_id IN ( -- Pediatric Rx
        SELECT patient_id
        FROM isanteplus.patient_prescription AS innerpp
        WHERE
        DATE(innerpp.visit_date) BETWEEN :startDate AND :endDate
    )
    AND p.patient_id IN ( -- ‌HIV First Pediatric Visit OR Pediatric Follow‐up‌
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE pv.encounter_id 
        AND DATE(pv.visit_date) BETWEEN :startDate AND :endDate
        AND pv.encounter_type IN (9,10)
    	AND TIMESTAMPDIFF(YEAR, p.birthdate, pv.visit_date) BETWEEN 1 AND 14
    )
    AND p.patient_id NOT IN ( -- exclude active TB
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE pv.is_active_tb IS true
        AND DATE(pv.visit_date) BETWEEN :startDate AND :endDate
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