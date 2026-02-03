select distinct o.person_id as patient_id FROM openmrs.obs o, openmrs.encounter e,
openmrs.encounter_type et
WHERE o.encounter_id = e.encounter_id
AND e.encounter_type = et.encounter_type_id
AND o.concept_id = 1543
AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba'
AND DATE(o.value_datetime) BETWEEN :startDate AND :endDate

UNION

 SELECT distinct pe.person_id as patient_id FROM openmrs.person pe WHERE 
 pe.death_date IS NOT NULL
 AND DATE(pe.death_date) BETWEEN :startDate AND :endDate;