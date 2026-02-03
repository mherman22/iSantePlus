select DISTINCT p.st_id as 'NO. de patient attribué par le site', p.national_id as 'Numéro d\'identité national',
p.given_name as Prénom,p.family_name as Nom, p.gender as Sexe,
TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Age,p.telephone as Téléphone
FROM isanteplus.patient_pregnancy ppr
LEFT OUTER JOIN  isanteplus.patient_laboratory plab
ON ppr.patient_id=plab.patient_id
INNER JOIN isanteplus.patient p
ON p.patient_id=ppr.patient_id
WHERE ((p.vih_status=1) OR ((plab.test_id=1042 OR plab.test_id=1040)
AND plab.test_result=703 AND plab.date_test_done BETWEEN :startDate AND :endDate))
AND ppr.patient_id IN (SELECT DISTINCT pdisp.patient_id from isanteplus.patient_dispensing pdisp
  WHERE pdisp.arv_drug=1065 AND pdisp.visit_date <= :endDate)
AND p.gender <> 'M'
AND ppr.voided <> 1
AND ((ppr.start_date BETWEEN :startDate AND :endDate) OR (ppr.end_date BETWEEN :startDate AND :endDate))