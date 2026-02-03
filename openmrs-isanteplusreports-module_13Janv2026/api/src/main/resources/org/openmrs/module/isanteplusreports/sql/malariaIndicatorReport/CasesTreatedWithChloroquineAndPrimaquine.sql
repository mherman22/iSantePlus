SELECT 
	DISTINCT patient_id 
FROM 
	isanteplus.patient_malaria p
WHERE 
	`treated_with_chloroquine` = 1 
	AND `treated_with_primaquine` = 1
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND voided = 0