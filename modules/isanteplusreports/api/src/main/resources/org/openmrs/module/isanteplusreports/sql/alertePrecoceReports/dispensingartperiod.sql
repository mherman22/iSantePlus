select distinct pdisp.patient_id FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp
WHERE p.patient_id = pdisp.patient_id
AND pdisp.arv_drug = 1065
AND ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date))
between :startDate AND :endDate;