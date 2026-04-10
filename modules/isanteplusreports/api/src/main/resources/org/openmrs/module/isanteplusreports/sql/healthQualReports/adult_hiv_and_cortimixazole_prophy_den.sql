SELECT
	p.patient_id
FROM
    isanteplus.patient p
    LEFT JOIN isanteplus.patient_prescription pp
    ON p.patient_id = pp.patient_id
WHERE
    p.vih_status = 1
    AND p.patient_id IN (
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE (
                DATE(pv.visit_date) BETWEEN :startDate AND :endDate
                OR DATE(pp.visit_date) BETWEEN :startDate AND :endDate
            )
            AND pv.age_in_years > 14
    )
    AND p.patient_id IN (
        SELECT poa.patient_id
        FROM isanteplus.patient_on_arv poa
	)
	AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,159492)
    );