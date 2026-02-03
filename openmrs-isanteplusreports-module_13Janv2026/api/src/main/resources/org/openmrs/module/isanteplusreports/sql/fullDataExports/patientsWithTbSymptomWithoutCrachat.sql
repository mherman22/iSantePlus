/*
Patients avec signes et symptômes suggérant la TB, mais sans analyse des crachats ou radiographie pulmonaires
Patients with signs and symptoms evocative of TB, but with no sputum or x-ray test
*/
select distinct  p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
stat.name_fr as 'Status de patient', p.last_visit_date as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_tb_diagnosis pdiag
ON pdiag.patient_id=p.patient_id
LEFT OUTER JOIN isanteplus.arv_status_loockup stat
ON stat.id=p.arv_status
WHERE pdiag.visit_date between :startDate AND :endDate
AND pdiag.cough_for_2wks_or_more=1
AND pdiag.voided <> 1
AND pdiag.patient_id NOT IN (SELECT patim.patient_id FROM isanteplus.patient_imagerie patim
WHERE patim.visit_date between :startDate AND :endDate
AND (patim.crachat_barr IN(664,703) OR patim.radiographie_pul IN(1115,1137,5158,6049,6050,6052,114108,5622)));