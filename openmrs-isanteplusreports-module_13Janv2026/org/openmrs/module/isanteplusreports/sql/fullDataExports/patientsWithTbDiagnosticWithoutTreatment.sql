/*Patients avec des diagnostics de TB, mais sans traitement
 Patients with TB diagnosis, but no treatment */
select distinct p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
DATE_FORMAT(DATE(p.last_visit_date), "%d-%m-%Y") as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_tb_diagnosis pdiag
ON pdiag.patient_id=p.patient_id
WHERE (pdiag.tb_diag = 1 OR pdiag.mdr_tb_diag = 1 OR tb_pulmonaire = 1 OR tb_multiresistante = 1 OR tb_extrapul_ou_diss = 1)
AND pdiag.patient_id NOT IN (SELECT patdisp.patient_id FROM isanteplus.patient_dispensing patdisp
WHERE ((patdisp.drug_id IN (75948,82900,767,84360,78280) AND (patdisp.rx_or_prophy <> 163768))) 
AND patdisp.dispensation_date between :startDate AND :endDate)
AND pdiag.status_tb_treatment <> 1
AND pdiag.voided <> 1
AND pdiag.visit_date between :startDate AND :endDate
ORDER BY 1 ASC;