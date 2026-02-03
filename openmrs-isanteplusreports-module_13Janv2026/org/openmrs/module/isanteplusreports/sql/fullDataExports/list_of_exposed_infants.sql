select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Âge, pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat, isanteplus.exposed_infants exp_inf
WHERE pat.patient_id = exp_inf.patient_id
AND pat.voided <> 1;