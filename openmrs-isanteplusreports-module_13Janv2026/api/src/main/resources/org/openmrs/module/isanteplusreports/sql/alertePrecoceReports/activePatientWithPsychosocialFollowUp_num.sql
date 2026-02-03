SELECT p.patient_id FROM isanteplus.patient p,isanteplus.patient_status_arv psa,
(SELECT ps.patient_id, MAX(DATE(ps.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv ps 
	WHERE DATE(ps.date_started_status) 
	between :startDate AND :endDate GROUP BY 1) B, openmrs.obs o, openmrs.encounter e,
	openmrs.encounter_type et, openmrs.concept c
 WHERE p.patient_id = psa.patient_id
 AND psa.patient_id = B.patient_id
 AND B.patient_id = o.person_id
 AND o.encounter_id = e.encounter_id
 AND e.encounter_type = et.encounter_type_id
 AND o.value_coded = c.concept_id
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND et.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 AND o.concept_id = 163145 
 AND c.uuid IN('9fb9a204-56d4-488c-9693-c067c53b593d',
 				'5079a471-61b4-4d0e-ad87-c08fc31162b2','3c9afedd-4287-4f32-ac76-41410a61d5e0')
 AND DATE(e.encounter_datetime) between :startDate AND :endDate
 AND DATE(psa.date_started_status) between :startDate AND :endDate;