# 区块链浏览器说明

## 一、功能介绍

区块链浏览器将区块链中的数据可视化，并进行实时的展示。用户能够以Web页面的方式，方便的获取当前区块链中的信息。

区块链浏览器分为两个部分：server和page。

server负责定时从区块链节点的RPC接口中查数据，并写入数据库中。

page负责从数据库中拉取数据，并在Web中展示。

请使用chrome浏览器访问page。

## 二、使用方式

### 1、环境

| 环境     | 版本              |
| ------ | --------------- |
| 浏览器    | chrome          |
| Java   | jdk1.8.0_121    |
| gradle | gradle-2.1或以上版本 |
| 数据库    | mysql-5.6或以上版本  |
| Web服务  | Tomcat 9.0.1    |

环境部署方法请参考常见问题。

### 2、部署

> clone代码

```shell
git clone http://github.com/FISCO-BCOS/fisco-bcos-browser.git
cd fisco-bcos-browser
```

> 分别部署

（1）[server部署](server/fisco-bcos-browser/README.md)

（2）[page部署](page/fisco-bcos-browser/README.md)



## 三、常见问题

### 1、Java环境部署

此处给出简单步骤，供快速查阅。更详细的步骤，请参考[官网](http://www.oracle.com/technetwork/java/javase/downloads/index.html)。

（1）从[官网](http://www.oracle.com/technetwork/java/javase/downloads/index.html)下载1.8.0版本的java安装包，并解压到相应目录

```shell
mkdir /software
tar -zxvf jdk1.8.0.tar.gz /software/
```

（2）配置环境变量

```shell
export JAVA_HOME=/software/jdk1.8.0_141
export PATH=$JAVA_HOME/bin:$PATH 
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```

### 2、gradle环境部署

此处给出简单步骤，供快速查阅。更详细的步骤，请参考[官网](http://www.gradle.org/downloads)。

（1）从[官网](http://www.gradle.org/downloads)下载2.14版本的gradle安装包，并解压到相应目录。

```shell
mkdir /software/
unzip -d /software/ gradle-2.14.zip
```

（2）配置环境变量

```shell
export GRADLE_HOME=/software/gradle-2.14
export PATH=$GRADLE_HOME/bin:$PATH
```

### 3、数据库部署

此处以Centos/Fedora为例。

（1）切换到root

```shell
sudo -s
```

（2）安装mysql

```shell
yum install mysql*
#某些版本的linux，需要安装mariadb，mariadb是mysql的一个分支
yum install mariadb*
```

（3）启动mysql

```shell
service mysqld start
#若安装了mariadb，则使用下面的命令启动
service mariadb start
```

（4）登录数据库，创建账户，授权

```shell
mysql -u root
```

> 登录之后，创建账户。%表示任何IP都可以用test这个用户访问，密码是test1234

```sql
/*授权test用户本地访问数据库*/
create user 'test'@'localhost' identified by 'test1234';

/*授权test用户从任意的远程IP访问数据库*/
create user 'test'@'%' identified by'test1234';
```

（5）建表

> 建数据库，进入数据库。

```sql
create database test;
show databases;
use test;
```

> 建表，直接将fisco-bcos-browser/server/fisco-bcos-browser/script/db/bcos_browser_table.sql导入数据库(需绝对路径)

```sql
source /home/app/fisco-bcos-browser/server/fisco-bcos-browser/script/db/bcos_browser_table.sql /*绝对路径*/
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

> 创建表之后，将test数据库的表的权限授权给test账户

```sql
/*授权test用户本地访问数据库test的所有表*/
grant all on test.* to 'test'@'localhost';

/*授权test用户从任意的远程IP访问数据库test的所有表*/
grant all on test.* to 'test'@'%';
```

（6）测试是否成功

> 另开一个ssh测试用户是否可以登陆，并成功授权，登陆数据库

```shell
mysql -utest -ptest1234 -h 127.0.0.1 -P 3306
```

> 登陆成功后，执行sql语句，若出现错误，则用户授权不成功

```sql
show databases;
use test;
select * from tb_txnByDay;
```



### 4、Tomcat操作

（1）部署（以Centos/Fedora为例）

```shell
sudo yum install tomcat
```

（2）部署Web的应用

> 此处即是部署浏览器的page部分，war包的生成请参考[page部署](page/bcos-browser/README.md)。

```
cp bcos-browser-1.0.war /nemo/tomcat/webapps
```

（3）启动/关闭Tomcat服务

```shell
cd /nemo/tomcat/bin
#启动
./startup.sh
#关闭
./shutdown.sh
```

（4）访问Web

> 浏览器访问URL

```url
http://127.0.0.1:8080/bcos-browser
```

### 5、gradle build问题

第一次执行时，会下载一些包，请耐心等待。若出现如下的ERROR，可忽略，让其继续运行。之后若出现SUCCESSFUL，则表示build成功。

```shell
[Fatal Error] spring-core-4.1.8.RELEASE.pom:2:127: XML document structures must start and end within the same entity.
```

### 6、server端的start.sh执行失败

检查是否是windows与linux格式不统一的问题，将windows格式转换成linux格式。

```shell
dos2unix start.sh
```

