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
 AND o.concept_id = c.concept_id
 AND DATE(psa.date_started_status) = DATE(B.date_status)
 AND psa.id_status IN (6,8)
 AND et.uuid IN ('a0d57dca-3028-4153-88b7-c67a30fde595',
 '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','09d3f0aa-bf4b-42b9-a750-138178bda202')
 AND o.concept_id = 163145 
 AND c.uuid IN('1054AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
 				'1825AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
 				'a7014c39-eac8-44ee-ab58-d0dd416682fa',
 				'36dbbb2a-7c98-4c0b-aef9-c1d7d559064f',
 				'b7138252-def2-466c-b166-bb6d911c7a54',
 				'2421bd25-726f-41be-953d-8173381b3ab0',
 				'3d376960-3b97-4958-8c86-7626b2fd1bd0',
 				'2fac6db5-1fcb-419a-84a6-17bd09a047b1',
 				'6b31605d-5987-4744-9444-b8b1f8f217da',
 				'68c14421-1a12-470b-8cd0-500cebdb7177',
 				'159740AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
 				'e0f3ac04-1f14-427d-b7e5-e5a06db9905e',
 				'72a714d2-1183-431e-9a30-f1361aa0b177',
 				'163577AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA',
 				'fe556012-245c-4a30-a3bf-f0961be4aa10',
 				'ab30c201-df39-452e-bbc9-c718f5be98d6',
 				'd4ba3c5b-7fad-4bca-8a5e-0834f1623f46',
 				'aad7ae43-39e9-4e25-a79d-9a334b4ae5a4')
 AND DATE(e.encounter_datetime) between :startDate AND :endDate
 AND DATE(psa.date_started_status) between :startDate AND :endDate;