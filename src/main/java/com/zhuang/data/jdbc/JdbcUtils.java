package com.zhuang.data.jdbc;

import com.zhuang.data.config.MyDataProperties;
import com.zhuang.data.exception.ExecuteSqlException;
import com.zhuang.data.exception.GetConnectionException;
import com.zhuang.data.jdbc.util.ResultSetUtils;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class JdbcUtils {

    private static MyDataProperties zhuangDataProperties;

    static {
        zhuangDataProperties = new MyDataProperties();
        try {
            Class.forName(zhuangDataProperties.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int executeNonQuery(String sql, Object... params) {
        return executeNonQuery(getConnection(), sql, params);
    }

    public static int executeNonQuery(Connection connection, String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                int pIndex = 1;
                for (Object item : params) {
                    preparedStatement.setObject(pIndex++, item);
                }
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T queryEntity(Class<T> entityType, String sql, Object... params) {
        return queryEntity(getConnection(), entityType, sql, params);
    }

    public static <T> T queryEntity(Connection connection, Class<T> entityType, String sql, Object... params) {
        List<T> resultList = queryEntities(connection, entityType, sql, params);
        if (resultList.size() > 1)
            throw new RuntimeException("“JdbcUtils.queryEntity”方法内查询结果集有多条记录！");
        else if (resultList.size() == 1)
            return resultList.get(0);
        else
            return null;
    }

    public static <T> List<T> queryEntities(Class<T> entityType, String sql, Object... params) {
        return queryEntities(getConnection(), entityType, sql, params);
    }

    public static <T> List<T> queryEntities(Connection connection, Class<T> entityType, String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                int pIndex = 1;
                for (Object item : params) {
                    preparedStatement.setObject(pIndex++, item);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entityList = ResultSetUtils.readToEntities(resultSet, entityType);
            return entityList;
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map queryMap(String sql, Object... params) {
        return queryMap(getConnection(), sql, params);
    }

    public static Map queryMap(Connection connection, String sql, Object... params) {
        List<Map> resultList = queryMaps(connection, sql, params);
        if (resultList.size() > 1)
            throw new RuntimeException("“JdbcUtils.queryMap”方法内查询结果集有多条记录！");
        else if (resultList.size() == 1)
            return resultList.get(0);
        else
            return null;
    }

    public static List<Map> queryMaps(String sql, Object... params) {

        return queryMaps(getConnection(), sql, params);
    }

    public static List<Map> queryMaps(Connection connection, String sql, Object... params) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (params != null) {
                int pIndex = 1;
                for (Object item : params) {
                    preparedStatement.setObject(pIndex++, item);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map> maps = ResultSetUtils.readToMaps(resultSet);
            return maps;
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(zhuangDataProperties.getJdbcUrl(), zhuangDataProperties.getJdbcUserName(), zhuangDataProperties.getJdbcPassword());
        } catch (SQLException e) {
            throw new GetConnectionException("JdbcDbAccessor获取Connection失败！", e);
        }
    }

}
