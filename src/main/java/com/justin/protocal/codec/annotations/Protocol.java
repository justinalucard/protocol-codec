package com.justin.protocal.codec.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
    Class<?> serializeLengthDetermination() default Void.class;

    /**
     * 与{@link Protocol#serializeLengthDetermination()}同理
     * @return
     */
    Class<?> deserializeLengthDetermination() default Void.class;
}
