#!/bin/sh

# JDK路径
JAVA_HOME="/nemo/jdk1.8.0_141"

# Java主程序，也就是main(String[] args)方法类
APP_MAIN=org.bcos.browser.service.Main

# 初始化全局变量，用于标识交易前置系统的PID（0表示未启动）
tradePortalPID=0

# 获取Java应用的PID
# ------------------------------------------------------------------------------------------------------
# 说明：通过JDK自带的jps命令，联合Linux中的grep命令，可以准确查找到Java应用的PID
#       [jps -l]表示显示Java主程序的完整包路径
#       awk命令可以分割出PID（$1部分）及Java主程序名称（$2部分）
# 例子：[$JAVA_HOME/bin/jps -l | grep $APP_MAIN]命令执行，会看到[5775 com.cucpay.tradeportal.MainApp]
# 另外：这个命令也可以取到程序的PID-->[ps aux|grep java|grep $APP_MAIN|grep -v grep|awk '{print $2}']
# ------------------------------------------------------------------------------------------------------
getTradeProtalPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        tradePortalPID=`echo $javaps | awk '{print $1}'`
    else
        tradePortalPID=0
    fi
}

# 启动Java应用程序
# ------------------------------------------------------------------------------------------------------
# 1、调用getTradeProtalPID()函数，刷新$tradePortalPID全局变量
# 2、若程序已经启动（$tradePortalPID不等于0），则提示程序已启动
# 3、若程序未被启动，则执行启动命令
# 4、启动命令执行后，再次调用getTradeProtalPID()函数
# 5、若步骤4执行后，程序的PID不等于0，则打印Success，反之打印Failed
# 注意：[echo -n]表示打印字符后不换行
# 注意：[nohup command > /path/nohup.log &]是将作业输出到nohup.log，否则它会输出到该脚本目录下的nohup.out中
# ------------------------------------------------------------------------------------------------------
startup(){
    getTradeProtalPID
    echo "==============================================================================================="
    if [ $tradePortalPID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$tradePortalPID)"
        echo "==============================================================================================="
    else
        echo -n "Starting $APP_MAIN ..."
        $JAVA_HOME/bin/java -cp 'conf/:apps/*:lib/*' $APP_MAIN &
        getTradeProtalPID
        if [ $tradePortalPID -ne 0 ]; then
            echo "(PID=$tradePortalPID)...[Success]"
            echo "==============================================================================================="
        else
            echo "[Failed]"
            echo "==============================================================================================="
        fi
    fi
}

# 调用启动命令
startup