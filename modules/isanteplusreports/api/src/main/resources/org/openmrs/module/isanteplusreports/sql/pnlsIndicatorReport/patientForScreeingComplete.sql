/*Ind. 20.*/

SELECT DISTINCT p.patient_id
FROM isanteplus.patient p, isanteplus.patient_dispensing pd, isanteplus.patient_on_art pt,
     (select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
      FROM isanteplus.patient_status_arv psar
      WHERE psar.id_status in (6,8)
        and DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B,
    (SELECT ob.person_id as patient_id,
    MAX(DATE(ob.obs_datetime)) AS obs_datetime
      FROM openmrs.obs ob WHERE ob.concept_id = 1659
      AND ob.value_coded IN (1660,142177)
      AND ob.voided <> 1
      AND TIMESTAMPDIFF(MONTH, DATE(ob.obs_datetime),:endDate) <= 6 GROUP BY 1) C
WHERE p.patient_id = B.patient_id
  and p.patient_id = C.patient_id
  AND p.patient_id = pd.patient_id
  AND p.voided <> 1
  AND pd.rx_or_prophy = 163768
  AND pd.pills_amount >= 30
  AND ((DATE_ADD(pd.visit_date, INTERVAL pd.pills_amount DAY) BETWEEN  :startDate AND :endDate) or (
    pt.patient_id=p.patient_id and pt.date_completed_preventive_tb_treatment BETWEEN :startDate AND :endDate
    ))
  AND p.voided <> 1
  AND pd.voided <> 1;


