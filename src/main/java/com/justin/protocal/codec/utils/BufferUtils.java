package com.justin.protocal.codec.utils;

import java.util.Arrays;

public class BufferUtils {
    /**
     * 将传入的字节数组，按照指定长度，前方补0
     * @param bytes 需要修正的字节数组
     * @param length 修正后的长度
     * @return
     */
    public static byte[] fixLengthPaddingLeft(byte[] bytes, int length) {
        byte[] ret = new byte[length];
        if (length - bytes.length >= 0)
            System.arraycopy(bytes, 0, ret, length - bytes.length, bytes.length);
        else
            System.arraycopy(bytes, bytes.length - length, ret, 0, length);
        return ret;

    }



    /**
     * 将任意类型的数据进行合并
     * @param first
     * @param second
     * @param <S>
     * @return
     */
    public static <S> S[] concat(S[] first, S[] second) {
        S[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 将字节类型的数组进行合并
     * @param first
     * @param second
     * @return
     */
    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }
}
