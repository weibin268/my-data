package com.zhuang.data.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuang
 **/
public class ReflectionUtils {

    public static Object getFieldValue(Object target, Class clazz, String fieldName) {
        try {
            Field field = getDeclaredField(clazz, fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName() + "." + fieldName, e);
        }
    }

    public static Object invokeMethod(Object target, Class clazz, String methodName, Object... args) {
        try {
            Method method = clazz.getMethod(methodName);
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName() + "." + methodName, e);
        }
    }

    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        for (Class<?> tempClazz = clazz; tempClazz != Object.class; tempClazz = tempClazz.getSuperclass()) {
            try {
                Field field = tempClazz.getDeclaredField(fieldName);
                if (field != null) return field;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        for (Class<?> tempClazz = clazz; tempClazz != Object.class; tempClazz = tempClazz.getSuperclass()) {
            try {
                Field[] fields = tempClazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!fieldList.stream().anyMatch(c -> c.getName().equals(field.getName()))) {
                        fieldList.add(field);
                    }
                }
            } catch (Exception e) {
            }
        }
        Field[] result = new Field[fieldList.size()];
        return fieldList.toArray(result);
    }

    public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationClass) {
        for (Class<?> tempClazz = clazz; tempClazz != Object.class; tempClazz = tempClazz.getSuperclass()) {
            try {
                A annotation = tempClazz.getAnnotation(annotationClass);
                if (annotation != null) {
                    return annotation;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
