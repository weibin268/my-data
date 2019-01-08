package com.zhuang.data.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EntityUtils {

	public static Map<String, Object> convertToMap(Object entity) {
		if (entity == null) {
			return null;
		} else if (entity instanceof Map) {
			return (Map<String, Object>) entity;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String propertyName = property.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = property.getReadMethod();
					Object propertyValue = readMethod.invoke(entity);
					map.put(propertyName, propertyValue);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("实体转Map失败！", e);
		}
		return map;
	}
}
