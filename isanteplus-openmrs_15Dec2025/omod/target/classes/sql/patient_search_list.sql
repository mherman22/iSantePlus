
SELECT p.patient_id                                AS 'patientId',
       CONCAT(p.given_name, ' ', p.family_name)    AS 'fullName',
       p.gender                                    AS 'gender',
       TIMESTAMPDIFF(YEAR, p.birthdate, CURDATE()) AS 'age',
       p.birthdate                                 AS 'birthdate',
       p.st_id                                     AS 'stId',
       p.isante_id                                 AS 'isanteId',
       p.pc_id                                     AS 'pcId',
       p.national_id                               AS 'nationalId',
       p.identifier                                AS 'identifier',
       p.last_address                              AS 'lastAddress'
from isanteplus.patient p
         where (CONCAT(p.given_name, ' ', p.family_name) LIKE :criteria
                    OR
                CONCAT(p.family_name, ' ', p.given_name) LIKE :criteria
                    OR p.identifier LIKE :criteria
             OR p.identifier LIKE :criteria
             OR p.st_id LIKE :criteria
             OR p.isante_id LIKE :criteria
             OR p.national_id LIKE :criteria
             OR p.pc_id LIKE :criteria)
         and p.voided = 0
GROUP BY p.patient_id limit 25;