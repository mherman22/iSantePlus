/*Ce rapport contient des patients VIH+ seulement et qui ne sont pas discontinues*/
select count(DISTINCT CASE WHEN p.gender='F' THEN p.patient_id
			END) as Femme,
	count(DISTINCT CASE WHEN p.gender='M' THEN p.patient_id
			END) as Homme, 
	count(DISTINCT CASE WHEN p.gender NOT IN ('F','M') THEN p.patient_id
			END) as Autre,
    count(DISTINCT p.patient_id) as total
            FROM isanteplus.patient p
            WHERE p.vih_status=1
            AND p.patient_id NOT IN (SELECT enc.patient_id FROM
            						openmrs.encounter enc, openmrs.encounter_type enct
            						WHERE enc.encounter_type = enct.encounter_type_id
            						AND enct.uuid = '9d0113c6-f23a-4461-8428-7e9a7344f2ba'
            						AND enc.date_created between :startDate AND :endDate)
            AND p.voided <> 1
            AND p.date_created between :startDate AND :endDate;