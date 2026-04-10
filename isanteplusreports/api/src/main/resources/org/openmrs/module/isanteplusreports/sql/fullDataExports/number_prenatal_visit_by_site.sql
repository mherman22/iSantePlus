/*Nombre total de visites prénatales par site*/
select DISTINCT pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', pa.national_id as 'No. d\'identité nationale',
pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone 
from isanteplus.patient pa, isanteplus.visit_type vtype
WHERE pa.patient_id = vtype.patient_id AND vtype.concept_id=160288 AND vtype.v_type=1622
AND vtype.voided <> 1
AND vtype.encounter_date BETWEEN :startDate AND :endDate;