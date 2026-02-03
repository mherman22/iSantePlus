/*Nombre d'enfants nés de mère VIH (+) (exposés) enregistrés dans le programme PTME /
Number of infants born to HIV+ mothers registered in PTME program
*/
select distinct p.patient_id AS 'Patient Id', p.st_id,p.national_id as 'numéro identité national', p.given_name as prénom,
p.family_name as nom,p.gender as sexe, TIMESTAMPDIFF(YEAR,p.birthdate,now()) as Âge, p.last_visit_date as 'Dernière date'
FROM isanteplus.pediatric_hiv_visit pv, isanteplus.patient p
WHERE pv.patient_id = p.patient_id
	  AND pv.actual_vih_status=1405
      AND pv.ptme=1065
      AND pv.voided <> 1
	  AND pv.encounter_date BETWEEN :startDate AND :endDate;