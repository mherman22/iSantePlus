select cn.name as 'Nom du médicament', COUNT(DISTINCT pp.patient_id) as Quantité FROM 
openmrs.concept_name cn,isanteplus.patient_prescription pp
WHERE cn.concept_id=pp.drug_id
AND pp.voided <> 1
AND cn.locale='fr'
GROUP BY cn.name;