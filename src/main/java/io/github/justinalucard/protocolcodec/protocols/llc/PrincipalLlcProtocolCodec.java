package io.github.justinalucard.protocolcodec.protocols.llc;

import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.utils.CheckSumUtils;

public class PrincipalLlcProtocolCodec<Principal extends PrincipalLlcProtocol<? extends ProtocolFragment>>  extends ProtocolCodec<Principal> {
    public PrincipalLlcProtocolCodec(byte[] bytes) {
        super(bytes);
    }


    public PrincipalLlcProtocolCodec(String hexString) {
        super(hexString);
    }

    public PrincipalLlcProtocolCodec(Principal principal) {
        super(principal);
    }


    @Override
    protected void afterDeserialize(Principal ret) {
        CheckSumUtils.lrcCheckThrowable(ret.getCheck(), ret.getCommand(), ret.getData());
    }
}
