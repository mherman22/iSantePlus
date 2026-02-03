/*6 : Evenement supose etre attribuable a la vaccination et a l’immunisation (esavi mageure)*/
SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 6
AND ind.indicator_type_id = 6
AND ind.indicator_date between :startDate AND :endDate;