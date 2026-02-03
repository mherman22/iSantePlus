/* 25 Nombre de patients ARV conseillés en PF*/
SELECT fp.patient_id
FROM isanteplus.patient p, isanteplus.patient_on_arv pa,
      isanteplus.family_planning fp, (SELECT plf.patient_id,
  MAX(DATE(plf.encounter_date)) AS visit_date FROM isanteplus.family_planning plf
  WHERE DATE(plf.encounter_date) BETWEEN :startDate AND :endDate
      GROUP BY 1) B
     WHERE p.patient_id = pa.patient_id  
     AND pa.patient_id = fp.patient_id
     AND fp.patient_id = B.patient_id
     AND DATE(fp.encounter_date) = DATE(B.visit_date)
     AND DATE(fp.encounter_date) BETWEEN :startDate AND :endDate
     AND p.voided <> 1
     AND fp.voided <> 1
     AND fp.accepting_or_using_fp IN (1,2);