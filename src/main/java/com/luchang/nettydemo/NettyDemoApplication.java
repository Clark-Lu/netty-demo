package com.luchang.nettydemo;

import com.luchang.nettydemo.websocket.ServerBootstrapServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NettyDemoApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NettyDemoApplication.class, args);
        ServerBootstrapServer server = context.getBean(ServerBootstrapServer.class);
        server.start();
    }

}

