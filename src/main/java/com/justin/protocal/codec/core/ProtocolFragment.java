package com.justin.protocal.codec.core;


import com.justin.protocal.codec.utils.BufferUtils;

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
        fromBytes(bytes);
    }

    public ProtocolFragment(String hexString) {
        fromHexString(hexString);
    }

    public ProtocolFragment() {

    }

    protected void fromBytes(byte[] bytes) {
        this.setBytes(bytes);
    }

    protected void fromHexString(String hexString){
        this.setHexString(hexString);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.hexString = BufferUtils.bytesToHex(bytes);
    }

    public String getHexString() {
        return hexString.toUpperCase();
    }

    public void setHexString(String hexString) {
        this.hexString = hexString;
        this.bytes = BufferUtils.hexToByteArray(hexString);
    }


    /**
     * 打印美观化
     * @return 输出协议内容
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
     * @param o 需要比较的对象
     * @return 是否相等
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
