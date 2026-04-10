/* Patient avec Rendez-Vous programmé dans les 14 jours à venir  */
select DISTINCT  pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', 
pa.national_id as 'No. d\'identité nationale',pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone,f.name as 'fiches', asl.name_fr as 'Statut du patient', DATE_FORMAT(pv.visit_date, "%d-%m-%Y") as 'Date visite', DATE_FORMAT(pv.next_visit_date, "%d-%m-%Y") as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_visit pv, openmrs.form f, isanteplus.arv_status_loockup asl,
(SELECT pvi.patient_id,MAX(DATE(pvi.next_visit_date)) 
as next_visit_date, pvi.form_id FROM isanteplus.patient_visit pvi 
WHERE pvi.next_visit_date between date(now()) and date_add(date(now()),interval 14 day) GROUP BY 1,3) B 
where pa.patient_id=pv.patient_id AND pv.form_id=f.form_id and pa.arv_status = asl.id
AND pv.patient_id = B.patient_id
AND DATE(pv.next_visit_date) = B.next_visit_date
AND pv.form_id = B.form_id
and pv.next_visit_date between date(now()) and date_add(date(now()),interval 14 day)

UNION

select DISTINCT pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', pa.national_id as 'No. d\'identité nationale', 
pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone,f.name as 'fiches', asl.name_fr as 'Statut du patient',DATE_FORMAT(DATE(pd.visit_date), "%d-%m-%Y") as 'Date visite', DATE_FORMAT(pd.next_dispensation_date, "%d-%m-%Y") as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_dispensing pd, openmrs.encounter enc, openmrs.form f, isanteplus.arv_status_loockup asl,
(SELECT pdisp.patient_id, MAX(DATE(pdisp.next_dispensation_date)) AS next_dispensation_date, fo.form_id
FROM isanteplus.patient_dispensing pdisp, openmrs.encounter e, openmrs.form fo
WHERE pdisp.encounter_id = e.encounter_id AND e.form_id = fo.form_id 
AND pdisp.next_dispensation_date between date(now()) and date_add(date(now()),interval 14 day) GROUP BY 1,3) C 
where pa.patient_id=pd.patient_id 
AND pd.encounter_id=enc.encounter_id
AND enc.form_id=f.form_id 
AND pd.patient_id = C.patient_id
AND DATE(pd.next_dispensation_date) = C.next_dispensation_date
AND enc.form_id = C.form_id
AND pa.arv_status = asl.id
and pd.next_dispensation_date between date(now()) and date_add(date(now()),interval 14 day)
ORDER BY 12