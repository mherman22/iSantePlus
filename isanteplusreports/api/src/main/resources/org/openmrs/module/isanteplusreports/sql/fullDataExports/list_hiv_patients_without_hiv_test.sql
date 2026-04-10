select DISTINCT  pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', 
pa.national_id as 'No. d\'identité nationale',pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,DATE_FORMAT(pa.birthdate, "%d-%m-%Y") as 'Date de naissance', TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Téléphone
from isanteplus.patient pa
WHERE pa.vih_status = 1
AND pa.voided <> 1
AND pa.patient_id NOT IN (SELECT distinct pl.patient_id 
			FROM isanteplus.patient_laboratory pl WHERE pl.test_id IN (1042,1040)
            AND pl.test_result = 703 AND pl.voided <> 1);