#!/usr/bin/python3
# encoding: utf-8

import sys
import os
from .utils import *
from .mysql import *

baseDir = getBaseDir()
currentDir = getCurrentBaseDir()

def do():
    print("=====================    deploy   start... =====================")
    pullSource()
    installServer()
    changeWebConfig()
    startWeb()
    print("=====================    deploy   end...   =====================")
    
    web_port = getCommProperties("web.port")
    print("============== 通过以下链接访问，IP改成服务器IP ================")
    print("http://IP:{}/".format(web_port))
    return
    
def start():
    print("=========================    start... ==========================")
    startServer()
    startWeb()
    print("=========================    end...   ==========================")
    
    web_port = getCommProperties("web.port")
    print("============== 通过以下链接访问，IP改成服务器IP ================")
    print("http://IP:{}/".format(web_port))
    return
    
def end():
    stopServer()
    stopWeb()
    return

def pullSource():
    git_comm = "wget --no-check-certificate " + getCommProperties("package.url")
    if not os.path.exists("{}/fisco-bcos-browser.zip".format(currentDir)):
        print(git_comm)
        os.system(git_comm)
    else:
        info = "n"
        if sys.version_info.major == 2:
            info = raw_input("fisco-bcos-browser.zip编译包已经存在，是否重新下载？[y/n]:")
        else:
            info = input("fisco-bcos-browser.zip编译包已经存在，是否重新下载？[y/n]:")
        if info == "y" or info == "Y":
            doCmd("rm -rf fisco-bcos-browser.zip")
            doCmd("rm -rf server")
            doCmd("rm -rf web")
            print(git_comm)
            os.system(git_comm)
    if not os.path.exists("{}/server".format(currentDir)):
        doCmd("unzip -o fisco-bcos-browser.zip")
        if not os.path.exists("{}/server".format(currentDir)):
            print("file extract failed!")
            sys.exit(0)
    else:
        info1 = "n"
        if sys.version_info.major == 2:
            info1 = raw_input("fisco-bcos-browser.zip编译包已解压过，是否重新解压？[y/n]:")
        else:
            info1 = input("fisco-bcos-browser.zip编译包已解压过，是否重新解压？[y/n]:")
        if info1 == "y" or info1 == "Y":
            doCmd("rm -rf server")
            doCmd("rm -rf web")
            doCmd("unzip -o fisco-bcos-browser.zip")
            if not os.path.exists("{}/server".format(currentDir)):
                print("file extract failed!")
                sys.exit(0)

def installServer():
    os.chdir(currentDir)
    changeServerConfig()
    # if no re-create db, no need to init tables in db
    whether_init = serverDbInit()
    server_dir = currentDir + "/server"
    script_dir = server_dir + "/script"    
    if whether_init == True:
        serverScriptInit(script_dir)
        
    startServer()
    return     

def changeServerConfig():
    os.chdir(currentDir)
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = getCommProperties("mysql.port")
    mysql_user = getCommProperties("mysql.user")
    mysql_password = getCommProperties("mysql.password")
    mysql_database = getCommProperties("mysql.database")
    server_port = getCommProperties("server.port")

    # change server config
    server_dir = currentDir + "/server"
    script_dir = server_dir + "/script"
    conf_dir = server_dir + "/conf"
    
    if not os.path.exists(script_dir + "/temp.sh"):
        doCmd('cp -f {}/browser.sh {}/temp.sh'.format(script_dir, script_dir))
    else:
        doCmd('cp -f {}/temp.sh {}/browser.sh'.format(script_dir, script_dir))
    
    if not os.path.exists(conf_dir + "/temp.yml"):
        doCmd('cp -f {}/application.yml {}/temp.yml'.format(conf_dir, conf_dir))
    else:
        doCmd('cp -f {}/temp.yml {}/application.yml'.format(conf_dir, conf_dir))
    
    # change script config
    doCmd('sed -i "s/dbUsername/{}/g" {}/browser.sh'.format(mysql_user, script_dir))
    doCmd('sed -i "s/dbPassword/{}/g" {}/browser.sh'.format(mysql_password, script_dir))
    doCmd('sed -i "s/db_browser/{}/g" {}/browser.sh'.format(mysql_database, script_dir))
    
    # change server config
    doCmd('sed -i "s/127.0.0.1/{}/g" {}/application.yml'.format(mysql_ip, conf_dir))
    doCmd('sed -i "s/3306/{}/g" {}/application.yml'.format(mysql_port, conf_dir))
    doCmd('sed -i "s/dbUsername/{}/g" {}/application.yml'.format(mysql_user, conf_dir))
    doCmd('sed -i "s/dbPassword/{}/g" {}/application.yml'.format(mysql_password, conf_dir))
    doCmd('sed -i "s/db_browser/{}/g" {}/application.yml'.format(mysql_database, conf_dir))
    doCmd('sed -i "s/5101/{}/g" {}/application.yml'.format(server_port, conf_dir))

    return

def startServer():
    os.chdir(currentDir)
    server_dir = currentDir + "/server"
    os.chdir(server_dir)
    doCmdIgnoreException("source /etc/profile")
    doCmdIgnoreException("chmod u+x *.sh")
    doCmdIgnoreException("dos2unix *.sh")
    result = doCmd("bash start.sh")
    if result["status"] == 0:
        if_started = 'started' in result["output"]
        if if_started:
            info = "n"
            if sys.version_info.major == 2:
                info = raw_input("server进程已经存在，是否kill进程强制重启？[y/n]:")
            else:
                info = input("server进程已经存在，是否kill进程强制重启？[y/n]:")
            if info == "y" or info == "Y":
                doCmd("bash stop.sh")
                result_start = doCmd("bash start.sh")
                if result_start["status"] == 0:
                    if_success = 'Success' in result_start["output"]
                    if if_success:
                        print("======= server start success! =======")
                    else:
                        print("======= server start fail!    =======")
                else:
                    print("======= server start fail!    =======")
                return
            else:
                sys.exit(0)
        if_success = 'Success' in result["output"]
        if if_success:
            print("======= server start success! =======")
        else:
            print("======= server start fail!    =======")
    else:
        print("======= server start fail!    =======")
    return
    
def stopServer():
    server_dir = currentDir + "/server"
    os.chdir(server_dir)
    doCmdIgnoreException("source /etc/profile")
    doCmdIgnoreException("chmod u+x *.sh")
    doCmdIgnoreException("dos2unix *.sh")
    result = doCmd("bash stop.sh")
    if result["status"] == 0:
        if_success = 'Success' in result["output"]
        if if_success:
            print("======= server stop success! =======")
        else:
            print("======= server is not running! =======")
    else:
        print("======= server stop fail!    =======")
    return

def changeWebConfig():
    os.chdir(currentDir)
    # get properties
    server_port = getCommProperties("server.port")
    web_port = getCommProperties("web.port")
    pid_file = currentDir + "/nginx-browser-web.pid"

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
    doCmd('sed -i "s:pid_file:{}:g" {}/comm/nginx.conf'.format(pid_file, currentDir))

    return
    
def startWeb():
    os.chdir(currentDir)
    pid_file = currentDir + "/nginx-browser-web.pid"
    if os.path.exists(pid_file):
        info = "n"
        if sys.version_info.major == 2:
            info = raw_input("web进程已经存在，是否kill进程强制重启？[y/n]:")
        else:
            info = input("web进程已经存在，是否kill进程强制重启？[y/n]:")
        if info == "y" or info == "Y":
            fin = open(pid_file, 'r')
            pid = fin.read()
            cmd = "sudo kill -QUIT {}".format(pid)
            os.system(cmd)
            doCmdIgnoreException("sudo rm -rf " + pid_file)
        else:
            sys.exit(0)
    
    web_log_dir = currentDir + "/web/log"
    doCmd('mkdir -p {}'.format(web_log_dir))
    nginx_config_dir = currentDir + "/comm/nginx.conf"
    res = doCmd("which nginx")
    if res["status"] == 0:
        res2 = doCmd("sudo " + res["output"] + " -c " + nginx_config_dir)
        if res2["status"] == 0:
            print("=======  web   start success! =======")
        else:
            print("=======  web   start fail!    =======")
    else:
        print("======= error, nginx is not install! =======")
    return
    
def stopWeb():
    pid_file = currentDir + "/nginx-browser-web.pid"
    if os.path.exists(pid_file):
        fin = open(pid_file, 'r')
        pid = fin.read()
        cmd = "sudo kill -QUIT {}".format(pid)
        os.system(cmd)
        doCmdIgnoreException("sudo rm -rf " + pid_file)
        print("=======  web   stop success! =======")
    else:
        print("=======  web   is not running! =======")
    return
