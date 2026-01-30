/*Ind. 34.1.3*/
SELECT psa.patient_id
FROM isanteplus.patient_status_arv psa,
     (SELECT ob.person_id as patient_id, ob.encounter_id, ob.obs_datetime from openmrs.obs ob
      where ob.concept_id = 1748
        AND ob.value_coded = 509165910
        AND ob.voided <> 1) B
WHERE psa.patient_id = B.patient_id
  AND B.encounter_id = psa.encounter_id
  AND DATE_SUB(:endDate, INTERVAL 3 MONTH) < psa.date_started_status
  AND psa.date_started_status < :endDate
  AND psa.id_status = 1
GROUP BY 1;



