# 一键部署说明

## 1、前提条件

| 环境   | 版本                   |
| ------ | ---------------------- |
| Java   | jdk1.8.0_121或以上版本 |
| python | 2.7                    |
| 数据库 | mysql-5.6或以上版本    |

## 2、获取安装包

获取安装包：
```shell
wget https://github.com/FISCO-BCOS/fisco-bcos-browser/releases/download/v2.0.0-rc2/fisco-bcos-browser.zip
```
解压安装包：
```shell
unzip fisco-bcos-browser.zip
```
进入目录：
```shell
cd fisco-bcos-browser
```

## 3、修改配置（没有变化的可以不修改）：

**注意：** 1、数据库服务和数据需要提前准备。2、服务端口不能小于1024

```shell
数据库IP：sed -i "s/10.0.0.1/${your_db_ip}/g" common.properties
数据库端口：sed -i "s/3306/${your_db_port}/g" common.properties
数据库用户名：sed -i "s/root/${your_db_account}/g" common.properties
数据库密码：sed -i "s/123456/${your_db_password}/g" common.properties
数据库名称：sed -i "s/testDB/${your_db_name}/g" common.properties

部署服务器IP：sed -i "s/127.0.0.1/${your_server_ip}/g" common.properties
后端服务端口：sed -i "s/8088/${your_server_port}/g" common.properties
前端服务端口：sed -i "s/8081/${your_web_port}/g" common.properties

例子（将数据库IP由10.0.0.1改为0.0.0.0）：sed -i "s/10.0.0.1/0.0.0.0/g" common.properties
```

## 4、部署
使用以下命令进行部署：
```shell
python deploy.py startAll
```
前后端的单独启停和相关说明可查看帮助：
```shell
python deploy.py help
```

## 5、访问
例如：在浏览器输入以下访问地址，IP为部署服务器IP，端口为前端服务端口

```
http://127.0.0.1:8081/#/
```

## 6、日志路径
```
部署日志：log/
后端日志：server/log/
前端日志：web/log/
```


