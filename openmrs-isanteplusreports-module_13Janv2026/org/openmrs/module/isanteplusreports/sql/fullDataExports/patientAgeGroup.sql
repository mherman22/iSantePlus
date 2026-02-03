/*Patients par groupe d'âge*/

select 
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate))<15 THEN p.patient_id else null END) AS '0-14',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 15 AND 20 THEN p.patient_id else null END) AS '15-20',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 21 AND 30 THEN p.patient_id else null END) AS '21-30',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 31 AND 40 THEN p.patient_id else null END) AS '31-40',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 41 AND 50 THEN p.patient_id else null END) AS '41-50',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 51 AND 60 THEN p.patient_id else null END) AS '51-60',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 61 AND 70 THEN p.patient_id else null END) AS '61-70',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 71 AND 80 THEN p.patient_id else null END) AS '71-80',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 81 AND 90 THEN p.patient_id else null END) AS '81-90',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 91 AND 100 THEN p.patient_id else null END) AS '91-100',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 101 AND 110 THEN p.patient_id else null END) AS '101-110',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 111 AND 120 THEN p.patient_id else null END) AS '111-120',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) between 121 AND 130 THEN p.patient_id else null END) AS '121-130',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,:endDate)) > 130 THEN p.patient_id else null END) AS '>130',
        		COUNT(DISTINCT CASE WHEN (p.birthdate = '' OR p.birthdate is null) THEN p.patient_id else null END) AS Inconnu,
                count(DISTINCT p.patient_id) as 'Nombre total de patients'
        FROM isanteplus.patient p
        WHERE p.vih_status = 1
        AND (p.birthdate <>'' AND p.birthdate is not null)
        AND p.patient_id NOT IN (select enc.patient_id FROM openmrs.encounter enc, openmrs.encounter_type et 
			where enc.encounter_type = et.encounter_type_id AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba'
			AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate)
		AND p.voided <> 1
		AND p.date_created BETWEEN :startDate AND :endDate;