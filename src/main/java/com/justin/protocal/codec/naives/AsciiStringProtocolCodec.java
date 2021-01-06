package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.core.ProtocolFragment;

import java.nio.charset.StandardCharsets;

/**
 * ASCII字符串编码器
 */
public class AsciiStringProtocolCodec extends ProtocolCodec<String> {
    public AsciiStringProtocolCodec(byte[] bytes) {
        super(bytes);
    }

    /**
     * 隐藏了hexString的构造函数，对于字符串类的初始化直接使用字符串本身的值（而非hexString）进行构造。
     * 如果调试协议，可以使用{@link AsciiStringProtocolCodec#AsciiStringProtocolCodec(byte[])}进行测试
     * @param value
     */
    public AsciiStringProtocolCodec(String value) {
        this(value, false);
    }

    public AsciiStringProtocolCodec(String s, boolean hexString) {
        super(s, String.class);
        if(hexString){
            this.setHexString(s);
        }
        else{
            this.setValue(s);
        }
    }

    @Override
    protected String deserialize() {
        return new String(this.getBytes(), StandardCharsets.US_ASCII);
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment(this.getValue().getBytes(StandardCharsets.US_ASCII));
    }
}
