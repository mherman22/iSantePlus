SELECT DISTINCT(pl.patient_id)
FROM isanteplus.patient p, isanteplus.patient_status_arv ps,
     isanteplus.patient_laboratory pl, isanteplus.patient_on_art pat,
     (SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status
      FROM isanteplus.patient_status_arv psa WHERE
          psa.date_started_status BETWEEN :startDate AND :endDate GROUP BY 1) B,
     (SELECT DISTINCT (pla.patient_id), MAX(DATE(pla.visit_date)) as max_date_test
      FROM isanteplus.patient_laboratory pla
      WHERE pla.test_id IN (856, 1305)
        AND pla.visit_date <= :endDate
        AND  pla.voided <> 1 GROUP BY 1) C
WHERE p.patient_id = pl.patient_id
  AND ps.patient_id = pl.patient_id
  AND pat.patient_id = ps.patient_id
  AND ps.patient_id = B.patient_id
  AND ps.patient_id = C.patient_id
  AND DATE(ps.date_started_status) = DATE(B.date_status)
  AND ps.id_status IN (6, 8)
  AND pl.test_id IN (856, 1305)
  AND ps.date_started_status BETWEEN :startDate AND :endDate
  AND pl.voided <> 1
  AND pl.visit_date = C.max_date_test
  AND TIMESTAMPDIFF(MONTH, C.max_date_test, :endDate) < 12
  AND p.voided <> 1;
