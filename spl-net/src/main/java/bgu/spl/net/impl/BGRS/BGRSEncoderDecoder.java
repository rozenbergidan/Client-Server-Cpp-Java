package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private Decoder decoder;

    @Override
    public byte[] encode(String message) {
        short opCode;
        if(message.substring(0,3).equals("ERR")) opCode = 13;
        else opCode = 12;

        if (opCode == 13){
            short errMsg = Short.parseShort(message.substring(4));
            return arrMerge(shortToBytes(opCode),shortToBytes(errMsg));
        }
        else {
            String[] str = message.split(" ");
            byte[] tmp = arrMerge(shortToBytes(opCode), shortToBytes(Short.parseShort(str[1])));
            if(str.length >= 3){
                int secSpace = message.indexOf(" ", 4);
                byte[] msgArr = message.substring(secSpace+1).getBytes(StandardCharsets.UTF_8);
                tmp = arrMerge(tmp, msgArr);
            }
            byte[] endACK= new byte[1];
            endACK[0]='\0';
            return arrMerge(tmp,endACK);

        }
    }

    @Override
    public String decodeNextByte(byte nextByte) {
        if (len == 0) pushByte(nextByte);
        else if(len == 1){
            pushByte(nextByte);
            short opCode = bytesToShort(bytes);

            //factoryMethod
            decoder = getDecoder(opCode);
            return decoder.isDone();
        }else{
            return decoder.nextByte(nextByte);
        }
        return null;
    }



    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

    private String  bytesToString(byte[] b){
    String result =new String(b, 0, b.length, StandardCharsets.UTF_8);
    return result;
    }


    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private byte[] arrMerge(byte [] arr1, byte[] arr2){
        int size = arr1.length + arr2.length;
        byte[] output = new byte[size];
        for(int i = 0; i < arr1.length; i++){
            output[i]=arr1[i];
        }
        for(int i = arr1.length; i < size; i++){
            output[i] = arr2[i-arr1.length];
        }
        return output;
    }


    private Decoder getDecoder(short opCode){
        if(opCode == 4 || opCode == 11) return new oneShortDecoder(opCode);
        if(opCode == 5 || opCode == 6 || opCode == 7 || opCode == 9 ||opCode == 10 ) return new twoShortDecoder(opCode);
        if(opCode == 8) return new oneShortOneStringDecoder(opCode);
        if(opCode == 1 || opCode == 2 || opCode == 3) return new twoStringDecoder(opCode);
        return new oneShortDecoder(opCode);
    }

    private abstract class Decoder {
        short opCode;

        private Decoder(short opCode){
            this.opCode = opCode;
        }

        protected abstract String nextByte(byte nextByte);

        protected String isDone(){
            return null;
        }
    }
    private class oneShortDecoder extends Decoder {
        private oneShortDecoder(short opCode) {
            super(opCode);
        }

        @Override
        protected String nextByte(byte nextByte) {
            return null;
        }

        @Override
        protected String isDone() {
            len = 0;
            return "" + opCode;
        }
    }

    private class twoShortDecoder extends Decoder {
        private twoShortDecoder(short opCode) {
            super(opCode);
        }

        @Override
        protected String nextByte(byte nextByte) {
            pushByte(nextByte);
            if(len == 4){
                byte[] last2 = new byte[]{bytes[2],bytes[3]};
                short srt = bytesToShort(last2);
                len = 0;
                return ""+opCode+" "+srt;
            }
            return null;
        }
    }

    private class oneShortOneStringDecoder extends Decoder {
        private oneShortOneStringDecoder(short opCode) {
            super(opCode);
        }

        protected String nextByte(byte nextByte) {
            if (nextByte == '\0') {
                byte[] byteArr=Arrays.copyOfRange(bytes,2,len);
                String srt = bytesToString(byteArr);
                len = 0;
                return opCode +" "+ srt;
            }
            pushByte(nextByte);
            return null;
        }
    }

    private class twoStringDecoder extends Decoder {
        private int counter;
        private int cut;
        private String username;
        private String password;

        private twoStringDecoder(short opCode) {
            super(opCode);
            counter=0;
        }

        @Override
        protected String nextByte(byte nextByte) {
            if(nextByte=='\0') {
                counter++;
                if (counter == 1) {
                    byte[] userbyte = Arrays.copyOfRange(bytes, 2, len);
                    username = bytesToString(userbyte);
                    cut = len + 1;
                } else if (counter == 2) {
                    byte[] passbyte = Arrays.copyOfRange(bytes, cut, len);
                    password = bytesToString(passbyte);
                    len = 0;
                    counter = 0;
                    return "" + opCode + " " + username + " " + password;
                }
            }
            pushByte(nextByte);
            return null;
        }
    }
}
