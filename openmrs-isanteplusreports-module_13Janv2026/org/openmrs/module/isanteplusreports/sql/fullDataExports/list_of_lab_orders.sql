SELECT p.identifier as Identifier, p.given_name as Prenom, p.family_name as Nom, lab.visit_date Date, lab.order_destination as Destination, lab.test_name as Test
FROM isanteplus.patient_laboratory lab
LEFT JOIN isanteplus.patient p
ON lab.patient_id = p.patient_id
WHERE lab.order_destination<>''
AND lab.visit_date BETWEEN :startDate AND :endDate;
