package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.core.ProtocolFragment;

import java.nio.charset.Charset;

/**
 * BCD(8421)编码器
 */
public class Bcd8421ObjectCodec extends ObjectCodec<String> {
    public Bcd8421ObjectCodec(byte[] bytes) {
        super(bytes, String.class);
    }


    public Bcd8421ObjectCodec(String value) {
        this(value, false);
    }

    public Bcd8421ObjectCodec(String bcd, boolean hexString) {
        super();
        this.tClass = String.class;
        fromValue(bcd);
        if(hexString){
            this.setHexString(bcd);
        }
        else{
            this.setValue(bcd);
        }
    }


    @Override
    protected String deserialize() {
        return getHexString();
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment(getValue());
    }
}
