package com.justin.protocal.codec.naives;

import com.justin.protocal.codec.core.ObjectCodec;
import com.justin.protocal.codec.core.ProtocolFragment;

/**
 * 直接保留字节流，不做转换，一般用于转换类型为止的情况，类似于Object类型
 */
public class ByteArrayObjectCodec extends ObjectCodec<byte[]> {

    public ByteArrayObjectCodec(byte[] bytes) {
        fromBytes(bytes);
    }

    public ByteArrayObjectCodec(String hexString) {
        super(hexString, byte[].class);
    }

    @Override
    protected byte[] deserialize() {
        return getValue();
    }

    @Override
    protected ProtocolFragment serialize() {
       return new ProtocolFragment(getBytes());
    }

    @Override
    public byte[] getValue() {
        return getBytes();
    }
}
