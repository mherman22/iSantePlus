SELECT 
	DISTINCT patient_id 
FROM 
	isanteplus.patient_malaria p
WHERE 
	`positve_rapid_test_result` = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND voided = 0