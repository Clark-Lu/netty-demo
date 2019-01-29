package com.luchang.nettydemo.websocket;

import com.luchang.nettydemo.websocket.initializer.WebSocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * created by LuChang
 * 2019/1/29 15:39
 */
@Configuration
@ConfigurationProperties(prefix = "netty.server")
public class ServerBootstrapServer {

    private Integer masterCount;

    private Integer workerCount;

    private Boolean keepAlive;

    private Integer backlog;

    private Integer tcpPort;

    @Resource(name = "webSocketInitializer")
    private WebSocketInitializer webSocketInitializer;

    @Bean(name = "masterGroup")
    public NioEventLoopGroup masterGroup(){
        return new NioEventLoopGroup(masterCount);
    }

    @Bean(name = "workerGroup")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backlog);
        return options;
    }

    @Bean
    public ServerBootstrap serverBootstrap(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(masterGroup(),workerGroup()).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(webSocketInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (@SuppressWarnings("rawtypes") ChannelOption option : keySet) {
            serverBootstrap.option(option, tcpChannelOptions.get(option));
        }
        return serverBootstrap;
    }

    public Integer getMasterCount() {
        return masterCount;
    }

    public void setMasterCount(Integer masterCount) {
        this.masterCount = masterCount;
    }

    public Integer getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(Integer workerCount) {
        this.workerCount = workerCount;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getBacklog() {
        return backlog;
    }

    public void setBacklog(Integer backlog) {
        this.backlog = backlog;
    }

    public Integer getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(Integer tcpPort) {
        this.tcpPort = tcpPort;
    }

    private Channel channel;

    public void start(){
        try {
             channel = serverBootstrap().bind(tcpPort).sync().channel().closeFuture().sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop(){
        if (channel != null){
            channel.close();
            channel.parent().close();
        }
    }
}
