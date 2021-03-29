#!/bin/bash

echo -e "\n init start...."

IP=${1}
PORT=${2}

if [[ ! $IP || ! $PORT ]] ; then
    echo "Usage: bash webase.sh 127.0.0.1 3306"
    exit 1
fi

#dbUser
DBUSER="dbUsername"
#dbPass
PASSWD="dbPassword"
#dbName
DBNAME="db_browser"


#connect to database then execute init
cat browser-sql.list | mysql --user=$DBUSER --password=$PASSWD --host=$IP --database=$DBNAME --port=$PORT --default-character-set=utf8;

if [ "$?" == "0" ]; then
    echo -e "init success... \n"
else
    echo -e "init fail... \n"
fi

exit
