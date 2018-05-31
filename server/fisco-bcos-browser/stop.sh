#!/bin/sh

# JDK路径
JAVA_HOME="/nemo/jdk1.8.0_141"

APP_MAIN=org.bcos.browser.service.Main

tradePortalPID=0

getTradeProtalPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        tradePortalPID=`echo $javaps | awk '{print $1}'`
    else
        tradePortalPID=0
    fi
}

# 停止Java应用程序
# ------------------------------------------------------------------------------------------------------
# 1、调用getTradeProtalPID()函数，刷新$tradePortalPID全局变量
# 2、若程序已经启动（$tradePortalPID不等于0），则开始执行停止程序操作，否则提示程序未运行
# 3、使用[kill -9 PID]命令强制杀掉进程
# 4、使用[$?]获取上一句命令的返回值，若其为0，表示程序已停止运行，则打印Success，反之打印Failed
# 5、为防止Java程序被启动多次，这里增加了反复检查程序进程的功能，通过递归调用shutdown()函数的方式，反复kill
# 注意：Shell编程中，[$?]表示上一句命令或者上一个函数的返回值
# ------------------------------------------------------------------------------------------------------
shutdown(){
    getTradeProtalPID
    echo "==============================================================================================="
    if [ $tradePortalPID -ne 0 ]; then
        echo -n "Stopping $APP_MAIN(PID=$tradePortalPID)..."
        kill -9 $tradePortalPID
        if [ $? -eq 0 ]; then
            echo "[Success]"
            echo "==============================================================================================="
        else
            echo "[Failed]"
            echo "==============================================================================================="
        fi
        getTradeProtalPID
        if [ $tradePortalPID -ne 0 ]; then
            shutdown
        fi
    else
        echo "$APP_MAIN is not running"
        echo "==============================================================================================="
    fi
}

# 调用停止命令
shutdown