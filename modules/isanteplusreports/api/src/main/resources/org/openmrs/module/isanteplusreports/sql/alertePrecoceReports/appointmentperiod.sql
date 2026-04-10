select distinct pdisp.patient_id FROM isanteplus.patient p, 
isanteplus.patient_dispensing pdisp, isanteplus.patient_status_arv ps,
(select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) C
WHERE p.patient_id = pdisp.patient_id
AND pdisp.patient_id = ps.patient_id
AND ps.patient_id = C.patient_id
AND DATE(ps.date_started_status) = C.date_status
AND ps.id_status IN(6,8,9)
AND pdisp.arv_drug = 1065
AND DATE(pdisp.next_dispensation_date) between :startDate AND :endDate
AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate;