select DISTINCT p.national_id as 'No. d\'identité nationale', p.identifier as 'iSantéPlus ID',p.given_name as Prénom,
p.family_name as Nom, p.gender as Sexe,TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Âge,
p.telephone as Téléphone from isanteplus.patient p
WHERE p.patient_id NOT IN (SELECT pi.patient_id FROM openmrs.patient_identifier pi, openmrs.patient_identifier_type ptype 
WHERE pi.identifier_type = ptype.patient_identifier_type_id
AND ptype.uuid IN ('d059f6d0-9e42-4760-8de1-8316b48bc5f1','b7a154fd-0097-4071-ac09-af11ee7e0310'))
AND p.voided <> 1;