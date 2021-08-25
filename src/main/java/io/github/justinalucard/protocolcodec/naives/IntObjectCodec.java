package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;


import java.util.Arrays;


/**
 * 高位在前的整数编解码器
 */
public class IntObjectCodec extends ObjectCodec<Integer> {



    public IntObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public IntObjectCodec(String hexString) {
        super(hexString);
    }

    public IntObjectCodec(Integer integer) {
        super(integer);
    }

    @Override
    protected Integer deserialize() {
        byte[] bytes = getBytes();
        return ConverterUtils.getInt(bytes);
    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue()));
        }
        return null;
    }

}
