 /*Resume iSante PsychoSocial forms*/
	USE isanteplus;

-- Ajouter la colonne encounter_type dans la table patient_diagnosis
SET @col_exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = 'isanteplus'
      AND TABLE_NAME = 'patient_diagnosis'
      AND COLUMN_NAME = 'encounter_type'
);

SET @sql := IF(
    @col_exists = 0,
    'ALTER TABLE isanteplus.patient_diagnosis ADD COLUMN encounter_type INT(11);',
    'SELECT ''Column encounter_type already exists'';'
);

 PREPARE stmt FROM @sql;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;
-- ---------------------------FIN----------------------------

update isanteplus.patient_diagnosis ipd, openmrs.encounter oe
   set ipd.encounter_type = oe.encounter_type
 where ipd.patient_id = oe.patient_id
   and ipd.encounter_id = oe.encounter_id;


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


/*insertion of all symptome in the table patient_synptome*/
INSERT into patient_symptome(patient_id,encounter_id,location_id,concept_group,obs_group_id,concept_id,
answer_concept_id, encounter_date, date_created, voided, date_voided)

select distinct og_sympt.person_id, og_sympt.encounter_id,
og_sympt.location_id, og_sympt.concept_id, oval.obs_group_id, 
og_sympt.concept_id, osympt.value_coded, oval.value_datetime,
e.encounter_datetime, e.date_created, CASE
    WHEN og_sympt.voided = 0
     AND osympt.voided = 0
     AND oval.voided = 0
     AND e.voided = 0
    THEN 0
    ELSE 1
END AS voided, CASE
    WHEN og_sympt.voided = 1 THEN og_sympt.date_voided
    WHEN osympt.voided   = 1 THEN osympt.date_voided
    WHEN oval.voided     = 1 THEN oval.date_voided
    WHEN e.voided        = 1 THEN e.date_voided
	ELSE null
END AS date_voided
from 
(select * from openmrs.obs where concept_id in (509166547, 509166574)) og_sympt, 
(select * from openmrs.obs where concept_id = 1728) osympt, 
(select * from openmrs.obs where concept_id = 1730) oval, 
openmrs.encounter e, openmrs.encounter_type et
where og_sympt.concept_id in (509166547, 509166574)
and osympt.concept_id = 1728
and oval.concept_id = 1730
and og_sympt.obs_id = osympt.obs_group_id
and og_sympt.obs_id = oval.obs_group_id
and og_sympt.person_id = osympt.person_id
and osympt.person_id = oval.person_id
and og_sympt.person_id = e.patient_id
-- and og_sympt.voided = 0 and osympt.voided = 0 and oval.voided = 0 and e.voided = 0
and e.encounter_type = et.encounter_type_id
and e.encounter_id = og_sympt.encounter_id
and et.encounter_type_id 
	in (select encounter_type_id from openmrs.encounter_type 
	where uuid in ('a0d57dca-3028-4153-88b7-c67a30fde595', '51df75f7-a3de-4f82-a9df-c0bedaf5a2dd'))
on duplicate key update
	value_datetime = oval.value_datetime;	
-- ----------------------------FIN------------------------------------------------------------------

