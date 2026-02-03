/*Nombre d'enfants nés de mère VIH (+) (exposés) enregistrés dans le programme PTME /
Number of infants born to HIV+ mothers registered in PTME program
*/
SELECT COUNT(DISTINCT pv.patient_id) AS Total FROM isanteplus.pediatric_hiv_visit pv
WHERE pv.actual_vih_status=1405
      AND pv.ptme=1065
      AND pv.voided <> 1
	  AND pv.encounter_date BETWEEN :startDate AND :endDate;