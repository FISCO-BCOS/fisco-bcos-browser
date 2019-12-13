# webase前端部署文档

本项目是fisco-bcos应用平台项目，使用框架`vue-cli`。

兼容浏览器IE9及以上，360浏览器兼容版（IE9内核），360浏览器极速版，qq浏览器急速模式（chrome内核），chrome浏览器。

## 1、功能

1. 区块链概览，可以查看区块链信息。

2. 区块概览，可以查看区块信息和交易信息，交易信息支持input解码和event解码。

3. 节点管理，可以新增本机构节点和查看链上所有节点。

4. 合约管理，可以查看链上所有合约，新增，编译和部署合约。支持合约在线编辑。已部署的合约不能编辑，且可以发交易。能够查看合约编辑后的abi和bin，部署合约的地址。

5. 私钥管理，管理所有可以发交易的帐号，公钥用户是其他机构的帐号，无法在本机构发交易，可以通过手动绑定和自动同步获取。私钥用户为本机构发交易的用户。

6. 系统监控，查看节点所在服务器状态和查询错误日志。

7. 联盟治理，主要监控整条链所有机构所有用户发送交易行为，查看是否有异常用户和异常合约。

8. 帐号管理，只有admin帐号才能查看此功能，可以新增帐号（登录此系统帐号），修改密码等等。


## 2、部署

### 2.1 依赖环境

| 环境     | 版本              |
| ------ | --------------- |
| nginx   | nginx1.6或以上版本    |

nginx安装请参考附录

### 2.2 拉取代码

代码可以放在/data/app/page下面
执行命令：

    git clone http:xx/webase-web.git

在代码库中doc文件下有nginx配置文件，直接可以拿来替换安装的nginx的配置文件nginx.conf；
然后修改nginx.conf；

(1)、修改前端服务的ip地址和端口。

(2)、修改前端文件的路径,直接指向已拉取代码的dist目录。

(3)、修改后端服务的ip和端口，注意'/api'不要修改。


```Nginx

    upstream node_mgr_server{
        server 127.0.0.1:8083; //步骤三 节点管理服务地址及端口
    }
    server {
        listen       3002 default_server;   //步骤一 前端端口
        server_name  127.0.0.1;         //步骤一 前端地址，可配置为域名
        location / {
                root    /data/webase-web/dist;   //步骤二 前端文件路径
                index  index.html index.htm;
                try_files $uri $uri/ /index.html =404;
                }

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        location /mgr {
                    proxy_pass    http://node_mgr_server/;    		
                    proxy_set_header		Host				$host;
                        proxy_set_header		X-Real-IP			$remote_addr;
                        proxy_set_header		X-Forwarded-For		$proxy_add_x_forwarded_for;
                }
        }
```

### 2.4 启动nginx

(1)、启动nginx。
启动命令：

	/usr/local/sbin/nginx    (nginx下载在/usr/local目录下)

启动报错重点排查：日志路径是否正确（error.log和access.log）,nginx有没有添加用户权限。

(2)、打开页面，页面url是nginx配置的ip和端口。
例如:上面配置文件的url为   http://127.0.0.1:3002

(3)、打开页面后，请找运维提供帐号和密码登录。


## 3、附录
### 3.1 安装nginx（可参考[网络教程](http://www.runoob.com/linux/nginx-install-setup.html)）
#### 3.1.1 下载nginx依赖
在安装nginx前首先要确认系统中安装了gcc、pcre-devel、zlib-devel、openssl-devel。如果没有，请执行命令

	yum -y install gcc pcre-devel zlib-devel openssl openssl-devel
执行命令时注意权限问题，如遇到，请加上sudo
#### 3.1.2 下载nginx
nginx下载地址：https://nginx.org/download/（下载最新稳定版本即可）
或者使用命令：

	wget http://nginx.org/download/nginx-1.10.2.tar.gz  (版本号可换)
将下载的包移动到/usr/local/下
#### 3.1.3 安装nginx
##### 3.1.3.1解压
	tar -zxvf nginx-1.9.9.tar.gz

##### 3.1.3.2进入nginx目录

	cd nginx-1.9.9
##### 3.1.3.3配置

	./configure --prefix=/usr/local/nginx

##### 3.1.3.4make

	make
	make install
##### 3.1.3.5测试是否安装成功
使用命令：

	/usr/local/nginx/sbin/nginx –t
正常情况的信息输出：

	nginx: the configuration file /usr/local/nginx/conf/nginx.conf syntax is ok
	nginx: configuration file /usr/local/nginx/conf/nginx.conf test is successful