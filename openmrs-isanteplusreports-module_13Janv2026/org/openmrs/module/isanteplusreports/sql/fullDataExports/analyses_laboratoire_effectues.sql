select cn.name as 'Nom du test', count(DISTINCT lab.patient_id) as Quantité 
FROM openmrs.concept_name cn, isanteplus.patient_laboratory lab
WHERE cn.concept_id=lab.test_id AND cn.locale='fr'
AND lab.test_done=1
AND lab.voided <> 1
GROUP BY cn.name;