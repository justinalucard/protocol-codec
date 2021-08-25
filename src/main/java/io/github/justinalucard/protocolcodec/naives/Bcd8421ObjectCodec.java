package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;

/**
 * BCD(8421)编码器
 */
public class Bcd8421ObjectCodec extends ObjectCodec<String> {
    public Bcd8421ObjectCodec(byte[] bytes) {
        super(bytes);
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
