package io.github.justinalucard.protocolcodec.naives;

import java.nio.charset.Charset;
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

    @Override
    protected Charset getCharset() {
        return StandardCharsets.US_ASCII;
    }

    public AsciiStringObjectCodec(String s, boolean hexString) {
        super(s, hexString);
    }

}
