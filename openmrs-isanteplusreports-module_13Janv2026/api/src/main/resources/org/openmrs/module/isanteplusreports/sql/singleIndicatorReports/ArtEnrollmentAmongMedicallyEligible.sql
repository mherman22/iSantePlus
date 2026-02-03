SELECT 
	p.patient_id 
FROM `patient` p 
INNER JOIN patient_on_arv pa ON p.patient_id = pa.patient_id 
WHERE 
	p.voided = 0 
	AND pa.voided = 0
	AND pa.visit_date BETWEEN :startDate AND :endDate