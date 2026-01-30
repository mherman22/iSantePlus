/*Ind. 40 RéTENTION sous ARV à 12 MOIS*/

select p.patient_id
  from isanteplus.patient p
  where p.date_started_arv >=  DATE_SUB(:startDate, INTERVAL 12 MONTH)
    AND p.date_started_arv <=  DATE_SUB(:endDate, INTERVAL 12 MONTH)
    AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei);

/*select distinct p.patient_id
FROM isanteplus.patient p, isanteplus.patient_dispensing pdis,
     (SELECT pdp.patient_id, MIN(ifnull(DATE(pdp.dispensation_date), DATE(pdp.visit_date))) as visit_date
      FROM isanteplus.patient_dispensing pdp
      WHERE pdp.arv_drug = 1065 AND pdp.voided <> 1
      GROUP BY 1) B
WHERE p.patient_id=pdis.patient_id
  AND pdis.drug_id IN (select arvd.drug_id from isanteplus.arv_drugs arvd)
  AND B.patient_id = pdis.patient_id
  AND B.visit_date = ifnull(DATE(pdis.dispensation_date), DATE(pdis.visit_date))
  AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
  AND B.visit_date >=  DATE_SUB('2025-01-01', INTERVAL 12 MONTH)
  AND B.visit_date <=  DATE_SUB('2025-01-31', INTERVAL 12 MONTH);
*/





