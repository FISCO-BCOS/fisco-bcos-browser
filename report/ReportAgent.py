#!/usr/bin/env python 
# -*- coding: utf-8 -*-


# @file: AgentBROWSER_SERVER.py
# @author: fisco <fisco@webank.com>
#
# @date: 2017


import requests
import threading
import socket
import json
import time
import sys
import os
import re
import datetime
from copy import deepcopy
# reload(sys)
# sys.setdefaultencoding( "utf-8" )
######################  参数配置  ######################
ACCESS_NODE_INTERVAL = 60 #60s 多久询问、上报一次node的信息

HOST_IP = "192.168.1.1" #本机器的外网IP，仅作为浏览器端区分是哪台机器上报的数据
BROWSER_SERVER_IP = "192.168.1.1" #上报server端的IP
BROWSER_SERVER_PORT = "8080" #上报server端的端口

node0 = ["node0", "/bcos-data/node0/log.conf", 8545] #node的名字, log.conf的路径, RPC端口号, node的log目录(可选)
node1 = ["node1", "/bcos-data/node1/log.conf", 8546, "/bcos-data/node0/log/"] #node的名字, log.conf的路径, RPC端口号, log.conf的路径(可选)

#nodes = [node0]
nodes = [node0, node1]
BROWSER_SERVER_URL = "http://"+ BROWSER_SERVER_IP + ":" + BROWSER_SERVER_PORT + "/fisco-bcos-server/browserFacade" 
ALERT_URL = "http://"+ BROWSER_SERVER_IP + ":" + BROWSER_SERVER_PORT + "/fisco-bcos-server/browserFacade" #保留功能，目前未实现
###################### 参数配置结束 ###################### 

##################  保留字段 无需配置  ###################
ALERT_WAY = "1,2,3" 
ALERT_RECIVER = "bcosorg"
USER_AUTH_KEY = "fisco-bcos" #保留的字段，可以任意设置
INTERFACE_NAME = "test_chain0" #链的标识，任意设置
#########################################################
def getHostIp():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect((BROWSER_SERVER_IP, int(BROWSER_SERVER_PORT)))
        ip = s.getsockname()[0]
    except:
        print "Could not connect to BROWSER_SERVER" + BROWSER_SERVER_IP + ":" + BROWSER_SERVER_PORT
    finally:
        s.close()
 
    return ip

#HOST_IP = getHostIp() #本机器的IP地址
########################################################
# cpp 定义
STAT_PBFT_VIEWCHANGE_TAG = "PBFT ViewChange"

STAT_DB_GET = "DB get"
STAT_DB_SET = "DB set"
STAT_DB_GET_SIZE = "DB get size"
STAT_DB_SET_SIZE = "DB set size"
STAT_DB_HIT_MEM = "DB hit mem"

STAT_TX_EXEC = "TX Exec"
STAT_TX_TRACE = "Tx Trace Time"
STAT_BLOCK_PBFT_SEAL = "PBFT Seal Time"
STAT_BLOCK_PBFT_EXEC = "PBFT Exec Time"
STAT_BLOCK_PBFT_SIGN = "PBFT Sign Time"
STAT_BLOCK_PBFT_COMMIT = "PBFT Commit Time"
STAT_BLOCK_PBFT_CHAIN = "PBFT BlkToChain Time"
STAT_BLOCK_PBFT_VIEWCHANGE = "PBFT viewchange time"

REPORT_CAT_MAX = "max"
REPORT_CAT_MIN = "min"
REPORT_CAT_AVG = "avg"
REPORT_CAT_CNT = "cnt"
REPORT_CAT_SUC_CNT = "suc_cnt"
REPORT_CAT_SUC_PER = "suc_per"

### 其他
BLOCK_HEIGHT = "Block Height"
PBFT_VIEW = "PBFT View"
UNV_BLOCK_Q_SIZE = "Unverified Block Queue Size"
V_BLOCK_Q_SIZE = "Verified Block Queue Size"
UNV_TX_Q_SIZE = "Unverified Transactions Queue Size"
V_TX_Q_SIZE = "Verified Transactions Queue Size"

TX_FLOW = "tx_flow"
BLOCK_FLOW = "block_flow"

ENLARGE_FACTOR = 10000
SEP_SYMBLE = "|"
NORMAL_SEP_SYMBLE = "_"

__report_real_name = {
    STAT_DB_GET : u"数据库读",
    STAT_DB_SET : u"数据库写",
    STAT_DB_GET_SIZE : u"读数据大小(B)",
    STAT_DB_SET_SIZE : u"写数据大小(B)",
    STAT_DB_HIT_MEM : u"命中缓存",
    STAT_TX_EXEC : u"交易执行耗时(毫秒)",
    STAT_TX_TRACE : u"交易上链总耗时(毫秒)",
    STAT_BLOCK_PBFT_SEAL : u"PBFT打包耗时(毫秒)",
    STAT_BLOCK_PBFT_EXEC : u"PBFT执行耗时(毫秒)",
    STAT_BLOCK_PBFT_SIGN : u"PBFT签名耗时(毫秒)",
    STAT_BLOCK_PBFT_COMMIT : u"PBFT提交耗时(毫秒)",
    STAT_BLOCK_PBFT_CHAIN : u"PBFT区块落盘耗时(毫秒)",
    STAT_BLOCK_PBFT_VIEWCHANGE : u"PBFT_View共识耗时(毫秒)",
    REPORT_CAT_MAX : u"最大", 
    REPORT_CAT_MIN : u"最小",
    REPORT_CAT_AVG : u"平均",
    REPORT_CAT_CNT : u"总数",
    REPORT_CAT_SUC_CNT : u"成功次数", 
    REPORT_CAT_SUC_PER : u"成功百分比",
    BLOCK_HEIGHT : u"区块高度",
    PBFT_VIEW : u"PBFT view大小",
    UNV_BLOCK_Q_SIZE : u"未确认块队列大小",
    V_BLOCK_Q_SIZE : u"确认块队列大小",
    UNV_TX_Q_SIZE : u"未确认交易队列大小",
    V_TX_Q_SIZE : u"确认交易队列大小",
    TX_FLOW : u"交易流程跟踪",
    BLOCK_FLOW : u"出块流程跟踪",
}


def enlargeValue(value):
    return float(value) * ENLARGE_FACTOR


def doNothing(value): return value

ATTR_NAME = "attr"
__report_key_rule = {
    # for db
    STAT_DB_GET : {
        ATTR_NAME : "db_get",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
        REPORT_CAT_CNT : doNothing,
    },
    STAT_DB_SET : {
        ATTR_NAME : "db_set",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
        REPORT_CAT_CNT : doNothing,
    },
    STAT_DB_GET_SIZE : {
        ATTR_NAME : "db_get_size",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_DB_SET_SIZE : {
        ATTR_NAME : "db_set_size",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_DB_HIT_MEM : {
        ATTR_NAME : "db_hit_mem",
        REPORT_CAT_CNT : doNothing,
        REPORT_CAT_SUC_CNT : doNothing,
        REPORT_CAT_SUC_PER : doNothing,
    },
    # for tx
    STAT_TX_EXEC : {
        ATTR_NAME : "tx_exec",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
        REPORT_CAT_CNT : doNothing,
    },
    STAT_TX_TRACE : {
        ATTR_NAME : "tx_trace",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    # PBFT
    STAT_BLOCK_PBFT_SEAL : {
        ATTR_NAME : "pbft_seal",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_BLOCK_PBFT_EXEC : {
        ATTR_NAME : "pbft_exec",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_BLOCK_PBFT_SIGN : {
        ATTR_NAME : "pbft_sign",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_BLOCK_PBFT_COMMIT : {
        ATTR_NAME : "pbft_commit",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_BLOCK_PBFT_CHAIN : {
        ATTR_NAME : "pbft_chain",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
    STAT_BLOCK_PBFT_VIEWCHANGE : {
        ATTR_NAME : "pbft_viewchange",
        REPORT_CAT_MAX : doNothing,
        REPORT_CAT_MIN : doNothing,
        REPORT_CAT_AVG : doNothing,
    },
}
__alert_key = { STAT_PBFT_VIEWCHANGE_TAG : "timeout and viewchange" }

def statFilename(log_filename_format, less_time=0):
    # find log file
    # 按log.conf中的文件名格式进行文件名生成，减少的时间为格式最后一位的时间单位
    # 如配置为 "%Y%M%d%H" 日志为 YYYYMMDDHH , 那么就减少HH个单位对应的less_time时间生成文件名
    flag = log_filename_format[-1]
    t = datetime.timedelta(hours=int(less_time))
    if flag == "H":
        pass
    elif flag == "m":
        t = datetime.timedelta(minutes=int(less_time))
    elif flag == "d":
        t = datetime.timedelta(days=int(less_time))
    elif flag == "s":
        t = datetime.timedelta(seconds=int(less_time))
    logtime = (datetime.datetime.now() - t).strftime(log_filename_format)
    # logtime = (datetime.datetime.now() - datetime.timedelta(hours=int(less_hour))).strftime("%Y%m%d%H")
    suffix = logtime + '.log'
    return "stat_log_" + suffix
    
#######################################################
#### global var
nodes_state = dict()
TAIL_LINE_NUM = 1000
TAIL_TIME_GAP = ACCESS_NODE_INTERVAL  # 1 min
####################################################### 

def currentMs():
    return int(time.time()*1000)

# tail 工具
PAGE = 4096
class Tail(object):
    def __init__(self, filename, callback=sys.stdout.write):
        self.filename = filename
        self.callback = callback

    def n(self, n=10, node=-1):
        with open(self.filename, 'rb') as f:
            f.seek(0, 2)
            f_len = f.tell()
            rem = f_len % PAGE
            page_n = f_len // PAGE
            r_len = rem if rem else PAGE
            while True:
                if r_len >= f_len:
                    f.seek(0)
                    lines = f.readlines()[::-1]
                    break

                f.seek(-r_len, 2)
                # print('f_len: {}, rem: {}, page_n: {}, r_len: {}'.format(f_len, rem, page_n, r_len))
                lines = f.readlines()[::-1]
                count = len(lines) -1   # 末行可能不完整，减一行，加大读取量

                if count >= n:
                    break
                else:
                    r_len += PAGE
                    page_n -= 1
            output_line = lines[:n]
            output_line.reverse()
            if node == -1:
                for line in output_line:
                    self.callback(line)
            else:
                for line in output_line:
                    self.callback(line, node)
            pass
        pass

__tx_flow_pattern = re.compile(r'\[tx\](.+)')
__block_flow_pattern = re.compile(r'\[block\](\[Leader\])?(.+)')
__common_pattern = re.compile(r'([\w\s]+)\[(.+)\]:([\d:\s]+)')

__tx_flow_template = {
    "hash" : None,
    "start" : None,
    "onChain" : None
}
__block_flow_template = {
    "hash": None,
    "leader": False,
    "empty" : True,
    "height": 0,
    "start": None,
    "sealed": None,
    "execed": None,
    "signed": None,
    "commited": None,
    "onChain": None,
    "viewchange_start": None,
    "viewchanged": None,
}

def parseTxFlowLog(logstr):
    ret = __tx_flow_pattern.match(logstr)
    if ret is None or len(ret.groups()) != 1:
        return None
    
    s = ret.group(1)
    ret = s.split("|")
    m = deepcopy(__tx_flow_template)

    for item in ret:
        tmp = __common_pattern.match(item.strip())
        if tmp is None or len(tmp.groups()) != 3:
            continue
        tmp = tmp.groups()
        if tmp[0] == "start":
            m["hash"] = "0x" + tmp[1]
            m["start"] = { "msg" : None, "time" : tmp[2].strip() }
        else:
            m[tmp[0]] = { "msg" : tmp[1].strip(), "time" : tmp[2].strip() }
    return m


def parseBlockFlowLog(logstr):
    ret = __block_flow_pattern.match(logstr)
    if ret is None:
        return None

    if len(ret.groups()) != 2:
        return

    m = deepcopy(__block_flow_template)
    if ret.group(1):
        m['leader'] = True

    s = ret.group(2)
    isLeader = True
    ret = s.split("|")

    for item in ret:
        tmp = __common_pattern.match(item.strip())
        if tmp is None or len(tmp.groups()) != 3:
            continue
        tmp = tmp.groups()
        if tmp[0] == "execed":
            if tmp[1].startswith("#empty"):
                m["empty"] = True
                m["execed"] = { "msg" : tmp[1].strip(), "time" : tmp[2].strip() }               
            else:
                m["empty"] = False
                msg = ""
                for i in tmp[1].split(" "):
                    t=i.split(":")
                    if len(t) == 2:
                        if t[0] == "hash":
                            m["hash"] = "0x" + t[1]
                        elif t[0] == "height":
                            m["height"] = int(t[1])
                        elif t[0] == "unexected_hash":
                            msg += i
                            msg += " "
                        elif t[0] == "txnum":
                            msg += i
                            msg += " "
                m["execed"] = { "msg" : msg if msg else None, "time" : tmp[2].strip() }
        else:
            msg = tmp[1].strip()
            m[tmp[0]] = { "msg" : msg if msg else None, "time" : tmp[2].strip() }
    if m["empty"] == True:
        return None #若需要不上报空块则return None
    return m


def timeStrToStand(s):
    d = {
        r'%M':r'%m',
        r'%m':r'%M',
        r'%s':r'%S',
        r'%g':r'%f',
    }
    # p = r'\b(' + '|'.join(d.keys()) + r')\b'
    p = '|'.join(d.keys())
    pattern = re.compile(p)
    return pattern.sub(lambda x: d[x.group()], s)


def parseLogConf(logconf, nodename):
    try:
        time_format = ""
        file_format = ""
        dir_path = ""
        with open(logconf) as f:
            for line in f:
                line = line.strip()
                if line.startswith("*"):
                    if line.find("GLOBAL"): # only pickup GLOBAL block
                        for i in f:
                            line = i.strip()
                            if line.startswith("*"):
                                break # jump two level loop
                            # parse
                            if line.startswith("FORMAT"):
                                m = re.match(r'.*\{(.*)\}.*', line)
                                if m is not None and m.group(1): # pick up time format
                                    time_format = timeStrToStand(m.group(1))
                            elif line.startswith("FILENAME"):
                                m = re.match(r'(.*\"(.+)\/.*)?\{(.*)\}.*', line)
                                if m is not None and m.group(3): # pick up time format
                                    dir_path = m.group(2)
                                    file_format = timeStrToStand(m.group(3))
                        else: # pair with for
                            continue
                break

        if time_format and file_format and dir_path:
            return (time_format, file_format, dir_path)
        print "Not found related value(FORMAT, FILENAME, DIR_PATH)[", time_format, file_format, dir_path, "] in GLOBAL block in log.conf for node:" + nodename
        return None
    except Exception, e:
        print "file:" + logconf + " not exist or other error: " + e + " for node:" + nodename
    return None


class NodeState(object):
    def __init__(self, node):
        self.node_name = node[0]
        self.last_alert = dict()
        self.filter_time = 0
        self.flow_filter_time = 0
        self.tx_flow_log = []
        self.block_flow_log = []
        self.lock = threading.Lock()

        self.log_time_format = '%Y-%m-%d %H:%M:%S'
        self.log_filename_format = "%Y%M%d%H"
        self.dir_path = ""

        logconf = node[1]
        ret = parseLogConf(logconf, node[0])
        if ret is None:
            print "node:" + node[0] + ", some thing wrong for parsing log.conf"
            exit(1)
        self.log_time_format = ret[0]
        self.log_filename_format = ret[1]
        self.dir_path = ret[2]

        if len(node) >= 4: # [name, logconf, port, logpath]
            self.dir_path = node[3]
            
        if not self.dir_path.startswith("/"):
            print 'node:' + node[0] + ", "\
                'the dir_path is [' + self.dir_path + \
                '] in log.conf or parse log.conf error. We suggest to use absolute path for dir_path to find log file. ' + \
                'Add absolute path as the 4th params in node, e.g. ["node0", "/bcos-data/node0/log.conf", 8545, "/bcos-data/node0/log/"]'
            exit(1)

    def getLastReport(self, attr_name):
        return self.last_alert.get(attr_name, 0)

    def setLastReport(self, attr_name, report_time):
        self.last_alert[attr_name] = report_time

    def shouldReport(self, attr_name):
        now=currentMs() 
        last_time=self.getLastReport(attr_name)
        if now - last_time > 60000 : #相同的告警60s上报一次
            self.setLastReport(attr_name, now)
            return True
        return False

    def logTxFlow(self, logstr):
        tmp = parseTxFlowLog(logstr)
        if tmp:
            self.lock.acquire()
            self.tx_flow_log.append(tmp)
            self.lock.release()

    def logBlockFlow(self, logstr):
        tmp = parseBlockFlowLog(logstr)
        if tmp:
            self.lock.acquire()
            self.block_flow_log.append(tmp)
            self.lock.release()

    def popTxFlowLog(self):
        self.lock.acquire()
        arr = self.tx_flow_log
        self.tx_flow_log = [] # reset
        self.lock.release()
        return arr
    
    def popBlockFlowLog(self):
        self.lock.acquire()
        arr = self.block_flow_log
        self.block_flow_log = [] # reset
        self.lock.release()
        return arr

    pass


def thread_postToBrowserServer(node_name, attr, attr_name, value, timestamp):
    arguement = {
        "userAuthKey" : USER_AUTH_KEY,
        "metricDataList":[
            {
                "interfaceName": INTERFACE_NAME,
                "object" : HOST_IP + "_" + node_name,
                "attr": attr,
                "attrName": attr_name,
                "collectTimestamp": timestamp,
                "metricValue": value,
                "hostIp": HOST_IP
            }
        ]
    }
    print arguement
    try:
        rsp = requests.post(BROWSER_SERVER_URL, json=arguement)
    except:
        print "Could not post to BROWSER_SERVER"


def postToBrowserServer(node_name, attr, attr_name, value, timestamp):
    #开一个子线程发送
    t = threading.Thread(target = thread_postToBrowserServer, 
                      args = (node_name, attr, attr_name, value, timestamp), 
                     name = "thread_postToBrowserServer")
    t.start()


def thread_postAlert(node_name, attr_name, alert_level, alert_info, timestamp):
    arguement = {
        "alertList":[
            {
                "alert_title" : attr_name,
                "alert_level" : alert_level,
                "alert_obj" : HOST_IP + "_" + node_name,
                "alert_info" : "[timestamp:" + str(timestamp) + "] " + attr_name + ": " + alert_info,
                "alert_ip" : HOST_IP,
                "alert_way" : ALERT_WAY,
                "alert_reciver" : ALERT_RECIVER
            }
        ]   
    }
    print "##Alert:"
    print arguement
    try:
        rsp = requests.post(ALERT_URL, json=arguement)
        print rsp.text
    except:
        print "Could not report alert"


def postAlert(node_name, attr_name, alert_level, alert_info, timestamp):
    #alert_level: 数字  1:critical，2:major，3:minor，4:warning， 5:info。告警级别 critical > major > minor > warning > info
    return
    state = nodes_state.get(node_name, None)
    if state is not None:
        if state.shouldReport(attr_name):
            #开一个子线程发送
            t = threading.Thread(target = thread_postAlert, 
                            args = (node_name, attr_name, alert_level, alert_info, timestamp), 
                            name = "thread_postAlert")
            t.start()
    else:
        # todo add other alert
        pass

#########

__timeFilterPattern = re.compile(r'\w+\|((\w|-|:| |)+)\|(.+)')
__pattern = re.compile(r'.+\[\d+\]\[(.*)\]\[.*\](.+)')


def parser(line, node_state):
    if not line.startswith("##State"):
        return

    m = __pattern.match(line)
    if m is None or len(m.groups()) != 2:
        print "error in parser: \n" + line
        return
    name = m.group(1)
    s = m.group(2)
    item = s.split("|")
    
    if name in __alert_key: # 发警告信息    
        postAlert(node_state.node_name, name, 4, __alert_key[name], currentMs())
        return 

    # 正常上报
    if name in __report_key_rule:
        for i in item:
            ret = i.split(":")
            rawkey = ret[0].strip()
            if rawkey in __report_key_rule[name]:
                value = ret[1].strip()
                key = __report_key_rule[name][ATTR_NAME] + NORMAL_SEP_SYMBLE + rawkey
                report_key = __report_real_name[name] + " " + SEP_SYMBLE + " " + __report_real_name[rawkey]
                postToBrowserServer(node_state.node_name, key, report_key, __report_key_rule[name][rawkey](value), currentMs())
    pass


def parser2(line, node_state):
    if not line.startswith("##State Report"):       
        if line.startswith("[tx]"):
            node_state.logTxFlow(line)
        elif line.startswith("[block]"):
            node_state.logBlockFlow(line)


def logtimeparser(t, format):
    # return time.mktime(time.strptime(t, '%Y-%m-%d %H:%M:%S'))
    return time.mktime(time.strptime(t, format))


def timeCompare(t, node_state):
    now = 0
    try:
        now = logtimeparser(t, node_state.log_time_format)
    except:
        return True

    if now > node_state.filter_time:
        node_state.filter_time = now - TAIL_TIME_GAP
        return True 
    return False


def timeCompare2(t, node_state):
    now = 0
    try:
        now = logtimeparser(t, node_state.log_time_format)
    except:
        return True

    if now > node_state.flow_filter_time:
        node_state.flow_filter_time = now - 1 # TODO 修改成宏定义
        return True
    return False


def handleLine(line, node_state, compare, callback):  # filter time and line header 
    m = __timeFilterPattern.match(line)
    if m is None or len(m.groups()) != 3:
        print "error in handleLine parser: \n" + line
        return
    t = m.group(1)
    s = m.group(3)
    #print line
    if compare(t.strip(), node_state):
        # print line
        callback(s.strip(), node_state)
        pass


def handleFile(line, node_state):
    handleLine(line.strip(), node_state, timeCompare, parser)


def handleFile2(line, node_state):
    handleLine(line.strip(), node_state, timeCompare2, parser2)


def readFile(filename, node_state):
    py_tail = Tail(filename, handleFile)
    py_tail.n(TAIL_LINE_NUM, node_state)


def readFile2(filename, node_state):
    py_tail = Tail(filename, handleFile2)
    py_tail.n(500, node_state)


def accessFile(node_state, callback, recursive_deep=0):
    name = statFilename(node_state.log_filename_format, recursive_deep) # 每次递归减少1单位对应时间
    filename = node_state.dir_path + "/" + name
    
    if os.path.isfile(filename):
        callback(filename, node_state)
    else:
        if recursive_deep == 3: # 递归三次就出去,相当于向前找3个文件
            # todo can't find file
            print "can't find file for:" + filename
            postAlert(node_state.node_name, "logfile access ERROR", 5, "can't find file for:" + filename + ", try to find prev log file", currentMs())
        else:
            accessFile(node_state, callback, recursive_deep + 1)
    pass


def accessLog(node_state, interval=60):
    for sec in range(interval) :
        #流程统计上报interval次，每隔1s上报一次
        time.sleep(1)
        accessFile(node_state, readFile2)
        postToBrowserServer(node_state.node_name, TX_FLOW, __report_real_name[TX_FLOW], node_state.popTxFlowLog(), currentMs())
        postToBrowserServer(node_state.node_name, BLOCK_FLOW, __report_real_name[BLOCK_FLOW], node_state.popBlockFlowLog(), currentMs())        

    #单点统计在sleep了interal后再统一上报
    accessFile(node_state, readFile)
    # TODO 1秒一次

    pass
  
############# RPC 端口数据上报 #################
# last_report_time = 0
def accessNodeRpcPort(arguement, node_name, rpcPort):
    #print arguement
    try:
        rsp = requests.post("http://" + HOST_IP + ":" + str(rpcPort), json=arguement)
        print rsp.text
        info = json.loads(rsp.text) 
        return info #返回查询结果的json对象
    except:
        print "Could not access " + node_name + " RPC port " + str(rpcPort)

        #RPC端口错误，则告警
        # global last_report_time
        # if currentMs() - last_report_time > 60000 : #相同的告警60s上报一次
        postAlert(node_name, "RPC port not avaliable", 4, "AgentBROWSER_SERVER.py could not access RPC port. ", currentMs())
            # last_report_time = currentMs()
        raise Exception("Node RPC port access error")


def accessBlockNumber(node_name, rpcPort):
    arguement = {"jsonrpc":"2.0", "method":"eth_blockNumber", "params":[], "id":3424}

    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    block_height = int(info["result"], 16)
    print "Block height " + str(block_height)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, BLOCK_HEIGHT, __report_real_name[BLOCK_HEIGHT], block_height, currentMs()) 


def accessBlockView(node_name, rpcPort):
    arguement = {"jsonrpc": "2.0","method": "eth_pbftView", "params":[], "id":3424}
    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    block_view = int(info["result"], 16)
    print "Block View " + str(block_view)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, PBFT_VIEW, __report_real_name[PBFT_VIEW], block_view, currentMs())


def accessUnverifiedBlockQueueNumber(node_name, rpcPort):
    arguement = {"jsonrpc":"2.0", "method":"eth_unverifiedBlockQueueSize", "params":[], "id":3424}
    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    num = int(info["result"], 16)
    print "UnverifiedBlockQueueSize " + str(num)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, UNV_BLOCK_Q_SIZE, __report_real_name[UNV_BLOCK_Q_SIZE], num, currentMs())


def accessVerifiedBlockQueueNumber(node_name, rpcPort):
    arguement = {"jsonrpc":"2.0", "method":"eth_verifiedBlockQueueSize", "params":[], "id":3424}
    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    num = int(info["result"], 16)
    print "VerifiedBlockQueueSize " + str(num)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, V_BLOCK_Q_SIZE, __report_real_name[V_BLOCK_Q_SIZE] , num, currentMs())


def accessUnverifiedTxQueueNumber(node_name, rpcPort):
    arguement = {"jsonrpc":"2.0","method":"eth_unverifiedTransactionsQueueSize","params":[],"id":3424}
    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    num = int(info["result"], 16)
    print "UnverifiedTransactionsQueueSize " + str(num)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, UNV_TX_Q_SIZE, __report_real_name[UNV_TX_Q_SIZE], num, currentMs())


def accessVerifiedTxQueueNumber(node_name, rpcPort):
    arguement = {"jsonrpc":"2.0","method":"eth_verifiedTransactionsQueueSize","params":[],"id":3424}
    info = []
    try:
        info = accessNodeRpcPort(arguement, node_name, rpcPort)
    except:
        return

    num = int(info["result"], 16)
    print "VerifiedTransactionsQueueSize " + str(num)
    # TODO fix postToBrowserServer attr
    postToBrowserServer(node_name, V_TX_Q_SIZE, __report_real_name[V_TX_Q_SIZE], num, currentMs())

def accessRpc(node_name, rpcPort):
    accessBlockNumber(node_name, rpcPort)
    accessBlockView(node_name, rpcPort)
    accessUnverifiedBlockQueueNumber(node_name, rpcPort)
    accessVerifiedBlockQueueNumber(node_name, rpcPort)
    accessUnverifiedTxQueueNumber(node_name, rpcPort)
    accessVerifiedTxQueueNumber(node_name, rpcPort)

##############################

def accessNodeByTime(interval, node):
    #每隔interval，查询上报
    while True:
        print "accessNodeInfo " + node[0] + " " + node[1] + " " + str(node[2])
        accessRpc(node[0], node[2])
        # accessLog(node[0], node[1], interval) #与accessRpc不同，accessLog中，流程统计跑interval次，每1s上报一次，待interval后，将单点统计统一上报
        accessLog(nodes_state[node[0]], interval)

def main():
    for node in nodes:
        nodes_state[node[0]] = NodeState(node) # init node state
        #每个node一个Agent线程
        t = threading.Thread(target = accessNodeByTime,
                             args = (ACCESS_NODE_INTERVAL, node),
                             name = "thread_access_node")
        t.start()

if __name__ == "__main__":
    main()
