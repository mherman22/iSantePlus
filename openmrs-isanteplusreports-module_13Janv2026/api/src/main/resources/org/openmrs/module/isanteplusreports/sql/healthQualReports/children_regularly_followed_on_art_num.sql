SELECT
	p.patient_id
FROM
    isanteplus.patient p
    INNER JOIN isanteplus.patient_on_arv poa
        ON p.patient_id = poa.patient_id
    INNER JOIN isanteplus.health_qual_patient_visit pv
        ON p.patient_id = pv.patient_id
    INNER JOIN openmrs.encounter_type et
    ON pv.encounter_type = et.encounter_type_id
    LEFT JOIN isanteplus.patient_prescription pp
        ON poa.patient_id = pp.patient_id
WHERE
    p.vih_status = 1
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,1667,159492)
    )
    AND poa.patient_id NOT IN (
        SELECT plab.patient_id
        FROM isanteplus.patient_laboratory plab
        WHERE
            plab.test_done = 1
            AND plab.test_id = 844
            AND plab.test_result = 1302
    )
    AND pv.age_in_years <= 14
   /* AND DATE(pv.visit_date) BETWEEN SUBDATE(now(), INTERVAL :period MONTH) AND now()*/
    AND et.uuid IN('349ae0b4-65c1-4122-aa06-480f186c8350','33491314-c352-42d0-bd5d-a9d0bffc9bf1')
           /* OR (
                DATE(pp.visit_date) BETWEEN DATE_SUB(DATE(now()), INTERVAL :period MONTH) AND DATE(:currentDate)
            );*/