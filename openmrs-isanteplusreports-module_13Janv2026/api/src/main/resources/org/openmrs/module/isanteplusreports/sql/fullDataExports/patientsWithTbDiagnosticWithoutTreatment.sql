/*Patients avec des diagnostics de TB, mais sans traitement
 Patients with TB diagnosis, but no treatment */
select distinct p.patient_id AS 'Patient Id', p.st_id as 'No. de patient attribué par le site',p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge,
DATE_FORMAT(DATE(p.last_visit_date), "%d-%m-%Y") as 'Dernière date'
from isanteplus.patient p
INNER JOIN isanteplus.patient_tb_diagnosis pdiag
ON pdiag.patient_id=p.patient_id
WHERE (pdiag.tb_diag = 1 OR pdiag.mdr_tb_diag = 1 OR tb_pulmonaire = 1 OR tb_multiresistante = 1 OR tb_extrapul_ou_diss = 1)
AND pdiag.patient_id NOT IN (SELECT ppr.patient_id FROM isanteplus.patient_prescription ppr
WHERE ((ppr.drug_id IN (75948,82900,767,84360,78280) AND (ppr.rx_or_prophy <> 163768 OR ppr.rx_or_prophy is null) AND DATE(ppr.visit_date) between :startDate AND :endDate)))
AND (pdiag.status_tb_treatment <> 1 OR pdiag.status_tb_treatment IS NULL)
AND pdiag.voided <> 1
AND DATE(pdiag.visit_date) between :startDate AND :endDate
ORDER BY 2 ASC;