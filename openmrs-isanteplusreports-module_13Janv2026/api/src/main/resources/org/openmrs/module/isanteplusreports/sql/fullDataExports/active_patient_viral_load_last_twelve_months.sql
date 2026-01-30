/*Patient Actif avec une charge virale pendant les 12 derniers mois*/
select DISTINCT  pat.patient_id AS 'Patient Id', pat.st_id as 'Code ST', pat.national_id as 'Code National',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
DATE_FORMAT(pat.birthdate, "%d-%m-%Y") as 'Date de naissance',TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, asl.name_fr as 'Statut du patient', 
CASE WHEN (pl.test_id = 1305 AND pl.test_result = 1306) THEN 'Indétectable' 
WHEN (pl.test_id = 1305 AND pl.test_result = 1301) THEN 'Détectable'
ELSE pl.test_result
END
AS Résultat,
C.date_test AS 'Date du test',pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat,
isanteplus.patient_status_arv ps,isanteplus.arv_status_loockup asl,
isanteplus.patient_laboratory pl,(SELECT psar.patient_id,
      MAX(DATE(psar.date_started_status)) as date_status
      FROM isanteplus.patient_status_arv psar WHERE DATE(psar.date_started_status)
      BETWEEN :startDate AND :endDate GROUP BY 1)B,
(SELECT l.patient_id, MAX(IFNULL(DATE(l.date_test_done),DATE(l.visit_date))) 
AS date_test FROM isanteplus.patient_laboratory l WHERE l.test_id IN(856,1305)
AND l.test_result IS NOT NULL
AND TIMESTAMPDIFF(MONTH, IFNULL(DATE(l.date_test_done),DATE(l.visit_date)),:endDate) < 12
AND l.voided <> 1
GROUP BY 1) C
WHERE pat.patient_id = ps.patient_id
AND ps.patient_id = pl.patient_id
AND ps.id_status = asl.id
AND ps.patient_id = B.patient_id
AND pl.patient_id = C.patient_id
AND IFNULL(DATE(pl.date_test_done),DATE(pl.visit_date)) = C.date_test
AND DATE(ps.date_started_status) = DATE(B.date_status)
AND ps.id_status IN (6,8)
AND pl.test_id IN(856,1305)
AND pl.test_result IS NOT NULL
AND TIMESTAMPDIFF(MONTH, IFNULL(DATE(pl.date_test_done),DATE(pl.visit_date)),:endDate) < 12
AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate
AND IFNULL(DATE(pl.date_test_done),DATE(pl.visit_date)) <= :endDate
AND pat.voided <> 1
AND pl.voided <> 1;