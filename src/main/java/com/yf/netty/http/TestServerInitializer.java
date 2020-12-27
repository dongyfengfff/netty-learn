package com.yf.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个 netty 提供的 httpServerCodec codec => [coder - decoder]
        // todo HttpServerCodec() 是 netty 提供的 处理 http 的编码解码器
        pipeline.addLast("MyHttpServerCodec" , new HttpServerCodec());

        // 增加自定义处理器
        pipeline.addLast("MyTestHttpServerHandler" , new TestHttpServerHandler());
    }
}
