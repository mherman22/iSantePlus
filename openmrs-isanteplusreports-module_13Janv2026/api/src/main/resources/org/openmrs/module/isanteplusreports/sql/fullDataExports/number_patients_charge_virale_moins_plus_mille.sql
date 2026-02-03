/* Charge virale en fonction du nombre de copies/ml (selon la date du résultat)  */
select  moins_de_mille AS 'Patient avec résultat de charge virale < 1000 copies/ml', plus_de_mille AS 'Patient avec résultat de charge virale >= 1000 copies/ml', moins_de_mille + plus_de_mille as 'Patient unique'  
from(
select 
COUNT(DISTINCT CASE WHEN (case when pl.test_id=856 then pl.test_result
     when pl.test_id=1305 and pl.test_result=1301 then 1001
	 when pl.test_id=1305 and pl.test_result=1306 then 800
	 else pl.test_result
end) < 1000 THEN pl.patient_id else null END) AS moins_de_mille,
COUNT(DISTINCT CASE WHEN (case when pl.test_id=856 then pl.test_result
     when pl.test_id=1305 and pl.test_result=1301 then 1001
	 when pl.test_id=1305 and pl.test_result=1306 then 800
	 else pl.test_result
end) >= 1000 THEN pl.patient_id else null END) AS plus_de_mille,
count(distinct pl.patient_id) as Patient_unique
FROM isanteplus.patient_laboratory pl,(select plab.patient_id,max(DATE(plab.date_test_done)) as test_date from isanteplus.patient_laboratory plab 
WHERE plab.test_id in(856,1305) AND plab.test_done = 1 AND plab.voided <> 1
AND DATE(plab.date_test_done) BETWEEN :startDate AND :endDate group by 1)B
WHERE B.patient_id = pl.patient_id 
AND DATE(pl.date_test_done) = B.test_date 
AND pl.test_id in(856,1305)
AND pl.test_done = 1
AND pl.voided <> 1
AND ifnull(case when pl.test_id=856 then pl.test_result
     when pl.test_id=1305 and pl.test_result=1301 then 1001
	 when pl.test_id=1305 and pl.test_result=1306 then 800
	 else pl.test_result
end,0) > 0
AND DATE(pl.date_test_done) BETWEEN :startDate AND :endDate) A;

