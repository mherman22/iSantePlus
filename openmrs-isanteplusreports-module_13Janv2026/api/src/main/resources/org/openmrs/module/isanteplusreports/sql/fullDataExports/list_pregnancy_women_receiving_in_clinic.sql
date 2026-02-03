/*Liste de visites de femmes enceintes reçues en clinique / List of visits by pregnant women to the clinic*/
select distinct  p.patient_id AS 'Patient Id', p.st_id AS 'No. de patient attribué par le site', p.national_id AS 'No. d\'identité nationale',
p.given_name AS Prénom, p.family_name AS Nom, p.gender AS Sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) AS Age,
MAX(pp.start_date) AS 'Date visite'
FROM isanteplus.patient p, isanteplus.patient_pregnancy pp
WHERE p.patient_id=pp.patient_id
AND pp.voided <> 1
AND pp.start_date between :startDate AND :endDate
GROUP BY 1,2,3,4,5,6
ORDER BY pp.start_date DESC;