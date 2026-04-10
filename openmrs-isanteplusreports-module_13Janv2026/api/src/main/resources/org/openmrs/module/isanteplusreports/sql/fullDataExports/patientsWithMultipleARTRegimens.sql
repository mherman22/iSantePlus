/*Les patients ayant de multiples régimes VIH prescrits / distribués le même jour*/
select distinct  pa.patient_id AS 'Patient Id', f.name as Fiche, pa.st_id as 'Code ST', pa.national_id as 'Code national', pa.given_name as Prénom, 
pa.family_name as Nom, pa.gender as Sexe, TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge, 
pa.mother_name as 'Nom de la mère', pa.telephone as Téléphone, pa.visit_date as 'Date visite'
FROM openmrs.encounter en, openmrs.form f, 
(select pdisp.patient_id, pdisp.encounter_id, pat.st_id, pat.national_id, pat.given_name, pat.family_name, pat.gender, pat.birthdate, 
pat.mother_name, pat.telephone, pdisp.visit_date,
COUNT(pdisp.drug_id)
FROM isanteplus.patient_dispensing pdisp, isanteplus.patient pat WHERE pdisp.patient_id = pat.patient_id
AND pdisp.arv_drug = 1065 AND pdisp.voided <> 1
GROUP BY pdisp.patient_id, pdisp.drug_id, pdisp.visit_date
having count(pdisp.drug_id) > 1) pa
WHERE pa.patient_id = en.patient_id AND en.form_id = f.form_id
AND pa.encounter_id = en.encounter_id;