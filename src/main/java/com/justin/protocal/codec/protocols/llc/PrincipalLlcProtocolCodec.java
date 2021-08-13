package com.justin.protocal.codec.protocols.llc;

import com.justin.protocal.codec.core.ProtocolCodec;
import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.protocols.CheckSumVerifier;

public class PrincipalLlcProtocolCodec<Principal extends PrincipalLlcProtocol<? extends ProtocolFragment>>  extends ProtocolCodec<Principal>{
    public PrincipalLlcProtocolCodec(byte[] bytes) {
        this(bytes, null);
    }

    public PrincipalLlcProtocolCodec(byte[] bytes, Class<?> tClass) {
        super(bytes, tClass);
    }

    public PrincipalLlcProtocolCodec(String hexString, Class<?> tClass) {
        super(hexString, tClass);
    }

    public PrincipalLlcProtocolCodec(Principal principal, Class<?> tClass) {
        super(principal, tClass);
    }

    public PrincipalLlcProtocolCodec(Principal principal) {
        super(principal, principal.getClass());
    }

    public PrincipalLlcProtocolCodec(Class<?> tClass) {
        super(tClass);
    }

    @Override
    protected void afterDeserialize(Principal ret) {
        CheckSumVerifier.check(ret.getCheck(), ret.getCommand(), ret.getData());
    }
}
