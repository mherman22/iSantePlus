select DISTINCT  p.patient_id AS 'Patient Id'
FROM isanteplus.patient p, isanteplus.patient_status_arv ps, 
(select psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status FROM 
		isanteplus.patient_status_arv psa WHERE
		DATE(psa.date_started_status) <= :endDate GROUP BY 1) B
WHERE p.patient_id = ps.patient_id
 AND ps.patient_id = B.patient_id 
 AND DATE(ps.date_started_status) = DATE(B.date_status)
 AND ps.id_status IN (6,8)
 AND DATE(ps.date_started_status) <= :endDate
 AND p.patient_id NOT IN (select pid.patient_id FROM 
 openmrs.patient_identifier_type ptype, openmrs.patient_identifier pid
 WHERE ptype.patient_identifier_type_id = pid.identifier_type 
 AND ptype.uuid = 'e26ca279-8f57-44a5-9ed8-8cc16e90e559' 
 AND pid.voided <> 1)
AND p.voided <> 1;