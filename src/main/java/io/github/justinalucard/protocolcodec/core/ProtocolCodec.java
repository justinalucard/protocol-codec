package io.github.justinalucard.protocolcodec.core;

import io.github.justinalucard.protocolcodec.annotations.Protocol;
import io.github.justinalucard.protocolcodec.exceptions.DeserializationException;
import io.github.justinalucard.protocolcodec.exceptions.SerializationException;
import io.github.justinalucard.protocolcodec.naives.IntObjectCodec;
import io.github.justinalucard.protocolcodec.protocols.DeserializeLengthDetermination;
import io.github.justinalucard.protocolcodec.protocols.LengthField;
import io.github.justinalucard.protocolcodec.protocols.SerializeLengthDetermination;
import io.github.justinalucard.protocolcodec.utils.BufferUtils;
import io.github.justinalucard.protocolcodec.utils.ReflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 通用编解码器，是{@link ProtocolFragment}的高级版本。
 * 增加了{@link ProtocolCodec#getValue()}和对value的通用编码{@link ProtocolCodec#serialize()}解码{@link ProtocolCodec#deserialize()}实现
 *
 * @param <ProtocolData> 需要被编解码的协议数据类型
 */
public abstract class ProtocolCodec<ProtocolData extends ProtocolFragment> extends ObjectCodec<ProtocolData> {


    public ProtocolCodec(byte[] bytes) {
        super(bytes);
    }

    public ProtocolCodec(String hexString) {
        super(hexString);
    }


    public ProtocolCodec(ProtocolData protocolData) {
        super(protocolData);
        this.getValue().setBytes(this.getBytes());
    }


    public ProtocolCodec() {
        super();
    }


    /**
     * 根据{@link Protocol}注解进行通用解码
     *
     * @return 反序列化的结果
     */
    @SuppressWarnings("all")
    protected ProtocolData deserialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();

        try {
            ProtocolData ret = (ProtocolData) ReflectUtils.getGenericClass(getClass()).getConstructor(byte[].class).newInstance((Object) this.getBytes());
            int byteOffset = 0;
            int lengthFieldLength = 0;

            String currrentBranchNo = null;

            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {

                int currentLength = protocolDefine.getKey().isRestOf() ?
                        getBytes().length - byteOffset :
                        getDefinedLength(protocolDefine, lengthFieldLength);

                if(!protocolDefine.getKey().lengthByEndWith().equals(""))
                    currentLength = Integer.MIN_VALUE;

                byte[] byteSegments;
                if(currentLength == Integer.MIN_VALUE)
                    currentLength = getLengthByEndWith(byteOffset, protocolDefine.getKey().lengthByEndWith());
                byteSegments = Arrays.copyOfRange(getBytes(), byteOffset, byteOffset + currentLength);

                if (protocolDefine.getKey().isBranchRoot()) {
                    currrentBranchNo = new ProtocolFragment(byteSegments).getHexString();
                }

                if (protocolDefine.getKey().isBranchNode() && !protocolDefine.getKey().branchNo().equalsIgnoreCase(currrentBranchNo))
                    continue;

                Constructor<?> constructor;

                if (protocolDefine.getKey().isGenericData()) {
                    constructor = ((Class) ((ParameterizedType) ReflectUtils.getGenericClass(getClass()).getGenericSuperclass()).getActualTypeArguments()[0]).getConstructor(byteSegments.getClass());
                } else {
                    constructor = protocolDefine.getValue().getType().getConstructor(byteSegments.getClass());
                }
                Object newInstance = constructor.newInstance((Object) byteSegments);
                protocolDefine.getValue().set(ret, newInstance);

                if (protocolDefine.getKey().isLengthField()) {
                    lengthFieldLength = ((IntObjectCodec) newInstance).getValue();
                }

                byteOffset += currentLength;


            }
            afterDeserialize(ret);
            return ret;
        } catch (Exception ex) {
            throw new DeserializationException("协议解码失败，请检查协议定于与协议数据是否匹配", ex);
        }
    }

    /**
     * 在字节数组中，根据指定特征为结尾，返回该段内容的长度
     * @param byteOffset 读取开始的偏移量
     * @param lengthByEndWith 结尾特征
     * @return 这段内容的长度
     */
    private int getLengthByEndWith(int byteOffset, String lengthByEndWith) {
        byte[] seekBytes = new byte[getBytes().length - byteOffset];
        System.arraycopy(getBytes(), byteOffset, seekBytes, 0,getBytes().length - byteOffset);
        String seekHex = BufferUtils.bytesToHex(seekBytes);
        return (seekHex.indexOf(lengthByEndWith) + lengthByEndWith.length()) / 2;
    }

    /**
     * 解码后调用，一般用于解码后进行校验位比对验证，这里默认空方法，子类需要的时候，override即可
     *
     * @param protocolData 协议数据体
     */
    protected void afterDeserialize(ProtocolData protocolData) {

    }

    /**
     * 根据{@link Protocol}注解进行通用编码
     *
     * @return 编码后的结果
     */
    protected ProtocolFragment serialize() {
        Map<Protocol, Field> protocolDefines = getProtocolDefine();

        String currentBranchNo = null;
        try {
            byte[] result = new byte[]{};
            for (Map.Entry<Protocol, Field> protocolDefine : protocolDefines.entrySet()) {

                ProtocolFragment fragment = (ProtocolFragment) protocolDefine.getValue().get(getValue());

                if (protocolDefine.getKey().isBranchRoot()) {
                    currentBranchNo = fragment.getHexString();
                }

                int length = protocolDefine.getKey().isRestOf() ?
                        fragment.getBytes().length :
                        getDefinedLength(protocolDefine, null);

                if(!protocolDefine.getKey().lengthByEndWith().equals(""))
                    length = Integer.MIN_VALUE;

                if (protocolDefine.getKey().isBranchNode() &&
                        !protocolDefine.getKey().branchNo().equalsIgnoreCase(currentBranchNo))
                    continue;

                if(length == Integer.MIN_VALUE)
                    length = fragment.getBytes().length;


                result = BufferUtils.concat(result, protocolDefine.getKey().padding() == Protocol.Padding.LEFT ?
                                BufferUtils.fixLengthPaddingLeft(fragment.getBytes(), length) :
                                BufferUtils.fixLengthPaddingRight(fragment.getBytes(), length));

            }
            return new ProtocolFragment(result);
        } catch (Exception ex) {
            throw new SerializationException("协议编码失败，请确认协议模型Model是否正确，某个字段值为null触发空指针异常，除IsBranchNode=true且未匹配到BranchNo的字段值不能为null", ex);
        }
    }

    /**
     * 根据{@link Protocol#length()} {@link Protocol#serializeLengthDetermination()} {@link Protocol#deserializeLengthDetermination()}
     * 确定并返回对应协议字段的长度
     *
     * @param protocolDefine    协议注解的Map，包含注解与Field的关系
     * @param lengthFieldLength 解码时，根据协议规定，传入Length这个协议字段的值，用以确定，该截取多少字节为Data域，不在反序列化或者还未解析到Length的时候，可以传任意值，都会忽略
     * @return 长度
     * @throws InstantiationException                      反射的相关异常
     * @throws IllegalAccessException                      反射的相关异常
     * @throws java.lang.reflect.InvocationTargetException 反射的相关异常
     * @throws NoSuchMethodException                       反射的相关异常
     */
    private int getDefinedLength(Map.Entry<Protocol, Field> protocolDefine, Integer lengthFieldLength) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        int length = protocolDefine.getKey().length();

        if (protocolDefine.getKey().serializeLengthDetermination() != SerializeLengthDetermination.class) {
            if (getValue() != null && getValue() instanceof LengthField) { //serialize
                SerializeLengthDetermination o = protocolDefine.getKey().serializeLengthDetermination().getConstructor().newInstance();
                return o.getLength((LengthField) getValue());
            } else if (protocolDefine.getKey().deserializeLengthDetermination() != DeserializeLengthDetermination.class) { //deserialize
                DeserializeLengthDetermination o = protocolDefine.getKey().deserializeLengthDetermination().getConstructor().newInstance();
                return o.getLength(lengthFieldLength);
            }
        }

        return length;
    }


    /**
     * 根据传入的类型，反射并返回注解与field的关系Map
     *
     * @return 协议定义
     */
    protected Map<Protocol, Field> getProtocolDefine() {
        Map<Protocol, Field> annotations = ReflectUtils.getAnnotations(ReflectUtils.getGenericClass(getClass()), Protocol.class);
        Map<Protocol, Field> result = new LinkedHashMap<>();
        annotations.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey().order()))
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }


}
