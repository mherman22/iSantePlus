SELECT p.patient_id
   FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, 
   isanteplus.patient_status_arv psa,(select pdisp.patient_id, 
   MAX(ifnull(DATE(pdisp.dispensation_date),DATE(pdisp.visit_date))) as min_visit_date 
   FROM isanteplus.patient_dispensing pdisp WHERE  pdisp.voided <> 1  GROUP BY 1) B,
   (SELECT psar.patient_id,
      MAX(psar.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psar WHERE psar.date_started_status 
      BETWEEN :startDate AND :endDate GROUP BY 1)C
       WHERE p.patient_id = pdis.patient_id
       AND p.patient_id =B.patient_id 
       AND p.patient_id = psa.patient_id
       AND psa.patient_id = C.patient_id
       AND DATE(psa.date_started_status) = DATE(C.date_status)
       AND psa.id_status IN (6,8) 
       AND psa.date_started_status BETWEEN :startDate AND :endDate
       AND ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date)) = B.min_visit_date 
       AND DATEDIFF(pdis.next_dispensation_date,ifnull(
       DATE(pdis.dispensation_date),DATE(pdis.visit_date))) 
       BETWEEN 0 AND 89;

