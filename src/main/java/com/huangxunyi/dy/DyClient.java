package com.huangxunyi.dy;


import com.huangxunyi.dy.initializer.DouyuChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DyClient
{
    private static final String host = "openbarrage.douyutv.com";
    private static final int port = 8601;
    private String roomId;

    public DyClient(String roomId) {
        this.roomId = roomId;
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new DouyuChannelInitializer(roomId));
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            group.shutdownGracefully().sync();
        }
    }
}
