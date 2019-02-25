package com.huangxunyi.dy.utils;

import io.netty.channel.Channel;

public enum  Singleton {
    INSTANCE;
    public void sendHeartBeatToKeepAlive(Channel channel, long millis){
        Thread thread=new Thread(new HeartBeat(channel,millis));
        thread.start();
    }
    class HeartBeat implements Runnable{
        private Channel channel;
        private long millis;

        public HeartBeat(Channel channel, long millis) {
            this.channel = channel;
            this.millis = millis;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(millis);
                } catch (Exception e){
                    e.printStackTrace();
                }
                channel.writeAndFlush("type@=mrkl/");
            }
        }
    }
}