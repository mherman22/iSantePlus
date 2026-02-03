/* Suivi dispensation ARV */
SELECT distinct p.st_id as 'Code ST', p.national_id as 'Code National', p.family_name as Nom, p.given_name as Prénom,
DATE_FORMAT(DATE(p.birthdate), "%d-%m-%Y") as 'Date de naissance', TIMESTAMPDIFF(YEAR,p.birthdate,now()) AS Âge, p.gender AS Sexe,
DATE_FORMAT(DATE(p.date_started_arv), "%d-%m-%Y") as 'Date Initiation ARV',
DATE_FORMAT(DATE(p.last_visit_date), "%d-%m-%Y") as 'Date de Dernière Visite',
DATE_FORMAT(DATE(pdis.visit_date), "%d-%m-%Y") as 'Date de Dernière visite Arv',
DATE_FORMAT(DATE(pdis.dispensation_date), "%d-%m-%Y") as 'Date effective de derniere dispensation ARV',
DATE_FORMAT(DATE(pdis.next_dispensation_date), "%d-%m-%Y") as 'Prochain Rendez-vous ARV',
pdis.number_day_dispense as 'Nombre de Jours prescrit pour la derniere visite ARV'
FROM isanteplus.patient p, isanteplus.patient_prescription pdis, 
(select pd.patient_id, MAX(pd.encounter_id) as encounter_id FROM 
isanteplus.patient_prescription pd WHERE pd.arv_drug = 1065 AND pd.voided <> 1 
AND (pd.rx_or_prophy <> 163768 OR pd.rx_or_prophy is null) GROUP BY 1) B
WHERE p.patient_id = pdis.patient_id
AND pdis.patient_id = B.patient_id
AND pdis.encounter_id = B.encounter_id
AND p.vih_status = 1
AND p.patient_id IN (SELECT ip.patient_id from openmrs.isanteplus_patient_arv ip WHERE ip.arv_regimen is not null)
GROUP BY 1,2,3,4,5,6,7;