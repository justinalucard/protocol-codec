package com.justin.protocal.codec.protocols;

/**
 * Request类协议编码时如何确定Data域长度
 */
public class SerializeRequestDataLengthCalculator {
    LengthField lengthField;

    public SerializeRequestDataLengthCalculator(LengthField lengthField) {
        this.lengthField = lengthField;
    }

    public int getLength() {
        return lengthField.getLength().getValue() - 1;
    }
}
