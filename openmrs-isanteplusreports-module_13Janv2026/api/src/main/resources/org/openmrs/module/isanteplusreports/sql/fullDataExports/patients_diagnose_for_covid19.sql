select distinct p.patient_id AS 'Patient Id', p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance'
FROM isanteplus.patient p, isanteplus.patient_diagnosis pdiag
WHERE p.patient_id = pdiag.patient_id
AND pdiag.concept_id = 1284 
AND pdiag.answer_concept_id = 155762
AND DATE(pdiag.encounter_date) between :startDate AND :endDate;