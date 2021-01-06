package com.justin.protocal.codec.protocols;

/**
 * Request类协议解码时如何确定Data域长度
 */
public class DeserializeRequestLengthCalculator {
    public int getLength(int length) {
        return length - 1;
    }
}
