/*Patients avec des analyses de crachats ou radiographe pulmonaires anormales, mais sans aucun diagnostic de TB
 Patients with abnormal sputum or x-ray test results, but no established TB diagnosis*/
select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
stat.name_fr as 'Status de patient', p.last_visit_date as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_imagerie pi
ON pi.patient_id=p.patient_id
LEFT OUTER JOIN isanteplus.arv_status_loockup stat
ON stat.id=p.arv_status
WHERE pi.patient_id NOT IN (SELECT pdiag.patient_id FROM isanteplus.patient_tb_diagnosis pdiag
WHERE pdiag.visit_date between :startDate AND :endDate)
AND pi.visit_date between :startDate AND :endDate
AND (pi.crachat_barr=703 OR pi.radiographie_pul NOT IN (1115,5158))
AND p.voided <> 1;