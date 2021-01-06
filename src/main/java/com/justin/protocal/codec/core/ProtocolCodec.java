package com.justin.protocal.codec.core;


import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.naives.IntProtocolCodec;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 通用编解码器，是{@link ProtocolFragment}的高级版本。
 * 增加了{@link ProtocolCodec#value}和对value的通用编码{@link ProtocolCodec#serialize()}解码{@link ProtocolCodec#deserialize()}实现
 * @param <T>
 */
public abstract class ProtocolCodec<T> extends ProtocolFragment {
    public ProtocolCodec(byte[] bytes) {
        this(bytes, null);
    }

    public ProtocolCodec(byte[] bytes, Class<?> tClass) {
        super(bytes);
        this.tClass = tClass;
        this.setValue(this.deserialize());
    }

    public ProtocolCodec(String hexString) {
        super(hexString);
        this.setValue(this.deserialize());
    }


    public ProtocolCodec(T t, Class<?> tClass) {
        super();
        this.tClass = tClass;
        this.setValue(t);
        this.setBytes(this.serialize().getBytes());
    }

    public ProtocolCodec(T t) {
        this(t, null);
    }

    public ProtocolCodec(Class<?> tClass) {
        this.tClass = tClass;
    }

    private Class<?> tClass;

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
    protected T deserialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();

        try {
            T ret = (T) tClass.getConstructor(byte[].class).newInstance((Object) this.getBytes());
            int byteOffset = 0;
            int lengthFieldLength = 0;
            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {

                int currentLength = getDefinedLength(protocolDefine, lengthFieldLength);

                byte[] byteSegments = Arrays.copyOfRange(getBytes(), byteOffset, byteOffset + currentLength);

//                Constructor<?> constructor = protocolDefine.getValue().getType().getConstructor(byteSegments.getClass(), protocolDefine.getValue().getType());
//                if(constructor == null)

                Constructor<?> constructor = protocolDefine.getValue().getType().getConstructor(byteSegments.getClass());

                Object newInstance = constructor.newInstance((Object) byteSegments);
                protocolDefine.getValue().set(ret, newInstance);

                if(protocolDefine.getKey().isLengthField()){
                    lengthFieldLength = ((IntProtocolCodec)newInstance).getValue();
                }

                byteOffset += currentLength;
            }
            afterDeserialize(ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
    protected ProtocolFragment serialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();
        try {
            byte[] result = new byte[]{};
            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {
                ProtocolFragment fragment = (ProtocolFragment) protocolDefine.getValue().get(value);

//                if (fragment == null && protocolDefine.getKey().valueCalculator() != Void.class) {
//                    fragment = new IntProtocolCodec(3);
//                }

                result = concat(result, fixLength(fragment.getBytes(), getDefinedLength(protocolDefine, null)));

            }
            return new ProtocolFragment(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据{@link Protocol#length()} {@link Protocol#serializeLengthDetermination()} {@link Protocol#deserializeLengthDetermination()}
     * 确定并返回对应协议字段的长度
     * @param protocolDefine 协议注解的Map，包含注解与Field的关系
     * @param lengthFieldLength 解码时，根据协议规定，传入Length这个协议字段的值，用以确定，该截取多少字节为Data域，不在反序列化或者还未解析到Length的时候，可以传任意值，都会忽略
     * @return 长度
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws NoSuchMethodException
     */
    private int getDefinedLength(Map.Entry<Protocol, Field> protocolDefine, Integer lengthFieldLength) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        int length = protocolDefine.getKey().length();

        if (protocolDefine.getKey().serializeLengthDetermination() != Void.class) {
            if(value != null) { //serialize
//                Object o = protocolDefine.getKey().serializeLengthCalculator().getConstructor(value.getClass()).newInstance(value);
                Object o = protocolDefine.getKey().serializeLengthDetermination().getConstructors()[0].newInstance(value);

                Method method = o.getClass().getMethod("getLength", (Class<?>[]) null);
                Object invoke = method.invoke(o, (Object[]) null);
                return (int) invoke;
            }else if(protocolDefine.getKey().deserializeLengthDetermination() != Void.class){ //deserialize

                Object o = protocolDefine.getKey().deserializeLengthDetermination().getConstructor().newInstance();
                Method method = o.getClass().getMethod("getLength", int.class);
                Object invoke = method.invoke(o, lengthFieldLength);
                return (int) invoke;
            }
        }

        return length;
    }


    /**
     * 根据传入的类型，反射并返回注解与field的关系Map
     * @return
     */
    protected Map<Protocol, Field> getProtocolDefine() {
        Class<?> clazz = tClass;
        Field[] fields = clazz.getFields();
        fields = concat(fields, clazz.getDeclaredFields());
        Map<Protocol, Field> annotations = new HashMap<>();
        for (Field field : fields) {
            Protocol annotation = field.getAnnotation(Protocol.class);
            if (annotation != null) {
                field.setAccessible(true);
                annotations.put(annotation, field);
            }
        }
        Map<Protocol, Field> result = new LinkedHashMap<>();
        annotations.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey().order()))
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }


    /**
     * 将传入的字节数组，按照指定长度，前方补0
     * @param bytes 需要修正的字节数组
     * @param length 修正后的长度
     * @return
     */
    private byte[] fixLength(byte[] bytes, int length) {
        byte[] ret = new byte[length];
        if (length - bytes.length >= 0)
            System.arraycopy(bytes, 0, ret, length - bytes.length, bytes.length);
        else
            System.arraycopy(bytes, bytes.length - length, ret, 0, length);
        return ret;

    }


    /**
     * 将任意类型的数据进行合并
     * @param first
     * @param second
     * @param <S>
     * @return
     */
    private <S> S[] concat(S[] first, S[] second) {
        S[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 将字节类型的数组进行合并
     * @param first
     * @param second
     * @return
     */
    private  byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

}
