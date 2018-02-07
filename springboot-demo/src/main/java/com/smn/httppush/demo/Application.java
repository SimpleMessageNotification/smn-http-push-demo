package com.smn.httppush.demo;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 服务启动类
 *
 * @author zhangyx
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    @Value("${server.http.port:8080}")
    private int httpPort;

    @Value("${server.http.enable:false}")
    private boolean httpEnable;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 添加http支持
     *
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory containerFactory =
                        (TomcatEmbeddedServletContainerFactory) container;
                if (httpEnable) {
                    Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
                    connector.setPort(httpPort);
                    containerFactory.addAdditionalTomcatConnectors(connector);
                }
            }
        };
    }
}


