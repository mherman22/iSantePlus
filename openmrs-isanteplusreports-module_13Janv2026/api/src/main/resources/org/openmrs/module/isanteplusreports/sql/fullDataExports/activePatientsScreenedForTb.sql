select DISTINCT  p.patient_id AS 'Patient Id', 
p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',
p.given_name as Prénom,p.family_name as Nom, p.gender as Sexe,
DATE_FORMAT(p.birthdate, "%d-%m-%Y") as 'Date de naissance',
TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Age, 
asl.name_fr as 'Statut du patient', p.last_address as Adresse,
 p.telephone as Téléphone,p.mother_name as Contact
FROM isanteplus.patient p, isanteplus.patient_status_arv ps,isanteplus.arv_status_loockup asl,
(select psar.patient_id, MAX(DATE(psar.date_started_status)) AS date_status
	FROM isanteplus.patient_status_arv psar WHERE
	DATE(psar.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B,
	openmrs.obs o
    WHERE p.patient_id = ps.patient_id
	AND ps.id_status = asl.id
    AND ps.patient_id = B.patient_id
    AND p.patient_id = o.person_id
    AND DATE(ps.date_started_status) = DATE(B.date_status)
	AND ps.id_status IN (6,8)
    AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate
     AND o.concept_id = 1659 AND o.value_coded IN (1660,142177)
    AND DATE(o.obs_datetime) <= :endDate
    AND p.voided <> 1
    AND o.voided <> 1;