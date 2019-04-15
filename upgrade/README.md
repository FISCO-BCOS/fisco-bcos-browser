# 数据库脚本升级说明

## 1.进入数据库升级目录：
```shell
cd upgrade
```

## 2.修改配置（没有变化的可以不修改）：
```shell
修改数据库IP：sed -i "s/127.0.0.1/${your_db_ip}/g" fisco-bcos.sh
修改数据库端口：sed -i "s/3306/${your_db_port}/g" fisco-bcos.sh
修改数据库用户名：sed -i "s/root/${your_db_account}/g" fisco-bcos.sh
修改数据库密码：sed -i "s/123456/${your_db_password}/g" fisco-bcos.sh
修改数据库名称：sed -i "s/testdb/${your_db_name}/g" fisco-bcos.sh
修改交易表名：sed -i "s/tb_transaction_1/${your_trans_table_name}/g" fisco-bcos-dml.sql

例子（将密码由123456改为abcd）：sed -i "s/123456/abcd/g" fisco-bcos.sh
```

## 3.执行
```shell
sh fisco-bcos.sh
```
** 数据库有多个交易表的话，需根据步骤2修改交易表名，再执行步骤3 **
