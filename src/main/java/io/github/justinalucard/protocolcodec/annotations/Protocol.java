package io.github.justinalucard.protocolcodec.annotations;

import io.github.justinalucard.protocolcodec.protocols.DeserializeLengthDetermination;
import io.github.justinalucard.protocolcodec.protocols.SerializeLengthDetermination;

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
     * @return 顺序
     */
    int order() default 0;
    /**
     * 该协议字段的长度
     * @return 占用字节的长度
     */
    int length() default 0;
    /**
     * 是否将剩余的字节全部解析到该协议字段
     * @return 是否剩余的字节全部打包解析
     */
    boolean isRestOf() default false;
    /**
     * 如果当前协议字段是长度字段，必须将该值设置成true
     * @return 该字段是否为长度定义区
     */
    boolean isLengthField() default false;

    /**
     * 由于协议中Data部分的长度，一般都无法确定。序列化时用来确定这不确定的data域的长度
     * @return 序列化长度计算器
     */
    Class<? extends SerializeLengthDetermination> serializeLengthDetermination() default SerializeLengthDetermination.class;

    /**
     * 与{@link Protocol#serializeLengthDetermination()}同理
     * @return 反序列化长度计算器
     */
    Class<? extends DeserializeLengthDetermination> deserializeLengthDetermination() default DeserializeLengthDetermination.class;

    /**
     * 标识是否为消息体数据部分，一般数据部分使用泛型
     * @return 是否为消息体数据部分
     */
    boolean isGenericData() default false;

    /**
     * 是否是分支节点的根节点，当该值为true，则代表，后续遇到{@link #isBranchNode()}为true节点，将根据当前字段的值判断是否与{@link #branchNo()}的值一直才进行解析
     * @return 是否是分支节点的决定字段
     */
    boolean isBranchRoot() default false;

    /**
     * 是否为分支的子节点，当为true时，将根据上一个有{@link #isBranchRoot()}注解的字段值进行与{@link #branchNo()}的比对，相等的才进行解析
     * @return 是否为分支子节点
     */
    boolean isBranchNode() default false;

    /**
     * 分支编号，该值与上一个{@link #isBranchRoot()}注解标记的字段值的十六进制结果进行比对，一致的将进行解析
     * @return 分支编号
     */
    String branchNo() default "";

    /**
     * （动态长度）长度由结束符决定，当遇到该结束符的时候，认为读取完整。
     * @return 长度结束符
     */
    String lengthByEndWith() default "";
}
