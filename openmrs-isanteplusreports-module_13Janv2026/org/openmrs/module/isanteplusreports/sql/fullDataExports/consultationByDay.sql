SELECT DATE_FORMAT(DATE(patvi.date_started), "%d-%m-%Y") as VisitDate, loc.name as Clinic, CONCAT(pat.given_name,' ',pat.family_name) as Patient,
CASE WHEN pat.vih_status=1 THEN 'Patient VIH'
     WHEN pat.vih_status=0 THEN 'Patient non VIH'
END as PatientType, enct.name as Form
FROM isanteplus.patient pat, openmrs.visit patvi, openmrs.location loc, openmrs.encounter enc,
 openmrs.encounter_type enct
 WHERE pat.patient_id=patvi.patient_id
 AND patvi.visit_id=enc.visit_id
 AND enc.location_id=loc.location_id
 AND enc.encounter_type=enct.encounter_type_id
 AND patvi.voided <> 1
 AND DATE(patvi.date_started) BETWEEN :startDate AND :endDate
 ORDER BY patvi.date_started DESC;