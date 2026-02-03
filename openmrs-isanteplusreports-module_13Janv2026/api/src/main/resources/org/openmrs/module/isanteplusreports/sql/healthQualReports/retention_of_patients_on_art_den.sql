SELECT DISTINCT 
	p.patient_id
FROM isanteplus.patient p
    LEFT JOIN isanteplus.patient_dispensing pd ON p.patient_id = pd.patient_id AND pd.arv_drug = 1065
    INNER JOIN isanteplus.patient_status_arv arv ON p.patient_id = arv.patient_id
WHERE p.vih_status = '1' -- HIV+ patient
AND p.arv_status NOT IN (1,2,3)
   AND TIMESTAMPDIFF(YEAR, p.birthdate, :currentDate) > 14; -- adult
