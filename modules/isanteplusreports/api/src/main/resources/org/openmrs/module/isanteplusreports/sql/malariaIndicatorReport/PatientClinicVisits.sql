SELECT 
	DISTINCT patient_id 
FROM 
	isanteplus.patient_visit p
WHERE 
	p.visit_date BETWEEN :startDate AND :endDate
	AND voided = 0