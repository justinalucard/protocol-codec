package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.core.ProtocolFragment;

import java.nio.charset.StandardCharsets;

/**
 * ASCII字符串编码器
 */
public class AsciiStringObjectCodec extends StringObjectCodec {
    public AsciiStringObjectCodec(byte[] bytes) {
        super(bytes);
    }


    public AsciiStringObjectCodec(String value) {
        this(value, false);
    }

    public AsciiStringObjectCodec(String s, boolean hexString) {
        super(s, StandardCharsets.US_ASCII, hexString);
    }

}
