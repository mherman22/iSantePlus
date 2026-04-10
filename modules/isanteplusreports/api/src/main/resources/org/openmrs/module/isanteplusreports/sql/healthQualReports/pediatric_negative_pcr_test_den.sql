SELECT
	p.patient_id
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
