/*
	Fréquence des visites prénatales par patient /
	Frequency of prenatal visits per patient
*/
SELECT count(distinct CASE WHEN (a.total=1) THEN a.patient_id ELSE null END) AS '1 visite prénatale', 
count(distinct CASE WHEN (a.total=2) THEN a.patient_id ELSE null END) AS '2 visites prénatales',
count(distinct CASE WHEN (a.total=3) THEN a.patient_id ELSE null END) AS '3 visites prénatales',
count(distinct CASE WHEN (a.total>=4) THEN a.patient_id ELSE null END) AS '4 visites prénatales et plus' FROM (
		SELECT vtype.patient_id,COUNT(DISTINCT vtype.encounter_date) AS total FROM isanteplus.visit_type vtype
                       WHERE vtype.concept_id=160288
                       AND vtype.v_type=1622
                       AND vtype.voided <> 1
					   AND vtype.encounter_date BETWEEN :startDate AND :endDate
                      GROUP BY vtype.patient_id) a;