package io.github.justinalucard.protocalcodec.naives;

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
