SELECT p.patient_id
FROM isanteplus.patient p,
     isanteplus.patient_dispensing pd,
     isanteplus.patient_on_art pat,
     (SELECT pd.patient_id, pd.visit_date AS min_vist_date
      FROM isanteplus.patient_dispensing pd
      WHERE pd.drug_id = 83360
        AND pd.rx_or_prophy = 163768) B
WHERE p.patient_id = pd.patient_id
  AND (p.transferred_in IS NULL OR p.transferred_in = 0)
  AND (p.birthdate <> '' AND p.birthdate is not null)
  AND p.date_started_arv IS NOT NULL
  AND pd.drug_id = 78280
  AND pd.rx_or_prophy = 163768
  AND p.arv_status IN (6, 8)
  AND ((p.patient_id = pat.patient_id and pat.date_completed_preventive_tb_treatment BETWEEN :startDate AND :endDate)
        OR(TIMESTAMPDIFF(MONTH, pd.visit_date,:endDate) = 6))
  AND p.voided <> 1
  AND pd.voided <> 1;

