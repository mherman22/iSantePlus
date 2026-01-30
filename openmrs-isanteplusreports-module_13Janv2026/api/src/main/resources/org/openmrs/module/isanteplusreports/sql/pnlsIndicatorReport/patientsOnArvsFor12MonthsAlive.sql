SELECT p.patient_id
FROM isanteplus.patient_on_art pat, isanteplus.patient_status_arv psa, 
isanteplus.patient p, (SELECT ps.patient_id, MAX(ps.date_started_status) AS max_staus_date, 
ps.id_status FROM isanteplus.patient_status_arv ps 
WHERE ps.date_started_status <= :endDate GROUP BY 1) B
WHERE p.patient_id = pat.patient_id
AND p.patient_id = psa.patient_id
AND p.patient_id = B.patient_id
AND psa.id_status = B.id_status
AND DATE(psa.date_started_status) = DATE(B.max_staus_date)
AND ((p.transferred_in = 1 AND TIMESTAMPDIFF(MONTH, pat.date_started_arv_for_transfered, :endDate) = 12)
OR (TIMESTAMPDIFF(MONTH, p.date_started_arv, :endDate) = 12))
AND psa.id_status IN (6,8,9);