/*Nombre d'enfants exposés testés par PCR /
 Number of exposed infants tested by PCR*/
SELECT DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Âge
FROM isanteplus.patient pat 
LEFT OUTER JOIN  isanteplus.patient_laboratory plab
ON pat.patient_id=plab.patient_id
LEFT OUTER JOIN isanteplus.virological_tests vt
ON pat.patient_id = vt.patient_id
WHERE(
		(vt.concept_group=1361 AND vt.test_id=162087 
		AND vt.answer_concept_id=1030
		AND vt.encounter_date BETWEEN :startDate AND :endDate)  
		OR(plab.test_id=844 AND (plab.date_test_done is not null OR plab.date_test_done <> '')
		AND plab.voided <> 1 
		AND plab.date_test_done BETWEEN :startDate AND :endDate)
	);