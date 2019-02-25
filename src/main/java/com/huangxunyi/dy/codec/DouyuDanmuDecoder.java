package com.huangxunyi.dy.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DouyuDanmuDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.isReadable(12)){
            int dataLength_HEADER=byteBuf.readIntLE();
            int dataLength_BODY=byteBuf.readIntLE();
            short dataType=byteBuf.readShortLE();
            byte encryptFlag=byteBuf.readByte();
            byte keptFlag=byteBuf.readByte();
            if(dataLength_BODY!=dataLength_BODY||byteBuf.readableBytes()!=dataLength_HEADER-8){
                byteBuf.readerIndex(byteBuf.readableBytes());
                return;
            }
            byte[] data=new byte[dataLength_HEADER-8];
            byteBuf.readBytes(data);
            String stringData=new String(data);
            list.add(parseRespond(stringData));
        }
    }
    protected Map<String, Object> parseRespond(String data){
        Map<String, Object> rtnMsg = new HashMap<String, Object>();

        //处理数据字符串末尾的'/0字符'
        data = StringUtils.substringBeforeLast(data, "/");

        //对数据字符串进行拆分
        String[] buff = data.split("/");

        //分析协议字段中的key和value值
        for(String tmp : buff){
            //获取key值
            String key = StringUtils.substringBefore(tmp, "@=");
            //获取对应的value值
            Object value = StringUtils.substringAfter(tmp, "@=");

            //如果value值中包含子序列化值，则进行递归分析
            if(StringUtils.contains((String)value, "@A")){
                value = ((String)value).replaceAll("@S", "/").replaceAll("@A", "@");
                value = this.parseRespond((String)value);
            }

            //将分析后的键值对添加到信息列表中
            rtnMsg.put(key, value);
        }

        return rtnMsg;

    }
}