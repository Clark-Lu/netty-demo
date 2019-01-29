package com.luchang.nettydemo.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * created by LuChang
 * 2019/1/5 15:20
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 6666;
        new EchoServer(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        /**
         * Creates the EventLoopGroup
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /**
             * Creates the ServerBootstrap
             */
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    /**
                     * Specifies the use of an NIO transport Channel
                     */
                    .channel(NioServerSocketChannel.class)
                    /**
                     * Sets the socket address using the specified port
                     */
                    .localAddress(new InetSocketAddress(port))
                    /**
                     * Adds an EchoServerHandler to the Channel's ChannelPipeline
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            /**
             * Binds the server asynchronously, sync() waits for bind to complete
             */
            ChannelFuture channelFuture = bootstrap.bind().sync();
            /**
             * Gets the CloseFuture of the Channel and blocks the current thread util it's complete
             */
            channelFuture.channel().closeFuture().sync();
        } finally {
            /**
             * Shuts down the EventLoop and release all resources
             */
            group.shutdownGracefully().sync();
        }
    }
}
