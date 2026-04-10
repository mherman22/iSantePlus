/*Medicaments dispenses*/
/*Dispensations*/
select cn.name as MedicationName, COUNT(DISTINCT pdis.patient_id) as Count
FROM openmrs.concept_name cn, isanteplus.patient_dispensing pdis, isanteplus.patient p
WHERE cn.concept_id = pdis.drug_id
AND pdis.patient_id = p.patient_id
AND p.vih_status = 1
AND pdis.voided <> 1
AND cn.locale='fr'
GROUP BY cn.name;