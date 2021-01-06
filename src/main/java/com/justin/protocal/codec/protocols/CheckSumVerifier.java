package com.justin.protocal.codec.protocols;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.exceptions.IllegalProtocolException;
import com.justin.protocal.codec.utils.CheckSumUtils;

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
