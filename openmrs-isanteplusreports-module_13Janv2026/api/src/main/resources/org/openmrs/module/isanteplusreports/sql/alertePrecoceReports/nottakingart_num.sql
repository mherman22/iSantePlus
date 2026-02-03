SELECT DISTINCT B.patient_id FROM 
(select distinct pdisp.patient_id, DATE(pdisp.next_dispensation_date) AS next_visit
FROM isanteplus.patient_dispensing pdisp
WHERE pdisp.arv_drug = 1065 AND pdisp.voided <> 1
AND DATE(pdisp.next_dispensation_date) between :startDate AND :endDate
GROUP BY 1) B, isanteplus.patient_status_arv ps,
(select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) C
WHERE B.patient_id = ps.patient_id
AND ps.patient_id = C.patient_id
AND DATE(ps.date_started_status) = C.date_status
AND ps.id_status IN (8,9)
AND B.next_visit < C.date_status
AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate;