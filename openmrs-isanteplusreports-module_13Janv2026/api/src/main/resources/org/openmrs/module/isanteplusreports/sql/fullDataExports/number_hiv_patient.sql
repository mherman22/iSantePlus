select l.name as Site, COUNT(DISTINCT p.patient_id) as Count FROM openmrs.location l, isanteplus.patient p
WHERE l.location_id = p.location_id
AND p.vih_status = 1
AND p.patient_id NOT IN (select enc.patient_id FROM openmrs.encounter enc, openmrs.encounter_type et,
                         (
                         	select e.patient_id, MAX(DATE(e.encounter_datetime)) as encounter_datetime FROM openmrs.encounter e where 
                         	DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate GROUP BY 1
                         ) B
where enc.encounter_type = et.encounter_type_id
AND enc.patient_id = B.patient_id
AND DATE(enc.encounter_datetime) = B.encounter_datetime
AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba'
AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate)
AND p.date_created BETWEEN :startDate AND :endDate
GROUP BY 1
ORDER BY 1 DESC;