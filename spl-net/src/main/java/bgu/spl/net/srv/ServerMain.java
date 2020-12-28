package bgu.spl.net.srv;

import bgu.spl.net.impl.echo.EchoProtocol;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;

public class ServerMain {
    public static void main(String[] args){
        try(BaseServer<String> BS = BaseServer.threadPerClient(7777,()->new EchoProtocol(),()->new LineMessageEncoderDecoder())) {
            BS.serve();
        }catch (Exception e){}
    }
}
