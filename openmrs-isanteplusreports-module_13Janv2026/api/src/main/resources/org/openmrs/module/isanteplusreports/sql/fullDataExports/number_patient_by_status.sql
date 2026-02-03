/*Number of patients par ARV status*/
select asl.name_fr as 'Statut ARV', COUNT(DISTINCT p.patient_id) AS Total
FROM isanteplus.arv_status_loockup asl, isanteplus.patient_status_arv p,
(SELECT ps.patient_id, MAX(ps.start_date) as start_date 
FROM isanteplus.patient_status_arv ps WHERE ps.start_date <= :endDate GROUP BY 1) B
WHERE asl.id = p.id_status
AND p.patient_id = B.patient_id
AND p.start_date = B.start_date
AND p.start_date <= :endDate
GROUP BY asl.name_fr;