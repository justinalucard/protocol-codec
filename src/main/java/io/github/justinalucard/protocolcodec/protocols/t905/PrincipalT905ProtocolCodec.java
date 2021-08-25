package io.github.justinalucard.protocolcodec.protocols.t905;

import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.protocols.CheckSumVerifier;

public class PrincipalT905ProtocolCodec<Principal extends PrincipalT905Protocol<? extends ProtocolFragment>>  extends ProtocolCodec<Principal> {

    public PrincipalT905ProtocolCodec(byte[] bytes) {
        super(bytes);
    }

    public PrincipalT905ProtocolCodec(String hexString) {
        super(hexString);
    }

    public PrincipalT905ProtocolCodec(Principal principal, Class<?> tClass) {
        super(principal);
    }

    public PrincipalT905ProtocolCodec(Principal principal) {
        super(principal);
    }



    @Override
    protected void afterDeserialize(Principal ret) {
        CheckSumVerifier.check(ret.getCheck(), ret.getIsuId(), ret.getMessageSerialNo(), ret.getData());
    }
}
