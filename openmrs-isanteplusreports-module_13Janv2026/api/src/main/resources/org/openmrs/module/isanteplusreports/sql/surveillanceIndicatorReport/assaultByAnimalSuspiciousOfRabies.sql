/*Agression par animal suspecte de rage*/
SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 1
AND ind.indicator_type_id = 1
AND ind.indicator_date between :startDate AND :endDate;