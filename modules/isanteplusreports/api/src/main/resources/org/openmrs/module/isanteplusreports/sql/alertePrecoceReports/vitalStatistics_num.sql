select distinct p.person_id as patient_id FROM openmrs.person p
WHERE DATE(p.birthdate) BETWEEN :startDate AND :endDate;