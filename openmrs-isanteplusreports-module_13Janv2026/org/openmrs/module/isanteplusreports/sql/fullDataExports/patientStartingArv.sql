/*Liste des patients ayant démarré un régime ARV*/
select distinct DATE(pdis.visit_date) as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.gender AS Sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) AS Âge
FROM isanteplus.patient p,isanteplus.patient_dispensing pdis, 
(SELECT pdp.patient_id,MIN(ifnull(DATE(pdp.dispensation_date),DATE(pdp.visit_date))) as visit_date FROM isanteplus.patient_dispensing pdp WHERE pdp.arv_drug = 1065 AND pdp.voided <> 1 GROUP BY 1) B
WHERE p.patient_id=pdis.patient_id
AND pdis.drug_id IN (select arvd.drug_id from isanteplus.arv_drugs arvd)
AND B.patient_id = pdis.patient_id
AND B.visit_date = ifnull(DATE(pdis.dispensation_date),DATE(pdis.visit_date))
AND p.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
AND p.patient_id IN (select distinct pa.patient_id from openmrs.isanteplus_patient_arv pa where pa.arv_regimen is not null)
AND B.visit_date between :startDate AND :endDate;