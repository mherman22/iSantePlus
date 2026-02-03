/*Nombre de femmes enceintes testées pour la syphilis*/
select distinct p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
	FROM isanteplus.patient p INNER JOIN isanteplus.patient_pregnancy ppr
	ON p.patient_id = ppr.patient_id
	INNER JOIN  isanteplus.patient_laboratory plab
	ON ppr.patient_id=plab.patient_id
	WHERE plab.test_id IN (1619,163626,1031)
        AND plab.test_result is not null
        AND plab.test_done = 1
	AND plab.voided <> 1
	AND ppr.start_date BETWEEN :startDate AND :endDate