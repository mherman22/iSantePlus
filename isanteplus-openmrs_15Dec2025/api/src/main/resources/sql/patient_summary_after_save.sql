set SQL_SAFE_UPDATES = 0;

-- insert data to patient table
insert into patient(patient_id, given_name, family_name, gender, birthdate, creator, date_created, last_inserted_date,last_updated_date, voided)
select pn.person_id, pn.given_name, pn.family_name, pe.gender, pe.birthdate, pn.creator, pn.date_created, now() as last_inserted_date,now() as last_updated_date,pn.voided
from openmrs.person_name pn, openmrs.person pe, openmrs.patient pa
where pe.person_id=pn.person_id and pe.person_id=pa.patient_id
    on duplicate key update
                         given_name=pn.given_name,
                         family_name=pn.family_name,
                         gender=pe.gender,
                         birthdate=pe.birthdate,
                         creator=pn.creator,
                         date_created=pn.date_created,
                         last_updated_date = now(),
                         voided = pn.voided;

-- ST CODE
update patient p,openmrs.patient_identifier pi, openmrs.patient_identifier_type pit
set p.st_id=pi.identifier
where p.patient_id=pi.patient_id
  and pi.identifier_type=pit.patient_identifier_type_id
  and pit.uuid='d059f6d0-9e42-4760-8de1-8316b48bc5f1'
  and pi.voided=0;

-- PC CODE
update patient p,openmrs.patient_identifier pi, openmrs.patient_identifier_type pit
set p.st_id=pi.identifier
where p.patient_id=pi.patient_id
  and pi.identifier_type=pit.patient_identifier_type_id
  and pit.uuid='b7a154fd-0097-4071-ac09-af11ee7e0310'
  and pi.voided=0;

-- iSantePlus_ID
update patient p,openmrs.patient_identifier pi, openmrs.patient_identifier_type pit
set p.identifier=pi.identifier
where p.patient_id=pi.patient_id
  and pi.identifier_type=pit.patient_identifier_type_id
  and pit.uuid='05a29f94-c0ed-11e2-94be-8c13b969e334'
  and pi.voided=0;

-- update patient with address
update patient p, openmrs.person_address padd
set p.last_address=
    case when ((padd.address1 <> '' and padd.address1 is not null) and (padd.address2 <> '' and padd.address2 is not null))
    then CONCAT(padd.address1,' ',padd.address2)
    when ((padd.address1 <> '' and padd.address1 is not null) and (padd.address2 = '' or padd.address2 is null))
    then padd.address1 else padss2
end
where p.patient_id = padd.person_id
	     and padd.voided = 0;

