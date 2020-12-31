package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * there are 4 different decodes:
 * LOGOUT + MYCOURSE
 * ADMINREG+STUDENTREG+LOGIN
 * COURSEREG+KDAMCHECK+COURSESTAT+ISREGISTER+UNREGISTER
 * STUDENTSTAT
 */
public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    private String decodedMassege;


    @Override
    public String decodeNextByte(byte nextByte) {
        if(len==2){
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            if(result.equals("ADMINGREG")){
                return decodedAdminReg(???);
            }
        }
        pushByte(nextByte);
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

    private void bytesToString(){
    String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
    len =0;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }
}
