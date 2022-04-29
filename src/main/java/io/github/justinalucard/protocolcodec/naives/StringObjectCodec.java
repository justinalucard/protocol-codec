package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;

import java.nio.charset.Charset;

/**
 * 字符串编码器
 */
public class StringObjectCodec extends ObjectCodec<String> {
    public StringObjectCodec(byte[] bytes) {
        super(bytes);
    }

    /**
     * 隐藏了hexString的构造函数，对于字符串类的初始化直接使用字符串本身的值（而非hexString）进行构造。
     * 如果调试协议，可以使用{@link StringObjectCodec#StringObjectCodec(byte[])}进行测试
     * @param value 原始的字符串
     */
    public StringObjectCodec(String value, Charset charset) {
        this(value, charset,false);
    }

    public StringObjectCodec(String s, Charset charset, boolean hexString) {
        super();
        this.charset = charset;
        fromValue(s);
        if(hexString){
            this.setHexString(s);
        }
        else{
            this.setValue(s);
        }
    }

    Charset charset;

    @Override
    protected String deserialize() {
        return new String(this.getBytes(), charset);
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment(this.getValue().getBytes(charset));
    }
}
