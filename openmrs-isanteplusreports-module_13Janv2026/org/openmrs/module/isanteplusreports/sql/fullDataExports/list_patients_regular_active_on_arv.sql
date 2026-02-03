select DISTINCT pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro d\'identité national',
pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,
TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, arv.name_fr as 'Status de patient',
patstatus.start_date as 'Dernière date', pat.last_address as Adresse, pat.telephone as Téléphone,
DATE_FORMAT(pat.next_visit_date, "%d-%m-%Y") as 'Date de prochaine visite', pat.mother_name as Contact,
      CASE WHEN(patstatus.dis_reason=5240) THEN 'Perdu de vue'
		    WHEN (patstatus.dis_reason=159492) THEN 'Transfert'
			WHEN (patstatus.dis_reason=159) THEN 'Décès'
			WHEN (patstatus.dis_reason=1667) THEN 'Discontinuations'
			WHEN (patstatus.dis_reason=1067) THEN 'Inconnue'
		END as 'Raison de discontinuation'
FROM isanteplus.patient pat
INNER JOIN isanteplus.patient_status_arv patstatus
ON pat.patient_id=patstatus.patient_id
INNER JOIN isanteplus.arv_status_loockup arv
ON patstatus.id_status=arv.id
INNER JOIN (SELECT ps.patient_id, MAX(ps.start_date) as start_date 
FROM isanteplus.patient_status_arv ps WHERE ps.start_date <= :endDate GROUP BY 1) B
ON patstatus.patient_id = B.patient_id
WHERE patstatus.id_status = 6
AND patstatus.start_date = B.start_date
AND patstatus.start_date <= :endDate 
GROUP BY pat.patient_id;