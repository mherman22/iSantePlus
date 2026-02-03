/*La liste des patients dont la date de renflouement des ARV est arrivée à terme*/

select distinct p.patient_id AS 'Patient Id', DATE(pdisp.visit_date) as 'Date Visite',p.st_id as 'NO. de patient attribué par le site',
p.national_id as 'Numéro d\'identité national',p.given_name as Nom,
p.family_name as Prénom, p.birthdate as 'Date de naissance',pdisp.next_dispensation_date as 'Date de dispensation'
FROM isanteplus.patient p, isanteplus.patient_dispensing pdisp,
(SELECT pad.patient_id, MAX(pad.next_dispensation_date) as next_dispensation_date FROM isanteplus.patient_dispensing pad GROUP BY 1) B
WHERE p.patient_id = pdisp.patient_id
AND pdisp.patient_id = B.patient_id
AND DATE(pdisp.next_dispensation_date) = DATE(B.next_dispensation_date)
AND (DATEDIFF(DATE(now()),DATE(pdisp.next_dispensation_date)) BETWEEN 0 AND 90)
/*AND p.patient_id NOT IN(SELECT dreason.patient_id FROM isanteplus.discontinuation_reason dreason WHERE dreason.reason IN(159,1667,159492))*/
AND pdisp.arv_drug = 1065
AND pdisp.rx_or_prophy <> 163768
AND p.arv_status IN (6,8,9)
AND pdisp.voided <> 1
GROUP BY 1,2,3,4,5,6,7;