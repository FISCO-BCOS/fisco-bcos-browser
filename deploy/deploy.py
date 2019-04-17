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
    if "run" == param:
        check()
        build()
    elif "check"== param:
        check()
    elif "test"== param:
        test()
    elif "help"== param:
        help()
    else:
        paramError()
    return

def check():
    browserCheck.do()

def build():
    browserBuild.do()

def test():
    pass

def help():
    helpMsg = '''
Usage: $0 Receiver Message [other]

Parameter:
    run : check the environment and deploy
    check : check the environment
    test : check if the servers are ok
    
Attention:
    1.support with python 2.7, jdk1.8.0_121+, mysql 5.6+
    2.network unobstructed
    3.you had installed: git,wget,zlib-devel,openssl-devel,nginx.If not,we will install,but it's possible fail.
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