SELECT DISTINCT
	p.patient_id
FROM isanteplus.patient p
    LEFT JOIN isanteplus.patient_dispensing pd ON p.patient_id = pd.patient_id AND pd.arv_drug = 1065
    INNER JOIN isanteplus.patient_status_arv arv ON p.patient_id = arv.patient_id
    INNER JOIN ( SELECT pnv.patient_id FROM isanteplus.patient_visit pnv
	WHERE pnv.encounter_type IN (5, 11) -- Ord. Médicale OR Ord. médicale Pédiatrique
	AND (pnv.visit_date BETWEEN DATE(:currentDate) AND DATE_ADD(DATE(:currentDate), INTERVAL 90 DAY)
	OR pnv.visit_date BETWEEN DATE_SUB(DATE(:currentDate), INTERVAL 90 DAY) AND DATE(:currentDate))) C
	ON p.patient_id = C.patient_id
WHERE p.vih_status = '1' -- HIV+ patient
AND p.arv_status NOT IN (1,2,3)
AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14 -- adult
	-- drug order form completed on date X. The date of the drug order form should not surpass the reporting period by more than 90 days.
 AND arv.start_date BETWEEN SUBDATE(:currentDate, INTERVAL :period MONTH) AND :currentDate
