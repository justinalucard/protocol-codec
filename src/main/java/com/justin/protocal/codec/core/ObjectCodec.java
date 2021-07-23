package com.justin.protocal.codec.core;


import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.naives.IntObjectCodec;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用编解码器，可对任何对象(Object)进行编码。
 * 增加了{@link ObjectCodec#value}和对value的通用编码{@link ObjectCodec#serialize()}解码{@link ObjectCodec#deserialize()}实现
 * @param <T>
 */
public abstract class ObjectCodec<T> extends ProtocolFragment {
    public ObjectCodec(byte[] bytes) {
        this(bytes, null);
    }

    public ObjectCodec(byte[] bytes, Class<?> tClass) {
        super(bytes);
        this.tClass = tClass;
        this.setValue(this.deserialize());
    }

    public ObjectCodec(String hexString) {
        super(hexString);
        this.setValue(this.deserialize());
    }


    public ObjectCodec(T t, Class<?> tClass) {
        super();
        this.tClass = tClass;
        this.setValue(t);
        this.setBytes(this.serialize().getBytes());
    }

    public ObjectCodec(T t) {
        this(t, null);
    }

    public ObjectCodec(Class<?> tClass) {
        this.tClass = tClass;
    }

    protected Class<?> tClass;

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public void setBytes(byte[] bytes) {
        super.setBytes(bytes);
    }

    /**
     * 根据{@link Protocol}注解进行通用解码
     * @return
     */
    @SuppressWarnings("all")
    protected abstract T deserialize();

    /**
     * 解码后调用，一般用于解码后进行校验位比对验证，这里默认空方法，子类需要的时候，override即可
     * @param ret
     */
    protected void afterDeserialize(T ret){

    }

    /**
     * 根据{@link Protocol}注解进行通用编码
     * @return 编码后的结果
     */
    protected abstract ProtocolFragment serialize();


}
