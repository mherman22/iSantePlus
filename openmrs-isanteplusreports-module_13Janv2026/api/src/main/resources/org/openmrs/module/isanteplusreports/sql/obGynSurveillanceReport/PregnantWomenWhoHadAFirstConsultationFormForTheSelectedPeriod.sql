SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_ob_gyn` p
WHERE 
	`encounter_type_id` = 12
	AND p.pregnant = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND p.voided = 0