package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.utils.ConverterUtils;

import java.util.Arrays;


/**
 * Long长整数编解码器
 */
public class LongObjectCodec extends ObjectCodec<Long> {



    public LongObjectCodec(byte[] bytes) {
        super(bytes, Long.class);
    }

    public LongObjectCodec(String hexString) {
        super(hexString, Long.class);
    }

    public LongObjectCodec(Long value) {
        super(value, Long.class);
    }

    @Override
    protected Long deserialize() {
        byte[] bytes = getBytes();
        return ConverterUtils.getLong(bytes);
    }

    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue()));
        }
        return null;
    }


}
