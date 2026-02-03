SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_ob_gyn` p
WHERE 
	p.pregnant = 1
	AND p.high_risk = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND p.voided = 0
	