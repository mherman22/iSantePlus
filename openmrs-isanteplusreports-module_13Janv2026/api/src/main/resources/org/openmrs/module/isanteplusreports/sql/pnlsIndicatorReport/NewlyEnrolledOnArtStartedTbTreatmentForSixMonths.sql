/*7 a, :Nombre de patients nouvellement sous ARV ayant initié un traitement préventif de la
 * TB il y a 6 mois: (TB_PREV D)*/
SELECT p.patient_id 
FROM isanteplus.patient p,isanteplus.patient_dispensing pd,
(SELECT pd.patient_id,MIN(pd.visit_date) AS min_vist_date 
FROM isanteplus.patient_dispensing pd 
WHERE pd.drug_id = 78280 AND pd.rx_or_prophy = 163768 GROUP BY 1) B
      WHERE p.patient_id = pd.patient_id
	  AND (p.transferred_in <> 1 OR p.transferred_in IS NULL)
	  AND p.patient_id = B.patient_id
      AND (p.birthdate <>'' AND p.birthdate is not null)
      AND p.date_started_arv IS NOT NULL
      AND DATE(pd.visit_date) = DATE(B.min_vist_date)
	  /*AND p.date_started_arv BETWEEN :startDate AND :endDate*/
	  AND pd.drug_id = 78280
	  AND pd.rx_or_prophy = 163768
	  AND TIMESTAMPDIFF(MONTH,pd.visit_date, :endDate) >= 6
	  AND TIMESTAMPDIFF(MONTH,pd.visit_date, :endDate) < 7
	  AND TIMESTAMPDIFF(MONTH,p.date_started_arv, :endDate) >= 6
	  AND TIMESTAMPDIFF(MONTH,p.date_started_arv, :endDate) < 7
	  AND p.voided <> 1
	  AND pd.voided <> 1
        AND p.patient_id NOT IN
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
                AND pd.drug_id  =78280
                AND pd.rx_or_prophy = 163768
                AND pd.visit_date = B.min_vist_date
                AND TIMESTAMPDIFF(MONTH,pd.visit_date, :endDate) >= 6
                AND TIMESTAMPDIFF(MONTH,pd.visit_date, :endDate) < 7
                AND TIMESTAMPDIFF(MONTH,p.date_started_arv, :endDate) >= 6
                AND TIMESTAMPDIFF(MONTH,p.date_started_arv, :endDate) < 7
                AND p.voided <> 1);
