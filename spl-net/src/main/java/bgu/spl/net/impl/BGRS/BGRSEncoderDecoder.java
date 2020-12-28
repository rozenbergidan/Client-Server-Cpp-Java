package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;


public class BGRSEncoderDecoder implements MessageEncoderDecoder<String[]> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;


    @Override
    public String[] decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(String[] message) {
        return new byte[0];
    }

    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

}
