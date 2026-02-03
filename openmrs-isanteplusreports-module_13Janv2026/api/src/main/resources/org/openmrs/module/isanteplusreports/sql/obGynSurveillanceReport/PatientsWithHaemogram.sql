SELECT 
	DISTINCT p.patient_id 
FROM 
	isanteplus.`patient_laboratory` p
WHERE 
	p.test_id IN (163701, 163700)
	AND p.visit_date BETWEEN :startDate AND :endDate
	AND p.voided = 0