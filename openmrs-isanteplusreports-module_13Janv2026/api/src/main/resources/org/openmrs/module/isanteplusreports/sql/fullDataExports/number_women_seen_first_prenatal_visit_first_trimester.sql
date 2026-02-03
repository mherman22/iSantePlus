/*Nombre de femmes enceintes vues en première visite après leur 1er trimestre*/
SELECT distinct p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
p.last_visit_date as 'Dernière date'
FROM isanteplus.patient_pregnancy ppr
INNER JOIN isanteplus.patient p
ON ppr.patient_id = p.patient_id
INNER JOIN isanteplus.patient_menstruation pm
ON pm.patient_id=ppr.patient_id
INNER JOIN isanteplus.visit_type vt
ON vt.patient_id=pm.patient_id
INNER JOIN (SELECT vtype.patient_id, MIN(vtype.encounter_date) as encounter_date FROM isanteplus.visit_type vtype
                          WHERE vtype.concept_id=160288 AND vtype.v_type=1622 AND vtype.voided <> 1 GROUP BY 1) B
ON vt.patient_id = B.patient_id
WHERE vt.encounter_date = B.encounter_date
AND ppr.start_date BETWEEN :startDate AND :endDate
AND pm.encounter_date BETWEEN :startDate AND :endDate
AND TIMESTAMPDIFF(MONTH, B.encounter_date,DATE(pm.ddr))<=3
AND ppr.voided <> 1
AND pm.voided <> 1;