select person_id, address2, address1, city_village, state_province, country, preferred
from person_address where person_id =:patientId and preferred <> 1