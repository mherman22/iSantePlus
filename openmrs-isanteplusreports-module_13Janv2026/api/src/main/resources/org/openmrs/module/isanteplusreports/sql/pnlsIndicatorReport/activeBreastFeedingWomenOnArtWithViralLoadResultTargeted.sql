/*Ind. 39.1.1*/
SELECT A.patient_id FROM
    (SELECT ps.patient_id as patient_id, max(date_started_status)
     FROM isanteplus.patient_status_arv ps
     WHERE ps.id_status IN (6, 8)
       AND ps.date_started_status BETWEEN :startDate AND :endDate
     GROUP BY 1) A,
    (SELECT DISTINCT(pla.patient_id) as patient_id, MAX(pla.visit_date)
     FROM isanteplus.patient_laboratory pla
     WHERE pla.test_id IN (856, 1305)
       AND pla.test_result IS NOT NULL
       AND pla.visit_date >  DATE_SUB(:endDate, INTERVAL 12 MONTH)
       AND pla.visit_date < :endDate
       AND  pla.voided <> 1
     GROUP BY 1) B, isanteplus.patient_on_art pat
WHERE A.patient_id = B.patient_id
  AND A.patient_id = pat.patient_id
  AND pat.date_breast_feeding BETWEEN :startDate AND :endDate











