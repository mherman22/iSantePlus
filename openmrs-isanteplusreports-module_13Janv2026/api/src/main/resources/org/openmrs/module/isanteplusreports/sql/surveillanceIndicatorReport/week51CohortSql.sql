select ind.patient_id from isanteplus.indicators ind 
WHERE WEEK(ind.indicator_date) = 51;