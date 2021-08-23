package io.github.justinalucard.protocolcodec.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtils {

    public static Method[] getMethods(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        methods = BufferUtils.concat(BufferUtils.concat(methods, clazz.getDeclaredMethods()), clazz.getSuperclass().getDeclaredMethods());
        for (Method method : methods) {
            method.setAccessible(true);
        }
        return methods;
    }

    public static Field[] getFields(Class<?> clazz){
        Field[] fields = clazz.getFields();
        fields = BufferUtils.concat(BufferUtils.concat(fields, clazz.getDeclaredFields()), clazz.getSuperclass().getDeclaredFields());
        for (Field field : fields) {
            field.setAccessible(true);
        }
        return fields;
    }

    public static <T extends Annotation> Map<T, Field> getAnnotations(Class<?> clazz, Class<T> tClass){
        Field[] fields = getFields(clazz);
        Map<T, Field> annotations = new HashMap<>();
        for (Field field : fields) {
            T annotation = field.getAnnotation(tClass);
            if (annotation != null) {
                field.setAccessible(true);
                annotations.put(annotation, field);
            }
        }
        return annotations;
    }

    public static Class<?> getGenericClass(Class<?> clazz, int idx){
        return ((Class<?>)((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[idx]);
    }
    public static Class<?> getGenericClass(Class<?> clazz){
        return getGenericClass(clazz, 0);
    }
}
