/* Charge virale en fonction du nombre de copies/ml (selon la date du résultat)  */
select  moins_de_mille AS 'Patient avec résultat de charge virale < 1000 copies/ml', plus_de_mille AS 'Patient avec résultat de charge virale >= 1000 copies/ml', moins_de_mille + plus_de_mille as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN ((pl.test_id = 856 AND pl.test_done = 1 AND (pl.test_result is not null AND pl.test_result > 0) AND pl.test_result < 1000)) THEN pl.patient_id else null END) AS moins_de_mille,
COUNT(DISTINCT CASE WHEN (pl.test_id = 856 AND pl.test_done = 1 AND (pl.test_result is not null AND pl.test_result > 0) AND pl.test_result >= 1000) THEN pl.patient_id else null END) AS plus_de_mille,
count(distinct pl.patient_id) as Patient_unique
FROM isanteplus.patient_laboratory pl,(select plab.patient_id,max(DATE(plab.date_test_done)) as test_date from isanteplus.patient_laboratory plab 
WHERE plab.test_id = 856 group by 1)B
WHERE B.patient_id = pl.patient_id 
AND DATE(pl.date_test_done) = B.test_date
AND pl.test_id = 856
AND pl.test_done = 1
AND pl.voided <> 1
AND (pl.test_result > 0 AND pl.test_result is not null)
AND DATE(pl.date_test_done) BETWEEN :startDate AND :endDate) A;

