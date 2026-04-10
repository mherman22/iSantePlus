SELECT
    COUNT(DISTINCT CASE WHEN p.gender='F' THEN p.patient_id END) as F,
    COUNT(DISTINCT CASE WHEN p.gender='M' THEN p.patient_id END) as M
FROM isanteplus.patient p
INNER JOIN openmrs.encounter e ON p.patient_id = e.patient_id
WHERE DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate;