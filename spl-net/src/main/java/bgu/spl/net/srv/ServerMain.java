package bgu.spl.net.srv;

import bgu.spl.net.impl.BGRS.BGRSEncoderDecoder;
import bgu.spl.net.impl.BGRS.BGRSProtocol;
import bgu.spl.net.impl.Database.Database;
import bgu.spl.net.impl.echo.EchoProtocol;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;

import javax.xml.crypto.Data;
import java.util.Dictionary;

public class ServerMain {
    public static void main(String[] args){
        try(Reactor<String> reactor = new Reactor<>(10,7777,()->new BGRSProtocol(),()->new BGRSEncoderDecoder())){
            Database.getInstance().initialize("./Courses.txt");
            Database.getInstance().adminReg("idan","123");
            Database.getInstance().studentReg("tsuri","123");
//            Database.getInstance().courseReg("6","tsuri");
//            Database.getInstance().courseReg("1","tsuri");
//            Database.getInstance().courseReg("2","tsuri");
//
//            String str = Database.getInstance().studentStatus("tsuri","idan");
//            System.out.println(str);
//
            Database database= Database.getInstance();


            reactor.serve();
        }catch (Exception e){}

    }
}
