package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.utils.ConverterUtils;


/**
 * (BYTE)UInt8无符号整数编解码器
 */
public class UInt8ObjectCodec extends IntObjectCodec {



    public UInt8ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public UInt8ObjectCodec(String hexString) {
        super(hexString);
    }

    public UInt8ObjectCodec(Integer integer) {
        super(integer);
    }


    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue().byteValue()));
        }
        return null;
    }

    @Override
    protected Integer deserialize() {
        return ConverterUtils.getUInt8(getBytes());
    }
}
