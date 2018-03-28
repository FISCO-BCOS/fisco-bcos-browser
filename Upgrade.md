# 区块链浏览器升级描述（v1.0.0 to v2.0.0）

此文档是对FISCO-BCOS区块链浏览器，从v1.0.0升级到v2.0.0的描述。

## 一、升级点

**易用**

- 一键部署脚本。
- 可通过页面配置多个需要监控的区块链节点。

**功能**

- 支持监控节点是否存活。
- 支持监控多个节点的各种指标（单点统计）。
- 支持监控区块出块流程（共识流）。
- 支持监控交易的上链过程（交易流）。

**架构**

- 增加Report Agent，通过在每个部署了区块链节点的机器上部署Report Agent，监控区块链节点。
- server端调整为通过tomcat启动，可接收监控agent上报的数据。
- 数据库增加更多的表。



## 二、升级方法

若已经部署了上个版本的区块链浏览器，可通过此方式，不删除上一版本数据，直接增量升级到新版本。

**注意：本版本兼容FISCO-BCOS V2.0.0，不兼容FISCO-BCOS V1.0.0。请先确认连接的区块链节点是FISCO-BCOS V2.0.0+**

> 拉取最新代码，请确认最新代码是v1.1.0，若不是，则需要拉取v1.1.0的代码。

``` shell
git clone http://github.com/FISCO-BCOS/fisco-bcos-browser.git
```

### 2.1 Page端

#### 2.1.1 生成新的web应用

```shell
cd page/fisco-bcos-browser/
gradle build
```

> 第一次执行此步骤，会下载一些包，请耐心等待。若部此步骤不成功，请查阅常见问题。成功后会提示SUCCESSFUL。

```shell
BUILD SUCCESSFUL

Total time: 4 mins 40.446 secs
```

> 成功后可得到war包

```shell
dist/apps/fisco-bcos-browser.war
```

#### 2.1.2 先关闭tomcat服务器

```shell
cd /software/tomcat/bin
./shutdown.sh #停止tomcat服务
```

#### 2.1.3 删除旧的war包

```shell
cd /software/tomcat/webapps/
rm -rf fisco-bcos-browser.war fisco-bcos-browser
```

#### 2.1.4 发布Web应用

```shell
cp page/fisco-bcos-browser/dist/apps/fisco-bcos-browser.war /software/tomcat/webapps/fisco-bcos-browser.war #拷贝war包到tomcat目录中
cd /software/tomcat/bin
#./startup.sh #若还需配置Server端，则跳过此步骤。在Server端配置后，再一起启动tomcat服务

```

### 2.2 Server端

#### 2.2.1 升级数据库

在/server/fisco-bcos-browser/shell/upgrade_browser.sh为数据库的升级脚本.

（1） 配置脚本参数

```shell
vim upgrade_browser.sh
```

在脚本中根据上个版本的数据库信息配置如下参数：

```shell
######################  参数配置  ######################

userName=""  #浏览器数据库用户
password=""   #浏览器数据库用户密码
passwordRoot="" #数据库root账号的密码
dbName=""     #浏览器所使用的数据库名
dbIp=""   #数据库所在机器IP

###################### 参数配置结束 #################### 
```

（2）执行升级脚本

> 脚本执行后，将清除tb_nodesInfo中数据，其他表中的数据将会保留、更新数据库表结构。

```shell
# 下面命令是在/server/fisco-bcos-browser/shell目录下
chmod +x upgrade_browser.sh
./upgrade_browser.sh
```

#### 2.2.2 配置数据库

> 在application.properties文件中配置数据库相关参数。

```shell
cd server/fisco-bcos-browser 
vim src/main/resources/application.properties
```

> 将下述变量修改为与现有的配置一致，其他配置使用默认即可。

```shell
db.ip=127.0.0.1
db.port=3306
db.user=fisco-dev
db.password=fisco-dev1234
db.database=test
```

#### 2.2.3 部署server服务

> 在/server/fisco-bcos-browser目录下执行命令，生成server war包

```shell
# server/fisco-bcos-browser目录下
gradle build
```

> 将生成的war包拷贝Tomcat的webapps目录下，生成的war包在/server/fisco-bcos-server/apps目录下（war包与page生成的war包放在一个Tomcat中即可），在数据库升级过后启动服务即可

```shell
/software/tomcat/bin/shutdown.sh #停止tomcat服务
cp fisco-bcos-server/apps/fisco-bcos-server.war /software/tomcat/webapps #拷贝war包到tomcat目录中
sudo /software/tomcat/bin/startup.sh #启动tomcat服务
```

### 2.3 Report Agent

在每个部署了区块链节点的机器上部署Report Agent，来对每个区块链节点进行监控。具体的部署方法，请参考：[FISCO-BCOS浏览器监控上报脚本](report/README.md)。















