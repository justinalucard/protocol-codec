package com.justin.protocal.codec.core;


import java.util.Arrays;
import java.util.Objects;

/**
 * 万物皆object，在协议解析框架里，万物皆ProtocolFragment。
 * 提供了字节流与十六进制字符串的双向绑定转换功能。
 */
public class ProtocolFragment {
    private byte[] bytes;
    private String hexString;


    public ProtocolFragment(byte[] bytes) {
        this.setBytes(bytes);
    }

    public ProtocolFragment(String hexString) {
        this.setHexString(hexString);
    }

    public ProtocolFragment() {

    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.hexString = ProtocolFragment.bytesToHex(bytes);
    }

    public String getHexString() {
        return hexString.toUpperCase();
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
        this.bytes = ProtocolFragment.hexToByteArray(hexString);
    }

    private static String bytesToHex(byte[] bytes) {
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

    /**
     * 打印美观化
     * @return
     */
    @Override
    public String toString() {
        return "ProtocolFragment{" +
                "bytes=" + Arrays.toString(bytes) +
                ", hexString='" + hexString + '\'' +
                '}';
    }

    /**
     * 修改equals方法，只要byte[]和十六进制字符串一致，即认为相等
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolFragment that = (ProtocolFragment) o;
        return Arrays.equals(bytes, that.bytes) &&
                Objects.equals(hexString, that.hexString);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(hexString);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
