package com.yf.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;


/**
 * HTTPObject 客户端和服务器端通信的数据被封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断 msg 是不是 httprequest 请求
        System.out.println("msg 类型 = " +  msg.getClass());
        System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

        HttpRequest httpRequest = (HttpRequest) msg;
        // 获取 URI ，过滤特定的资源
        URI uri = new URI(httpRequest.uri());
        if ("/favicon.ico".equals(uri.getPath())) {
            System.out.println("请求了 favicon.ico ， 不做响应");
            return;
        }

        // 回复信息给浏览器 http 协议
        ByteBuf content = Unpooled.copiedBuffer("hello , 我是服务器" , CharsetUtil.UTF_8);

        // 构造一个 http 响应, 即 httpresponse
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE , "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH , content.readableBytes());
//        response.headers().set(HttpHeaderNames.ACCEPT_CHARSET , CharsetUtil.UTF_8);

        // 将构建好的 response 返回
        ctx.writeAndFlush(response);


    }
}
