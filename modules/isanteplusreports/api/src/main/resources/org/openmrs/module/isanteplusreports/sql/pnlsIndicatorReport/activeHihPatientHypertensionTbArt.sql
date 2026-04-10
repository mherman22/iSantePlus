/*Ind. 25*/
SELECT A.patient_id FROM
(SELECT psa.patient_id AS patient_id, MAX(psa.date_started_status)
FROM isanteplus.patient_status_arv psa
WHERE psa.date_started_status BETWEEN :startDate AND :endDate
  AND psa.id_status IN (6,8)
GROUP BY 1) A,
(SELECT o.person_id AS patient_id, DATE(MAX(e.encounter_datetime))
FROM openmrs.obs o, openmrs.encounter e
      WHERE o.encounter_id=e.encounter_id
        AND o.person_id=e.patient_id
        AND ((o.concept_id IN(6042,6097)
        AND o.value_coded=117401) OR (o.concept_id=1284 AND value_coded IN(140987,117399,113087,161644,117401,116116,165205,117383)))
        AND o.voided=0
        AND DATE(e.encounter_datetime) BETWEEN :startDate AND :endDate
        GROUP BY 1) B
WHERE A.patient_id = B.patient_id;



