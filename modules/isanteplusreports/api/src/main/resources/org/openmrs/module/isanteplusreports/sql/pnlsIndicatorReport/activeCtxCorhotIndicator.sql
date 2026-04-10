/*5 b) Nombre de patients VIH(+) sous ARV placées sous PROPHYLAXIE CTX au cours du mois*/
SELECT p.patient_id
FROM isanteplus.patient_on_arv p,
     isanteplus.patient pt,
     isanteplus.patient_status_arv ps,
     isanteplus.patient_dispensing pd,
     (SELECT pdis.patient_id, DATE(MIN(pdis.visit_date)) AS min_visit_date
    FROM isanteplus.patient_dispensing pdis WHERE pdis.drug_id = 105281
                                              AND (pdis.voided IS NULL OR pdis.voided <> 1)
GROUP BY 1) B,
    (SELECT psa.patient_id,
    MAX(psa.start_date) as date_status
FROM isanteplus.patient_status_arv psa WHERE DATE(psa.start_date) < :startDate GROUP BY 1) C
WHERE p.patient_id = B.patient_id
  AND p.patient_id = C.patient_id
  AND p.patient_id = pt.patient_id
  AND p.patient_id = ps.patient_id
  AND p.patient_id = pd.patient_id
  AND DATE(ps.start_date) = DATE(C.date_status)
  AND DATE(pd.visit_date) = DATE(B.min_visit_date)
  AND pd.drug_id = 105281
  AND DATE(pd.visit_date) BETWEEN :startDate AND :endDate
  AND DATE(ps.start_date) < :startDate
  AND (p.voided IS NULL OR p.voided <> 1)
  AND (pd.voided IS NULL OR pd.voided <> 1);
