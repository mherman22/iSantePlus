/*Ind. 26*/
SELECT A.patient_id FROM
(SELECT psa.patient_id AS patient_id, MAX(psa.date_started_status)
 FROM isanteplus.patient_status_arv psa
 WHERE psa.date_started_status BETWEEN :startDate AND :endDate
   AND psa.id_status IN (6,8)
 GROUP BY 1) A,
(SELECT o.person_id AS patient_id, DATE(MAX(e.encounter_datetime)) AS encounter_datetime
 FROM openmrs.obs o, openmrs.encounter e
 WHERE o.encounter_id=e.encounter_id
   AND o.person_id=e.patient_id
   AND ((o.concept_id IN(6042,6097)
       AND o.value_coded=117401) OR (o.concept_id=1284 AND value_coded IN(140987,117399,113087,161644,117401,116116,165205,117383)))
   AND o.voided=0
   AND DATE(e.encounter_datetime) > DATE_SUB(:endDate, INTERVAL 6 MONTH)
   AND DATE(e.encounter_datetime) <= :endDate
GROUP BY 1) B,
(SELECT o.person_id AS patient_id, DATE(MAX(o.obs_datetime)) AS obs_datetime
   FROM openmrs.obs o, openmrs.obs o1
  WHERE o.person_id = o1.person_id
    AND o.encounter_id = o1.encounter_id
    AND o.obs_datetime = o1.obs_datetime
    AND (o.concept_id=5085 AND o.value_numeric < 140
        AND DATE(o.obs_datetime) > DATE_SUB(:endDate, INTERVAL 6 MONTH)
        AND DATE(o.obs_datetime) <= :endDate AND o.voided=0)
        AND (o1.concept_id=5086 AND o1.value_numeric < 90
    AND DATE(o1.obs_datetime) > DATE_SUB(:endDate, INTERVAL 6 MONTH)
    AND DATE(o1.obs_datetime) <= :endDate AND o1.voided=0)
  GROUP BY 1
) C
WHERE A.patient_id = B.patient_id
  AND B.patient_id = C.patient_id
  AND B.encounter_datetime = C.obs_datetime;











