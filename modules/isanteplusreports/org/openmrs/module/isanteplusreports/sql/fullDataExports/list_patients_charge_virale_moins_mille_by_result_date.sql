select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat, isanteplus.patient_laboratory pl, (select plab.patient_id,max(plab.date_test_done) as test_date from isanteplus.patient_laboratory plab 
WHERE plab.test_id IN(856,1305) AND plab.test_done = 1 AND plab.voided <> 1 group by 1)B
	WHERE pat.patient_id = pl.patient_id
    AND B.patient_id = pl.patient_id 
	AND pl.date_test_done = B.test_date
    AND pl.test_id IN(856,1305)
	AND pl.test_done = 1
	AND (pl.test_result < 1000 OR pl.test_result=1306)
    AND pat.patient_id NOT IN (SELECT ei.patient_id FROM isanteplus.exposed_infants ei)
    AND pl.date_test_done BETWEEN :startDate AND :endDate;