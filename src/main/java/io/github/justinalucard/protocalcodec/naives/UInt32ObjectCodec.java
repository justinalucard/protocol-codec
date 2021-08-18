package io.github.justinalucard.protocalcodec.naives;

import io.github.justinalucard.protocalcodec.core.ProtocolFragment;
import io.github.justinalucard.protocalcodec.utils.ConverterUtils;


/**
 * (DWORD)UInt32无符号整数编解码器
 */
public class UInt32ObjectCodec extends LongObjectCodec {



    public UInt32ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public UInt32ObjectCodec(String hexString) {
        super(hexString);
    }

    public UInt32ObjectCodec(Long value) {
        super(value);
    }


    @Override
    protected ProtocolFragment serialize() {
        if(this.getValue() != null){
            return new ProtocolFragment(ConverterUtils.getBytes(this.getValue().intValue()));
        }
        return null;
    }

    @Override
    protected Long deserialize() {
        return ConverterUtils.getUInt32(getBytes());
    }
}
