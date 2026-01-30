/*Ind. 39.2.1*/
/*Liste des patients avec charge virale < 1000 copies/ml (selon la date du résultat)*/
select DISTINCT pat.patient_id
FROM isanteplus.patient pat
         LEFT JOIN isanteplus.arv_status_loockup asl ON pat.arv_status = asl.id, isanteplus.patient_laboratory pl,
     (select plab.patient_id, max(plab.date_test_done) as test_date
      from isanteplus.patient_laboratory plab
      WHERE plab.test_id IN(856, 1305)
        AND plab.test_done = 1
        AND plab.voided <> 1
        AND plab.date_test_done BETWEEN :startDate AND :endDate
      group by 1) B, isanteplus.patient_on_art part
WHERE pat.patient_id = pl.patient_id
  AND B.patient_id = pl.patient_id
  AND pl.patient_id = part.patient_id
  AND pl.date_test_done = B.test_date
  AND pl.test_id IN(856, 1305)
  AND pat.arv_status in (6, 8)
  AND pl.test_done = 1
  AND part.breast_feeding = 1
  AND pl.voided <> 1
  AND ifnull(case when pl.test_id=856 then pl.test_result
                  when pl.test_id=1305 and pl.test_result=1301 then 1001
                  when pl.test_id=1305 and pl.test_result=1306 then 800
                  else pl.test_result
                 end,0) between 0.01 and 1000
  AND pl.visit_date >  DATE_SUB(:endDate, INTERVAL 12 MONTH)
  AND pl.visit_date < :endDate;