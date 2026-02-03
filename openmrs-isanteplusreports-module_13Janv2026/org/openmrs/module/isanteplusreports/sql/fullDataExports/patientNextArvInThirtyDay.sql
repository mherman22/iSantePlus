/*La liste des patients dont la date de renflouement des ARV est prévue dans les 30 prochains jours*/
select distinct DATE(pdisp.visit_date) as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance',pdisp.next_dispensation_date as 'Date de dispensation'
FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp,
	(SELECT pd.patient_id, MAX(pd.next_dispensation_date) as next_dispensation_date 
	FROM isanteplus.patient_dispensing pd WHERE pd.arv_drug = 1065 AND 
	(pd.rx_or_prophy <> 163768 OR pd.rx_or_prophy is null) AND pd.voided <> 1 GROUP BY 1) B
	WHERE p.patient_id = pdisp.patient_id
	AND pdisp.patient_id = B.patient_id
	AND pdisp.next_dispensation_date = B.next_dispensation_date
	AND DATEDIFF(pdisp.next_dispensation_date,now()) between 0 and 30
	AND p.patient_id NOT IN (select enc.patient_id FROM openmrs.encounter enc, openmrs.encounter_type et 
	where enc.encounter_type = et.encounter_type_id AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba' 
	AND enc.patient_id IN (select dr.patient_id FROM isanteplus.discontinuation_reason dr));