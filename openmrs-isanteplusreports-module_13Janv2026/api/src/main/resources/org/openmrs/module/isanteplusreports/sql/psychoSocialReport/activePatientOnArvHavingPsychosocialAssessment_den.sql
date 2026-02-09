
select A.patient_id from
(select patient_id, max(last_updated_date) as last_status_date
from patient_status_arv where id_status in (6, 8)
                          and DATE(last_updated_date) between '2026-02-01' and '2026-02-08'
                        group by 1) A