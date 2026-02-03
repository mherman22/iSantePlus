select DISTINCT  pat.patient_id AS 'Patient Id', pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat, isanteplus.patient_laboratory pl
	WHERE pat.patient_id = pl.patient_id
    AND pl.test_id = 844
	AND pl.test_done = 1
	AND pl.test_result IN(1301,1302,1300,1304)
	AND pl.voided <> 1
    AND pl.date_test_done BETWEEN :startDate AND :endDate
    
 UNION
 
  select DISTINCT  pat.patient_id AS 'Patient Id', pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat,isanteplus.virological_tests vt
	WHERE pat.patient_id = vt.patient_id
    AND vt.test_id = 162087
	AND vt.answer_concept_id = 1030
	AND vt.test_result IN (664,703,1138)
	AND vt.voided <> 1
    AND vt.test_date BETWEEN :startDate AND :endDate;