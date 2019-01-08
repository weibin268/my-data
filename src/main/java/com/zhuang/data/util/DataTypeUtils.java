package com.zhuang.data.util;

import java.util.Date;

public class DataTypeUtils {

    public static boolean isPrimitiveType(Object object) {
        boolean result = false;
        if (object.getClass().isPrimitive())
            return true;
        if (object.getClass() == Short.class || object.getClass() == Integer.class || object.getClass() == Long.class
                || object.getClass() == Double.class || object.getClass() == Float.class
                || object.getClass() == String.class) {
            result = true;
        }
        return result;
    }

    public static Class<?> getJavaTypeFromDbType(String dbType) {
        if (dbType.equalsIgnoreCase("varchar") || dbType.equalsIgnoreCase("char")
                || dbType.equalsIgnoreCase("text") || dbType.equalsIgnoreCase("tinytext")) {
            return String.class;
        } else if (dbType.equalsIgnoreCase("datetime") || dbType.equalsIgnoreCase("date")) {
            return Date.class;
        } else if (dbType.equalsIgnoreCase("bigint")) {
            return Long.class;
        } else if (dbType.equalsIgnoreCase("int") || dbType.equalsIgnoreCase("integer")) {
            return Integer.class;
        } else if (dbType.equalsIgnoreCase("tinyint") || dbType.equalsIgnoreCase("smallint")
                || dbType.equalsIgnoreCase("mediumint")) {
            return Short.class;
        } else if (dbType.equalsIgnoreCase("decimal") || dbType.equalsIgnoreCase("double")
                || dbType.equalsIgnoreCase("numeric")) {
            return Double.class;
        } else if (dbType.equalsIgnoreCase("float")) {
            return Float.class;
        } else if (dbType.equalsIgnoreCase("bit")) {
            return Boolean.class;
        } else {
            return Object.class;
        }
    }
}
