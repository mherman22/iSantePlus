/*Liste des patients avec charge virale >= 1000 copies/ml (selon la date du résultat)*/
select DISTINCT  pat.patient_id AS 'Patient Id', pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, asl.name_fr as 'Statut du patient', pat.last_address as Adresse, pat.telephone as Téléphone,
pat.mother_name as Contact FROM isanteplus.patient pat LEFT JOIN isanteplus.arv_status_loockup asl
ON pat.arv_status = asl.id, isanteplus.patient_laboratory pl, (select plab.patient_id,max(plab.date_test_done) as test_date from isanteplus.patient_laboratory plab 
WHERE plab.test_id in (856,1305) AND plab.test_done = 1 AND plab.voided <> 1 
AND plab.date_test_done BETWEEN :startDate AND :endDate group by 1)B
	WHERE pat.patient_id = pl.patient_id
    AND B.patient_id = pl.patient_id 
	AND pl.date_test_done = B.test_date
    AND pl.test_id in (856,1305)
	AND pat.arv_status in (6,8)
	AND pl.test_done = 1
        AND pl.voided <> 1
	AND ifnull(case when pl.test_id=856 then pl.test_result
     when pl.test_id=1305 and pl.test_result=1301 then 1001
	 when pl.test_id=1305 and pl.test_result=1306 then 800
	 else pl.test_result
end,0)>=1000
    AND pl.date_test_done BETWEEN :startDate AND :endDate;