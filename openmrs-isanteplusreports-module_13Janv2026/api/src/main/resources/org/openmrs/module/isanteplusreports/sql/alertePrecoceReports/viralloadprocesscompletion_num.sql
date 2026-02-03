SELECT B.patient_id FROM isanteplus.patient p, 
	(select pdisp.patient_id, min(DATE(pdisp.visit_date)) as visit_date
		from isanteplus.patient_dispensing pdisp WHERE 
		pdisp.arv_drug = 1065 AND pdisp.voided = 0 group by pdisp.patient_id
		HAVING TIMESTAMPDIFF(MONTH, min(pdisp.visit_date),:endDate) between 9 and 15) B,
		(select pl.patient_id from isanteplus.patient_laboratory pl
		            WHERE pl.test_id = 856 AND pl.test_result > 0 AND pl.voided = 0
					AND DATE(pl.visit_date) between :startDate and :endDate group by 1) lab
		WHERE p.patient_id = B.patient_id
		AND B.patient_id = lab.patient_id;