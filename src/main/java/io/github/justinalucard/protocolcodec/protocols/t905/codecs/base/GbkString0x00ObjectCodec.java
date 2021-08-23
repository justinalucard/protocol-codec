package io.github.justinalucard.protocolcodec.protocols.t905.codecs.base;

import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.naives.GbkStringObjectCodec;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * GBK结尾补0x00字符串编码器
 */
public class GbkString0x00ObjectCodec extends GbkStringObjectCodec {
    public GbkString0x00ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public GbkString0x00ObjectCodec(String value) {
        this(value, false);
    }

    public GbkString0x00ObjectCodec(String s, boolean hexString) {
        super(s, hexString);
    }

    @Override
    protected String deserialize() {
        byte[] newBytes = this.getBytes();
        if(this.getBytes().length > 0) {
            newBytes = Arrays.copyOf(this.getBytes(), this.getBytes().length - 1);
        }
        return new String(newBytes, Charset.forName("GBK"));
    }

    @Override
    protected ProtocolFragment serialize() {
        byte[] bytes = this.getValue().getBytes(Charset.forName("GBK"));
        bytes = Arrays.copyOf(bytes, bytes.length + 1);
        return new ProtocolFragment(bytes);
    }


}
