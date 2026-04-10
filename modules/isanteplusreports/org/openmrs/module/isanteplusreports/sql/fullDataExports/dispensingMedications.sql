select cn.name as MedicationName, COUNT(DISTINCT pdis.patient_id) as Count
FROM openmrs.concept_name cn, isanteplus.patient_dispensing pdis
WHERE cn.concept_id = pdis.drug_id
AND pdis.voided <> 1
AND cn.locale='fr'
GROUP BY cn.name;