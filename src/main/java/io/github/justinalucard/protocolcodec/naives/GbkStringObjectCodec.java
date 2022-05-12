package io.github.justinalucard.protocolcodec.naives;

import java.nio.charset.Charset;

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
        super(s, hexString);
    }

    @Override
    protected Charset getCharset() {
        return Charset.forName("GBK");
    }
}
