select p.st_code as 'NO. de patient attribué par le site', p.national_code as 'Numéro d\'identité national', p.given_name as 'Prénom', p.family_name as 'Nom',
       p.gender as 'Sexe', p.age as 'Age',
       case when p.status = 'interrupted' then 'Interruption'
            when p.status = 'deceased' then 'Décédé'
            when p.status = 'missed' then 'Rendez-vous raté'
            when p.status = 'unknown' then 'Inconnu'
            when p.status = 'regular' then 'Actif'
            when p.status = 'transferred' then 'Transféré'
            when p.status = 'stopped'then 'Stoppé'
            end as 'Status du patient',
            p.status_date as 'Dernière date', p.address as 'Adresse', p.telephone as 'Téléphone', p.mother_name as 'Contact',
       p.reason as 'Raison de discontinuation'
from summary.patient p
where p.status_date >= p.status_date
and p.status_date <= :endDate
and p.status is not null
and p.status = 'regular';
