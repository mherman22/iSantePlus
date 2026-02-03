/*Nombre de femmes VIH(+) enrôlées en soins devenues enceintes*/
/*Number of HIV-positive women enrolled in care who became pregnant*/
select DISTINCT  p.patient_id AS 'Patient Id',p.st_id as 'NO. de patient attribué par le site', p.national_id as 'No. d\'identité nationale',p.given_name as Prénom,
p.family_name as Nom, p.gender as Sexe,TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Âge,
p.telephone as Téléphone from isanteplus.patient_pregnancy ppr
LEFT OUTER JOIN  isanteplus.patient_laboratory plab
ON ppr.patient_id=plab.patient_id
INNER JOIN isanteplus.patient p
ON p.patient_id=ppr.patient_id
INNER JOIN (SELECT pdisp.patient_id, MIN(pdisp.visit_date) as visit_date
FROM isanteplus.patient_dispensing pdisp WHERE pdisp.arv_drug=1065 GROUP BY 1) A
ON p.patient_id = A.patient_id
WHERE ((p.vih_status=1) OR ((plab.test_id=1042 OR plab.test_id=1040)
AND plab.test_result=703))
AND A.visit_date < ppr.start_date
AND ppr.voided <> 1
AND ppr.start_date BETWEEN :startDate AND :endDate;