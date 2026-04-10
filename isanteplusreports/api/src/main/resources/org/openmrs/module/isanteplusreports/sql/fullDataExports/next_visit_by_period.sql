select DISTINCT  pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', pa.national_id as 'No. d\'identité nationale',
pa.identifier as 'iSantéPlus ID',pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone,f.name as 'fiches', asl.name_fr as 'Statut du patient', DATE_FORMAT(pv.visit_date, "%d-%m-%Y") as 'Date visite', DATE_FORMAT(pv.next_visit_date, "%d-%m-%Y") as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_visit pv, openmrs.form f, isanteplus.arv_status_loockup asl 
where pa.patient_id=pv.patient_id AND pv.form_id=f.form_id and pa.arv_status = asl.id
/*and pv.next_visit_date between date(now()) and date_add(date(now()),interval 14 day)*/
AND pv.next_visit_date BETWEEN :startDate AND :endDate
UNION
select DISTINCT pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', pa.national_id as 'No. d\'identité nationale', 
pa.identifier as 'iSantéPlus ID', pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone,f.name as 'fiches', asl.name_fr as 'Statut du patient',DATE_FORMAT(DATE(pd.visit_date), "%d-%m-%Y") as 'Date visite', DATE_FORMAT(pd.next_dispensation_date, "%d-%m-%Y") as 'Prochaine visite' 
from isanteplus.patient pa, isanteplus.patient_dispensing pd, openmrs.encounter enc, openmrs.form f, isanteplus.arv_status_loockup asl 
where pa.patient_id=pd.patient_id 
AND pd.encounter_id=enc.encounter_id
AND enc.form_id=f.form_id 
AND pa.arv_status = asl.id
/*and pd.next_dispensation_date between date(now()) and date_add(date(now()),interval 14 day)*/
AND pd.next_dispensation_date BETWEEN :startDate AND :endDate
ORDER BY 12