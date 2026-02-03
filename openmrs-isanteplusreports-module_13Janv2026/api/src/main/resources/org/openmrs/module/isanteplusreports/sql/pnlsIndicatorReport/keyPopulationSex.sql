SELECT pa.patient_id
FROM isanteplus.patient_on_art pa, openmrs.obs o, openmrs.concept c
        WHERE pa.patient_id = o.person_id
        AND o.concept_id = c.concept_id
        AND c.uuid = 'b2726cc7-df4b-463c-919d-1c7a600fef87'
        AND o.value_coded = 160579
        AND o.voided <> 1
        /*AND o.obs_datetime BETWEEN :startDate AND :endDate;*/