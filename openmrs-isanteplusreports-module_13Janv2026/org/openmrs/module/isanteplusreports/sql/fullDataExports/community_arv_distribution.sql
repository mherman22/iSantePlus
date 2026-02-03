/* Rapport de distribution des ARVs en communauté (DAC) */
select  Communautaire,Institution,CONCAT(ROUND(((Communautaire /(Institution+Communautaire)) * 100),2)," %") as pourcentage, (Institution+Communautaire) as 'Patient unique'  from(
select 
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=1755) THEN pdisp.patient_id else null END) AS Communautaire,
COUNT(DISTINCT CASE WHEN (pdisp.dispensation_location=0) THEN pdisp.patient_id else null END) AS Institution
FROM isanteplus.patient_dispensing pdisp,(select pdi.patient_id,max(DATE(pdi.visit_date)) as visit_date from isanteplus.patient_dispensing pdi where pdi.arv_drug = 1065 AND pdi.voided <> 1 AND DATE(pdi.visit_date) BETWEEN :startDate AND :endDate group by 1)B
WHERE pdisp.dispensation_location IN(1755,0)
AND B.patient_id=pdisp.patient_id 
AND DATE(pdisp.visit_date)= B.visit_date
AND pdisp.arv_drug = 1065
AND pdisp.voided <> 1
AND pdisp.dispensation_date BETWEEN :startDate AND :endDate) A;