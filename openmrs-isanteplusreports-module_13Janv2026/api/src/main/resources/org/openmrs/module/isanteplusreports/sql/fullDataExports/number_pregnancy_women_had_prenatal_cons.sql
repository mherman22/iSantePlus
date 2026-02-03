/*Nombre de femmes enceintes vues en première consultation prénatale*/
select DISTINCT  pa.patient_id AS 'Patient Id', pa.st_id as 'NO. de patient attribué par le site', pa.national_id as 'No. d\'identité nationale',
pa.given_name as Prénom,
pa.family_name as Nom, pa.gender as Sexe,TIMESTAMPDIFF(YEAR, pa.birthdate,DATE(now())) as Âge,
pa.telephone as Telephone FROM isanteplus.patient pa, isanteplus.visit_type vtype, isanteplus.patient_pregnancy pp,
(select vt.patient_id, MIN(vt.encounter_date) as encounter_date 
FROM isanteplus.visit_type vt WHERE vt.concept_id=160288 AND vt.v_type=1622 GROUP BY 1) B
WHERE pa.patient_id = vtype.patient_id
AND vtype.patient_id=pp.patient_id
AND vtype.concept_id=160288 AND vtype.v_type=1622
AND vtype.patient_id = B.patient_id
AND vtype.encounter_date = B.encounter_date
AND vtype.voided <> 1
AND pp.voided <> 1
AND vtype.encounter_date BETWEEN :startDate AND :endDate
AND pp.start_date BETWEEN :startDate AND :endDate;