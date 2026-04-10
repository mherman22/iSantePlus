SELECT DISTINCT pdisp.patient_id FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp,
(select pdis.patient_id, pdis.dispensation_date, pdis.next_dispensation_date 
	FROM isanteplus.patient_dispensing pdis WHERE DATE(pdis.next_dispensation_date) 
	between :startDate AND :endDate AND pdis.arv_drug = 1065) B
WHERE p.patient_id = pdisp.patient_id
AND pdisp.patient_id = B.patient_id
AND DATE(pdisp.dispensation_date) between :startDate AND :endDate
AND pdisp.arv_drug = 1065;