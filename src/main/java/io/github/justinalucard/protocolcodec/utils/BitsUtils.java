package io.github.justinalucard.protocolcodec.utils;

public class BitsUtils {

    public static byte[] toByteArray(String binary){
        StringBuilder sb = new StringBuilder(binary);

        int byteLength = binary.length() / 8;
        int paddingLeft = binary.length() % 8;

        if(paddingLeft > 0){
            byteLength += 1;
            sb.insert(0, new String(new char[8 - paddingLeft]).replace("\0", "0"));
        }
        byte[] ret = new byte[byteLength];

        for (int i = 0; i < byteLength; i++) {
            ret[i] = bitToByte(sb.substring(i * 8, (i + 1) * 8));
        }

        return ret;
    }

    public static String toBinaryString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(byteToBits(aByte));
        }
        return sb.toString();
    }

    public static String toBinaryString(String hexString){
        return toBinaryString(BufferUtils.hexToByteArray(hexString));
    }

    public static String byteToBits(byte b) {
        return "" +(byte)((b >> 7) & 0x1) +
                (byte)((b >> 6) & 0x1) +
                (byte)((b >> 5) & 0x1) +
                (byte)((b >> 4) & 0x1) +
                (byte)((b >> 3) & 0x1) +
                (byte)((b >> 2) & 0x1) +
                (byte)((b >> 1) & 0x1) +
                (byte)((b) & 0x1);
    }

    public static byte bitToByte(String binary) {
        int re, len;
        if (null == binary) {
            return 0;
        }
        len = binary.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (binary.charAt(0) == '0') {// 正数
                re = Integer.parseInt(binary, 2);
            } else {// 负数
                re = Integer.parseInt(binary, 2) - 256;
            }
        } else {//4 bit处理
            re = Integer.parseInt(binary, 2);
        }
        return (byte) re;
    }
}
