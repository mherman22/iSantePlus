/* Consultation par jour */
SELECT  pat.patient_id AS 'Patient Id', DATE_FORMAT(DATE(patvi.date_started), "%d-%m-%Y") as VisitDate, loc.name as Clinic, CONCAT(pat.given_name,' ',pat.family_name) as Patient,
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
AND enct.uuid IN ('17536ba6-dd7c-4f58-8014-08c7cb798ac7','204ad066-c5c2-4229-9a62-644bc5617ca2',
'349ae0b4-65c1-4122-aa06-480f186c8350','33491314-c352-42d0-bd5d-a9d0bffc9bf1',
'5c312603-25c1-4dbe-be18-1a167eb85f97','49592bec-dd22-4b6c-a97f-4dd2af6f2171',
'12f4d7c3-e047-4455-a607-47a40fe32460','a5600919-4dde-4eb8-a45b-05c204af8284',
'709610ff-5e39-4a47-9c27-a60e740b0944','fdb5b14f-555f-4282-b4c1-9286addf0aae'
)
 AND DATE(patvi.date_started) BETWEEN :startDate AND :endDate
 ORDER BY patvi.date_started DESC;