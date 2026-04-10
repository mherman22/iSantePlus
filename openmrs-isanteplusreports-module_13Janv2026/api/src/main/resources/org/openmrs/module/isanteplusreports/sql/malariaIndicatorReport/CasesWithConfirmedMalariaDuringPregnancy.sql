SELECT 
	DISTINCT patient_id 
FROM 
	isanteplus.patient_malaria p
WHERE 
	`confirmed_malaria_preganancy` = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND voided = 0