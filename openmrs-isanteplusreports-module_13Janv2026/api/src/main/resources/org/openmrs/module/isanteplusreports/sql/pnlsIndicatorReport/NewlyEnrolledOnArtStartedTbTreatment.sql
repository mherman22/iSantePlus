SELECT p.patient_id
FROM isanteplus.patient p,isanteplus.patient_dispensing pd,
     (SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date
      FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 78280
                                              AND pd.rx_or_prophy = 163768 GROUP BY 1) B
WHERE p.patient_id = pd.patient_id
  AND (p.transferred_in = 0 OR p.transferred_in IS NULL)
  AND p.patient_id = B.patient_id
  AND (p.birthdate <>'' AND p.birthdate is not null)
  AND p.date_started_arv IS NOT NULL
  AND p.date_started_arv BETWEEN :startDate AND :endDate
  AND pd.drug_id  = 78280
  AND pd.rx_or_prophy = 163768
  AND pd.visit_date = B.min_vist_date
  AND pd.visit_date BETWEEN :startDate AND :endDate
  AND p.voided <> 1
  and p.patient_id NOT IN
      (SELECT p.patient_id
       FROM isanteplus.patient p,isanteplus.patient_dispensing pd,
            (SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date
             FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 78280
                                                     AND pd.rx_or_prophy = 163768 GROUP BY 1) B,
            (SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date
             FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 83360
                                                     AND pd.rx_or_prophy = 163768 GROUP BY 1) C
       WHERE p.patient_id = pd.patient_id
         AND (p.transferred_in = 0 OR p.transferred_in IS NULL)
         AND p.patient_id = B.patient_id
         AND B.patient_id = C.patient_id
         AND B.min_vist_date = C.min_vist_date
         AND (p.birthdate <>'' AND p.birthdate is not null)
         AND p.date_started_arv IS NOT NULL
         AND p.date_started_arv BETWEEN :startDate AND :endDate
         AND pd.drug_id  = 78280
         AND pd.rx_or_prophy = 163768
         AND pd.visit_date = B.min_vist_date
         AND pd.visit_date BETWEEN :startDate AND :endDate
         AND p.voided <> 1);
