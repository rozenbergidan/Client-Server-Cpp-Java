package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * there are 4 different decodes:
 * LOGOUT + MYCOURSE 1
 * ADMINREG+STUDENTREG+LOGIN 2
 * COURSEREG+KDAMCHECK+COURSESTAT+ISREGISTER+UNREGISTER 3
 * STUDENTSTAT 4
 */
public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private decoder deco;

    private String decodedMassege;

    private boolean desided;

    @Override
    public byte[] encode(String message) {
        short opCode = Short.parseShort(message.substring(0,2));
        if (opCode == 13){
            short errMsg = Short.parseShort(message.substring(4));

        }
        else if(opCode == 12){
            boolean additionalMsg= message.substring(4).contains(" ");
            if(additionalMsg){
                String msg=
            }
        }
        return new byte[0];
    }

    @Override
    public String decodeNextByte(byte nextByte) {
        if (len == 0) pushByte(nextByte);
        else if(len == 1){
            pushByte(nextByte);
            short opCode = bytesToShort(bytes);
            //factoryMethod
            deco = getDecoder(opCode);
            return deco.isDone();
        }else{
            return deco.nextByte(nextByte);
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
    String result =new String(b, 0, len, StandardCharsets.UTF_8);
    return result;
    }


    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private decoder getDecoder(short opCode){
        if(opCode == 4 || opCode == 11) return new oneShortDecoder(opCode);
        if(opCode == 5 || opCode == 6 || opCode == 7 || opCode == 9 ||opCode == 10 ) return new twoShortDecoder(opCode);
        if(opCode == 8) return new oneShortOneStringDecoder(opCode);
        if(opCode == 1 || opCode == 2 || opCode == 3) return new twoStringDecoder(opCode);
        //throw new Exception("opCode not supported");
        return new oneShortDecoder(opCode);
    }

    private abstract class  decoder{
        short opCode;

        private decoder(short opCode){
            this.opCode = opCode;
        }

        protected abstract String nextByte(byte nextByte);

        protected String isDone(){
            return null;
        }
    }
    private class oneShortDecoder extends decoder{
        private oneShortDecoder(short opCode) {
            super(opCode);
        }

        @Override
        protected String nextByte(byte nextByte) {
            return null;
        }

        @Override
        protected String isDone() {
            return "" + opCode;
        }
    }

    private class twoShortDecoder extends decoder{
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

    private class oneShortOneStringDecoder extends decoder {
        private oneShortOneStringDecoder(short opCode) {
            super(opCode);
        }

        protected String nextByte(byte nextByte) {
            if (nextByte == '\0') {
                byte[] byteArr=Arrays.copyOfRange(bytes,2,len);
                String srt = bytesToString(byteArr);
                len = 0;
                return opCode + srt;
            }
            pushByte(nextByte);
            return null;
        }
    }

    private class twoStringDecoder extends decoder{
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
