package io.github.justinalucard.protocolcodec.core;


import io.github.justinalucard.protocolcodec.annotations.Protocol;

/**
 * 通用编解码器，可对任何对象(Object)进行编码。
 * 增加了{@link ObjectCodec#value}和对value的通用编码{@link ObjectCodec#serialize()}解码{@link ObjectCodec#deserialize()}实现
 * @param <T> 对象解码器需要进行编解码的原始类型
 */
public abstract class ObjectCodec<T> extends ProtocolFragment {

    protected ObjectCodec(){

    }


    public ObjectCodec(byte[] bytes) {
        super(bytes);
    }

    public ObjectCodec(String hexString) {
        super(hexString);
    }



    public ObjectCodec(T value) {
        fromValue(value);
    }



    @Override
    protected void fromBytes(byte[] bytes) {
        super.fromBytes(bytes);
        this.setValue(this.deserialize());
    }

    @Override
    protected void fromHexString(String hexString) {
        super.fromHexString(hexString);
        this.setValue(this.deserialize());
    }

    protected void fromValue(T value){
        this.setValue(value);
        this.setBytes(this.serialize().getBytes());
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
     * @return 反序列化后的结果
     */
    @SuppressWarnings("all")
    protected abstract T deserialize();

    /**
     * 解码后调用，一般用于解码后进行校验位比对验证，这里默认空方法，子类需要的时候，override即可
     * @param t 序列化后的结果
     */
    protected void afterDeserialize(T t){

    }

    /**
     * 根据{@link Protocol}注解进行通用编码
     * @return 编码后的结果
     */
    protected abstract ProtocolFragment serialize();


}
