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
     * @param s 原始的字符串
     */
    public StringObjectCodec(String s, boolean hexString) {
        super();

        if(hexString){
            fromHexString(s);
        }
        else{
            fromValue(s);
        }
    }

    protected Charset getCharset(){
        return Charset.defaultCharset();
    }

    @Override
    protected String deserialize() {
        return new String(this.getBytes(), getCharset());
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment(this.getValue().getBytes(getCharset()));
    }
}
