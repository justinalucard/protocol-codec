package com.justin.protocal.codec.naives;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * GBK字符串编码器
 */
public class GbkStringObjectCodec extends StringObjectCodec {
    public GbkStringObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public GbkStringObjectCodec(String value) {
        this(value, false);
    }

    public GbkStringObjectCodec(String s, boolean hexString) {
        super(s, Charset.forName("GBK"), hexString);
    }

}
