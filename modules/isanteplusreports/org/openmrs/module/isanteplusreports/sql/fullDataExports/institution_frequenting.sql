SELECT
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
  FROM isanteplus.patient p LEFT OUTER JOIN openmrs.encounter enc
 ON p.patient_id = enc.patient_id
 WHERE p.voided <> 1;				   
						   
						   
						   
						   