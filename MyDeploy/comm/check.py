#!/usr/bin/env python
# encoding: utf-8

import log as deployLog
import sys
from utils import *

log = deployLog.getLogger()
checkServer =["git","wget","gcc-c++","-y pcre pcre-devel","-y zlib zlib-devel","-y openssl openssl-devel","zlib-devel","nginx"]

def installRequirements():
    print "checking nginx"
    result = doCmd("which nginx")
    if result["status"] != 0:
        info = raw_input("此操作将会清除原有nginx，请确认无其他nginx服务在运行，按y键继续执行[y/n]:")
        if info == "y" or info == "Y":
            doCmdIgnoreException("yum -y remove nginx")
        else:
            sys.exit(0)
        for require in checkServer:
            print "checking  {}".format(require)
            installIfNotExistsByYum(require)

def check():
    print "checking envrionment......"
    res1 = doCmdIgnoreException("java -version")
    if res1["status"] != 0:
        print "Error,no java or java is not configured"
        sys.exit(0)
    res2 = doCmdIgnoreException("gradle -version")
    if res2["status"] != 0:
        print "Error,no gradle or gradle is not configured"
        sys.exit(0)
    print "Check completed. Environment is ready"
    res3 = doCmdIgnoreException("which nginx")
    if res3["status"] !=0:
        print "Not install nginx,please check it!"
    return


def hasInstallServer(server):
    result = doCmdIgnoreException("which {}".format(server))
    if result["status"] == 0:
        return True
    else:
        return False


def installIfNotExistsByYum(server):
    if isCentos():
        result = doCmd("yum -y install {}".format(server))
        if result["status"] !=0:
            os.system("yum install epel-release")
            os.system("sudo yum install python-pip")
            os.system("pip install --upgrade pip")
            os.system("pip install requests")
            result = doCmd("yum install {}".format(server))
    elif isSuse():
        os.system("zypper install  -y {}".format(server))
    elif isUbuntu():
        os.system("apt-get install  -y {}".format(server))
    else:
        raise Exception("ERROR ,NO SUPPORT THIS PLATFORM ,ONLY SUPPORT CENTOS ,SUSE ,UBUNTU .")
    return


def do():
    installRequirements()
    check()

if __name__ == '__main__':
    pass
