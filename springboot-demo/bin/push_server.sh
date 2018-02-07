#!/bin/sh

app_name="http-push"
myClasspath=$(cd `dirname $0`;cd ..; pwd)

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

function start()
{
    JVM_MEM_OPT_DEFAULT=" -Xms1024m -Xmx1024m -server "
	count=`ps -ef |grep java|grep $app_name|grep -v grep|wc -l`
	if [ $count != 0 ];then
		echo "$app_name is running..."
	else
		echo "Start to $app_name ..."
		nohup java $JVM_MEM_OPT_DEFAULT  -classpath "$myClasspath/lib/*" com.smn.httppush.demo.Application > /dev/null 2>&1 &
		sleep 3;
		count=`ps -ef |grep java|grep $app_name|grep -v grep|wc -l`
		if [ $count == 0 ];then
		    echo "Start $app_name failed..."
		else
		    echo "Start success..."
		fi
	fi
}

function stop()
{
	echo "Stop $app_name"
	boot_id=`ps -ef |grep java|grep $app_name|grep -v grep|awk '{print $2}'`
	count=`ps -ef |grep java|grep $app_name|grep -v grep|wc -l`

	if [ $count != 0 ];then
	    kill $boot_id
    	count=`ps -ef |grep java|grep $app_name|grep -v grep|wc -l`

		boot_id=`ps -ef |grep java|grep $app_name|grep -v grep|awk '{print $2}'`
		kill -9 $boot_id

		sleep 3;
		echo "Stop $app_name success..."

	else
	    echo "$app_name is not running..."
	fi
}

function restart()
{
	stop
	sleep 2
	start
}

function status()
{
    count=`ps -ef |grep java|grep $app_name|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$app_name is running..."
    else
        echo "$app_name is not running..."
    fi
}

case $1 in
	start)
	start;;
	stop)
	stop;;
	restart)
	restart;;
	status)
	status;;
	*)

	echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {app_nameJarName} \033[0m
\033[0;31m Example: \033[0m
	  \033[0;33m sh  $0  start esmart-test.jar \033[0m"
esac