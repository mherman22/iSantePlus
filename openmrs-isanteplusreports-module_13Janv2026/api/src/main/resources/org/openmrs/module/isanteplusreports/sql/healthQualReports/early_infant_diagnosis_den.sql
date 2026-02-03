SELECT
	p.patient_id
FROM
	isanteplus.patient p
WHERE
	(
		p.patient_id IN (
	        SELECT phv.patient_id -- between 4 weeks and 12 months of age who have had a HIV First Pediatric Visit or Pediatric Follow-up or Pediatric Rx
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
		OR 
		p.patient_id IN (
	        SELECT phv.patient_id -- between 12 and 18 months of age who have had HIV First Pediatric Visit or Pediatric Follow-up or Pediatric Rx AND a positive rapid test
	        FROM isanteplus.health_qual_patient_visit phv
	        LEFT JOIN isanteplus.patient_prescription pp
	            ON phv.patient_id = pp.patient_id
	        INNER JOIN isanteplus.patient_laboratory plab 
	        	ON plab.patient_id = phv.patient_id 
	        WHERE
	        	(
		            DATE(phv.visit_date) BETWEEN :startDate AND :endDate AND phv.encounter_type IN (9,10) -- Paeds initial and followup encounter types
		            OR 
		            DATE(pp.visit_date) BETWEEN :startDate AND :endDate -- Prescription
	            )
			    AND plab.test_id = 1040 -- Rapid HIV Test
			    AND plab.test_result = 703 -- Positive
				AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate
			    AND TIMESTAMPDIFF(MONTH, p.birthdate, phv.visit_date) BETWEEN 12 AND 18
		)
	)
	AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159, 1667, 159492)
	)
    AND p.patient_id IN (
        SELECT plab.patient_id
        FROM isanteplus.patient_laboratory plab
        WHERE
            plab.test_done = 1
            AND plab.test_id = 844
            AND plab.test_result IS NOT NULL
            AND DATE(plab.date_test_done) <= :endDate
        );
	