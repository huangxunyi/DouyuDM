package com.huangxunyi.dy.codec;

import com.huangxunyi.dy.utils.FormatTransfer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class DouyuDanmuEncoder extends MessageToByteEncoder<String> {
    public final static int DY_MESSAGE_TYPE_CLIENT = 689;
    protected void encode(ChannelHandlerContext channelHandlerContext, String data, ByteBuf byteBuf) throws Exception {
        data=data+"\0";
        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        DataOutputStream doutput = new DataOutputStream(boutput);
        try
        {
            boutput.reset();
            doutput.write(FormatTransfer.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(data.length() + 8), 0, 4);        // 4 bytes packet length
            doutput.write(FormatTransfer.toLH(DY_MESSAGE_TYPE_CLIENT), 0, 2);   // 2 bytes message type
            doutput.writeByte(0);                                               // 1 bytes encrypt
            doutput.writeByte(0);                                               // 1 bytes reserve
            doutput.writeBytes(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        byteBuf.writeBytes(boutput.toByteArray());
    }
}