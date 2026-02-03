select DISTINCT  p.patient_id AS 'Patient Id', 
p.st_id as 'Code ST',p.national_id as 'Code national',
p.given_name as Prénom,p.family_name as Nom, p.gender as Sexe,
DATE_FORMAT(p.birthdate, "%d-%m-%Y") as 'Date de naissance',
TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Age, 
DATE_FORMAT(pid.date_created, "%d-%m-%Y") as 'Date Empreinte', p.last_address as Adresse,
p.telephone as Téléphone,p.mother_name as Contact
FROM openmrs.patient_identifier_type ptype,openmrs.patient_identifier pid,
isanteplus.patient p, isanteplus.patient_status_arv ps, 
(select psa.patient_id, DATE(psa.date_started_status) as date_status FROM 
		isanteplus.patient_status_arv psa WHERE
		DATE(psa.date_started_status) = DATE(now()) GROUP BY 1)
WHERE ptype.patient_identifier_type_id = pid.identifier_type
AND p.patient_id = pid.patient_id
AND ptype.uuid = 'e26ca279-8f57-44a5-9ed8-8cc16e90e559'
AND pid.voided <> 1
AND p.voided <> 1;