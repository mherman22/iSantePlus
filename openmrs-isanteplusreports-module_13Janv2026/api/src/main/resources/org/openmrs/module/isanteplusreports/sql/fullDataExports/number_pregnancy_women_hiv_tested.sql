/* 	Nombre de femmes enceintes testées pour le VIH */
select distinct p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
FROM isanteplus.patient p, isanteplus.patient_pregnancy ppr, isanteplus.patient_laboratory plab,
openmrs.encounter e, openmrs.encounter_type et
WHERE 
p.patient_id = ppr.patient_id AND ppr.patient_id = plab.patient_id
AND p.patient_id = e.patient_id
AND e.encounter_type = et.encounter_type_id
AND plab.test_id IN (1042,1040,163610)
AND plab.voided <> 1
AND ppr.voided <> 1
AND ((ppr.start_date BETWEEN :startDate AND :endDate) OR (ppr.end_date BETWEEN :startDate AND :endDate))
AND et.uuid IN ('5c312603-25c1-4dbe-be18-1a167eb85f97','49592bec-dd22-4b6c-a97f-4dd2af6f2171','d95b3540-a39f-4d1e-a301-8ee0e03d5eab')
AND DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate;