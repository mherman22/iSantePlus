SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 12
AND ind.indicator_type_id = 12
AND ind.indicator_date between :startDate AND :endDate;