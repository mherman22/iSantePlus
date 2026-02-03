SELECT 
	DISTINCT v.patient_id 
FROM
(
	SELECT patient_id FROM isanteplus.`patient_visit` WHERE voided = 0 AND encounter_type IN (1,2) AND visit_date <= :endDate
	UNION
	SELECT patient_id FROM isanteplus.`pediatric_hiv_visit` WHERE voided = 0  AND encounter_date <= :endDate
	UNION
	SELECT patient_id FROM isanteplus.`patient_prescription` WHERE visit_date <= :endDate	
) v
INNER JOIN Isanteplus.`patient_prescription` pp ON pp.patient_id = v.patient_id
INNER JOIN Isanteplus.`discontinuation_reason` d ON d.patient_id = v.patient_id
INNER JOIN Isanteplus.`patient` p ON p.patient_id = v.patient_id
WHERE 
	p.voided = 0
	AND pp.drug_id = 105281 -- Cotrimoxazole
	AND d.visit_date BETWEEN :startDate AND :endDate