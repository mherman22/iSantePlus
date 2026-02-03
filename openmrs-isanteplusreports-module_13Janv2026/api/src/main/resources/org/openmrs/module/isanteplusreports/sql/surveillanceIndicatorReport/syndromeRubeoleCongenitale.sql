/*16 : Syndrome de rubeole congenitale*/
SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 16
AND ind.indicator_type_id = 16
AND ind.indicator_date between :startDate AND :endDate;