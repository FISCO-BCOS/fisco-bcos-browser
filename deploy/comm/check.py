#!/usr/bin/env python
# encoding: utf-8

import log as deployLog
import sys
from utils import *

log = deployLog.getLogger()
checkDependent = ["git","wget","nginx"]

def installRequirements():
    for require in checkDependent:
        print "checking {}".format(require)
        installByYum(require)
    return

def checkSoft():
    print "checking java"
    res1 = doCmd("java -version")
    if res1["status"] != 0:
        print "  error! java is not install or configure!"
        sys.exit(0)
    res2 = doCmd("which nginx")
    if res2["status"] !=0:
        print "  error! nginx is not install!"
        sys.exit(0)
    return
    
def checkPort():
    print "checking port"
    deploy_ip = getCommProperties("deploy.ip")
    server_port = getCommProperties("server.port")
    web_port = getCommProperties("web.port")
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    res1 = net_if_used(deploy_ip,server_port)
    if res1:
        sys.exit(0)
    res2 = net_if_used(deploy_ip,web_port)
    if res2:
        sys.exit(0)
    return

def hasInstallServer(server):
    result = doCmdIgnoreException("which {}".format(server))
    if result["status"] == 0:
        return true
    else:
        return false

def installByYum(server):
    if isCentos():
        result = doCmd("sudo yum -y install {}".format(server))
        if result["status"] !=0:
            os.system("yum install epel-release")
            os.system("sudo yum install python-pip")
            os.system("pip install --upgrade pip")
            os.system("pip install requests")
            result = doCmd("yum install {}".format(server))
    elif isSuse():
        os.system("sudo zypper install  -y {}".format(server))
    elif isUbuntu():
        os.system("sudo apt-get install  -y {}".format(server))
    else:
        raise Exception("error,not support this platform,only support centos,suse,ubuntu.")
    return

def do():
    print "================== envrionment check... =================="
    installRequirements()
    checkSoft()
    checkPort()
    print "================== envrionment ready... =================="

if __name__ == '__main__':
    pass
