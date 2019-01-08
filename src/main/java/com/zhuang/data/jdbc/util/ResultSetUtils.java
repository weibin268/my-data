package com.zhuang.data.jdbc.util;

import com.zhuang.data.orm.annotation.UnderscoreNaming;
import com.zhuang.data.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultSetUtils {

    public static <T> List<T> readToEntities(ResultSet resultSet, Class<T> entityType) throws SQLException {
        List<T> result = new ArrayList<T>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        try {
            while (resultSet.next()) {
                T entity;
                entity = entityType.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    columnName = StringUtils.underscoreToCamelCase(columnName);
                    Object value = resultSet.getObject(i);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, entityType);
                    Method setterMethod = propertyDescriptor.getWriteMethod();
                    setterMethod.invoke(entity, value);
                }
                result.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException("ResultSetUtils.readToList执行失败！", e);
        }
        return result;
    }

    public static List<Map> readToMaps(ResultSet resultSet) throws SQLException {
        List<Map> result = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        try {
            while (resultSet.next()) {
                Map map = new LinkedHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    columnName = StringUtils.underscoreToCamelCase(columnName);
                    Object value = resultSet.getObject(i);
                    map.put(columnName, value);
                }
                result.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException("ResultSetUtils.readToList执行失败！", e);
        }
        return result;
    }

}
