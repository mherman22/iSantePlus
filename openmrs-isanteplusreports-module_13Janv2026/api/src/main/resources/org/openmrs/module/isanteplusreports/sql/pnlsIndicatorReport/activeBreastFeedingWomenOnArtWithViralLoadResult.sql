SELECT pl.patient_id            								            
FROM isanteplus.patient_status_arv ps,isanteplus.patient_laboratory pl, 
isanteplus.patient_on_art pat,

(SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status 
	FROM isanteplus.patient_status_arv psa WHERE 
 psa.date_started_status BETWEEN :startDate AND :endDate GROUP BY 1) B

    WHERE ps.patient_id = pl.patient_id
    AND pl.patient_id = pat.patient_id
    AND ps.patient_id = B.patient_id
    AND DATE(ps.date_started_status) = DATE(B.date_status)
    AND pat.breast_feeding = 1
	AND ps.id_status IN (6, 8)
	AND pl.test_id IN (856, 1305)
    AND ps.date_started_status BETWEEN :startDate AND :endDate 
    AND pat.date_breast_feeding BETWEEN :startDate AND :endDate 
	AND TIMESTAMPDIFF(MONTH, pl.visit_date,:endDate) < 12
	AND pl.voided <> 1;
