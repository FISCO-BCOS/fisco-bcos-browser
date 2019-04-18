# 一键部署说明

1、拉取代码<br>
执行命令：
```shell
git clone https://github.com/FISCO-BCOS/fisco-bcos-browser.git
```

2、进入一键部署目录：
```shell
cd fisco-bcos-browser/deploy
```

3、修改配置（没有变化的可以不修改）：
```shell
浏览器编译包url：sed -i "s#packageUrl#${package_url}#g" common.properties

数据库IP：sed -i "s/10.0.0.1/${your_db_ip}/g" common.properties
数据库用户名：sed -i "s/root/${your_db_account}/g" common.properties
数据库密码：sed -i "s/123456/${your_db_password}/g" common.properties
数据库名称：sed -i "s/testDB/${your_db_name}/g" common.properties

部署服务器IP：sed -i "s/127.0.0.1/${your_server_ip}/g" common.properties
后端服务端口：sed -i "s/8088/${your_server_port}/g" common.properties
前端服务端口：sed -i "s/8081/${your_web_port}/g" common.properties
```

**数据库服务器和数据库需要提前准备**

4、部署
```shell
python deploy.py run
```

5、访问<br>
在浏览器输入以下访问地址，IP为部署服务器IP，端口为前端服务端口
```
http://127.0.0.1:8081/#/
```

6、日志路径
```
部署日志：log/
后端日志：server/log/
前端日志：web/log/
```


