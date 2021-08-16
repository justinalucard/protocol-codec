package com.justin.protocal.codec.protocols.t905;

import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.protocols.CheckSumVerifier;

public class PrincipalT905ProtocolCodec<Principal extends PrincipalT905Protocol<? extends ProtocolFragment>>  extends ProtocolCodec<Principal>{
    public PrincipalT905ProtocolCodec(byte[] bytes) {
        this(bytes, null);
    }

    public PrincipalT905ProtocolCodec(byte[] bytes, Class<?> tClass) {
        super(bytes, tClass);
    }

    public PrincipalT905ProtocolCodec(String hexString, Class<?> tClass) {
        super(hexString, tClass);
    }

    public PrincipalT905ProtocolCodec(Principal principal, Class<?> tClass) {
        super(principal, tClass);
    }

    public PrincipalT905ProtocolCodec(Principal principal) {
        super(principal, principal.getClass());
    }

    public PrincipalT905ProtocolCodec(Class<?> tClass) {
        super(tClass);
    }

    @Override
    protected void afterDeserialize(Principal ret) {
        CheckSumVerifier.check(ret.getCheck(), ret.getIsuId(), ret.getMessageSerialNo(), ret.getData());
    }
}
