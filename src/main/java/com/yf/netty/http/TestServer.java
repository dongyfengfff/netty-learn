package com.yf.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty 服务器在 6666 端口监听，浏览器发出请求， http://localhost:6666/
 * 服务器可以回复消息给客户端，并对特定请求资源进行过滤
 *
 * Netty 可以做 Http 服务开发，并理解 Handler 实例和 客户端以及请求的关系
 */
public class TestServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {

            // 启动服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //
            serverBootstrap.group(bossGroup , workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(6789)
                    .sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
