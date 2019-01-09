package com.zhuang.data.orm.util;

import com.zhuang.data.orm.annotation.Column;
import com.zhuang.data.orm.annotation.GeneratedValue;
import com.zhuang.data.orm.annotation.Id;
import com.zhuang.data.orm.annotation.Table;
import com.zhuang.data.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author zhuang
 **/
public class AnnotationUtils {

    public static String getTableName(Class<?> entityType) {
        String result = null;
        Table table = entityType.getAnnotation(Table.class);
        if (table != null) {
            result = table.name();
        } else {
            try {
                Class<Annotation> jpaTableClass = (Class<Annotation>) Class.forName("javax.persistence.Table");
                Object jpaTable = entityType.getAnnotation(jpaTableClass);
                if (jpaTable != null) {
                    result = (String) ReflectionUtils.invokeMethod(jpaTable, jpaTableClass, "name");
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return result;
    }

    public static String getColumnName(Field field) {
        String result = null;
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            result = column.name();
        } else {
            try {
                Class<Annotation> jpaColumnClass = (Class<Annotation>) Class.forName("javax.persistence.Column");
                Object jpaColumn = field.getAnnotation(jpaColumnClass);
                if (jpaColumn != null) {
                    result = (String) ReflectionUtils.invokeMethod(jpaColumn, jpaColumnClass, "name");
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return result;
    }

    public static boolean isId(Field field) {
        boolean result = false;
        Id id = field.getAnnotation(Id.class);
        if (id != null) {
            result = true;
        } else {
            try {
                Class<Annotation> jpaIdClass = (Class<Annotation>) Class.forName("javax.persistence.Id");
                Object jpaId = field.getAnnotation(jpaIdClass);
                if (jpaId != null) {
                    result = true;
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return result;
    }

    public static boolean isGeneratedValue(Field field) {
        boolean result = false;
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue != null) {
            result = true;
        } else {
            try {
                Class<Annotation> jpaIdClass = (Class<Annotation>) Class.forName("javax.persistence.GeneratedValue");
                Object jpaGeneratedValue = field.getAnnotation(jpaIdClass);
                if (jpaGeneratedValue != null) {
                    result = true;
                }
            } catch (ClassNotFoundException e) {
            }
        }
        return result;
    }

}
