select DISTINCT  pat.patient_id AS 'Patient Id', pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, DATE(pdisp.visit_date) as 'Dernière date', 
pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact
FROM isanteplus.patient pat, isanteplus.patient_dispensing pdisp,
    (select pdi.patient_id,max(DATE(pdi.visit_date)) as visit_date from isanteplus.patient_dispensing pdi 
WHERE pdi.arv_drug = 1065 AND pdi.voided <> 1 AND DATE(pdi.visit_date) BETWEEN :startDate AND :endDate group by 1)B
WHERE pat.patient_id=pdisp.patient_id
AND pdisp.patient_id = B.patient_id
AND DATE(pdisp.visit_date) = B.visit_date
AND pdisp.dispensation_location = 0
AND pdisp.arv_drug = 1065
AND DATE(pdisp.visit_date) BETWEEN :startDate AND :endDate;