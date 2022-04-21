package com.aha.netty.discardserver.simplechat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("========" + incoming.remoteAddress() + " in.");
        }
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
        Channel leaving = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("========" + leaving.remoteAddress() + " leave.");
        }
        channels.remove(leaving);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("========" + "[" + incoming.remoteAddress() + "]:" + s + "\n");
            } else {
                channel.writeAndFlush("========" + "[you]:" + s + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        Channel online = ctx.channel();
        System.out.println("========" + online.remoteAddress() + " online.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        Channel offline = ctx.channel();
        System.out.println("========" + offline.remoteAddress() + " offline.");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("========" + "exception！" + cause.getMessage());
    }
}
