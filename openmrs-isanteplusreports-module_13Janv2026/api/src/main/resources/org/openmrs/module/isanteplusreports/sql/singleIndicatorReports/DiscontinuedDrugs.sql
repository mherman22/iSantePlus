SELECT 
	d.patient_id 
FROM isanteplus.`discontinuation_reason` d 
INNER JOIN
(
	SELECT patient_id FROM isanteplus.patient_visit WHERE voided = 0 AND encounter_type IN (1,2) AND visit_date <= :endDate
	UNION
	SELECT patient_id FROM isanteplus.`pediatric_hiv_visit` WHERE voided = 0  AND encounter_date <= :endDate
	UNION
	SELECT patient_id FROM isanteplus.`patient_prescription` WHERE visit_date <= :endDate
) v ON v.patient_id = d.patient_id
WHERE 
	reason = 1667 
	AND visit_date BETWEEN :startDate AND :endDate