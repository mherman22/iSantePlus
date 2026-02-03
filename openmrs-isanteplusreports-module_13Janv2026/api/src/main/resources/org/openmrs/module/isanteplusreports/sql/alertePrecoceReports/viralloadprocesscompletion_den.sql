SELECT B.patient_id FROM isanteplus.patient p, 
	(select pdisp.patient_id, min(DATE(pdisp.visit_date)) as visit_date
		from isanteplus.patient_dispensing pdisp WHERE pdisp.arv_drug = 1065 
		AND pdisp.voided = 0 group by pdisp.patient_id
		HAVING TIMESTAMPDIFF(MONTH, min(pdisp.visit_date),:endDate) between 9 and 15) B
		WHERE p.patient_id = B.patient_id;