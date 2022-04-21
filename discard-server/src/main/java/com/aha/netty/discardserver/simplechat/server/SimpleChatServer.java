package com.aha.netty.discardserver.simplechat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleChatServer {
    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("========" + "SimpleChatServer start!");
            // 绑定端口，接收连接
            ChannelFuture cf = bootstrap.bind(this.port).sync();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            cf.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("exception!", e);
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            System.out.println("========" + "SimpleChatServer shutdown!");
        }

    }

    public static void main(String[] args) {
        new SimpleChatServer(8080).run();
    }

}
