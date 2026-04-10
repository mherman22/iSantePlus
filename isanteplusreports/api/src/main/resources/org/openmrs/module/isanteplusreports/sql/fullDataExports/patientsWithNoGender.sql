/*Patients sans désignation de sexe */
select distinct  p.patient_id AS 'Patient Id', p.st_id AS 'No. de patient attribué par le site', p.national_id AS 'No. d\'identité nationale',
p.given_name AS Prénom, p.family_name AS Nom, p.gender AS Sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) AS Âge
FROM isanteplus.patient p
WHERE p.gender NOT IN ('M','F')
AND p.voided <> 1;