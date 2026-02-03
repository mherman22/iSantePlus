select distinct p.st_id as 'No. de patient attribué par le site', p.national_id as 'No. d\'identité nationale',
p.family_name as Nom,p.given_name as Prénom, DATE(enc.encounter_datetime) as 'Dernière date'
from isanteplus.patient p, openmrs.encounter enc, openmrs.encounter_type entype
WHERE p.patient_id=enc.patient_id
AND enc.encounter_type=entype.encounter_type_id
AND entype.uuid='873f968a-73a8-4f9c-ac78-9f4778b751b6'
AND enc.encounter_id=(SELECT MAX(en.encounter_id) FROM openmrs.encounter en)
AND enc.voided <> 1;