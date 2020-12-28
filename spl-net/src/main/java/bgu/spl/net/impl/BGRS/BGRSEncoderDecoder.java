package bgu.spl.net.impl.BGRS;

import bgu.spl.net.api.MessageEncoderDecoder;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    @Override
    public String decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(String message) {
        
        return new byte[0];
    }
}
