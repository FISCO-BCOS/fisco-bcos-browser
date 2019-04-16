#!/usr/bin/python
# encoding: utf-8

import sys
import os
from utils import *

baseDir = getBaseDir()
currentDir = getCurrentBaseDir()

def do():
    print "==================staring git clone... =============="
    pullBrowserServer()
    print "==================staring build ====================="
    gradleBuild()
    print "==================end build ========================="
    # log.infoPrint("pullFramework success")
    startSH()
    startNginx()
    return

def pullBrowserServer():
       # /Users/Van/PythonProjects/Deploy/deploy
    git_comm = "git clone https://github.com/FISCO-BCOS/fisco-bcos-browser.git -b " + getCommProperties("git.branch")
    if not os.path.exists("{}/deploy/fisco-bcos-browser".format(baseDir)):
        print git_comm
        os.system(git_comm)


def gradleBuild():
    work_dir = os.getcwd() + "/fisco-bcos-browser/"
    print "word_dir:{}".format(work_dir)
    os.chdir(work_dir)
    doCmdIgnoreException("git checkout dev2.0.0")
    os.chdir(work_dir+"server/fisco-bcos-browser")
    result = doCmd("gradle build")
    if result["status"] == 0:
        print "================build success!====================="
    else:
        print result["output"]
        sys.exit(0)
    os.chdir(currentDir)
    changeConfig()
    return

def startNginx():
    nginx_config_dir = currentDir + "/comm/nginx.conf"
    res = doCmd("which nginx")
    if res["status"] == 0:
        res2 = doCmd(res["output"] + " -c " +nginx_config_dir)
        if res2["status"] == 0:
            print "nginx start success"
        else:
            print "nginx start failed"
            sys.exit(0)

def changeConfig():
    # 读取配置
    web_server_port = getCommProperties("web_server.port")
    mysql_host = getCommProperties("mysql.host")
    mysql_user = getCommProperties("mysql.user")
    mysql_password = getCommProperties("mysql.password")
    mysql_database = getCommProperties("mysql.database")
    nginx_port = getCommProperties("nginx.proxy.port")
    web_server_ip = getCommProperties("web_server.ip")

    # print "web_server_port : {},mysql_host : {}, mysql_database : {}".format\
    #     (web_server_port,mysql_host,mysql_database)
    # 修改server配置
    yml_config_dir = os.getcwd() + "/fisco-bcos-browser/server/fisco-bcos-browser/dist/conf"
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/application.yml'.format(mysql_host, yml_config_dir))
    doCmd('sed -i "s/8088/{}/g" {}/application.yml'.format(web_server_port, yml_config_dir))
    doCmd('sed -i "s/testdb/{}/g" {}/application.yml'.format(mysql_database, yml_config_dir))
    doCmd('sed -i "s/root/{}/g" {}/application.yml'.format(mysql_user, yml_config_dir))
    doCmd('sed -i "s/123456/{}/g" {}/application.yml'.format(mysql_password, yml_config_dir))

    # change nginx config
    config_dir = currentDir
    doCmd('sed -i "s/8081/{}/g" {}/comm/nginx.conf'.format(nginx_port, config_dir))
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/comm/nginx.conf'.format(web_server_ip, config_dir))
    doCmd('sed -i "s/web_page_url/{}/g" {}/comm/nginx.conf'.format(mysql_password, config_dir))

    return

def startSH():
    dist_dir = currentDir + "/fisco-bcos-browser/server/fisco-bcos-browser/dist"
    os.chdir(dist_dir)
    result = doCmd("sh start.sh")
    if result["status"] == 0:
        print "================start server success!================"
    else:
        print "================start server fail!==================="
        sys.exit(0)
