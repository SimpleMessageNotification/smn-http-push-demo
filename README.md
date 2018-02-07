# smn-http-push-demo
基于springboot编写的消息通知服务http/https推送demo。同时支持http/https协议、部署、打包等

### 前提条件
+ java 8
+ maven

### 编译包下载
springboot-demo/bin目录下http-push-springboot.zip

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

### 2、Linux环境
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
 在程序http-push/config/application.properties中设置server.http.port参数
 ```java
server.http.enable=true
server.http.port=8080
```
 
+ 2、配置https参数和端口
 在程序http-push/config/application.properties
```java
server.port:8443
server.ssl.key-store:config/keystore.p12
server.ssl.key-store-password:Huawei1234
server.ssl.keyStoreType:PKCS12
server.ssl.keyAlias:tomcat
```
+ 3、配置日志路径  
在程序http-push/config/logback-spring.xml，日志路径请使用绝对路径，设置
```java
<property name="LOG_HOME" value="/var/log/http-push"/>
```