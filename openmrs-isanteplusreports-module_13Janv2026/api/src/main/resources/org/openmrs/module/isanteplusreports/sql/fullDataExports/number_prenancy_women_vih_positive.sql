/*Nombre de femmes enceintes VIH(+) */
select distinct p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
FROM isanteplus.patient_pregnancy ppr
INNER JOIN  isanteplus.patient_laboratory plab
ON ppr.patient_id=plab.patient_id
INNER JOIN isanteplus.patient p
ON p.patient_id=ppr.patient_id
INNER JOIN openmrs.encounter e
ON e.patient_id = p.patient_id
INNER JOIN openmrs.encounter_type et
ON e.encounter_type = et.encounter_type_id
WHERE ((plab.test_id=1042 OR plab.test_id=1040 OR plab.test_id=163610)
AND plab.test_result=703)
AND p.gender <> 'M'
AND ppr.voided <> 1
AND ((ppr.start_date BETWEEN :startDate AND :endDate) OR (ppr.end_date BETWEEN :startDate AND :endDate))
AND et.uuid IN ('5c312603-25c1-4dbe-be18-1a167eb85f97','49592bec-dd22-4b6c-a97f-4dd2af6f2171','d95b3540-a39f-4d1e-a301-8ee0e03d5eab')
AND DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate;