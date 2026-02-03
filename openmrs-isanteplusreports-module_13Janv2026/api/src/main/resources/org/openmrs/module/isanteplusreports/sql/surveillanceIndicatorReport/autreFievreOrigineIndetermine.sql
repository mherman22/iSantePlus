/*AUTRE FIEVRE A INVESTIGUER (D'ORIGINE INDETERMINEE)*/
SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 70
AND ind.indicator_type_id = 70
AND ind.indicator_date between :startDate AND :endDate;
