SELECT
    COUNT( DISTINCT CASE WHEN (
            p.gender = 'F'
      AND pp.rx_or_prophy = 163768 -- prophy
      AND pp.drug_id = 78280 -- INH chemoprophylaxis 
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
        ) THEN p.patient_id else null END
    ) AS 'femaleNumerator',
    COUNT( DISTINCT CASE WHEN (
            p.gender = 'M'
      AND pp.rx_or_prophy = 163768 -- prophy
      AND pp.drug_id = 78280 -- INH chemoprophylaxis 
      AND (pp.visit_date BETWEEN :startDate AND :endDate)
        ) THEN p.patient_id else null END
    ) AS 'maleNumerator',
    COUNT( DISTINCT CASE WHEN (
            p.gender = 'F'
        ) THEN p.patient_id else null END
    ) AS 'femaleDenominator',
  COUNT( DISTINCT CASE WHEN (
            p.gender = 'M'
        ) THEN p.patient_id else null END
    ) AS 'maleDenominator'
FROM isanteplus.patient p
LEFT JOIN isanteplus.patient_prescription pp
ON p.patient_id = pp.patient_id
WHERE
    p.vih_status = 1
    AND TIMESTAMPDIFF(MONTH, p.birthdate, :endDate) < 14
    AND p.patient_id IN (
        SELECT pv.patient_id
        FROM isanteplus.health_qual_patient_visit pv
        WHERE
            pv.is_active_tb IS true
            AND (
                DATE(pv.visit_date) BETWEEN :startDate AND :endDate
                OR (
                    DATE(pp.visit_date) BETWEEN :startDate AND :endDate
                    AND pp.rx_or_prophy = 138405
                )
            )
    )
    AND p.patient_id NOT IN (
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (159,1667,159492) -- 159-deceased, 1667- Discontinuations, 159492- Transfer
    );
