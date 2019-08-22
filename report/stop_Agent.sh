#! /bin/sh
agent_pid=`ps aux|grep "ReportAgent.py"|grep -v grep|awk '{print $2}' `
kill_cmd="kill -9 ${agent_pid}"
echo "${kill_cmd}"
eval ${kill_cmd}