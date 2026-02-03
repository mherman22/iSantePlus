select ind.patient_id from isanteplus.indicators ind 
WHERE MONTH(ind.indicator_date) = 10;