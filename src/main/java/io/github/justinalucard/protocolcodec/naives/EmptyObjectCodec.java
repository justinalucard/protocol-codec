package io.github.justinalucard.protocolcodec.naives;

import io.github.justinalucard.protocolcodec.core.ObjectCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;

public class EmptyObjectCodec extends ObjectCodec<Object> {

    public EmptyObjectCodec() {
    }

    public EmptyObjectCodec(byte[] bytes) {
        super(bytes, byte[].class);
    }

    public EmptyObjectCodec(String hexString) {
        super(hexString, byte[].class);
    }

    @Override
    protected byte[] deserialize() {
        return new byte[0];
    }

    @Override
    protected ProtocolFragment serialize() {
        return new ProtocolFragment();
    }
}
