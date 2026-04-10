SELECT ps.patient_id
FROM isanteplus.patient_status_arv ps,isanteplus.patient_on_art pat,
     (SELECT psa.patient_id,
             MAX(psa.date_started_status) as date_status
      FROM isanteplus.patient_status_arv psa WHERE psa.date_started_status
                                                       BETWEEN :startDate AND :endDate GROUP BY 1) B,

     (SELECT pdis.patient_id, date_started_regime_treatment as visit_date, pdis.treatment_regime_lines
      FROM isanteplus.patient_on_art pdis
      WHERE pdis.treatment_regime_lines is not null
        AND pdis.treatment_regime_lines='THIRD_LINE') C

WHERE ps.patient_id = pat.patient_id
  AND ps.patient_id = B.patient_id
  AND B.patient_id = C.patient_id
  AND DATE(ps.date_started_status) = DATE(B.date_status)
  AND ps.id_status IN (6,8)
  AND ps.date_started_status BETWEEN :startDate AND :endDate;

