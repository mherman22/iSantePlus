SELECT p.patient_id
FROM isanteplus.patient p,
     isanteplus.patient_on_art pa,
     isanteplus.patient_status_arv psa,
     (SELECT psar.patient_id,
             MAX(psar.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psar
      WHERE psar.date_started_status
                BETWEEN :startDate AND :endDate
      GROUP BY 1) B
WHERE p.patient_id = pa.patient_id
  AND p.patient_id = psa.patient_id
  AND psa.patient_id = B.patient_id
  AND DATE (psa.date_started_status) = DATE (B.date_status)
  AND psa.id_status IN (6
    , 8)
  AND psa.date_started_status BETWEEN :startDate
  AND :endDate
  AND pa.last_folowup_vist_date IS NOT NULL
  AND DATEDIFF (:endDate
    , pa.last_folowup_vist_date ) <= 90
  AND DATEDIFF(pa.last_folowup_vist_date
    , IFNULL(DATE (pa.second_last_folowup_vist_date)
    , DATE (pa.first_vist_date))) BETWEEN 0
  AND 89;
