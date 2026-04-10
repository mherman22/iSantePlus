SELECT DISTINCT E.patient_id FROM (
			select pe.patient_id,pe.visit_date,pe.regimen from isanteplus.pepfarTable pe,
			(select p.patient_id,DATE(MAX(p.visit_date)) AS m_visit_date from isanteplus.pepfarTable p,
			(SELECT l.patient_id, MAX(DATE(l.date_test_done)) AS result_date FROM 
			isanteplus.patient_laboratory l WHERE l.test_id = 856
			AND l.date_test_done <= :endDate GROUP BY 1) m_lab
			WHERE p.patient_id = m_lab.patient_id
			AND DATE(p.visit_date) <= DATE(m_lab.result_date) GROUP BY 1) p1
			WHERE pe.patient_id = p1.patient_id
			AND DATE(pe.visit_date) = DATE(p1.m_visit_date)
		) A,
		(
			select pe.patient_id,pe.visit_date,pe.regimen from isanteplus.pepfarTable pe,
			(select p.patient_id,DATE(MAX(p.visit_date)) AS m_visit_date from isanteplus.pepfarTable p,
			(SELECT l.patient_id, MAX(DATE(l.date_test_done)) AS result_date FROM 
			isanteplus.patient_laboratory l WHERE l.test_id = 856
			AND l.date_test_done <= :endDate GROUP BY 1) m_lab
			WHERE p.patient_id = m_lab.patient_id 
			AND DATE(p.visit_date) BETWEEN DATE(m_lab.result_date) 
			AND DATE_ADD(DATE(m_lab.result_date), INTERVAL 3 month) group by 1) p1
			WHERE pe.patient_id = p1.patient_id
			AND DATE(pe.visit_date) = DATE(p1.m_visit_date)
		) B,
		(
			SELECT DISTINCT lab.patient_id FROM isanteplus.patient_laboratory lab,
			(SELECT l.patient_id, MAX(DATE(l.date_test_done)) AS result_date FROM 
				isanteplus.patient_laboratory l WHERE l.test_id = 856
				AND l.date_test_done <= :endDate GROUP BY 1) plab,
			(SELECT la.patient_id, MAX(DATE(la.date_test_done)) AS result_date,
			   la.test_result FROM 
				isanteplus.patient_laboratory la WHERE la.test_id = 856
				AND DATE(la.date_test_done) < 
				(SELECT MAX(DATE(lb.date_test_done)) AS date_result FROM 
				isanteplus.patient_laboratory lb WHERE lb.test_id = 856
				AND DATE(lb.date_test_done) <= :endDate 
				AND la.patient_id = lb.patient_id)
				AND DATE(la.date_test_done) <= :endDate GROUP BY 1) D	
			WHERE lab.patient_id = plab.patient_id
			AND plab.patient_id = D.patient_id
			AND DATE(lab.date_test_done) = DATE(plab.result_date)
			AND DATE(D.result_date) < DATE(plab.result_date)
			AND lab.test_id = 856
			AND lab.test_result > 1000
			AND D.test_result > 1000
			AND DATE(lab.date_test_done) <= :endDate
		) E
		WHERE A.patient_id = B.patient_id AND B.patient_id = E.patient_id 
		AND STRCMP(A.regimen,B.regimen)<>0;