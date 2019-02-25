package com.huangxunyi.dy;

public class Main {
    private static final String[] roomIds={"9999","74960"};
    public static void main(String args[]) throws Exception{
        for(String roomId:roomIds){
            Thread thread=new Thread(new StartCatchDouyuDanmu(new DyClient(roomId)));
            thread.start();
        }
    }
    static class StartCatchDouyuDanmu implements Runnable {
        private DyClient douyuDanmuClient;

        public StartCatchDouyuDanmu(DyClient dyClient) {
            this.douyuDanmuClient = dyClient;
        }

        public void run() {
            try {
                douyuDanmuClient.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
