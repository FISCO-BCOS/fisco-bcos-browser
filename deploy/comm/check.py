#!/usr/bin/env python
# encoding: utf-8

import log as deployLog
import sys
from utils import *

log = deployLog.getLogger()
checkDependent = ["git","wget","nginx"]

def do():
    print "===================== envrionment check... ====================="
    installRequirements()
    checkSoft()
    checkServerPort()
    checkWebPort()
    checkDbConnect()
    print "===================== envrionment ready... ====================="
    
def installRequirements():
    for require in checkDependent:
        print "check {}...".format(require)
        hasInstall = hasInstallServer(require)
        if not hasInstall:
            installByYum(require)
        print "check finished Sucessfully."
    return

def checkSoft():
    print "check java..."
    res1 = doCmdIgnoreException("java -version")
    if res1["status"] != 0:
        print "  error! java is not install or configure!"
        sys.exit(0)
    res_home = doCmd("echo $JAVA_HOME")
    if res_home["output"].strip() == "":
        print ("  error! JAVA_HOME has not been configured!")
        sys.exit(0)
    print "check finished Sucessfully."
    return
    
def checkServerPort():
    print "check server port.."
    deploy_ip = "127.0.0.1"
    server_port = getCommProperties("server.port")
    res1 = net_if_used(deploy_ip,server_port)
    if res1:
        sys.exit(0)
    print "check finished Sucessfully."
    return
    
def checkWebPort():
    print "check web port..."
    deploy_ip = "127.0.0.1"
    web_port = getCommProperties("web.port")
    res2 = net_if_used(deploy_ip,web_port)
    if res2:
        sys.exit(0)
    print "check finished Sucessfully."
    return
    
def checkDbConnect():
    print "check db connection..."
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = getCommProperties("mysql.port")
    ifLink = do_telnet(mysql_ip,mysql_port)
    if not ifLink:
        print 'The database ip:{} port:{} is disconnected, please confirm.'.format(mysql_ip, mysql_port)
        sys.exit(0)
    print "check finished Sucessfully."

def hasInstallServer(server):
    result = doCmdIgnoreException("which {}".format(server))
    if result["status"] == 0:
        return True
    else:
        return False

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

if __name__ == '__main__':
    pass
