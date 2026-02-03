select distinct p.st_id as 'No. de patient attribué par le site', p.national_id as 'No. d\'identité nationale',
p.family_name as Nom,p.given_name as Prénom, MAX(DATE(enc.encounter_datetime)) as 'Dernière date'
from isanteplus.patient p,
 openmrs.encounter enc, openmrs.encounter_type entype
WHERE p.patient_id=enc.patient_id
AND enc.encounter_type=entype.encounter_type_id
AND p.vih_status=1
AND p.patient_id NOT IN (SELECT enco.patient_id FROM openmrs.encounter enco, openmrs.encounter_type enct
	WHERE enco.encounter_type=enct.encounter_type_id
    AND enct.uuid IN ('17536ba6-dd7c-4f58-8014-08c7cb798ac7','349ae0b4-65c1-4122-aa06-480f186c8350'))
AND p.voided <> 1
GROUP BY p.patient_id;