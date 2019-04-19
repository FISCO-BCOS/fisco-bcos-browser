#!/usr/bin/python
# encoding: utf-8

import sys
import os
from utils import *

baseDir = getBaseDir()
currentDir = getCurrentBaseDir()

def do():
    print "================== deploy start... =================="
    pullSource()
    changeConfig()
    startServer()
    startWeb()
    print "================== deploy end... =================="
    return

def pullSource():
    git_comm = "wget " + getCommProperties("package.url")
    print git_comm
    if not os.path.exists("{}/fisco-bcos-browser.zip".format(currentDir)):
        os.system(git_comm)
    else:
        info = raw_input("fisco-bcos-browser.zip编译包已经存在，是否要重新下载[y/n]:")
        if info == "y" or info == "Y":
            doCmd("rm -rf fisco-bcos-browser.zip")
            doCmd("rm -rf server")
            doCmd("rm -rf web")
            os.system(git_comm)
    if not os.path.exists("{}/server".format(currentDir)):
    	doCmd("unzip -o fisco-bcos-browser.zip")
    	if not os.path.exists("{}/server".format(currentDir)):
            print "file extract failed!"
            sys.exit(0)

def gradleBuild():
    work_dir = os.getcwd() + "/fisco-bcos-browser/"
    print "word_dir:{}".format(work_dir)
    os.chdir(work_dir)
    doCmdIgnoreException("git checkout dev2.0.0")
    os.chdir(work_dir+"server/fisco-bcos-browser")
    result = doCmd("gradle build")
    if result["status"] == 0:
        print "======= build success! ======="
    else:
        print result["output"]
        sys.exit(0)
    os.chdir(currentDir)
    return

def changeConfig():
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = getCommProperties("mysql.port")
    mysql_user = getCommProperties("mysql.user")
    mysql_password = getCommProperties("mysql.password")
    mysql_database = getCommProperties("mysql.database")
    deploy_ip = getCommProperties("deploy.ip")
    server_port = getCommProperties("server.port")
    web_port = getCommProperties("web.port")

    # change server config
    server_dir = currentDir + "/server/conf"
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/application.yml'.format(mysql_ip, server_dir))
    doCmd('sed -i "s/3306/{}/g" {}/application.yml'.format(mysql_port, server_dir))
    doCmd('sed -i "s/root/{}/g" {}/application.yml'.format(mysql_user, server_dir))
    doCmd('sed -i "s/123456/{}/g" {}/application.yml'.format(mysql_password, server_dir))
    doCmd('sed -i "s/testDB/{}/g" {}/application.yml'.format(mysql_database, server_dir))
    doCmd('sed -i "s/8088/{}/g" {}/application.yml'.format(server_port, server_dir))

    # change web config
    web_dir = currentDir + "/web"
    web_log_dir = web_dir + "/log"
    doCmd('mkdir -p {}'.format(web_log_dir))
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/comm/nginx.conf'.format(deploy_ip, currentDir))
    doCmd('sed -i "s/8088/{}/g" {}/comm/nginx.conf'.format(server_port, currentDir))
    doCmd('sed -i "s/8081/{}/g" {}/comm/nginx.conf'.format(web_port, currentDir))
    doCmd('sed -i "s:log_path:{}:g" {}/comm/nginx.conf'.format(web_log_dir, currentDir))
    doCmd('sed -i "s:web_page_url:{}:g" {}/comm/nginx.conf'.format(web_dir, currentDir))

    return

def startServer():
    server_dir = currentDir + "/server"
    os.chdir(server_dir)
    doCmdIgnoreException("source /etc/profile")
    result = doCmd("sh start.sh")
    if result["status"] == 0:
        print "======= server start success! ======="
    else:
        print "======= server start fail! ======="
        sys.exit(0)

def startWeb():
    nginx_config_dir = currentDir + "/comm/nginx.conf"
    res = doCmd("which nginx")
    if res["status"] == 0:
        res2 = doCmd("sudo " + res["output"] + " -c " + nginx_config_dir)
        if res2["status"] == 0:
            print "=======  web  start success! ======="
        else:
            print "=======  web  start fail! ======="
            sys.exit(0)
    else:
        print "======= error, nginx is not install! ======="
        sys.exit(0)