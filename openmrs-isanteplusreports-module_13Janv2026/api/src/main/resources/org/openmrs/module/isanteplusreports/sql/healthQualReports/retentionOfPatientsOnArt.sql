SELECT
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
            AND p.patient_id = C.patient_id 
                AND arv.date_started_status BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
           AND p.patient_id = C.patient_id
            AND arv.date_started_status BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
        ) THEN p.patient_id ELSE null END
    ) AS 'maleNumerator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'F'
        ) THEN p.patient_id ELSE null END
    ) AS 'femaleDenominator',
    COUNT(
        DISTINCT CASE WHEN (
            p.gender = 'M'
        ) THEN p.patient_id ELSE null END
    ) AS 'maleDenominator'
FROM isanteplus.patient p
    LEFT JOIN isanteplus.patient_dispensing pd ON p.patient_id = pd.patient_id AND pd.arv_drug = 1065
    INNER JOIN isanteplus.patient_status_arv arv ON p.patient_id = arv.patient_id
    AND arv.date_started_status BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
	LEFT JOIN ( SELECT pnv.patient_id FROM isanteplus.health_qual_patient_visit pnv
      			WHERE pnv.encounter_type IN (5, 11)
      			AND (pnv.visit_date BETWEEN DATE(:currentDate) AND DATE_ADD(DATE(:currentDate), INTERVAL 90 DAY)
				OR pnv.visit_date BETWEEN DATE_SUB(DATE(:currentDate), INTERVAL 90 DAY) AND DATE(:currentDate))) C
				ON p.patient_id = C.patient_id
WHERE p.vih_status = '1'
AND p.arv_status NOT IN (1,2,3)
  /*  AND p.patient_id NOT IN (   
        SELECT discon.patient_id
        FROM isanteplus.discontinuation_reason discon
        WHERE discon.reason IN (1667, 159492)
    )*/ AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14;
