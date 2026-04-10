select count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 0 AND 30 THEN p.patient_id END) as '0-30 jours',
count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 31 AND 60 THEN p.patient_id END) as '31-60 jours',
count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 61 AND 90 THEN p.patient_id END) as '61-90 jours',
count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) between 91 AND 120 THEN p.patient_id END) as '91-120 jours',
count(distinct case when DATEDIFF(pdis.next_dispensation_date,pdis.visit_date) > 120 THEN p.patient_id END) as '>120 jours',
count(distinct p.patient_id) as 'Patient unique'
FROM isanteplus.patient p, isanteplus.patient_dispensing pdis, isanteplus.patient_on_arv parv
WHERE p.patient_id=pdis.patient_id
AND pdis.visit_id=parv.visit_id
AND (pdis.next_dispensation_date<>'' AND pdis.next_dispensation_date is not null)
AND pdis.voided <> 1
AND pdis.visit_date between :startDate AND :endDate;