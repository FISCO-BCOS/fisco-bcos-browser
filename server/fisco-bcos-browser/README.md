# 区块链浏览器server端说明

## 一、功能介绍

本工程是区块链浏览器后台服务，主要功能是获取区块链信息数据存储到数据库，以便前端调用数据显示。

工程主要是通过定时任务获取区块链信息存储到数据库。分为三个任务：
1、处理区块链全局信息（handleBlockChainInfo）：存储区块链全局信息和节点信息
2、处理区块信息（handleBlockInfo）：存储各个区块信息及其包含交易信息和交易回执信息
3、处理未上链交易信息（handlePendingTransInfo）：存储未上链交易信息



## 二、使用方式

### 1、环境

| 环境     | 版本              |
| ------ | --------------- |
| Java   | jdk1.8.0_121    |
| gradle | gradle-2.1或以上版本 |
| 数据库    | mysql-5.6或以上版本  |

环境部署方法请参考[浏览器说明的常见问题](../../README.md)。

### 2、部署

```shell
cd server/fisco-bcos-browser
```

（1）配置

**数据库、区块链接口**

```shell
vim src/main/resources/application.properties
```

> 将下述变量修改为数据库对应的IP地址，端口，用户名，密码，数据库名。node.url指向对应区块链节点的RPC端口地址。

```shell
db.ip=127.0.0.1
db.port=3306
db.user=test
db.password=test1234
db.database=test

node.url=http://127.0.0.1:8080
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

> 切换到root

```shell
sudo -s
```

> root账号登录数据库

```shell
mysql -u root
```

> 登陆之后，建数据库，进入数据库。

```sql
create database test;
show databases;
use test;
```

> 建表，直接将bcos-browser/server/fisco-bcos-browser/script/db/fisco-bcos-browser_table.sql导入数据库(需绝对路径)

```sql
source /home/app/bcos-browser/server/fisco-bcos-browser/script/db/fisco_bcos_browser_table.sql /*绝对路径*/
show tables;
```

> 可看到表已建好。

```sql
show tables;
+-----------------------+
| Tables_in_bsp_acgs    |
+-----------------------+
| tb_block              |
| tb_blockChainInfo     |
| tb_nodesInfo          |
| tb_pendingTransaction |
| tb_transaction        |
| tb_transactionReceipt |
| tb_txnByDay           |
+-----------------------+
7 rows in set (0.00 sec)
```

> 创建表之后，将test数据库的表的权限授权给test用户

```sql
/*授权test用户本地访问数据库test的所有表*/
grant all on test.* to 'test'@'localhost';

/*授权test用户从任意的远程IP访问数据库test的所有表*/
grant all on test.* to 'test'@'%';
```

（3）生成server程序

```shell
# server/fisco-bcos-browser目录下
gradle build
```

> 第一次执行此步骤，会下载一些包，请耐心等待。若部此步骤不成功，请查阅常见问题。成功后会提示SUCCESSFUL。

```shell
BUILD SUCCESSFUL

Total time: 15 mins 50.759 secs
```

> 成功后在目录下得到文件夹fisco-bcos-server，```ls fisco-bcos-server/```可看到文件夹中的内容。

```shell
apps  conf  lib  script  serverStatus.sh  start.sh  stop.sh
```

（4）启动server服务

```shell
# server/fisco-bcos-browser目录下
cd fisco-bcos-server/ #注意：需要进到生成的文件夹fisco-bcos-server中，再执行start.sh脚本
sh start.sh		
#若需要停止server服务
sh stop.sh
```

> 若执行start.sh失败，请查阅常见问题。
>
> 启动成功后输出

```shell
===============================================================================================
Starting org.bcos.browser.service.Main ...(PID=21797)...[Success]
===============================================================================================
Nov 07, 2017 2:42:29 PM org.springframework.context.support.ClassPathXmlApplicationContext prepareRefresh
INFO: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@4b1c1ea0: startup date [Tue Nov 07 14:42:29 CST 2017]; root of context hierarchy
Nov 07, 2017 2:42:29 PM org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
INFO: Loading XML bean definitions from class path resource [applicationContext.xml]
Nov 07, 2017 2:42:30 PM org.springframework.beans.factory.config.PropertyPlaceholderConfigurer loadProperties
INFO: Loading properties file from class path resource [application.properties]
Nov 07, 2017 2:42:31 PM org.springframework.context.support.DefaultLifecycleProcessor start
INFO: Starting beans in phase 2147483647
Nov 07, 2017 2:42:31 PM org.springframework.scheduling.quartz.SchedulerFactoryBean startScheduler
INFO: Starting Quartz Scheduler now
```



## 三、详细说明

### 1、目录说明

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

