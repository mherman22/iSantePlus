select DISTINCT p.identifier as 'iSantéPlus ID', p.st_id as 'Code ST', p.national_id as 'Code national',
p.given_name as Prénom,p.family_name as Nom, p.gender as Sexe,
TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Age,
p.last_address as Adresse, p.telephone as Téléphone,
p.mother_name as 'Nom de la mère' FROM isanteplus.patient p
WHERE p.patient_id = :id