package bgu.spl.net.srv;

import bgu.spl.net.impl.BGRS.BGRSEncoderDecoder;
import bgu.spl.net.impl.BGRS.BGRSProtocol;
import bgu.spl.net.impl.echo.EchoProtocol;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;

public class ServerMain {
    public static void main(String[] args){
        try(Reactor<String> reactor = new Reactor<>(10,7777,()->new BGRSProtocol(),()->new BGRSEncoderDecoder())){
            reactor.serve();
        }catch (Exception e){}
    }
}
