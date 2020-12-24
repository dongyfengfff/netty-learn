package com.yf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 使用 netty 写 tcp 服务端
 */
public class NettyServer {

    public static void main(String[] args)  {

        // todo 创建线程组 BossGroup and WorkerGroup
        // bossGroup 只处理连接请求，workerGrup 处理真正的业务
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // todo **********  默认 bossGroup  workerGroup 含有的子线程 NioEventLoop 的个数是 cpu 核数 * 2
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            // todo 创建 server 端的 启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            // todo 使用链式编程来进行设置
            bootstrap.group(bossGroup , workerGroup)  // 设置线程组
                    .channel(NioServerSocketChannel.class)  // 使用 NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE , true)  // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 创建一个通道初始化对象
                        // 给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });  // 为 workGroup 的 EventLoop 对应的管道设置处理器

            System.out.println("........  server is ready .........");

            // todo 启动服务器并绑定端口，并且同步，生成了一个 ChannelFuture 对象
            ChannelFuture cf = bootstrap.bind(3333).sync();

            // TODO 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // TODO 优雅的关系
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
