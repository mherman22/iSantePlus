SELECT usr.username as User,(CASE WHEN (DATE(enc.encounter_datetime) is not null AND DATE(enc.encounter_datetime) <> '' 
 AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate) 
 THEN DATE(enc.encounter_datetime) ELSE p.date_created END) AS visit_date,
 COUNT(DISTINCT CASE WHEN (p.vih_status = 1 AND p.date_created BETWEEN :startDate AND :endDate) 
						THEN p.patient_id else null END) AS 'Nouveau Patient VIH',
 COUNT(DISTINCT CASE WHEN (p.vih_status = 0 AND p.date_created BETWEEN :startDate AND :endDate) 
						THEN p.patient_id else null END) AS 'Nouveau Patient Non VIH',
 COUNT(DISTINCT CASE WHEN ((p.vih_status = 1 OR p.vih_status = 0 ) AND p.date_created BETWEEN :startDate AND :endDate) 
						THEN p.patient_id  else null END) AS 'Nouveau Patient',
 COUNT(DISTINCT CASE WHEN (p.vih_status = 1 AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate
                           ) 
						THEN p.patient_id else null END) AS 'Patient VIH',
 COUNT(DISTINCT CASE WHEN (p.vih_status = 0 AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate) 
						THEN p.patient_id else null END) AS 'Patient Non VIH',
COUNT(DISTINCT CASE WHEN ((p.vih_status = 1 OR p.vih_status = 0 ) AND DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate) 
						THEN p.patient_id else null END) AS 'Total Patient'
  FROM openmrs.users usr INNER JOIN isanteplus.patient p ON usr.user_id = p.creator LEFT OUTER JOIN openmrs.encounter enc
 ON p.patient_id = enc.patient_id
 WHERE ((DATE(enc.encounter_datetime) BETWEEN :startDate AND :endDate) OR ((p.date_created BETWEEN :startDate AND :endDate)))
 AND p.voided <> 1
 GROUP BY 1, 2
 ORDER BY 2 DESC;