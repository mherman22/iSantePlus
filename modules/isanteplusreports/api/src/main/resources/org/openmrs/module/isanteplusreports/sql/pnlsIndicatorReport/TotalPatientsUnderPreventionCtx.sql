SELECT pat.patient_id
      FROM isanteplus.patient pat, isanteplus.patient_on_arv p, isanteplus.patient_dispensing pd,
      isanteplus.patient_status_arv ps, 
      (select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) D
      WHERE pat.patient_id = p.patient_id
      AND p.patient_id = pd.patient_id
      AND pd.patient_id = ps.patient_id
      AND ps.patient_id = D.patient_id
      AND DATE(ps.date_started_status) = D.date_status
      AND ps.id_status = 6
      AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate
	   AND pd.drug_id = 105281 
      AND DATE(pd.next_dispensation_date) >= :startDate 
	   AND (pd.voided IS NULL OR pd.voided <> 1)
	   AND (pat.voided IS NULL OR pat.voided <> 1);