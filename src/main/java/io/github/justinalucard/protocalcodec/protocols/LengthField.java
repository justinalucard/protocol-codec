package io.github.justinalucard.protocalcodec.protocols;

import io.github.justinalucard.protocalcodec.annotations.Protocol;
import io.github.justinalucard.protocalcodec.naives.IntObjectCodec;

/**
 * 将Length协议字段抽取出接口，方便{@link Protocol#serializeLengthDetermination()}具体类的使用
 */
public interface LengthField {
    IntObjectCodec getLength();
}
