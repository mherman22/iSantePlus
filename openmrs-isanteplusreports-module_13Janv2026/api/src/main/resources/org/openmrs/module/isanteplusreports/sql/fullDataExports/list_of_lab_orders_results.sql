SELECT test_name as 'Test', visit_date as 'Date', CASE WHEN test_done = 1 THEN 'COMPLETE' ELSE 'INCOMPLETE' END as 'Status'
FROM isanteplus.patient_laboratory
WHERE order_destination IS NOT NULL
AND IF ('ALL' = :result, 1, test_done = IF ('COMPLETE' = :result, 1 ,0))
AND IF (-1 = :testType, 1, test_id = :testType)
AND visit_date BETWEEN :startDate AND :endDate
ORDER BY IF( 'YES' = :sortByDate, visit_date, NULL) DESC;