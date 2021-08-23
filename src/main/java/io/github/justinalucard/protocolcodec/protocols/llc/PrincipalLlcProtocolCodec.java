package io.github.justinalucard.protocolcodec.protocols.llc;

import io.github.justinalucard.protocolcodec.core.ProtocolCodec;
import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.protocols.CheckSumVerifier;

public class PrincipalLlcProtocolCodec<Principal extends PrincipalLlcProtocol<? extends ProtocolFragment>>  extends ProtocolCodec<Principal> {
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