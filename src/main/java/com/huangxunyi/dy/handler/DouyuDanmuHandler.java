package com.huangxunyi.dy.handler;

import com.huangxunyi.dy.utils.Singleton;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class DouyuDanmuHandler extends SimpleChannelInboundHandler<Map<String,Object>> {
    private String roomId;

    public DouyuDanmuHandler(String roomId) {
        this.roomId = roomId;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Map<String,Object> map) throws Exception {
        Channel channel=channelHandlerContext.channel();
        if("loginres".equals(map.get("type"))){
            Singleton.INSTANCE.sendHeartBeatToKeepAlive(channel,45000);
            return;
        }
        if("pingreq".equals(map.get("type"))){
            channel.writeAndFlush("type@=joingroup/rid@="+roomId+"/gid@=-9999/");
            return;
        }
        System.out.println(map.toString());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("type@=loginreq/roomid@="+roomId+"/");
    }
}