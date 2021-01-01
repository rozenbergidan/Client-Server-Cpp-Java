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
    public String decodeNextByte(byte nextByte) {
        if (len == 0) pushByte(nextByte);
        else if(len == 1){
            pushByte(nextByte);
            short opCode = bytesToShort(bytes);
            //factoryMethod
            //deco = getDecoder(opCode)
            //return deco.isDone();
        }else {
            //return deco.nextByte();
        }
        return null;

    }


    @Override
    public byte[] encode(String message) {
        return new byte[0];
    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
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

    private abstract class  decoder{
        short opCode;

        public decoder(short opCode){
            this.opCode = opCode;
        }

        protected abstract String nextByte(byte nextByte);

        protected String isDone(){
            return null;
        }
    }
    private class oneShortDecoder extends decoder{

        public oneShortDecoder(short opCode) {
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
        public twoShortDecoder(short opCode) {
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
    private class oneShortOneStringDecoder extends decoder{
        public oneShortOneStringDecoder(short opCode) {
            super(opCode);
        }

        @Override
        protected String nextByte(byte nextByte) {
            if(nextByte == '\0'){
                byte[] byteArr  = new byte[len - 2];
                String srt = bytesToString(byteArr);
                len = 0;
                return opCode + srt;
            }
            pushByte(nextByte);
            return null;
        }
    }
}
