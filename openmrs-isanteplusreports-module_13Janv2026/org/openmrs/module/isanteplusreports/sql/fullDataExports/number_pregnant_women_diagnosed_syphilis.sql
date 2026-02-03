/*Nombre de femmes enceintes diagnostiquées syphilis (+)*/
select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
FROM  isanteplus.patient p INNER JOIN isanteplus.patient_pregnancy ppr
ON p.patient_id = ppr.patient_id
	LEFT OUTER JOIN  isanteplus.patient_laboratory plab
	ON ppr.patient_id=plab.patient_id
    LEFT OUTER JOIN isanteplus.patient_diagnosis pdiag
    ON ppr.patient_id=pdiag.patient_id
	WHERE ((plab.test_id IN (1619,163626,1031)
	AND plab.test_done=1 AND plab.test_result=703
    AND plab.visit_date BETWEEN :startDate AND :endDate
    )
    OR (pdiag.concept_id=1284 AND pdiag.answer_concept_id=112493
    AND pdiag.encounter_date BETWEEN :startDate AND :endDate
    ))
    AND ppr.voided <> 1
	AND ppr.start_date BETWEEN :startDate AND :endDate