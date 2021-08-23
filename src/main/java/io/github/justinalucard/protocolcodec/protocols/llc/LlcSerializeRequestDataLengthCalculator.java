package io.github.justinalucard.protocolcodec.protocols.llc;

import io.github.justinalucard.protocolcodec.protocols.LengthField;
import io.github.justinalucard.protocolcodec.protocols.SerializeLengthDetermination;

/**
 * Request类协议编码时如何确定Data域长度
 */
public class LlcSerializeRequestDataLengthCalculator implements SerializeLengthDetermination {
    public int getLength(LengthField lengthField) {
        return lengthField.getLength().getValue() - 1;
    }
}
