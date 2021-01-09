package bgu.spl.net.srv;

import bgu.spl.net.impl.BGRS.BGRSEncoderDecoder;
import bgu.spl.net.impl.BGRS.BGRSProtocol;

public class TpcMain {
    public static void main(String[] args){
        try(BaseServer<String> tpc = new BaseServer<String>(7777, () -> new BGRSProtocol(), () -> new BGRSEncoderDecoder()) {
            @Override
            protected void execute(BlockingConnectionHandler<String> handler) {
                new Thread(handler).start();
            }
        }){
            tpc.serve();
        }catch (Exception e){}

    }
}
