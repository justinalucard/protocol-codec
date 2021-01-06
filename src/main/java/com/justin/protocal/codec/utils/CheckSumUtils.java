package com.justin.protocal.codec.utils;

import com.justin.protocal.codec.core.ProtocolFragment;
import com.justin.protocal.codec.exceptions.IllegalProtocolException;

public class CheckSumUtils {
    /**
     * 简单的校验位算法
     * @param protocolFragments
     * @return
     */
    public static ProtocolFragment lrc(ProtocolFragment... protocolFragments){
        if(protocolFragments != null && protocolFragments.length > 0){
            int sum = 0;
            for (ProtocolFragment protocolFragment : protocolFragments){
                for (byte aByte : protocolFragment.getBytes()) {
                    sum += aByte;
                }
            }
            return new ProtocolFragment(new byte[]{(byte)(sum & 0xFF)});
        }
        throw new IllegalProtocolException("未提供数据进行校验位生成");
    }
}
