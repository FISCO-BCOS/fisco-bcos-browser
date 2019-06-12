# 常见问题解答

* 1、未安装MySQL-python

   ```
   Traceback (most recent call last):
     File "deploy.py", line 4, in <module>
       import comm.check as commCheck
     File "/data/temp/browser/fisco-bcos-browser/deploy/comm/check.py", line 7, in <module>
       from mysql import *
     File "/data/temp/browser/fisco-bcos-browser/deploy/comm/mysql.py", line 6, in <module>
       import MySQLdb as mdb
   ImportError: No module named MySQLdb
   ```

   答：MySQL-python安装请参看部署附录7.3

* 2、数据库用户名或密码不对

   ```
   ...
   checking database connection
   Traceback (most recent call last):
     File "/data/temp/browser/fisco-bcos-browser/deploy/comm/mysql.py", line 21, in dbConnect
       conn = mdb.connect(host=mysql_ip, port=mysql_port, user=mysql_user, passwd=mysql_password, charset='utf8')
     File "/usr/lib64/python2.7/site-packages/MySQLdb/__init__.py", line 81, in Connect
       return Connection(*args, **kwargs)
     File "/usr/lib64/python2.7/site-packages/MySQLdb/connections.py", line 193, in __init__
       super(Connection, self).__init__(*args, **kwargs2)
   OperationalError: (1045, "Access denied for user 'root'@'localhost' (using password: YES)")
   ```

   答：确认数据库用户名和密码