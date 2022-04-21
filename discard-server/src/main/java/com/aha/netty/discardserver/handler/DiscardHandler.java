package com.aha.netty.discardserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardHandler extends ChannelInboundHandlerAdapter {

    /*@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = ((ByteBuf)msg);
        try {
            System.out.println("====丢弃收到的数据");
//            while (in.isReadable()) {
//                System.out.println(in.readByte());
//                System.out.flush();
//            }
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        } finally {
            in.release();
        }
    }*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush(msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        System.out.println("=====关闭通道");
        ctx.close();
    }
}
