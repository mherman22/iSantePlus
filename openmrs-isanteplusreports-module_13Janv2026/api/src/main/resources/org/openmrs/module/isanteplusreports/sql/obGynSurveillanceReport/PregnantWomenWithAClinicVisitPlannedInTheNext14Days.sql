SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_ob_gyn` p
WHERE 
	p.pregnant = 1
	AND p.next_visit_date BETWEEN DATE(NOW()) AND DATE_ADD(NOW(), INTERVAL 14 DAY)
	AND p.voided = 0
	