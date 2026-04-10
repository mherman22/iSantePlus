/*Nombre de visites de femmes enceintes reçues en clinique / Number of visits by pregnant women to the clinic*/
select COUNT(distinct pp.patient_id) AS Total 
FROM isanteplus.patient p, isanteplus.patient_pregnancy pp
WHERE p.patient_id=pp.patient_id
AND p.gender <> 'M'
AND pp.voided <> 1
AND pp.start_date between :startDate AND :endDate;