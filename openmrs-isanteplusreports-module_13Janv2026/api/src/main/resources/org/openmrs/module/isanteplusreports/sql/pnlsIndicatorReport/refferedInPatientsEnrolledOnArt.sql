/*2: Enrôlement et référence des personnes des populations clés*/
/*2)Préalablement enrôlées et référées */

SELECT p.patient_id
FROM isanteplus.patient p
        WHERE p.transferred_in = 1
        AND p.date_started_arv IS NOT NULL
		AND p.date_started_arv BETWEEN :startDate AND :endDate 
		AND p.voided = 0;