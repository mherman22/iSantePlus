SELECT  p.patient_id AS 'Patient Id', p.site_code as 'Site Code', lab.creation_date as 'Date Création',
         p.given_name as 'Prénom', p.family_name as 'Nom',
 lab.order_destination as 'Destination', lab.visit_date as 'Date de Prélèvement', lab.test_name as 'Test', p.st_id as 'ST Code', p.isante_id as 'iSanté ID'
FROM isanteplus.patient_laboratory lab
LEFT JOIN isanteplus.patient p
ON lab.patient_id = p.patient_id
WHERE lab.order_destination <> ''
AND lab.creation_date BETWEEN :startDate AND :endDate;
