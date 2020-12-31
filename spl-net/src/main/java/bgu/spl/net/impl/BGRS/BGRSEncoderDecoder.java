package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;


public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    private String decodedMassege;

    private boolean desided;

    @Override
    public String decodeNextByte(byte nextByte) {

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
        byte[] srt = new byte[]{bytes[0],bytes[1]};
    String result = bytesToShort(srt) + new String(bytes, 2, len, StandardCharsets.UTF_8);

    len =0;
    }
    private abstract static class decodeHelper{

    }

}
