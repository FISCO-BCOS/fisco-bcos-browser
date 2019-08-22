# 区块链浏览器Page端说明

## 一、功能介绍

本工程是区块链浏览器前台服务，主要功能是展示区块链的信息，如：块信息、交易信息等。



## 二、使用方式

### 1、环境

| 环境     | 版本              |
| ------ | --------------- |
| Java   | jdk1.8.0_121    |
| gradle | gradle-2.1或以上版本 |
| 数据库    | mysql-5.6或以上版本  |
| Web服务  | Tomcat 9.0.1    |

环境部署方法请参考[浏览器说明的常见问题](../../README.md)。

### 2、部署

```shell
cd page/fisco-bcos-browser/
```

（1）配置

**数据库**

```shell
vim src/main/resources/jdbc.properties
```

> 将下述变量修改为数据库对应的IP地址，数用户名，密码。

```shell
jdbc_url = jdbc:mysql://127.0.0.1/test
jdbc_username = fisco-dev
jdbc_password = fisco-dev1234
```

**log目录**

```shell
vim src/main/resources/log4j2.xml
```

> 修改RollingFile标签里的fileName和filePattern，指向需要log打印的位置。

```html
<RollingFile name="fixedTimeFileAppender" fileName="/home/app/bcos-browser/page/logs/bcos-browser.log" filePattern="/home/app/bcos-browser/page/logs/bcos-browser.log.%d{yyyy-MM-dd}.%i.log.gz">
```

（2）生成Web应用

```shell
#page/fisco-bcos-browser/目录下
gradle build
```

> 第一次执行此步骤，会下载一些包，请耐心等待。若部此步骤不成功，请查阅常见问题。成功后会提示SUCCESSFUL。

```shell
BUILD SUCCESSFUL

Total time: 4 mins 40.446 secs
```

> 成功后可得到war包：

```shell
dist/apps/fisco-bcos-browser.war
```

（3）发布Web应用

```shell
cp dist/apps/fisco-bcos-browser.war /nemo/tomcat/webapps/fisco-bcos-browser.war #拷贝war包到tomcat目录中
cd /nemo/tomcat/bin
./startup.sh #启动tomcat服务
```

（4）访问Web

> 浏览器访问URL

```url
http://127.0.0.1:8080/fisco-bcos-browser
```



## 三、详细说明

### 1、网页内容

HOME页，展示总体概览信息、最近14天历史交易折线图，区块链概览信息、交易概览信息。

Blocks页，展示区块链的列表信息。

Transactions页，展示交易列表信息。

Pending Transactions页，展示正在处理，但未上链的交易信息。

### 2、目录说明

（1）java文件夹

​	org.bcos.browser.base：一些工具类和常用类

​	org.bcos.browser.controller：一些控制器controller

​	org.bcos.browser.dto：程序对应数据库表的实体类

​	org.bcos.browser.entity：页面出入参的实体类

​	org.bcos.browser.mapper：mybatis相关的数据库操作接口

​	org.bcos.browser.service：存放服务类

（2）resources文件夹

​	存放配置类

（3）webapp文件夹

​	assets、cdn-cgi：一些插件存放目录

​	css：css文件存放目录

​	js:存放js文件

​	WEB-INF/pages：存放jsp页面

### 3、查看日志

日志的目录在配置步骤配置，tomcat服务启动后会在相应目录自动生成日志。

```shell
tail -f fisco-bcos-browser.log
```
