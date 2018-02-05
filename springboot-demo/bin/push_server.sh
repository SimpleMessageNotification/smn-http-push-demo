#!/bin/sh

SpringBoot=$2
myClasspath=.
jarList=

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

if [ "$SpringBoot" = "" ];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

function start()
{
    JVM_MEM_OPT_DEFAULT=" -Xms1024m -Xmx1024m -server "
    classPath=""
    for jarList in $(ls -1 $myClasspath/lib/*.jar); do
        classPath=$classPath:$jarList
    done
    echo classpath
	count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`
	if [ $count != 0 ];then
		echo "$SpringBoot is running..."
	else
		echo "Start $SpringBoot success..."
		nohup java $JVM_MEM_OPT_DEFAULT  -classpath "%CLASSPATH%" com.smn.httppush.demo.Application > /dev/null 2>&1 &
	fi
}

function stop()
{
	echo "Stop $SpringBoot"
	boot_id=`ps -ef |grep java|grep $SpringBoot|grep -v grep|awk '{print $2}'`
	count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`

	if [ $count != 0 ];then
	    kill $boot_id
    	count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`

		boot_id=`ps -ef |grep java|grep $SpringBoot|grep -v grep|awk '{print $2}'`
		kill -9 $boot_id
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
    count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$SpringBoot is running..."
    else
        echo "$SpringBoot is not running..."
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

	echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {SpringBootJarName} \033[0m
\033[0;31m Example: \033[0m
	  \033[0;33m sh  $0  start esmart-test.jar \033[0m"
esac