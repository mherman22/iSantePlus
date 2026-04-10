/*
 Facteurs de risque de transmission du VIH / Risk Factors for HIV Transmission
 */
select cn.name as Mode, COUNT(distinct vrf.patient_id) as Total
FROM openmrs.concept_name cn, isanteplus.vih_risk_factor vrf
WHERE cn.concept_id=vrf.risk_factor
AND cn.locale='fr'
AND vrf.voided <> 1
AND vrf.encounter_date BETWEEN :startDate AND :endDate
GROUP BY cn.name