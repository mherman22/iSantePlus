/* Liste des patients éligible pour leur prémière charge virale */
select DISTINCT p.st_id as 'Code ST',
p.given_name as Prénom, p.family_name as Nom, p.telephone as Téléphone,TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge, p.gender as Sexe
FROM isanteplus.patient p, isanteplus.patient_on_arv parv
WHERE p.patient_id = parv.patient_id
AND (TIMESTAMPDIFF(MONTH,DATE(p.date_started_arv),DATE(now())) >= 6)
AND p.patient_id NOT IN(SELECT pl.patient_id FROM isanteplus.patient_laboratory pl
		WHERE pl.test_id IN(856, 1305) AND pl.test_done=1 AND pl.voided <> 1 AND ((pl.test_result is not null) OR (pl.test_result <> '')))
AND p.patient_id NOT IN (select enc.patient_id FROM openmrs.encounter enc, openmrs.encounter_type et 
			where enc.encounter_type = et.encounter_type_id AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba')
AND p.vih_status = 1;