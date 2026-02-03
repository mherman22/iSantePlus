/*Rapport : Éventualité de duplication d'enregistrement de patients */
select distinct  pat.patient_id AS 'Patient Id', pat.st_id as 'Code ST', pat.national_id as 'Code national', p.family_name as Nom, p.given_name as Prénom, 
pat.gender as Sexe,TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Âge, 
pat.last_address as Adresse, p.mother_name as 'Nom de la mère', pat.telephone as Téléphone
FROM (select pa.given_name, 
pa.family_name, pa.birthdate, 
pa.mother_name, count(distinct pa.patient_id) as dupCount  FROM  isanteplus.patient pa
GROUP BY pa.given_name, pa.family_name, pa.birthdate, pa.mother_name
having count(distinct pa.patient_id) > 1) p, isanteplus.patient pat
WHERE p.given_name = pat.given_name AND p.family_name = pat.family_name
AND p.birthdate = pat.birthdate AND (p.mother_name = pat.mother_name OR 
(p.mother_name is null AND pat.mother_name is null)) AND pat.voided <> 1
order by 3,4,6;
