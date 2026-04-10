/*Indicator 14 b*/

SELECT p.patient_id
FROM isanteplus.patient p,
     isanteplus.patient_dispensing pd,
     isanteplus.patient_on_art pat,
     (SELECT pd.patient_id, pd.visit_date AS min_vist_date
      FROM isanteplus.patient_dispensing pd
      WHERE pd.drug_id = 78280
        AND pd.rx_or_prophy = 163768) B,
     (SELECT pd.patient_id, pd.visit_date AS min_vist_date
      FROM isanteplus.patient_dispensing pd
      WHERE pd.drug_id = 83360
        AND pd.rx_or_prophy = 163768) C
WHERE p.patient_id = pd.patient_id
  AND p.patient_id = pat.patient_id
  AND (p.transferred_in IS NULL OR p.transferred_in = 0)
  AND p.patient_id = B.patient_id
  AND B.patient_id = C.patient_id
  AND B.min_vist_date = C.min_vist_date
  AND (p.birthdate <> '' AND p.birthdate is not null)
  AND p.date_started_arv IS NOT NULL
  AND (TIMESTAMPDIFF(MONTH,p.date_started_arv,:endDate) > 3)
  AND DATE (pd.visit_date) = DATE (B.min_vist_date)
  AND pd.rx_or_prophy = 163768
  AND
    (pat.date_completed_preventive_tb_treatment BETWEEN :startDate AND :endDate
   OR (TIMESTAMPDIFF(MONTH,C.min_vist_date,:endDate) = 3)
   OR (TIMESTAMPDIFF(MONTH,C.min_vist_date,:endDate) = 2 and C.min_vist_date < :startDate))
  AND p.voided <> 1
  AND pd.voided <> 1;
