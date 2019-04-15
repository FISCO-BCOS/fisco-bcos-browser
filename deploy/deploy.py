#!/usr/bin/python
# encoding: utf-8
import sys
import comm.check as browserCheck
import comm.build as browserBuild
def do():
    if len(sys.argv)==1:
        help()
        return
    param =  sys.argv[1]
    if "all" == param:
        check()
        build()
    elif "check"== param:
        check()
    elif "build" == param:
        check()
        build()
    else:
        paramError()
        return
    return


def check():
    browserCheck.do()

def build():
    browserBuild.do()


def test():
    pass


def help():
    helpMsg = '''This script is used to deploy fisco-bcos-browser
Usage: $0 Receiver Message [other]
Parameter:
    all : check the environment,build the code ,deploy and test
    check : check the environment
    build : build the code to get binary file
    deploy : deploy the frame ,you must run the command build to get  binary file
    test :  test if all the server are ok
Attention:
    1.support with python 2.7
    2.network unobstructed
    3.you had installed :gcc-c++,cmake,yasm,glibc-devel,zlib-devel ,if not ,we will install it ,but It's possible to fail.
    4.Tars uses /usr/local/mysql/ as default path. If yours is not this, please modify the file CMakeLists.txt(framework/tarscpp/CMakeLists.txt, framework/CMakeLists.txt) before compile.
    '''
    print helpMsg
    return

def paramError():
    print "param error! only supprot: check, build."
    help()
    return


if __name__ == '__main__':
    do()
    pass