select distinct B.patient_id FROM isanteplus.patient p, (SELECT pdisp.patient_id, min(DATE(pdisp.visit_date))
						FROM isanteplus.patient_dispensing pdisp 
						WHERE pdisp.arv_drug = 1065 AND pdisp.voided = 0 GROUP BY pdisp.patient_id
						having TIMESTAMPDIFF(MONTH, min(DATE(pdisp.visit_date)),:endDate)=12) B,
						isanteplus.patient_status_arv psa
						WHERE p.patient_id = B.patient_id
						AND B.patient_id = psa.patient_id
						AND psa.id_status = 9
						AND DATE(psa.date_started_status) between :startDate AND :endDate;