# 区块链浏览器服务说明

# 目录
> * [功能说明](#chapter-1)
> * [前提条件](#chapter-2)
> * [部署说明](#chapter-3)
> * [问题排查](#chapter-4)
> * [附录](#chapter-5)

# 1. <a id="chapter-1"></a>功能说明

本工程是区块链浏览器的后端服务，功能是解析节点数据储存数据库，向前端提供数据接口，页面展示。

# 2. <a id="chapter-2"></a>前提条件

| 环境     | 版本              |
| ------ | --------------- |
| Java   | jdk1.8.0_121或以上版本    |
| gradle | gradle-5.0或以上版本 |
| 数据库    | mysql-5.6或以上版本  |
备注：安装说明请参看附录。

# 3. <a id="chapter-3"></a>部署说明

## 3.1 拉取代码
执行命令：
```shell
git clone https://github.com/FISCO-BCOS/fisco-bcos-browser.git
```

**注意**：代码拉取后，切换到相应分支。

```shell
cd fisco-bcos-browser
git checkout XXXXX
```

## 3.2 编译代码

（1）进入目录：
```shell
cd server/fisco-bcos-browser
```

（2）执行构建命令：
```shell
gradle build
```
构建完成后，会在目录中生成已编译的代码目录dist。

## 3.3 修改配置

（1）进入目录：
```shell
cd dist/conf
```

（2）修改服务配置（没变化可以不修改）：

数据库服务器，和数据库需要提前准备，创建方法可以参照附录。
```shell
修改当前服务端口：sed -i "s/8088/${your_server_port}/g" application.yml
修改数据库IP：sed -i "s/127.0.0.1/${your_db_ip}/g" application.yml
修改数据库名称：sed -i "s/testDB/${your_db_name}/g" application.yml
修改数据库用户名：sed -i "s/root/${your_db_account}/g" application.yml
修改数据库密码：sed -i "s/123456/${your_db_password}/g" application.yml
例子（将端口由8088改为8090）：sed -i "s/8088/8090/g" application.yml
```

## 3.4 服务启停
进入到已编译的代码根目录：
```shell
cd dist
```
```shell
启动：sh start.sh
停止：sh stop.sh
检查：sh status.sh
```

## 3.5 查看日志

进入到已编译的代码根目录：
```shell
cd dist
```

查看
```shell
tail -f log/fisco-bcos-browser.log
```

# 4. <a id="chapter-4"></a>问题排查

## 4.1 启停失败
如果脚本执行出现问题，尝试以下操作：
```shell
chmod +x *.sh
```
## 4.2 gradle build失败

```shell
gradle build
```
执行后，出现下面错误。**请检查gradle版本，需要使用5.0以上版本。**
`Could not find method annotationProcessor() for arguments [org.projectlombok:lombok:1.18.2] on object of type org.gradle.api.internal.artifacts.dsl.dependencies.DefaultDependencyHandler.`

# 5. <a id="chapter-5"></a>附录

## 5.1 Java环境部署

此处给出简单步骤，供快速查阅。更详细的步骤，请参考[官网](http://www.oracle.com/technetwork/java/javase/downloads/index.html)。

（1）从[官网](http://www.oracle.com/technetwork/java/javase/downloads/index.html)下载对应版本的java安装包，并解压到相应目录

```shell
mkdir /software
tar -zxvf jdkXXX.tar.gz /software/
```

（2）配置环境变量

```shell
export JAVA_HOME=/software/jdk1.8.0_121
export PATH=$JAVA_HOME/bin:$PATH 
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```

## 5.2 gradle环境部署

此处给出简单步骤，供快速查阅。更详细的步骤，请参考[官网](http://www.gradle.org/downloads)。

（1）从[官网](http://www.gradle.org/downloads)下载对应5.0以上版本的gradle安装包，并解压到相应目录

```shell
mkdir /software/
unzip -d /software/ gradle-XXX.zip
```

（2）配置环境变量

```shell
export GRADLE_HOME=/software/gradle-XXX
export PATH=$GRADLE_HOME/bin:$PATH
```

## 5.3 数据库部署

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
systemctl start mariadb.service
```

（4）初始化数据库用户

初次登录
```shell
mysql -u root
```

给root设置密码和授权远程访问
```sql
mysql > SET PASSWORD FOR 'root'@'localhost' = PASSWORD('123456');
mysql > GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
```

授权test用户本地访问数据库
```sql
mysql > create user 'test'@'localhost' identified by 'test1234';
```

（5）测试连接

另开一个ssh测试本地用户test是否可以登录数据库

```shell
mysql -utest -ptest1234 -h 127.0.0.1 -P 3306
```

登陆成功后，执行以下sql语句，若出现错误，则用户授权不成功

```sql
mysql > show databases;
mysql > use test;
```

（6）创建数据库

登录数据库

```shell
mysql -utest -ptest1234 -h 127.0.0.1 -P 3306
```

创建数据库

```sql
mysql > create database testDB;
```

### 5.3.1 常见错误 
#### 5.3.1.1 腾讯云centos mysql安装完成后，登陆报错：Access denied for user 'root'@'localhost'

1. 编辑 /etc/my.cnf ，在[mysqld] 部分最后添加一行
```
   skip-grant-tables 
```
2. 保存后重启mysql
```shell
   service mysqld restart 
```
3. 输入以下命令，回车后输入密码再回车登录Mysql
```shell
   mysql -uroot -p mysql  
```

