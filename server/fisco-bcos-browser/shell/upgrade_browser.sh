#!/bin/bash

######################  参数配置  ######################

userName=""  #浏览器一期数据库用户
password=""   #浏览器一期数据库用户密码
passwordRoot="" #数据库root账号的密码
dbName=""     #浏览器一期所使用的数据库名
dbIp=""   #数据库所在机器IP

###################### 参数配置结束 #################### 


#################### 计算全局路径####################
dirpath="$(cd "$(dirname "$0")" && pwd)"

#################### sql路径####################
sqlpath="${dirpath}/.."



###################### 升级数据库 ######################
echo "开始升级数据库..." 

echo "更新一期表...清除tb_nodesInfo表中的数据"

#更新一期表（清除tb_nodesInfo表中的数据）
mysql -uroot -p${passwordRoot} --silent -h ${dbIp} -e "
use ${dbName};

truncate table tb_nodesInfo;

quit"
 

echo "升级一期表...一期表字段信息升级"  

#升级浏览器一期表（更改字段信息）
mysql -uroot -p${passwordRoot} --silent -h ${dbIp} -e "
use ${dbName};
set global event_scheduler = 1;

alter table tb_block modify column miner varchar(256);

alter table tb_transaction add contractName longtext COMMENT '合约名';
alter table tb_transaction add version longtext COMMENT '合约版本';
alter table tb_transaction add method longtext COMMENT '被调用的合约中的函数名';
alter table tb_transaction add params longtext COMMENT '被调用的函数的参数';

alter table tb_nodesInfo modify column pk_id varchar(128) NOT NULL;
alter table tb_nodesInfo modify column addr varchar(32) NOT NULL;
alter table tb_nodesInfo modify column blockNumber int(11) NOT NULL;
alter table tb_nodesInfo add active varchar(10) not null COMMENT '节点存活标识';

quit"


echo "升级浏览器一期表...创建节点信息表" 

#升级浏览器一期表（创建节点信息表）
mysql -uroot -p${passwordRoot} --silent -h ${dbIp} -e "
use ${dbName};

CREATE TABLE tb_nodeConnection (                             
  pk_id int(11) NOT NULL AUTO_INCREMENT,
  ip varchar(16) NOT NULL COMMENT '节点ip',
  rpcPort  int(11) NOT NULL COMMENT '节点rpc端口',
  PRIMARY KEY (pk_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点rpc链接表';

quit"


echo "导入浏览器二期表...赋予用户存储权限" 

#导入浏览器二期表（赋予用户存储权限）
mysql -uroot -p${passwordRoot} -h ${dbIp} -e "

use ${dbName};

source ${sqlpath}/script/db/bcos_browser_table_v2.sql;

grant all on mysql.proc to '${userName}'@'localhost';

grant all on mysql.proc to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_single_stat_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_single_stat_by_day to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_transaction_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_transaction_by_day to '${userName}'@'%';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_block_by_day to '${userName}'@'localhost';

grant execute on procedure ${dbName}.procedure_insert_table_tb_stat_block_by_day to '${userName}'@'%';

quit"


 





































