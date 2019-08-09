# 区块链浏览器Server端说明

## 一、功能介绍

本工程是区块链浏览器后台服务，主要功能是获取区块链信息数据存储到数据库，以便前端调用数据显示。

（1）通过定时任务获取区块链信息存储到数据库。定时任务分别为：

- 处理区块链全局信息（handleBlockChainInfo）：存储区块链全局信息和节点信息
- 处理区块信息（handleBlockInfo）：存储各个区块信息及其包含交易信息和交易回执信息
- 处理未上链交易信息（handlePendingTransInfo）：存储未上链交易信息

（2）接收report agent服务上报数据

- 接收report agent上报数据的url：http://IP:端口/fisco-bcos-server/browserFacade


## 二、使用方式

### 1、环境

| 环境     | 版本              |
| ------ | --------------- |
| Java   | jdk1.8.0_121    |
| tomcat | 1.7或以上版本        |
| gradle | gradle-2.1或以上版本 |
| 数据库    | mysql-5.6或以上版本  |


环境部署方法请参考[浏览器说明的常见问题](../../README.md)。

### 2、手动部署

在一键部署脚本中，已经包含了部署区块链浏览器server端。此处给出手动部署方式，供参考。

```shell
cd server/fisco-bcos-browser
```

（1）配置

**数据库**

```shell
vim src/main/resources/application.properties
```

> 将下述变量修改为数据库对应的IP地址，端口，用户名，密码，数据库名；其他配置使用默认即可。

```shell
db.ip=127.0.0.1
db.port=3306
db.user=dbUser
db.password=dbPassword
db.database=test

```

**maven仓库修改**

```shell
# server/fisco-bcos-browser目录下
vim build.gradle
```

> 将maven仓库修改成自己可用的仓库。

```shell
repositories {
    maven { url "https://mvnrepository.com" }
    mavenLocal()
    mavenCentral()
}

```

**log目录**

```shell
# server/fisco-bcos-browser目录下
vim src/main/resources/log4j2.xml
```

> 修改Property标签里的内容，指向需要log打印的位置。

```html
<Property name="logPath">/home/app/fisco-bcos-browser/server/logs/</Property>
```

（2）数据库建表

数据库的配置过程请参考常见问题 3、数据库部署。在数据库配置好后，进行建表操作。

> 登录数据库

```shell
mysql -uroot -h 127.0.0.1 -P 3306
```

> 登陆之后，建数据库，进入数据库。

```sql
create database `test` default character set utf8 collate utf8_general_ci;/*创建数据库，设置字符集*/
show databases;
use test;
```

> 注意：建表前需先查看mysql触发器是否启动，命令如下：

```sql
show variables like '%scheduler%'; /*查看触发器是否启动*/
set global event_scheduler = 1;  /*启动触发器*/
```

> 建表，直接将fisco-bcos-browser/server/fisco-bcos-browser/script/db/文件夹下的文件导入数据库(需绝对路径)

```sql
source /home/app/fisco-bcos-browser/server/fisco-bcos-browser/script/db/bcos_browser_table.sql /*绝对路径*/
source /home/app/fisco-bcos-browser/server/fisco-bcos-browser/script/db/bcos_browser_table_v2.sql /*绝对路径*/
show tables;
```

> 可看到表已建好。

```sql
show tables;
+------------------------------+
| Tables_in_test               |
+------------------------------+
| tb_block                     |
| tb_blockChainInfo            |
| tb_nodesInfo                 |
| tb_pendingTransaction        |
| tb_single_stat_20171214      |
| tb_stat_block_20171214       |
| tb_stat_transaction_20171214 |
| tb_transaction               |
| tb_transactionReceipt        |
| tb_txnByDay                  |
+------------------------------+
10 rows in set (0.00 sec)
```

> 创建表之后，给fisco-dev用户授权

```sql
/*授权fisco-dev用户本地访问数据库test的所有表*/
grant all on test.* to 'fisco-dev'@'localhost';

/*授权fisco-dev用户从任意的远程IP访问数据库test的所有表*/
grant all on test.* to 'fisco-dev'@'%';

/*授权fisco-dev用户存储过程权限*/
grant all on mysql.proc to 'fisco-dev'@'localhost';
grant all on mysql.proc to 'fisco-dev'@'%';
grant execute on procedure test.procedure_insert_table_tb_single_stat_by_day to 'fisco-dev'@'localhost';
grant execute on procedure test.procedure_insert_table_tb_single_stat_by_day to 'fisco-dev'@'%';
grant execute on procedure test.procedure_insert_table_tb_stat_transaction_by_day to 'fisco-dev'@'localhost';
grant execute on procedure test.procedure_insert_table_tb_stat_transaction_by_day to 'fisco-dev'@'%';
grant execute on procedure test.procedure_insert_table_tb_stat_block_by_day to 'fisco-dev'@'localhost';
grant execute on procedure test.procedure_insert_table_tb_stat_block_by_day to 'fisco-dev'@'%';

```

（3）生成web应用

```shell
# server/fisco-bcos-browser目录下
gradle build
```

> 第一次执行此步骤，会下载一些包，请耐心等待。若部此步骤不成功，请查阅常见问题。成功后会提示SUCCESSFUL。

```shell
BUILD SUCCESSFUL

Total time: 15 mins 50.759 secs
```

> 成功后在目录下得到文件夹bcos-server，在apps目录中存在war包。

```shell
/apps/bcos-server.war
```

（4）发布Web应用

将生成的war包拷贝Tomcat的webapps目录下

```shell
cp bcos-server/apps/bcos-server.war /nemo/tomcat/webapps/bcos-server.war #拷贝war包到tomcat目录中
cd /nemo/tomcat/bin
./startup.sh #启动tomcat服务
```
（5）接收上报数据接口访问

```url
127.0.0.1:8080/bcos-server/browserFacade
```
端口为tomcat中为server配置的端口。



## 三、详细说明

### 1、目录说明
​	上报数据处理类：/fisco-bcos-browser/src/main/java/org/bcos/browser/controller

​	数据库操作接口：/fisco-bcos-browser/src/main/java/org/bcos/browser/dao

​	数据库实体类：/fisco-bcos-browser/src/main/java/org/bcos/browser//dto

​	服务处理类：/fisco-bcos-browser/src/main/java/org/bcos/browser/service

​	常用工具类：/fisco-bcos-browser/src/main/java/org/bcos/browser/util

​	配置文件：/fisco-bcos-browser/src/main/resources

​	数据库操作mapper：/fisco-bcos-browser/src/main/resources/mapper

​	数据库表结构：/fisco-bcos-browser/script/db

### 2、查看日志

日志的目录在配置步骤配置，server启动后会在相应目录自动生成日志。

（1）全局日志

```shell
tail -f bcos-server.log
```

（2）方法执行耗时监控日志

```shell
tail -f monitor.log
```
