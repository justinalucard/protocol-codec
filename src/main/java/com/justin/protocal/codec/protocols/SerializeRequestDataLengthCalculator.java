package com.justin.protocal.codec.protocols;

/**
 * Request类协议编码时如何确定Data域长度
 */
public class SerializeRequestDataLengthCalculator implements SerializeLengthDetermination {
    public int getLength(LengthField lengthField) {
        return lengthField.getLength().getValue() - 1;
    }
}
