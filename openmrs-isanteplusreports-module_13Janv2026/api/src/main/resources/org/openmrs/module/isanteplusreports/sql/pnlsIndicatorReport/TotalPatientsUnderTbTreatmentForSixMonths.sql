SELECT p.patient_id 
FROM isanteplus.patient p,isanteplus.patient_dispensing pd,
(SELECT pd.patient_id, MIN(pd.visit_date) AS min_vist_date 
FROM isanteplus.patient_dispensing pd 
WHERE pd.drug_id = 78280 AND pd.rx_or_prophy = 163768 GROUP BY 1) B
      WHERE p.patient_id = pd.patient_id
	  AND p.transferred_in = 0
	  AND p.patient_id = B.patient_id
      AND (p.birthdate <>'' AND p.birthdate is not null)
      AND p.date_started_arv IS NOT NULL
      AND pd.visit_date = B.min_vist_date
	  AND (p.date_started_arv < :startDate 
	  OR p.date_started_arv BETWEEN :startDate AND :endDate )
	  AND pd.drug_id = 78280
	  AND pd.rx_or_prophy = 163768
	  AND TIMESTAMPDIFF(MONTH,pd.visit_date,:endDate) >= 6
	  AND TIMESTAMPDIFF(MONTH,pd.visit_date,:endDate) < 7 
	  AND (p.voided IS NULL OR p.voided <> 1)
	  AND (pd.voided IS NULL OR pd.voided <> 1);