# 一键部署说明

1.进入一键部署目录：
```shell
cd deploy
```

2.修改配置（没有变化的可以不修改）：
```shell
修改数据库IP：sed -i "s/127.0.0.1/${your_db_ip}/g" common.properties
修改数据库用户名：sed -i "s/root/${your_db_account}/g" common.properties
修改数据库密码：sed -i "s/123456/${your_db_password}/g" common.properties
修改数据库名称：sed -i "s/testdb/${your_db_name}/g" common.properties
修改后端服务IP：sed -i "s/10.0.0.1/${your_server_ip}/g" common.properties
修改后端服务端口：sed -i "s/8088/${your_server_port}/g" common.properties
修改nginx代理端口：sed -i "s/8081/${your_nginx_port}/g" common.properties

例子（将密码由123456改为abcd）：sed -i "s/123456/abcd/g" common.properties
```
** 数据库服务器和数据库需要提前准备 **

3.部署

```shell
python deploy.py build
```


