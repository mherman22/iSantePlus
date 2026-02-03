SELECT pat.patient_id   
FROM isanteplus.patient_on_art pat, isanteplus.family_planning fp,
(SELECT fpl.patient_id, MIN(DATE(fpl.encounter_date)) AS visit_date 
FROM isanteplus.family_planning fpl GROUP BY 1) B
WHERE pat.patient_id = fp.patient_id
AND fp.patient_id = B.patient_id
AND DATE(fp.encounter_date) = DATE(B.visit_date)
AND fp.family_planning_method_name = "VAG_TABS"
AND DATE(fp.encounter_date) BETWEEN :startDate AND :endDate
AND fp.voided = 0
AND fp.accepting_or_using_fp = 1;