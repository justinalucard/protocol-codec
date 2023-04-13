package io.github.justinalucard.protocolcodec.utils;

import io.github.justinalucard.protocolcodec.core.ProtocolFragment;
import io.github.justinalucard.protocolcodec.exceptions.IllegalProtocolException;

public class CheckSumUtils {
    /**
     * LRC校验位算法
     *
     * @param protocolFragments 所有需要进行lrc算法的因子
     * @return lrc算法计算结果
     */
    public static ProtocolFragment lrc(ProtocolFragment... protocolFragments) {
        if (protocolFragments != null && protocolFragments.length > 0) {
            int sum = 0;
            for (ProtocolFragment protocolFragment : protocolFragments) {
                for (byte aByte : protocolFragment.getBytes()) {
                    sum += aByte;
                }
            }
            return new ProtocolFragment(new byte[]{(byte) (sum & 0xFF)});
        }
        throw new IllegalProtocolException("未提供数据进行校验位生成");
    }

    /**
     * LRC校验检查
     *
     * @param check             待检查的校验位
     * @param protocolFragments 参与校验计算的因子
     */
    public static void lrcCheckThrowable(ProtocolFragment check, ProtocolFragment... protocolFragments) {
        if (!lrc(protocolFragments).equals(check)) {
            throw new IllegalProtocolException("协议校验不通过");
        }
    }


    /**
     * XOR异或和校验位算法
     *
     * @param protocolFragments 所有需要进行异或和校验算法的因子
     * @return 异或和算法计算结果
     */
    public static ProtocolFragment xor(ProtocolFragment... protocolFragments) {
        if (protocolFragments != null && protocolFragments.length > 0) {
            byte b = 0;
            for (ProtocolFragment protocolFragment : protocolFragments) {
                for (byte aByte : protocolFragment.getBytes()) {
                    b ^= aByte;
                }
            }
            return new ProtocolFragment(new byte[]{b});

        }
        throw new IllegalProtocolException("未提供数据进行校验位生成");
    }

    /**
     * XOR校验检查
     *
     * @param check             待检查的校验位
     * @param protocolFragments 参与校验计算的因子
     */
    public static void xorCheckThrowable(ProtocolFragment check, ProtocolFragment... protocolFragments) {
        if (!xor(protocolFragments).equals(check)) {
            throw new IllegalProtocolException("协议校验不通过");
        }
    }
}
