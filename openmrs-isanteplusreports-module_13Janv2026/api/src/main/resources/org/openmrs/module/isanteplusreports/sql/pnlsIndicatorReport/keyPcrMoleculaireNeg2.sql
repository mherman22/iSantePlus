/*Ind. 18.2.2 Neg*/

SELECT p.patient_id
FROM isanteplus.patient p,
     (SELECT psa.patient_id,
             MAX(DATE(psa.date_started_status)) AS date_status
      FROM isanteplus.patient_status_arv psa WHERE psa.id_status in (6,8)
                                               and DATE(psa.date_started_status) BETWEEN :startDate AND :endDate
GROUP BY 1) B,
    (SELECT ob.person_id as patient_id, ob2.value_datetime AS value_datetime
FROM openmrs.obs ob, openmrs.obs ob1, openmrs.obs ob2
WHERE ob.obs_id = ob1.obs_group_id
  AND ob.obs_id = ob2.obs_group_id
  AND ob.concept_id = 509165561
  AND ob1.concept_id = 509165920
  AND ob2.concept_id = 159964
  AND ob1.value_coded = 1302
  AND ob.voided <> 1
  AND ob2.value_datetime BETWEEN :startDate AND :endDate
GROUP BY 1) D
WHERE p.patient_id = B.patient_id
  AND p.patient_id = D.patient_id
  AND p.date_started_arv < :startDate
  AND p.voided <> 1;
