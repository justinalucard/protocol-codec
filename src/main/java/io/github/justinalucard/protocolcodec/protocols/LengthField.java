package io.github.justinalucard.protocolcodec.protocols;

import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.naives.IntObjectCodec;

/**
 * 将Length协议字段抽取出接口，方便{@link Protocol#serializeLengthDetermination()}具体类的使用
 */
public interface LengthField {
    IntObjectCodec getLength();
}
