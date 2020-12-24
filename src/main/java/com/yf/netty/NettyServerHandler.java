package com.yf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**\
 * 自定义一个 Handler ，需要继承 netty 规定好的某个 HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // todo 读取数据 (在此处读取客户端发送的消息)

    /**
     *
     * @param ctx  上下文对象，包含了 pipeline channel
     * @param msg  客户端发送的数据 默认是 Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        System.out.println("服务器读取线程 : " + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看 channel 和 pipeline 的关系");
        
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();  // 本质是 双向链表


        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是 : " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址是 : " + channel.remoteAddress());
    }

    /**
     * 数据读取完毕的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        // 将数据写入到缓存，并刷新
        // 一般对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello , 客户端 ...  hahaha " , CharsetUtil.UTF_8));
    }

    /**
     * 处理异常的方法，一般关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
