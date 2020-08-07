#!/bin/bash

APP_MAIN=org.bcos.browser.Application
tradePortalPID=0
getTradeProtalPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        tradePortalPID=`echo $javaps | awk '{print $1}'`
    else
        tradePortalPID=0
    fi
}

getStatus(){
    getTradeProtalPID
    echo "==============================================================================================="
    if [ $tradePortalPID -ne 0 ]; then
        echo "$APP_MAIN is running(PID=$tradePortalPID)"
        echo "==============================================================================================="
    else
        echo "$APP_MAIN is not running"
        echo "==============================================================================================="
    fi
}

getStatus
