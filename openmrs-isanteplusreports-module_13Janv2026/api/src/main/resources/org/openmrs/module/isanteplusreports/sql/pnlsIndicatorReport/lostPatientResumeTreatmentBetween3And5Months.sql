/*Ind. 35.2*/
SELECT p.patient_id FROM isanteplus.patient p,
     (SELECT psa.patient_id, MAX( psa.date_started_status) as date_started_status
      FROM isanteplus.patient_status_arv psa
      WHERE DATE_SUB(:endDate, INTERVAL 8 MONTH) < psa.date_started_status
        AND DATE_SUB(:endDate, INTERVAL 3 MONTH) > psa.date_started_status
        AND psa.id_status IN (3, 9)
      GROUP BY 1) A
WHERE p.patient_id = A.patient_id
  AND p.arv_status in (6, 8)
  AND DATE (p.last_visit_date) <= :endDate
  AND DATE (p.last_visit_date) >= DATE_SUB(:endDate, INTERVAL 3 MONTH);
