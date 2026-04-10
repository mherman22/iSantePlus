SELECT
    COUNT(DISTINCT CASE WHEN p.gender='F' THEN p.patient_id END) as F,
    COUNT(DISTINCT CASE WHEN p.gender='M' THEN p.patient_id END) as M
FROM isanteplus.patient p
INNER JOIN isanteplus.health_qual_patient_visit pv ON p.patient_id = pv.patient_id
WHERE pv.visit_date BETWEEN :startDate AND :endDate;
