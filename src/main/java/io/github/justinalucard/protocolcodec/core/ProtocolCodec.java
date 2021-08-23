package io.github.justinalucard.protocolcodec.core;

import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.naives.IntObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.DeserializeLengthDetermination;
import io.github.justinalucard.protocolcodec.protocols.LengthField;
import io.github.justinalucard.protocolcodec.protocols.SerializeLengthDetermination;
import io.github.justinalucard.protocolcodec.utils.BufferUtils;
import io.github.justinalucard.protocolcodec.utils.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;


/**
 * 通用编解码器，是{@link ProtocolFragment}的高级版本。
 * 增加了{@link ProtocolCodec#getValue()}和对value的通用编码{@link ProtocolCodec#serialize()}解码{@link ProtocolCodec#deserialize()}实现
 * @param <ProtocolData> 需要被编解码的协议数据类型
 */
public abstract class ProtocolCodec<ProtocolData extends ProtocolFragment> extends ObjectCodec<ProtocolData> {


    public ProtocolCodec(byte[] bytes, Class<?> tClass) {
        super(bytes, tClass);
    }

    public ProtocolCodec(String hexString, Class<?> tClass) {
        super(hexString, tClass);
    }


    public ProtocolCodec(ProtocolData protocolData, Class<?> tClass) {
        super(protocolData, tClass);
        this.getValue().setBytes(this.getBytes());
    }



    public ProtocolCodec(Class<?> tClass) {
        super(tClass);
    }



    /**
     * 根据{@link Protocol}注解进行通用解码
     * @return 反序列化的结果
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
     * @param protocolData 协议数据体
     */
    protected void afterDeserialize(ProtocolData protocolData){

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
                ProtocolFragment fragment = (ProtocolFragment) protocolDefine.getValue().get(getValue());

                result = BufferUtils.concat(result, BufferUtils.fixLengthPaddingLeft(fragment.getBytes(), getDefinedLength(protocolDefine, null)));

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
     * @throws InstantiationException 反射的相关异常
     * @throws IllegalAccessException 反射的相关异常
     * @throws java.lang.reflect.InvocationTargetException 反射的相关异常
     * @throws NoSuchMethodException 反射的相关异常
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
     * @return 协议定义
     */
    protected Map<Protocol, Field> getProtocolDefine() {
        Map<Protocol, Field> annotations = ReflectUtils.getAnnotations(tClass, Protocol.class);
        Map<Protocol, Field> result = new LinkedHashMap<>();
        annotations.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey().order()))
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }


}
