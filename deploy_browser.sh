#!/bin/bash

######################  参数配置  ######################

userName="test"  #需要创建的数据库用户
password="123456" #需要创建的数据库用户密码
passwordRoot="123456" #数据库root账号的密码
dbName="bcos_browser"     #需要创建的数据库名
dbIp="192.168.1.100"   #数据库所在机器IP
tomcatpath="/software/tomcat/" #tomcat所在路径

###################### 参数配置结束 #################### 




#################### 计算全局路径####################
dirpath="$(cd "$(dirname "$0")" && pwd)"

#server gradle build 路径
serverpath="${dirpath}/server/fisco-bcos-browser"

#page gradle build 路径
pagepath="${dirpath}/page/fisco-bcos-browser" 

#生成war包cp进Tomcat路径
deployserverpath="${tomcatpath}/webapps"	

#启动Tomcat bin目录下的项目启动脚本路径
startbroswer="${tomcatpath}/bin"



###################### 配置数据库 ######################
echo "开始配置数据库..." 
 
# execute sql stat
#新建用户和数据库
mysql -uroot -p${passwordRoot} --silent -h ${dbIp} -e "

drop database if exists ${dbName};

create database ${dbName} default character set utf8 collate utf8_general_ci;

set global event_scheduler = 1;

create user '${userName}'@'localhost' identified by '${password}';

create user '${userName}'@'%' identified by '${password}';

quit"

#导入数据库的表
mysql -uroot -p${passwordRoot} -h ${dbIp} -e "
use ${dbName};

source ${serverpath}/script/db/bcos_browser_table.sql;
source ${serverpath}/script/db/bcos_browser_table_v2.sql;

grant all on ${dbName}.* to '${userName}'@'localhost';

grant all on ${dbName}.* to '${userName}'@'%';

grant all on mysql.proc to '${userName}'@'localhost';

grant all on mysql.proc to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_single_stat_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_single_stat_by_day to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_transaction_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_transaction_by_day to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_block_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_block_by_day to '${userName}'@'%';

quit"

###################### 配置server ######################

# execute sql end  
set -e

echo "开始配置server...."

echo "
db.ip=${dbIp}
db.user=${userName}
db.password=${password}
db.database=${dbName}
db.port=3306
db.initialSize=10
db.minIdle=0
db.maxActive=100
db.maxWait=10000
db.timeBetweenEvictionRunsMillis=60000
db.minEvictableIdleTimeMillis=300000

startTrigger.schedule.time=0/3 * * * * ?
blockInfoTrigger.schedule.time=0/5 * * * * ?
pendingTransTrigger.schedule.time=0/1 * * * * ?

" > ${serverpath}/src/main/resources/application.properties

#server gradle build and cp
echo "gradle build start"

echo "${serverpath}"

cd ${serverpath}

gradle build

#sudo rm ${deployserverpath}/bcos-server* -rf
#sudo cp "${serverpath}/bcos-server/apps/bcos-server.war" "${deployserverpath}"

###################### 配置page ######################

#page gradle build and cp 
cd ${pagepath}

echo "开始配置page...."

echo "
jdbc_driverClassName = com.mysql.jdbc.Driver
jdbc_initialSize = 5
jdbc_maxActive = 20
jdbc_minIdle = 5
jdbc_maxIdle = 5
jdbc_maxWait = 60000
jdbc_url = jdbc:mysql://${dbIp}/${dbName}
jdbc_username = ${userName}
jdbc_password = ${password}
" > ${pagepath}/src/main/resources/jdbc.properties

echo "${pagepath}"

gradle build

#sudo rm ${deployserverpath}/fisco-bcos-browser* -rf
#sudo cp "${pagepath}/dist/apps/fisco-bcos-browser.war" "${deployserverpath}"

echo "gradle build end"

###################### 启动tomcat ######################

echo "开始部署浏览器应用..."

cd ${startbroswer}

#set +e && sudo sh shutdown.sh && set -e

sudo rm ${deployserverpath}/fisco-bcos-server* -rf
sudo cp "${serverpath}/fisco-bcos-server/apps/fisco-bcos-server.war" "${deployserverpath}"

sudo rm ${deployserverpath}/fisco-bcos-browser* -rf
sudo cp "${pagepath}/dist/apps/fisco-bcos-browser.war" "${deployserverpath}"

sudo sh startup.sh

echo "部署成功！请继续在每台机器上部署report脚本。"

exit;
































