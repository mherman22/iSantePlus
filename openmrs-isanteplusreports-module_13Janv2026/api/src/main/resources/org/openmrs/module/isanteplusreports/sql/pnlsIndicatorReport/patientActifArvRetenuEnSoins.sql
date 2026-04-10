/*Ind. 30*/

SELECT DISTINCT ps.patient_id
FROM isanteplus.patient_status_arv ps, isanteplus.patient_visit pv,
     (SELECT psa.patient_id, MAX(DATE(psa.date_started_status)) as date_status
      FROM isanteplus.patient_status_arv psa WHERE
          DATE(psa.date_started_status) BETWEEN :startDate AND :endDate GROUP BY 1) B,

    (SELECT pv.patient_id, MIN(pv.visit_date) AS min_visit_date
      FROM isanteplus.patient_visit pv
          WHERE DATE_SUB(:startDate, INTERVAL 12 MONTH) < pv.visit_date
            and pv.encounter_type in (select et.encounter_type_id from openmrs.encounter_type et
                                    where et.uuid in ('204ad066-c5c2-4229-9a62-644bc5617ca2','33491314-c352-42d0-bd5d-a9d0bffc9bf1'))
          group by 1) C,
    (SELECT pv.patient_id, MAX(pv.visit_date) AS max_visit_date
    FROM isanteplus.patient_visit pv
    WHERE DATE_SUB(:startDate, INTERVAL 12 MONTH) < pv.visit_date
      and pv.encounter_type in (select et.encounter_type_id from openmrs.encounter_type et
    where uuid in ('204ad066-c5c2-4229-9a62-644bc5617ca2','33491314-c352-42d0-bd5d-a9d0bffc9bf1')) group by 1) D,
    (SELECT pv.patient_id, count(*) AS qte
    FROM isanteplus.patient_visit pv
    where pv.encounter_type in (select et.encounter_type_id from openmrs.encounter_type et
                              where et.uuid in ('204ad066-c5c2-4229-9a62-644bc5617ca2','33491314-c352-42d0-bd5d-a9d0bffc9bf1'))
    group by 1) E
WHERE ps.patient_id = B.patient_id
    AND ps.patient_id = pv.patient_id
    AND ps.patient_id = B.patient_id
    and C.patient_id = D.patient_id
    and E.patient_id = pv.patient_id
    and E.qte >= 2
    and pv.encounter_type in (select et.encounter_type_id from openmrs.encounter_type et
                            where et.uuid in ('204ad066-c5c2-4229-9a62-644bc5617ca2','33491314-c352-42d0-bd5d-a9d0bffc9bf1'))
    AND DATEDIFF(D.max_visit_date, C.min_visit_date) >= 90
    AND DATE(ps.date_started_status) = B.date_status
    AND ps.id_status IN (6,8)
    AND DATE(ps.date_started_status) BETWEEN :startDate AND :endDate;


