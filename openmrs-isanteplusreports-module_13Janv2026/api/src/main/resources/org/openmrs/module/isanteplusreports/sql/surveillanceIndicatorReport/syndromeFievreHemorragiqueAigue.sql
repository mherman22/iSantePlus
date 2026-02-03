/*15 : Syndrome de fievre hemorragique aigue*/
SELECT ind.patient_id FROM isanteplus.indicators ind
WHERE ind.indicator_id = 15
AND ind.indicator_type_id = 15
AND ind.indicator_date between :startDate AND :endDate;