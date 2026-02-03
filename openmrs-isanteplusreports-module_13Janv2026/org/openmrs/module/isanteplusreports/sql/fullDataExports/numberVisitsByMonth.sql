select distinct loc.name as Site,MONTH(en.encounter_datetime) as Visit_MM,
YEAR(en.encounter_datetime) as AAAA,count(distinct en.patient_id) as Count
FROM openmrs.encounter en, openmrs.location loc
WHERE en.location_id=loc.location_id
AND en.voided <> 1
GROUP BY loc.name, MONTH(en.encounter_datetime), YEAR(en.encounter_datetime)
ORDER BY 3 DESC;
