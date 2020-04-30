package com.zhuang.data.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
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
        if (isInDbTypes(dbType, "char", "varchar", "tinytext", "text", "longtext")) {
            return String.class;
        } else if (isInDbTypes(dbType, "datetime", "date", "year")) {
            return Date.class;
        } else if (isInDbTypes(dbType, "time")) {
            return Time.class;
        } else if (isInDbTypes(dbType, "timestamp")) {
            return Timestamp.class;
        } else if (isInDbTypes(dbType, "bigint")) {
            return BigInteger.class;
        } else if (isInDbTypes(dbType, "int", "integer", "mediumint")) {
            return Integer.class;
        } else if (isInDbTypes(dbType, "tinyint", "smallint")) {
            return Short.class;
        } else if (isInDbTypes(dbType, "double")) {
            return Double.class;
        } else if (isInDbTypes(dbType, "decimal", "numeric")) {
            return BigDecimal.class;
        } else if (isInDbTypes(dbType, "float")) {
            return Float.class;
        } else if (isInDbTypes(dbType, "bit")) {
            return Boolean.class;
        } else if (isInDbTypes(dbType, "blob")) {
            return byte[].class;
        } else {
            return Object.class;
        }
    }

    private static boolean isInDbTypes(String dbType, String... dbTypes) {
        for (String type : dbTypes) {
            if (dbType.equalsIgnoreCase(type)) return true;
        }
        return false;
    }

}
