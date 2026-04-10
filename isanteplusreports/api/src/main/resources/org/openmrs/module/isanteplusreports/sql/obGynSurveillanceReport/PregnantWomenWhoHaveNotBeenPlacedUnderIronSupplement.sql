SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_ob_gyn` p
WHERE 
	p.pregnant = 1
	AND p.`iron_supplement` = 0 OR p.`iron_supplement` IS NULL
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND p.voided = 0