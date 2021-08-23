package io.github.justinalucard.protocolcodec.protocols.t905;

import io.github.justinalucard.protocolcodec.protocols.DeserializeLengthDetermination;

/**
 * Request类协议解码时如何确定Data域长度
 */
public class T905DeserializeRequestLengthCalculator implements DeserializeLengthDetermination {
    public int getLength(int length) {
        return length - 8;
    }
}
