SELECT
	COUNT( DISTINCT CASE WHEN (
        p.gender = 'F'
            AND p.patient_id IN (
                SELECT plab.patient_id
                FROM isanteplus.patient_laboratory plab
                WHERE
                    plab.test_done = 1
                    AND plab.test_id = 844
                    AND plab.test_result IS NOT NULL
                    AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
            )
        ) THEN p.patient_id else null END
	) AS 'femaleNumerator',
    COUNT( DISTINCT CASE WHEN (
        p.gender = 'M'
        AND p.patient_id IN (
            SELECT plab.patient_id
            FROM isanteplus.patient_laboratory plab
            WHERE
                plab.test_done = 1
                AND plab.test_id = 844
                AND plab.test_result IS NOT NULL
                AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
            )
        ) THEN p.patient_id else null END
	) AS 'maleNumerator',
	COUNT( DISTINCT CASE WHEN (
			p.gender = 'F'
		) THEN p.patient_id else null END
	) AS 'femaleDenominator',
    COUNT( DISTINCT CASE WHEN (
			p.gender = 'M'
		) THEN p.patient_id else null END
	) AS 'maleDenominator'
FROM
	isanteplus.patient p
WHERE
	p.patient_id IN (
        SELECT phv.patient_id
        FROM isanteplus.health_qual_patient_visit phv
        LEFT JOIN isanteplus.patient_prescription pp
            ON phv.patient_id = pp.patient_id
        WHERE
            (
	            DATE(phv.visit_date) BETWEEN :startDate AND :endDate AND phv.encounter_type IN (9,10) -- Paeds initial and followup encounter types
	            OR 
	            DATE(pp.visit_date) BETWEEN :startDate AND :endDate
            )
		    AND TIMESTAMPDIFF(MONTH, p.birthdate, phv.visit_date) <= 12
		    AND TIMESTAMPDIFF(WEEK, p.birthdate, phv.visit_date) >= 4
	)
	
	AND p.patient_id IN ( -- Eligible for PCR
		SELECT DISTINCT patient_id -- Exposed to HIV between 4 weeks and 12 months
        FROM isanteplus.pediatric_hiv_visit v
        WHERE 
            actual_vih_status = 1405 ANd v.encounter_date <= :endDate
            AND TIMESTAMPDIFF(WEEK, p.birthdate, v.encounter_date) >= 4
            AND TIMESTAMPDIFF(MONTH, p.birthdate, v.encounter_date) <= 12
		UNION		
        SELECT DISTINCT pvt.patient_id -- Exposed to HIV between 12 and 18 months with a +ve HIV antigen test
        FROM isanteplus.pediatric_hiv_visit v
        INNER JOIN isanteplus.virological_tests pvt ON v.patient_id = pvt.patient_id
        WHERE 
            pvt.answer_concept_id = 1030
            AND pvt.test_result = 703
            AND pvt.encounter_date <= :endDate	
            AND v.encounter_date <= :endDate		
            AND v.actual_vih_status = 1405	
            AND TIMESTAMPDIFF(MONTH, p.birthdate, v.encounter_date) BETWEEN 12 AND 18
	)
	AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159, 1667, 159492)
	);
