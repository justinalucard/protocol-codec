package com.justin.protocal.codec.protocols.llc;

import com.justin.protocal.codec.protocols.LengthField;
import com.justin.protocal.codec.protocols.SerializeLengthDetermination;

/**
 * Request类协议编码时如何确定Data域长度
 */
public class LlcSerializeRequestDataLengthCalculator implements SerializeLengthDetermination {
    public int getLength(LengthField lengthField) {
        return lengthField.getLength().getValue() - 1;
    }
}
