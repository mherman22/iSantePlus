SELECT p.patient_id
FROM isanteplus.patient p,isanteplus.patient_dispensing pd,
(SELECT pd.patient_id, MIN(DATE(pd.visit_date)) AS min_vist_date 
FROM isanteplus.patient_dispensing pd WHERE pd.drug_id = 78280 GROUP BY 1) B
        WHERE p.patient_id = pd.patient_id
		AND (p.transferred_in <> 1 OR p.transferred_in IS NULL)
		AND p.patient_id = B.patient_id
        AND (p.birthdate <>'' AND p.birthdate is not null)
        AND p.date_started_arv IS NOT NULL
		AND (DATE(p.date_started_arv) < :startDate 
		OR DATE(p.date_started_arv) BETWEEN :startDate AND :endDate)
		AND pd.drug_id = 78280
		AND pd.rx_or_prophy = 163768
		AND DATE(pd.visit_date) = DATE(B.min_vist_date)
		AND DATE(pd.visit_date) BETWEEN :startDate AND :endDate
		AND p.voided <> 1
		AND pd.voided <> 1;