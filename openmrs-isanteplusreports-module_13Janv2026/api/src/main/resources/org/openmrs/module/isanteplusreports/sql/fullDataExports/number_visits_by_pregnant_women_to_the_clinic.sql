/*Nombre de visites de femmes enceintes reçues en clinique / Number of visits by pregnant women to the clinic*/
select distinct p.patient_id AS 'Patient Id', p.st_id AS 'NO. de patient attribué par le site',p.national_id as 'numéro identité national', p.given_name as prénom,p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
FROM isanteplus.patient p, isanteplus.patient_pregnancy pp
WHERE p.patient_id=pp.patient_id
AND p.gender <> 'M'
AND pp.voided <> 1
AND pp.start_date between :startDate AND :endDate;