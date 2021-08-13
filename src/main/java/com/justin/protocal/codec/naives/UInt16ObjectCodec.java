package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.exceptions.SerializationException;
import com.justin.protocal.codec.utils.ConverterUtils;


/**
 * (WORD)UInt16无符号整数编解码器
 */
public class UInt16ObjectCodec extends IntObjectCodec {



    public UInt16ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public UInt16ObjectCodec(String hexString) {
        super(hexString);
    }

    public UInt16ObjectCodec(Integer integer) {
        super(integer);
    }


    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue().shortValue()));
        }
        return null;
    }

    @Override
    protected Integer deserialize() {
        return ConverterUtils.getUInt16(getBytes());
    }
}
