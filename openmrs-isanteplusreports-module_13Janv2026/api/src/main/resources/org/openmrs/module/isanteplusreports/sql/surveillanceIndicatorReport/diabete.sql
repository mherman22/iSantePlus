-- Diabete
SELECT
    indicator_id        AS indicatorId,
    indicator_type_id   AS indicatorTypeId,
    patient_id          AS patientId,
    location_id         AS locationId,
    encounter_id        AS encounterId,
    indicator_date      AS indicatorDate,
    voided              AS voided,
    created_date        AS createdDate,
    last_updated_date   AS lastUpdatedDate
FROM isanteplus.indicators
WHERE indicator_id = 21
  AND indicator_type_id = 21
  AND indicator_date between :startDate and :endDate