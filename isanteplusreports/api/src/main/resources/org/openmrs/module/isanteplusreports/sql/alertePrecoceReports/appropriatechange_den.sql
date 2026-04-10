SELECT DISTINCT lab.patient_id FROM isanteplus.patient_laboratory lab,
	(SELECT l.patient_id, MAX(DATE(l.date_test_done)) AS result_date FROM 
		isanteplus.patient_laboratory l WHERE l.test_id = 856
		AND l.date_test_done <= :endDate GROUP BY 1) B,
	(SELECT la.patient_id, MAX(DATE(la.date_test_done)) AS result_date,
	   la.test_result FROM 
		isanteplus.patient_laboratory la WHERE la.test_id = 856
		AND DATE(la.date_test_done) < 
        (SELECT MAX(DATE(lb.date_test_done)) AS date_result FROM 
		isanteplus.patient_laboratory lb WHERE lb.test_id = 856
		AND DATE(lb.date_test_done) <= :endDate AND la.patient_id = lb.patient_id)
		AND DATE(la.date_test_done) <= :endDate GROUP BY 1) C	
	WHERE lab.patient_id = B.patient_id
	AND B.patient_id = C.patient_id
	AND DATE(lab.date_test_done) = DATE(B.result_date)
	AND DATE(C.result_date) < DATE(B.result_date)
	AND lab.test_id = 856
	AND lab.test_result > 1000
	AND C.test_result > 1000
	AND DATE(lab.date_test_done) <= :endDate;