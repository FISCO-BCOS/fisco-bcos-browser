#!/usr/bin/python
# encoding: utf-8
import sys
import comm.check as commCheck
import comm.build as commBuild
def do():
    if len(sys.argv)==1:
        help()
        return
    param = sys.argv[1]
    if "startAll" == param:
        commCheck.do()
        commBuild.do()
    elif "stopAll" == param:
        commBuild.end()
    elif "startServer" == param:
        commCheck.checkServerPort()
        commBuild.startServer()
    elif "stopServer" == param:
        commBuild.stopServer()
    elif "startWeb" == param:
        commCheck.checkWebPort()
        commBuild.startWeb()
    elif "stopWeb" == param:
        commBuild.stopWeb()
    elif "check"== param:
        commCheck.do()
    elif "help"== param:
        help()
    else:
        paramError()
    return

def help():
    helpMsg = '''
Usage: python deploy [Parameter]

Parameter:
    check : check the environment
    startAll : check the environment, deploy server and web
    stopAll : stop server and web
    startServer : start server
    stopServer : stop server
    startWeb : start web
    stopWeb : stop web
    
Attention:
    1. Need to install python2.7, jdk1.8.0_121+, mysql 5.6+, MySQL-python first
    2. Need to ensure a smooth network
    3. You need to install git, wget, nginx; if it is not installed, the installation script will automatically install these components, but this may fail.
    '''
    print helpMsg
    return

def paramError():
    print "Param error! Please check."
    print ""
    help()
    return

if __name__ == '__main__':
    do()
    pass