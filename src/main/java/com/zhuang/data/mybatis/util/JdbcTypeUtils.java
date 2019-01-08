package com.zhuang.data.mybatis.util;

import com.zhuang.data.enums.DbDialect;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @author zhuang
 * @create 6/30/18 10:24 AM
 **/
public class JdbcTypeUtils {

    public static String getJdbcTypeName(DbDialect dbDialect, Class propertyType) {

        if (propertyType == String.class) {
            return JdbcType.VARCHAR.name();
        } else if (propertyType == Date.class) {
            return JdbcType.DATE.name();
        } else if (propertyType == Integer.class) {
            return JdbcType.INTEGER.name();
        } else if (propertyType == Short.class) {
            return JdbcType.SMALLINT.name();
        } else if (propertyType == Long.class) {
            return JdbcType.BIGINT.name();
        } else if (propertyType == Boolean.class) {
            return JdbcType.BOOLEAN.name();
        } else if (propertyType == Float.class) {
            return JdbcType.FLOAT.name();
        } else if (propertyType == Double.class) {
            return JdbcType.DOUBLE.name();
        }

        return null;
    }
}
