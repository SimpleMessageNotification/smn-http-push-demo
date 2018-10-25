# smn-http-push-demo
基于springboot编写的消息通知服务http/https推送demo。同时支持http/https协议、部署、打包等

### 前提条件
+ java 8
+ maven

### 编译包下载
springboot-demo/dist目录下http-push-springboot.zip，[下载地址](https://github.com/SimpleMessageNotification/smn-http-push-demo/raw/master/springboot-demo/dist/http-push-springboot.zip)

### 自行编译
下载源码， 进入到源码springboot-demo目录，执行以下命令编译
```$java
    mvn clean package
```
编译成功后，在springboot-demo\target目录下生成程序包http-push-springboot.zip

### 安装使用说明
#### 1、Windows环境
使用压缩软件解压http-push-springboot.zip文件，进入到http-push\bin目录下
```java
    双击push_server.bat运行
```

#### 2、Linux环境
解压http-push-springboot.zip
```java
    unzip http-push-springboot.zip
```
解压成功后，进入http-push/bin目录
```java
    cd http-push/bin
```
运行程序
```java
    sh push_server.sh start
```
重启使用restart、查询状态status、停止stop

### 配置参数 
+ 1、http端口   
 在程序http-push->配置文件config/application.properties中设置server.http.port参数
 ```java
server.http.enable=true
server.http.port=8080
```
 
+ 2、配置https参数和端口  
 在程序http-push->配置文件config/application.properties, 关闭https请删除这段配置
```java
server.port:8443
server.ssl.key-store:config/keystore.p12
server.ssl.key-store-password:Huawei1234
server.ssl.keyStoreType:PKCS12
server.ssl.keyAlias:tomcat
```
+ 3、配置日志路径   
在程序http-push->配置文件config/logback-spring.xml中设置，日志路径请使用绝对路径。
```java
<property name="LOG_HOME" value="/var/log/http-push"/>
```

+ 4、缓存清理定时任务配置  
在程序http-push->配置文件config/application.properties中设置cert.cache.clean.task参数,默认凌晨1点
```java
### cert.cache.clean.task
cert.cache.clean.task=0 0 1 * * ?
```

### 添加消息通知服务订阅
+ 1、登陆华为云，进入消息通知服务的console，添加一个主题  
+ 2、点击进入主题，点击添加订阅，将部署好的http服务器，添加到主题中，httpurl为：http://eip:port/smn_push  
+ 3、点击主题，发布消息，消息可以在服务器的/var/log/http-push目录下看日志
