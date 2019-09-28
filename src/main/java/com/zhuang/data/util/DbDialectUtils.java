package com.zhuang.data.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.zhuang.data.enums.DbDialect;

public class DbDialectUtils {

    public static DbDialect getDbDialectByDataSource(DataSource dataSource) {
        try {
            return getDbDialectByConnection(dataSource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException("DbDialectUtil.getDbDialectByDataSource获取失败", e);
        }
    }

    public static DbDialect getDbDialectByConnection(Connection connection) {
        String dbProductName;
        try {
            dbProductName = connection.getMetaData().getDatabaseProductName();
            return DbDialect.getByProductName(dbProductName);
        } catch (SQLException e) {
            throw new RuntimeException("DbDialectUtil.getDbDialectByConnection获取失败", e);
        }
    }
}
