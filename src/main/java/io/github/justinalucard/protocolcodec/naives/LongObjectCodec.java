package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.ConverterUtils;


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
