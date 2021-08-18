package io.github.justinalucard.protocalcodec.protocols;

import io.github.justinalucard.protocalcodec.core.ProtocolFragment;
import io.github.justinalucard.protocalcodec.exceptions.IllegalProtocolException;
import io.github.justinalucard.protocalcodec.utils.CheckSumUtils;

/**
 * 校验位检查是否正确
 */
public class CheckSumVerifier {
    public static void check(ProtocolFragment check, ProtocolFragment... protocolFragments){
        if(!CheckSumUtils.lrc(protocolFragments).equals(check)){
            throw new IllegalProtocolException("协议校验不通过");
        }
    }
}
