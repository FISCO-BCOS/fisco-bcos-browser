#!/usr/bin/env python3
# encoding: utf-8

from . import log as deployLog
import sys
import MySQLdb as mdb
from .utils import *
from urllib import parse

log = deployLog.getLocalLogger()

def checkDbAuthorized():
    print ("check db user/password...")
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = int(getCommProperties("mysql.port"))
    mysql_user = getCommProperties("mysql.user")
    mysql_password = getCommProperties("mysql.password")

    try:
        # connect
        conn = mdb.connect(host=mysql_ip, port=mysql_port, user=mysql_user, passwd=mysql_password)
        # conn = mdb.connect(host=mysql_ip, port=mysql_port, user=mysql_user, passwd=mysql_password, database=mysql_database, charset='utf8')
        conn.close()
        print("check finished Sucessfully.")
        log.info("check db user/password correct!")
    except:
        import traceback
        print ("  error! wrong db user/password!")
        log.info("  check db user/password error {}".format(traceback.format_exc()))
        traceback.print_exc()
        sys.exit(0)

def serverDbInit():
    # return whether to init tables
    whether_init = True
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = int(getCommProperties("mysql.port"))
    mysql_user = getCommProperties("mysql.user")
    mysql_password_raw = getCommProperties("mysql.password")
    mysql_password = parse.unquote_plus(mysql_password_raw)
    mysql_database = getCommProperties("mysql.database")

    try:
        # connect
        conn = mdb.connect(host=mysql_ip, port=mysql_port, user=mysql_user, passwd=mysql_password, charset='utf8')
        conn.autocommit(1)
        cursor = conn.cursor()
        
        # check db
        result = cursor.execute('show databases like "%s"' %mysql_database)
        drop_db = 'DROP DATABASE IF EXISTS {}'.format(mysql_database)
        create_db = 'CREATE DATABASE IF NOT EXISTS {}'.format(mysql_database)
        if result == 1:
            info = "n"
            if sys.version_info.major == 2:
                info = raw_input("数据库{}已经存在，是否删除重建？[y/n]:".format(mysql_database))
            else:
                info = input("数据库{}已经存在，是否删除重建？[y/n]:".format(mysql_database))
            if info == "y" or info == "Y":
                log.info(drop_db)
                cursor.execute(drop_db)
                log.info(create_db)
                cursor.execute(create_db)
            # if not rebuild database, no need to re-init tables of database
            else:
                whether_init = False
        else:
            log.info(create_db)
            cursor.execute(create_db)
        cursor.close()
        conn.close()
        return whether_init
    except:
        import traceback
        log.info(" mysql except {}".format(traceback.format_exc()))
        traceback.print_exc()
        sys.exit(0)

# init table and table's default data
def serverScriptInit(script_dir):
    # get properties
    mysql_ip = getCommProperties("mysql.ip")
    mysql_port = int(getCommProperties("mysql.port"))
    mysql_user = getCommProperties("mysql.user")
    mysql_password_raw = getCommProperties("mysql.password")
    mysql_password = parse.unquote_plus(mysql_password_raw)
    mysql_database = getCommProperties("mysql.database")
    
    # read .sql content
    create_sql_path = script_dir + "/browser-ddl.sql"
    init_sql_path = script_dir + "/browser-dml.sql"
    # create table
    create_sql_list = readSqlContent(create_sql_path)
    # init table data
    init_sql_list = readSqlContent(init_sql_path)

    try:
        # connect
        conn = mdb.connect(host=mysql_ip, port=mysql_port, user=mysql_user, passwd=mysql_password, database=mysql_database, charset='utf8')
        conn.autocommit(1)
        cursor = conn.cursor()

        log.info("start create tables...")
        for sql_item in create_sql_list:
            log.info(sql_item)
            cursor.execute(sql_item)

        log.info("start init default data of tables...")
        for sql_item in init_sql_list:
            log.info(sql_item)
            cursor.execute(sql_item)
        
        print ("====== db script init success! ======")
        log.info("init tables success!")
        cursor.close()
        conn.close()
    except:
        import traceback
        print ("============== script init  fail! Please view log file (default path:./log/). ==============")
        log.info("init database tables error {}".format(traceback.format_exc()))
        traceback.print_exc()
        sys.exit(0)

def readSqlContent(sql_path):
    log.info("reading table sql file {}".format(sql_path))        
    with open(sql_path,encoding="utf-8",mode="r") as f:  
        data = f.read()
        lines = data.splitlines()
        sql_data = ''
        # remove -- comment
        for line in lines:
            if len(line) == 0:
                continue
            elif line.startswith("--"):
                continue
            else:
                sql_data += line
        sql_list = sql_data.split(';')[:-1]
        sql_list = [x.replace('\n', ' ') if '\n' in x else x for x in sql_list]
        return sql_list
    
if __name__ == '__main__':
    pass
