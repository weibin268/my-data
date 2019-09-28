package com.zhuang.data.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null) return null;
        try {
            Object obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("BeanUtils.mapToObject error!", e);
        }
    }

    public static Map<String, Object> objectToMap(Object obj) {
        return objectToMap(obj, false);
    }

    public static Map<String, Object> objectToMap(Object obj, boolean excludeNullFields) {
        if (obj == null) return null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Field[] declaredFields =   ReflectionUtils.getDeclaredFields(obj.getClass());;
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) continue;
                map.put(field.getName(), value);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("BeanUtils.objectToMap error!", e);
        }
    }
}
