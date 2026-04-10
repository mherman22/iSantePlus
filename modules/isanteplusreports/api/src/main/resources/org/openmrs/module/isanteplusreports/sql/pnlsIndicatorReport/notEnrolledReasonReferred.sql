SELECT pa.patient_id
FROM isanteplus.patient_on_art pa
        WHERE pa.reason_non_enrollment = "REFERRED"
         AND pa.date_non_enrollment <= :endDate ;