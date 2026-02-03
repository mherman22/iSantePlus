select distinct p.patient_id AS 'Patient Id', p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance', p.telephone AS Téléphone,cn.name AS 'Nom du vaccin'
FROM isanteplus.patient p, isanteplus.patient_immunization pim, openmrs.concept c,
openmrs.concept_name cn
WHERE p.patient_id = pim.patient_id
AND c.concept_id = cn.concept_id
AND pim.vaccine_concept_id = c.concept_id
AND cn.locale = "en"
AND cn.locale_preferred = 1
AND pim.vaccine_uuid IN ("166156AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","166154AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
				"166155AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","166157AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
				"166249AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA","166355AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
AND DATE(pim.vaccine_date) between :startDate AND :endDate;