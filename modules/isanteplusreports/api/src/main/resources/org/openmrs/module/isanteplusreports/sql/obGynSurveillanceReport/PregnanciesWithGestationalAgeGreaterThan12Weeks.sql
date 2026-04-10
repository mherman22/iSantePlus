SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_ob_gyn` p
WHERE 
	p.pregnant = 1
	AND p.gestation_greater_than_12_wks = 1
	AND (haemogram = 0 OR haemogram IS NULL)
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND p.voided = 0
	