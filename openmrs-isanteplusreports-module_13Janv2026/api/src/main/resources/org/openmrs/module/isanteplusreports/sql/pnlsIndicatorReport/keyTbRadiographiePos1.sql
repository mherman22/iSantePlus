SELECT p.patient_id
FROM isanteplus.patient p,
    (SELECT psa.patient_id,
    MAX(DATE(psa.date_started_status)) AS date_status
    FROM isanteplus.patient_status_arv psa WHERE psa.id_status in (6,8)
    and DATE(psa.date_started_status) BETWEEN :startDate AND :endDate
    GROUP BY 1) B,
    (SELECT ob.person_id as patient_id,
    MAX(DATE(ob.obs_datetime)) AS obs_datetime
    FROM openmrs.obs ob WHERE ob.concept_id = 12
    AND ob.value_coded in (1116,1137,6049,6050,6052,114108)
    AND ob.voided <> 1
    AND ob.obs_datetime BETWEEN :startDate AND :endDate
    GROUP BY 1) D
WHERE p.patient_id = B.patient_id
AND p.patient_id = D.patient_id
AND p.date_started_arv BETWEEN :startDate AND :endDate
AND p.voided <> 1;







