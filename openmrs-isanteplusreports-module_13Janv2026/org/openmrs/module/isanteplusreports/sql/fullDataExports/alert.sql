select al.libelle as Alert, count(a.id_alert) as Total FROM isanteplus.alert a, isanteplus.alert_lookup al
WHERE a.id_alert=al.id
GROUP BY a.id_alert;