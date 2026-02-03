 /*Liste des enfants éligibles pour un PCR*/
 select DISTINCT B.st_id as 'NO. de patient attribué par le site', B.national_id as 'Numéro d\'identité national',
	B.prenom as Prénom,B.nom as Nom, B.sexe as Sexe,
	B.age as Âge, B.telephone as Téléphone,
	B.mother_name as Contact 
		FROM (SELECT DISTINCT pat.st_id as st_id, pat.national_id as national_id,
	pat.given_name as prenom,pat.family_name as nom, pat.gender as sexe,
	TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as age, pat.telephone as telephone,
	pat.mother_name
	 FROM isanteplus.patient pat,isanteplus.exposed_infants exp_inf
	WHERE pat.patient_id = exp_inf.patient_id
    AND exp_inf.condition_exposee = 3
    AND (TIMESTAMPDIFF(DAY, pat.birthdate,DATE(now())) BETWEEN 28 AND 365)
    AND pat.patient_id NOT IN (select distinct o.person_id from openmrs.obs o where o.voided <> 1 AND (o.concept_id IN (844,1030)) OR (o.concept_id = 1271 AND value_coded = 844))
   UNION
   SELECT DISTINCT pat.st_id as st_id, pat.national_id as national_id,
	pat.given_name as prenom,pat.family_name as nom, pat.gender as sexe,
	TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as age, pat.telephone as telephone,
	pat.mother_name 
	FROM isanteplus.patient pat,isanteplus.serological_tests stests
	WHERE pat.patient_id = stests.patient_id
    AND stests.test_id = 162087
	AND stests.answer_concept_id IN(163722,1042)
    AND stests.test_result = 703
    AND pat.voided <> 1
    AND (TIMESTAMPDIFF(MONTH, pat.birthdate,DATE(now())) BETWEEN 12 AND 18)
    AND pat.patient_id NOT IN (select distinct o.person_id from openmrs.obs o where o.voided <> 1 AND (o.concept_id IN (844,1030)) OR (o.concept_id = 1271 AND value_coded = 844))
    ) B;