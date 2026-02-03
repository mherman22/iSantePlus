/*Patients avec traitement contre la TB complété
  Patients having completed TB treatment
  */
select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
stat.name_fr as 'Status de patient', p.last_visit_date as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_tb_diagnosis pdiag
ON pdiag.patient_id=p.patient_id
LEFT OUTER JOIN isanteplus.arv_status_loockup stat
ON stat.id=p.arv_status
WHERE pdiag.visit_date between :startDate AND :endDate
AND pdiag.status_tb_treatment=2
AND pdiag.voided <> 1;