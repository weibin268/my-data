package com.zhuang.data.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils {
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entityClass) {
        if (map == null) return null;
        try {
            T entity = entityClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    setter.invoke(entity, map.get(property.getName()));
                }
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("EntityUtils.mapToEntity error!", e);
        }
    }

    public static <T> Map<String, Object> entityToMap(T entity) {
        return entityToMap(entity, false);
    }

    public static <T> Map<String, Object> entityToMap(T entity, boolean excludeNullFields) {
        if (entity == null) return null;
        if (entity instanceof Map) return (Map) entity;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String propertyName = property.getName();
                if (propertyName.equals("class")) continue;
                Method readMethod = property.getReadMethod();
                Object propertyValue = readMethod.invoke(entity);
                if (excludeNullFields && propertyValue == null) continue;
                map.put(propertyName, propertyValue);
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException("EntityUtils.entityToMap error!", e);
        }
    }

	public static <T> T mapToEntity2(Map<String, Object> map, Class<T> entityClass) {
		if (map == null) return null;
		try {
			T obj = entityClass.newInstance();
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
			throw new RuntimeException("EntityUtils.mapToEntity error!", e);
		}
	}

	public static <T> Map<String, Object> entityToMap2(T entity) {
		return entityToMap2(entity, false);
	}

	public static <T> Map<String, Object> entityToMap2(T entity, boolean excludeNullFields) {
		if (entity == null) return null;
		if (entity instanceof Map) return (Map) entity;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Field[] declaredFields = ReflectionUtils.getDeclaredFields(entity.getClass());
			for (Field field : declaredFields) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if (excludeNullFields && value == null) continue;
				map.put(field.getName(), value);
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("EntityUtils.entityToMap error!", e);
		}
	}
}
