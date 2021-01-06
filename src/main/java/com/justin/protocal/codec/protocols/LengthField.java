package com.justin.protocal.codec.protocols;

import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.naives.IntProtocolCodec;

/**
 * 将Length协议字段抽取出接口，方便{@link Protocol#serializeLengthDetermination()}具体类的使用
 */
public interface LengthField {
    IntProtocolCodec getLength();
}
