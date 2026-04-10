SELECT DISTINCT patd.patient_id FROM isanteplus.patient p, 
isanteplus.patient_dispensing patd,
(select distinct pdisp.patient_id, DATE(pdisp.next_dispensation_date) AS next_visit
FROM isanteplus.patient_dispensing pdisp
WHERE pdisp.arv_drug = 1065 AND pdisp.voided <> 1
AND DATE(pdisp.next_dispensation_date) between :startDate AND :endDate
GROUP BY 1) B, isanteplus.patient_status_arv ps,
(select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) C
WHERE p.patient_id = patd.patient_id
AND patd.patient_id = B.patient_id
AND patd.patient_id = ps.patient_id
AND ps.patient_id = C.patient_id
AND DATE(ps.date_started_status) = C.date_status
AND ps.id_status = 6
AND patd.voided <> 1
AND next_visit BETWEEN :startDate AND :endDate
AND DATE(ps.date_started_status) >= DATE(B.next_visit)
AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate
AND patd.arv_drug = 1065;