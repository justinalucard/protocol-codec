package com.justin.protocal.codec.utils;

import com.justin.protocal.codec.exceptions.IllegalProtocolException;

public class ConverterUtils {
    /**

     * int到byte[] 由高位到低位

     * @param i 需要转换为byte数组的整行值。

     * @return byte数组 */

    public static byte[] intToByteArray(int i) {

        byte[] result = new byte[4];

        result[0] = (byte)((i >> 24) & 0xFF);

        result[1] = (byte)((i >> 16) & 0xFF);

        result[2] = (byte)((i >> 8) & 0xFF);

        result[3] = (byte)(i & 0xFF);

        return result;

    }

    /**

     * byte[]转int

     * @param bytes 需要转换成int的数组

     * @return int值

     */

    public static int byteArrayToInt(byte[] bytes) {

        if(bytes.length > 4 || bytes.length < 1){
            throw new IllegalProtocolException("整数的字节位数在1-4位之间");
        }

        int value=0;

        for(int i = 0; i < bytes.length; i++) {

            int shift= (bytes.length - 1 - i) * 8;

            value +=(bytes[i] & 0xFF) << shift;

        }

        return value;

    }
}
