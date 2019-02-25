package com.huangxunyi.dy.initializer;

import com.huangxunyi.dy.codec.DouyuDanmuDecoder;
import com.huangxunyi.dy.codec.DouyuDanmuEncoder;
import com.huangxunyi.dy.handler.DouyuDanmuHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class DouyuChannelInitializer extends ChannelInitializer<SocketChannel> {
    private String roomId;

    public DouyuChannelInitializer(String roomId) {
        this.roomId = roomId;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,20480,0,4,0,0,true));
        channelPipeline.addLast(new DouyuDanmuEncoder());
        channelPipeline.addLast(new DouyuDanmuDecoder());
        channelPipeline.addLast(new DouyuDanmuHandler(roomId));
    }
}