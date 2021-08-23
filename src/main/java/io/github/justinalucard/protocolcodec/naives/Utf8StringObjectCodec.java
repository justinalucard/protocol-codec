package io.github.justinalucard.protocolcodec.naives;

import java.nio.charset.StandardCharsets;

/**
 * UTF-8字符串编码器
 */
public class Utf8StringObjectCodec extends StringObjectCodec {
    public Utf8StringObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public Utf8StringObjectCodec(String value) {
        this(value, false);
    }

    public Utf8StringObjectCodec(String s, boolean hexString) {
        super(s, StandardCharsets.UTF_8, hexString);
    }

}
