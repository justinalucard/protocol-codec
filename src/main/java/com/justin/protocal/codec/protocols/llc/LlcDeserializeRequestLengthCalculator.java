package com.justin.protocal.codec.protocols.llc;

import com.justin.protocal.codec.protocols.DeserializeLengthDetermination;

/**
 * Request类协议解码时如何确定Data域长度
 */
public class LlcDeserializeRequestLengthCalculator implements DeserializeLengthDetermination {
    public int getLength(int length) {
        return length - 1;
    }
}
