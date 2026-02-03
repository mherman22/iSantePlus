select count(distinct B.patient_id) AS Total
FROM (SELECT distinct pl.patient_id FROM isanteplus.patient_laboratory pl
	WHERE pl.test_id = 844
	AND pl.test_done = 1
	AND pl.test_result IN(1301,1302,1300,1304)
	AND pl.voided <> 1
    AND pl.date_test_done BETWEEN :startDate AND :endDate
   UNION
   SELECT distinct vt.patient_id FROM isanteplus.virological_tests vt
	WHERE vt.test_id = 162087
	AND vt.answer_concept_id = 1030
	AND vt.test_result IN (664,703,1138)
	AND vt.voided <> 1
    AND vt.test_date BETWEEN :startDate AND :endDate
    ) B;