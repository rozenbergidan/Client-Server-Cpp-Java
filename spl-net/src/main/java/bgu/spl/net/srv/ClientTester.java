package bgu.spl.net.srv;

import java.io.*;
import java.net.Socket;

public class ClientTester {
    public static void main(String[] args) throws IOException {



        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
        try (Socket sock = new Socket("127.0.0.1", 7777);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {

            System.out.println("sending message to server");
            out.write("asd");
            out.newLine();


            out.flush();
            System.out.println("awaiting response");
            String line;
            line= in.readLine();
            System.out.println("message from server: " + line);
            line= in.readLine();
            System.out.println("message from server: " + line);
            line= in.readLine();
            System.out.println("message from server: " + line);
        }
    }
}
