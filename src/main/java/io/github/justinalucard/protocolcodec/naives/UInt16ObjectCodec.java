package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;


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
