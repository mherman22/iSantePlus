select DISTINCT B.patient_id FROM isanteplus.patient p, 
	(select pdisp.patient_id, min(DATE(pdisp.visit_date)) as visit_date
		from isanteplus.patient_dispensing pdisp WHERE pdisp.arv_drug = 1065 group by pdisp.patient_id
		HAVING TIMESTAMPDIFF(MONTH, min(pdisp.visit_date),:endDate)=12) B, 
		isanteplus.patient_status_arv psa
		WHERE p.patient_id = B.patient_id
		AND B.patient_id = psa.patient_id
		AND psa.id_status = 6
		AND DATE(psa.date_started_status) BETWEEN :startDate AND :endDate;