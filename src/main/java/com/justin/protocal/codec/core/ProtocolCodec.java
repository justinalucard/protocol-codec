package com.justin.protocal.codec.core;


import com.justin.protocal.codec.annotations.Protocol;
import com.justin.protocal.codec.naives.IntObjectCodec;
import com.justin.protocal.codec.protocols.DeserializeLengthDetermination;
import com.justin.protocal.codec.protocols.LengthField;
import com.justin.protocal.codec.protocols.SerializeLengthDetermination;
import com.justin.protocal.codec.protocols.lamp.LampRequestData;
import com.justin.protocal.codec.protocols.lamp.LampRequestDataCodec;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * 通用编解码器，是{@link ProtocolFragment}的高级版本。
 * 增加了{@link ProtocolCodec#value}和对value的通用编码{@link ProtocolCodec#serialize()}解码{@link ProtocolCodec#deserialize()}实现
 * @param <T>
 */
public abstract class ProtocolCodec<ProtocolData extends ProtocolFragment> extends ObjectCodec<ProtocolData> {
    public ProtocolCodec(byte[] bytes) {
        this(bytes, null);
    }

    public ProtocolCodec(byte[] bytes, Class<?> tClass) {
        super(bytes, tClass);
    }

    public ProtocolCodec(String hexString) {
        super(hexString);
    }


    public ProtocolCodec(ProtocolData protocolData, Class<?> tClass) {
        super(protocolData, tClass);
        this.getValue().setBytes(this.getBytes());
    }

    public ProtocolCodec(ProtocolData protocolData) {
        this(protocolData, null);
    }

    public ProtocolCodec(Class<?> tClass) {
        super(tClass);
    }

//
//    private ProtocolData value;
//
//    public ProtocolData getValue() {
//        return value;
//    }
//
//    public void setValue(ProtocolData value) {
//        this.value = value;
//    }
//
//    @Override
//    public void setBytes(byte[] bytes) {
//        super.setBytes(bytes);
//    }

    /**
     * 根据{@link Protocol}注解进行通用解码
     * @return
     */
    @SuppressWarnings("all")
    protected ProtocolData deserialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();

        try {
            ProtocolData ret = (ProtocolData) tClass.getConstructor(byte[].class).newInstance((Object) this.getBytes());
            int byteOffset = 0;
            int lengthFieldLength = 0;
            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {

                int currentLength = getDefinedLength(protocolDefine, lengthFieldLength);

                byte[] byteSegments = Arrays.copyOfRange(getBytes(), byteOffset, byteOffset + currentLength);

//                Constructor<?> constructor = protocolDefine.getValue().getType().getConstructor(byteSegments.getClass(), protocolDefine.getValue().getType());
//                if(constructor == null)

                Constructor<?> constructor;

                if(protocolDefine.getKey().isGenericData()){
                    constructor = ((Class)((ParameterizedType)tClass.getGenericSuperclass()).getActualTypeArguments()[0]).getConstructor(byteSegments.getClass());
                }
                else {
                    constructor = protocolDefine.getValue().getType().getConstructor(byteSegments.getClass());
                }
                Object newInstance = constructor.newInstance((Object) byteSegments);
                protocolDefine.getValue().set(ret, newInstance);

                if(protocolDefine.getKey().isLengthField()){
                    lengthFieldLength = ((IntObjectCodec)newInstance).getValue();
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
    protected void afterDeserialize(ProtocolData ret){

    }

    /**
     * 根据{@link Protocol}注解进行通用编码
     * @return 编码后的结果
     */
    protected ProtocolFragment serialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();
        if(!tClass.isAssignableFrom(ProtocolFragment.class)){

        }
        try {
            byte[] result = new byte[]{};
            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {
                ProtocolFragment fragment = (ProtocolFragment) protocolDefine.getValue().get(getValue());

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

        if (protocolDefine.getKey().serializeLengthDetermination() != SerializeLengthDetermination.class) {
            if(getValue() != null && getValue() instanceof LengthField) { //serialize
                SerializeLengthDetermination o = protocolDefine.getKey().serializeLengthDetermination().getConstructor().newInstance();
                return o.getLength((LengthField) getValue());
            }else if(protocolDefine.getKey().deserializeLengthDetermination() != DeserializeLengthDetermination.class){ //deserialize
                DeserializeLengthDetermination o = protocolDefine.getKey().deserializeLengthDetermination().getConstructor().newInstance();
                return o.getLength(lengthFieldLength);
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
        fields = concat(concat(fields, clazz.getDeclaredFields()), clazz.getSuperclass().getDeclaredFields());
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
