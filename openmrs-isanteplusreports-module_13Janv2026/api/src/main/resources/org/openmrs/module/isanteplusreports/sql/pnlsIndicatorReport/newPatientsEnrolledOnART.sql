SELECT p.patient_id
FROM isanteplus.patient p
        WHERE (p.transferred_in IS NULL OR p.transferred_in = 0)
        AND p.date_started_arv IS NOT NULL
        AND p.arv_status IS NOT NULL
		AND p.date_started_arv BETWEEN :startDate AND :endDate 
		AND p.voided = 0;