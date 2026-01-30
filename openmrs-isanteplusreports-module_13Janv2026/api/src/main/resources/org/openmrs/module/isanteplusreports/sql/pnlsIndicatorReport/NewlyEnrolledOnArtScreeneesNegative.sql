/*Ind. 17.2 a*/

SELECT p.patient_id
FROM isanteplus.patient p,
     (select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
      FROM isanteplus.patient_status_arv psar WHERE psar.id_status in (6,8)
                                                and DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B,
    (SELECT ob.person_id as patient_id,
    MAX(DATE(ob.obs_datetime)) AS obs_datetime
FROM openmrs.obs ob WHERE ob.concept_id = 1659
                      AND ob.value_coded=1660
                      AND ob.voided <> 1
                      AND TIMESTAMPDIFF(MONTH, DATE(ob.obs_datetime),:endDate) <= 6 GROUP BY 1) C
WHERE p.patient_id = B.patient_id
  and p.patient_id = C.patient_id
  AND p.date_started_arv IS NOT NULL
  and p.date_started_arv BETWEEN :startDate AND :endDate
  AND p.voided <> 1;


