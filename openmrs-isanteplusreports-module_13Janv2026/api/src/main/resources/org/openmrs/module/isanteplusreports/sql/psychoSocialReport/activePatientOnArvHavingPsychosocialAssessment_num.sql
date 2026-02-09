
select A.patient_id from
                        (select patient_id, max(last_updated_date) as last_status_date
                         from patient_status_arv where id_status in (6, 8)
                                                   and DATE(last_updated_date) between '2026-02-01' and '2026-02-08'
                         group by 1) A,
                        (select patient_id, encounter_id, encounter_date, encounter_type
                         from isanteplus.patient_diagnosis
                         where concept_id = 509166597
                           and DATE(encounter_date) between '2026-02-01' and '2026-02-08'
                           and encounter_type in (select encounter_type_id as encounter_type
                                                  from openmrs.encounter_type
                                                  where uuid in ('51df75f7-a3de-4f82-a9df-c0bedaf5a2dd','a0d57dca-3028-4153-88b7-c67a30fde595'))) B
where A.patient_id = B.patient_id