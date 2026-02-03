select 
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 0 AND 14 THEN p.patient_id else null END) AS '0-14',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 15 AND 20 THEN p.patient_id else null END) AS '15-20',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 21 AND 30 THEN p.patient_id else null END) AS '21-30',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 31 AND 40 THEN p.patient_id else null END) AS '31-40',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 41 AND 50 THEN p.patient_id else null END) AS '41-50',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 51 AND 60 THEN p.patient_id else null END) AS '51-60',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 61 AND 70 THEN p.patient_id else null END) AS '61-70',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 71 AND 80 THEN p.patient_id else null END) AS '71-80',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 81 AND 90 THEN p.patient_id else null END) AS '81-90',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 91 AND 100 THEN p.patient_id else null END) AS '91-100',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 101 AND 110 THEN p.patient_id else null END) AS '101-110',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 111 AND 120 THEN p.patient_id else null END) AS '111-120',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) between 121 AND 130 THEN p.patient_id else null END) AS '121-130',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) > 130 THEN p.patient_id else null END) AS '>130',
        		COUNT(DISTINCT CASE WHEN (TIMESTAMPDIFF(YEAR,p.birthdate,DATE(enc.encounter_datetime))) < 0 THEN p.patient_id else null END) AS 'inconnu',
                COUNT(DISTINCT p.patient_id) as 'Total'
        FROM isanteplus.patient p, openmrs.encounter enc, 
        (SELECT en.patient_id, MIN(DATE(en.encounter_datetime)) AS encounter_date FROM openmrs.encounter en WHERE en.voided <> 1 GROUP BY 1) B
        WHERE p.patient_id = enc.patient_id
        AND enc.patient_id = B.patient_id
        AND DATE(enc.encounter_datetime) = B.encounter_date
        AND p.vih_status = 1
        AND p.voided <> 1
        AND enc.patient_id NOT IN (select e.patient_id from openmrs.encounter e, openmrs.encounter_type et
                                   WHERE e.encounter_type = et.encounter_type_id AND et.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba')
        AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate;
      