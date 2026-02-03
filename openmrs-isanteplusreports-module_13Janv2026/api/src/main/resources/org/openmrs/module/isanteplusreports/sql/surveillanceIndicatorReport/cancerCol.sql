SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 40
AND ind.indicator_type_id = 40
AND ind.indicator_date between :startDate AND :endDate;