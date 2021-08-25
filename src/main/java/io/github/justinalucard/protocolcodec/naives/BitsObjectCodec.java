package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.BitsUtils;

public class BitsObjectCodec extends ObjectCodec<String> {

    public BitsObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public BitsObjectCodec(String s, boolean hexString) {
        super();
        if(hexString){
            fromHexString(s);
        }
        else{
            fromValue(s);
        }
    }

    public BitsObjectCodec(String value) {
        this(value, false);
    }

    @Override
    protected String deserialize() {
        return BitsUtils.toBinaryString(getBytes());
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment(BitsUtils.toByteArray(getValue()));
    }

}
