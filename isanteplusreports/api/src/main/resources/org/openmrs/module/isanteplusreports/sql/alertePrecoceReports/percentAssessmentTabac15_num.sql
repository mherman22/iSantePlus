SELECT DISTINCT p.patient_id FROM isanteplus.patient p, isanteplus.patient_comorbidity pcomo
          WHERE p.patient_id = pcomo.patient_id
            AND p.arv_status NOT IN (1, 2)
            AND pcomo.answer_concept_id = 163731
            AND DATE(pcomo.encounter_date) between :startDate AND :endDate
            AND (TIMESTAMPDIFF(YEAR, p.birthdate, CURDATE()) >= 15);

