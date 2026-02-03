/* Nombre d'enfants n&eacute;s de m&egrave;re VIH (+) plac&eacute;s sous ARV comme prophylaxie dans les 72 heures apr&egrave;s la naissance 
 * / Number of infants born to HIV+ mothers who received ARV prophylaxis within 72 hours of birth*/
select DISTINCT  p.patient_id AS 'Patient Id', p.st_id as 'NO. de patient attribué par le site', p.national_id as 'Numéro d\'identité national',
p.given_name as Prénom,p.family_name as Nom, p.gender as Sexe,
TIMESTAMPDIFF(YEAR, p.birthdate,DATE(now())) as Age
FROM isanteplus.patient p, isanteplus.pediatric_hiv_visit pv
WHERE p.patient_id = pv.patient_id
AND pv.prophylaxie72h=1065
AND pv.voided <> 1
AND pv.encounter_date BETWEEN :startDate AND :endDate;