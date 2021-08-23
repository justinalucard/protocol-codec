package io.github.justinalucard.protocolcodec.utils;

import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.exceptions.IllegalProtocolException;

public class CheckSumUtils {
    /**
     * 简单的校验位算法
     * @param protocolFragments 所有需要进行lrc算法的因子
     * @return lrc算法计算结果
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
