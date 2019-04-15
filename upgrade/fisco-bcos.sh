#!/bin/sh

#ip
IP="127.0.0.1"
#port
PORT="3306"
#user
DBUSER="root"
#password
PASSWD="123456"
#name
DBNAME="testdb"

#connect to database then execute
cat fisco-bcos.list | mysql --user=$DBUSER --password=$PASSWD --host=$IP --database=$DBNAME --port=$PORT --default-character-set=utf8;

exit