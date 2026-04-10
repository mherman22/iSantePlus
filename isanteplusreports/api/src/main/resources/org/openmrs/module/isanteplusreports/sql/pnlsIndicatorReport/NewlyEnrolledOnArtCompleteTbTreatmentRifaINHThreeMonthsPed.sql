/*14 a */

SELECT DISTINCT p.patient_id
FROM isanteplus.patient p,isanteplus.patient_dispensing pd, isanteplus.patient_on_art pt,
     (SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date
      FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 78280
                                              AND pd.rx_or_prophy = 163768 GROUP BY 1) B,
     (SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date
      FROM isanteplus.patient_dispensing pd WHERE  pd.drug_id = 83360
                                              AND pd.rx_or_prophy = 163768 GROUP BY 1) C
WHERE p.patient_id = pd.patient_id
  AND p.patient_id = B.patient_id
  AND B.patient_id = C.patient_id
  AND B.min_vist_date = C.min_vist_date
  AND (p.transferred_in <> 1 OR p.transferred_in IS NULL)
  AND (p.birthdate <>'' AND p.birthdate is not null)
  AND p.date_started_arv IS NOT NULL
  AND DATE(pd.visit_date) = DATE(B.min_vist_date)
  AND p.date_started_arv BETWEEN pd.dispensation_date AND :endDate
  AND pd.rx_or_prophy = 163768
  AND ((DATE_ADD(pd.visit_date, INTERVAL pd.pills_amount DAY) BETWEEN :startDate AND :endDate) or (
    pt.patient_id=p.patient_id and date_full_6_months_of_inh_has_px BETWEEN :startDate AND :endDate
    ))
  AND p.voided <> 1
  AND pd.voided <> 1;


