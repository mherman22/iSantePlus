SELECT DISTINCT p.patient_id
  FROM (SELECT
          CASE
              WHEN MONTH(:endDate) >= 10 THEN CONCAT(YEAR(:endDate),'-10','-01')
              WHEN MONTH(:endDate) < 10 THEN CONCAT(YEAR(:endDate) - 1,'-10','-01')
           END AS startDate) A,
        isanteplus.patient p, isanteplus.patient_tb_diagnosis pdiag
  WHERE ((pdiag.patient_id=p.patient_id
    AND pdiag.tb_started_treatment = 1
    AND DATE(pdiag.tb_treatment_start_date) BETWEEN A.startDate AND :endDate)
     OR (p.patient_id IN
        (SELECT ppr.patient_id FROM isanteplus.patient_prescription ppr
          WHERE ((ppr.drug_id IN (75948,82900,767,84360,78280)
            AND (ppr.rx_or_prophy = 138405)
            AND DATE(ppr.visit_date) BETWEEN A.startDate AND :endDate)))))
    AND vih_status = 1;
