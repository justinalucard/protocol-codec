package io.github.justinalucard.protocalcodec.annotations;

import io.github.justinalucard.protocalcodec.protocols.DeserializeLengthDetermination;
import io.github.justinalucard.protocalcodec.protocols.SerializeLengthDetermination;

import java.lang.annotation.*;

/**
 * 协议标记用的注解，每个协议组成部分，都必须使用该注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Protocol {
    /**
     * 协议字段所处的位置，越小的在越前面
     * @return
     */
    int order() default 0;
    /**
     * 该协议字段的长度
     * @return
     */
    int length() default 0;

    /**
     * 如果当前协议字段是长度字段，必须将该值设置成true
     * @return
     */
    boolean isLengthField() default false;

    /**
     * 由于协议中Data部分的长度，一般都无法确定。序列化时用来确定这不确定的data域的长度
     * @return
     */
    Class<? extends SerializeLengthDetermination> serializeLengthDetermination() default SerializeLengthDetermination.class;

    /**
     * 与{@link Protocol#serializeLengthDetermination()}同理
     * @return
     */
    Class<? extends DeserializeLengthDetermination> deserializeLengthDetermination() default DeserializeLengthDetermination.class;

    boolean isGenericData() default false;

}
