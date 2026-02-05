 /*Resume iSante PsychoSocial forms*/
	USE isanteplus;  

CREATE TABLE IF NOT EXISTS comprehension(
comprehension_id INT(11) AUTO_INCREMENT,
patient_id INT(11),
isante_id VARCHAR(11),
visitDate VARCHAR(10),
visit_date DATE,
compRemarks LONGTEXT,
obstaclesRemarks LONGTEXT,
barriersToApptsText VARCHAR(255),
barriersToHomeVisitsText VARCHAR(255),
CONSTRAINT pk_comprehension_isanteplus PRIMARY KEY (comprehension_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
	  
INSERT INTO comprehension(patient_id,isante_id,visitDate,compRemarks,
obstaclesRemarks,barriersToApptsText,barriersToHomeVisitsText)
SELECT p.patient_id, c.patientID, 
concat(c.visitDateDd,'-',c.visitDateMm,'-',c.visitDateYy),c.compRemarks,
c.obstaclesRemarks,c.barriersToApptsText, c.barriersToHomeVisitsText
FROM isanteplus.patient p, itech.comprehension c
WHERE p.isante_id = c.patientID
AND ((c.compRemarks IS NOT NULL OR c.compRemarks <> '')
OR (c.obstaclesRemarks IS NOT NULL OR c.obstaclesRemarks <> '')
OR (c.barriersToApptsText IS NOT NULL OR c.barriersToApptsText <> '')
OR (c.barriersToHomeVisitsText IS NOT NULL OR c.barriersToHomeVisitsText <> '')
);

/*insertion of all disymptomeagnosis in the table patient_synptome*/
	INSERT into patient_symptome(patient_id,encounter_id,location_id,concept_group,obs_group_id,concept_id,answer_concept_id, encounter_date, voided)
					
	select distinct ob_sympt.person_id,ob_sympt.encounter_id,
	ob_sympt.location_id,ob1.concept_id,ob_sympt.obs_group_id,ob_sympt.concept_id, ob_sympt.value_coded, e.encounter_datetime, ob_sympt.voided
	from openmrs.obs ob_sympt, openmrs.obs ob1, openmrs.encounter e, openmrs.encounter_type et
	where ob.person_id = ob1.person_id
	AND ob.encounter_id = ob1.encounter_id
	AND ob.obs_group_id = ob1.obs_id
	AND ob.encounter_id = e.encounter_id
	AND e.encounter_type = et.encounter_type_id	
	AND ob.concept_id = 1284
	AND (ob.value_coded <> '' OR ob.value_coded is not null)
	on duplicate key update
	encounter_id = ob.encounter_id,
	voided = ob.voided;
