SELECT 
	DISTINCT patient_id 
FROM 
	isanteplus.patient_malaria p
WHERE 
	`fever_for_less_than_2wks` = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND voided = 0