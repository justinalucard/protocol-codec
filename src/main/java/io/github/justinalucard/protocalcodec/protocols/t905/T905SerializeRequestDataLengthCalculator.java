package io.github.justinalucard.protocalcodec.protocols.t905;

import io.github.justinalucard.protocalcodec.protocols.LengthField;
import io.github.justinalucard.protocalcodec.protocols.SerializeLengthDetermination;

/**
 * Request类协议编码时如何确定Data域长度
 */
public class T905SerializeRequestDataLengthCalculator implements SerializeLengthDetermination {
    public int getLength(LengthField lengthField) {
        return lengthField.getLength().getValue() - 8;
    }
}
