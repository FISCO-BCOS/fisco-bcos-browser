#!/usr/bin/python
# encoding: utf-8
import sys
import comm.check as browserCheck
import comm.build as browserBuild
def do():
    if len(sys.argv)==1:
        help()
        return
    param = sys.argv[1]
    if "startAll" == param:
        check()
        startAll()
    elif "stopAll" == param:
        stopAll()
    elif "startServer" == param:
        startServer()
    elif "stopServer" == param:
        stopServer()
    elif "startWeb" == param:
        startWeb()
    elif "stopWeb" == param:
        stopWeb()
    elif "check"== param:
        check()
    elif "help"== param:
        help()
    else:
        paramError()
    return

def check():
    browserCheck.do()

def startAll():
    browserBuild.do()
    
def stopAll():
    browserBuild.end()
    
def startServer():
    browserBuild.startServer()
    
def stopServer():
    browserBuild.stopServer()
    
def startWeb():
    browserBuild.startWeb()
    
def stopWeb():
    browserBuild.stopWeb()

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
    1.rely on jdk1.8.0_121+, mysql 5.6+
    2.network is unobstructed
    3.you had installed: git,wget,nginx.If not,we will install,but it's possible fail.
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