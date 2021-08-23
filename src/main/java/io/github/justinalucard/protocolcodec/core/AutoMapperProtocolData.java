package io.github.justinalucard.protocolcodec.core;

import io.github.justinalucard.protocolcodec.utils.ReflectUtils;
import java.lang.reflect.Field;

/**
 * 数据体实现自动转换，需要注意，协议与模型定义的字段名必须保持一致。
 */
public class AutoMapperProtocolData<T> extends ProtocolFragment {

    public AutoMapperProtocolData(byte[] bytes) {
        super(bytes);
    }

    public AutoMapperProtocolData(String hexString) {
        super(hexString);
    }

    /**
     * 传入模型实体，将进行自动编码
     * @param t 模型实体
     */
    public AutoMapperProtocolData(T t) {
        try {
            Field[] targetFields = ReflectUtils.getFields(this.getClass());
            Field[] sourceFields = ReflectUtils.getFields(t.getClass());

            for (Field sourceField : sourceFields) {
                Field targetField = null;
                for (Field field : targetFields) {
                    if(field.getName().equalsIgnoreCase(sourceField.getName())){
                        targetField = field;
                        break;
                    }
                }

                if (targetField != null) {
                    Object value = targetField.getType().getConstructor(sourceField.getType()).newInstance(sourceField.get(t));
                    targetField.set(this, value);
                }
            }
        }
        catch(Exception ex){
            throw new RuntimeException("无法进行自动转换，确保协议与模型定义的字段名是否一致。模型类型必须与协议定义类型一致。例如：UInt16 模型类型必须为Integer，不能为int。", ex);
        }
    }

    /**
     * 获取解码后的模型实体
     * @return 模型实体
     */
    @SuppressWarnings("all")
    public T get() {
        try {
            Field[] sourceFields = ReflectUtils.getFields(this.getClass());
            Class<?> tClass = ReflectUtils.getGenericClass(this.getClass());
            Field[] targetFields = ReflectUtils.getFields(tClass);

            Object t = tClass.getConstructor().newInstance();

            for (Field sourceField : sourceFields) {
                for (Field targetField : targetFields) {
                    if(targetField.getName().equalsIgnoreCase(sourceField.getName())){
                        Object value = sourceField.get(this).getClass().getMethod("getValue", null).invoke(sourceField.get(this));
                        targetField.set(t, value);
                        break;
                    }
                }
            }

            return (T) t;
        }
        catch(Exception ex){
            throw new RuntimeException("无法进行自动转换，确保协议与模型定义的字段名是否一致。模型类型必须与协议定义类型一致。例如：UInt16 模型类型必须为Integer，不能为int。", ex);
        }
    }
}
