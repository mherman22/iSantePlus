#!/bin/bash

MYSQL_USER='root'
MYSQL_PASSWORD='Admin123'
MYSQL_PORT='3308'
MYSQL_HOST='127.0.0.1'
PATH_HOST=/Users/ron/Workspace/charess/openmrs/openmrs-module-isanteplusreports/etl

mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -P$MYSQL_PORT -h$MYSQL_HOST isanteplus < $PATH_HOST/isanteplusreportsdmlscript.sql
mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -P$MYSQL_PORT -h$MYSQL_HOST isanteplus < $PATH_HOST/insertion_obs_by_day.sql
mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -P$MYSQL_PORT -h$MYSQL_HOST isanteplus < $PATH_HOST/patient_status_arv_dml.sql
mysql -u$MYSQL_USER -p$MYSQL_PASSWORD -P$MYSQL_PORT -h$MYSQL_HOST isanteplus < $PATH_HOST/indicators_report.sql


