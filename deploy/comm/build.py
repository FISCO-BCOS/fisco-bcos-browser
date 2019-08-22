#!/usr/bin/python
# encoding: utf-8

import sys
import os
from utils import *
from mysql import *

baseDir = getBaseDir()
currentDir = getCurrentBaseDir()

def do():
    print "=====================    deploy   start... ====================="
    pullSource()
    startServer()
    startWeb()
    print "=====================    deploy   end...   ====================="
    
    web_port = getCommProperties("web.port")
    print "============== 通过以下链接访问，IP改成服务器IP ================"
    print "http://IP:{}/".format(web_port)
    return
    
def end():
    stopServer()
    stopWeb()
    return

def pullSource():
    git_comm = "wget " + getCommProperties("package.url")
    if not os.path.exists("{}/fisco-bcos-browser.zip".format(currentDir)):
        print git_comm
        os.system(git_comm)
    else:
        info = raw_input("fisco-bcos-browser.zip编译包已经存在，是否重新下载？[y/n]:")
        if info == "y" or info == "Y":
            doCmd("rm -rf fisco-bcos-browser.zip")
            doCmd("rm -rf server")
            doCmd("rm -rf web")
            print git_comm
            os.system(git_comm)
    if not os.path.exists("{}/server".format(currentDir)):
        doCmd("unzip -o fisco-bcos-browser.zip")
        if not os.path.exists("{}/server".format(currentDir)):
            print "file extract failed!"
            sys.exit(0)
    else:
        info1 = raw_input("fisco-bcos-browser.zip编译包已解压过，是否重新解压？[y/n]:")
        if info == "y" or info == "Y":
            doCmd("rm -rf server")
            doCmd("rm -rf web")
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

def changeServerConfig():
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = getCommProperties("mysql.port")
    mysql_user = getCommProperties("mysql.user")
    mysql_password = getCommProperties("mysql.password")
    mysql_database = getCommProperties("mysql.database")
    server_port = getCommProperties("server.port")

    # change server config
    server_dir = currentDir + "/server/conf"
    
    if not os.path.exists(server_dir + "/temp.yml"):
        doCmd('cp -f {}/application.yml {}/temp.yml'.format(server_dir, server_dir))
    else:
        doCmd('cp -f {}/temp.yml {}/application.yml'.format(server_dir, server_dir))
    
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/application.yml'.format(mysql_ip, server_dir))
    doCmd('sed -i "s/3306/{}/g" {}/application.yml'.format(mysql_port, server_dir))
    doCmd('sed -i "s/dbUsername/{}/g" {}/application.yml'.format(mysql_user, server_dir))
    doCmd('sed -i "s/dbPassword/{}/g" {}/application.yml'.format(mysql_password, server_dir))
    doCmd('sed -i "s/db_browser/{}/g" {}/application.yml'.format(mysql_database, server_dir))
    doCmd('sed -i "s/5101/{}/g" {}/application.yml'.format(server_port, server_dir))

    return

def startServer():
    os.chdir(currentDir)
    changeServerConfig()
    dbConnect()
    
    server_dir = currentDir + "/server"
    os.chdir(server_dir)
    doCmdIgnoreException("source /etc/profile")
    doCmdIgnoreException("chmod u+x *.sh")
    doCmdIgnoreException("dos2unix *.sh")
    result = doCmd("sh start.sh")
    if result["status"] == 0:
        if_started = 'started' in result["output"]
        if if_started:
            info = raw_input("server进程已经存在，是否kill进程强制安装？[y/n]:")
            if info == "y" or info == "Y":
                doCmd("sh stop.sh")
                result_start = doCmd("sh start.sh")
                if result_start["status"] == 0:
                    if_success = 'Success' in result_start["output"]
                    if if_success:
                        print "======= server start success! ======="
                    else:
                        print "======= server start fail!    ======="
                else:
                    print "======= server start fail!    ======="
                return
            else:
                sys.exit(0)
        if_success = 'Success' in result["output"]
        if if_success:
            print "======= server start success! ======="
        else:
            print "======= server start fail!    ======="
    else:
        print "======= server start fail!    ======="
    return
    
def stopServer():
    server_dir = currentDir + "/server"
    os.chdir(server_dir)
    doCmdIgnoreException("source /etc/profile")
    doCmdIgnoreException("chmod u+x *.sh")
    doCmdIgnoreException("dos2unix *.sh")
    result = doCmd("sh stop.sh")
    if result["status"] == 0:
        if_success = 'Success' in result["output"]
        if if_success:
            print "======= server stop success! ======="
        else:
            print "======= server is not running! ======="
    else:
        print "======= server stop fail!    ======="
    return

def changeWebConfig():
    # get properties
    server_port = getCommProperties("server.port")
    web_port = getCommProperties("web.port")

    # init configure file
    web_conf_dir = currentDir + "/comm"
    if not os.path.exists(web_conf_dir + "/temp.conf"):
        doCmd('cp -f {}/nginx.conf {}/temp.conf'.format(web_conf_dir, web_conf_dir))
    else:
        doCmd('cp -f {}/temp.conf {}/nginx.conf'.format(web_conf_dir, web_conf_dir))

    # change web config
    web_dir = currentDir + "/web"
    web_log_dir = web_dir + "/log"
    doCmd('mkdir -p {}'.format(web_log_dir))
    doCmd('sed -i "s/5101/{}/g" {}/comm/nginx.conf'.format(server_port, currentDir))
    doCmd('sed -i "s/5100/{}/g" {}/comm/nginx.conf'.format(web_port, currentDir))
    doCmd('sed -i "s:log_path:{}:g" {}/comm/nginx.conf'.format(web_log_dir, currentDir))
    doCmd('sed -i "s:web_page_url:{}:g" {}/comm/nginx.conf'.format(web_dir, currentDir))

    return
    
def startWeb():
    os.chdir(currentDir)
    changeWebConfig()
    
    nginx_config_dir = currentDir + "/comm/nginx.conf"
    res = doCmd("which nginx")
    if res["status"] == 0:
        res2 = doCmd("sudo " + res["output"] + " -c " + nginx_config_dir)
        if res2["status"] == 0:
            print "=======  web   start success! ======="
        else:
            print "=======  web   start fail!    ======="
    else:
        print "======= error, nginx is not install! ======="
    return
    
def stopWeb():
    if os.path.exists("/run/nginx-browser-web.pid"):
        fin = open('/run/nginx-browser-web.pid', 'r')
        pid = fin.read()
        cmd = "sudo kill -QUIT {}".format(pid)
        os.system(cmd)
        doCmdIgnoreException("sudo rm -rf /run/nginx-browser-web.pid")
        print "=======  web   stop success! ======="
    else:
        print "=======  web   is not running! ======="
    return