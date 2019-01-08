package com.zhuang.data.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.enums.DbProductNames;
import com.zhuang.data.mybatis.enums.DatabaseIdValues;

public class DbDialectUtils {

	public static DbDialect getDbDialectByMyBatisDatabaseId(String databaseId) {
		if (databaseId.equals(DatabaseIdValues.mysql)) {
			return DbDialect.MySQL;

		} else if (databaseId.equals(DatabaseIdValues.oracle)) {
			return DbDialect.Oracle;

		} else if (databaseId.equals(DatabaseIdValues.mssql)) {
			return DbDialect.MSSQL;

		} else if (databaseId.equals(DatabaseIdValues.db2)) {
			return DbDialect.DB2;

		} else if (databaseId.equals(DatabaseIdValues.sybase)) {
			return DbDialect.Sybase;

		} else {
			throw new RuntimeException("databaseId:" + databaseId + ",没有找到对应的DbDialect");
		}
	}

	public static DbDialect getDbDialectByDbProductName(String dbProductName) {
		if (dbProductName.equals(DbProductNames.MySQL.getName())) {
			return DbDialect.MySQL;
		} else if (dbProductName.equals(DbProductNames.Oracle.getName())) {
			return DbDialect.Oracle;
		} else if (dbProductName.equals(DbProductNames.MSSQL.getName())) {
			return DbDialect.MSSQL;
		} else if (dbProductName.equals(DbProductNames.DB2.getName())) {
			return DbDialect.DB2;
		} else if (dbProductName.equals(DbProductNames.Sybase.getName())) {
			return DbDialect.Sybase;
		} else {
			throw new RuntimeException("dbProductName:" + dbProductName + ",没有找到对应的DbDialect");
		}
	}

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
			return getDbDialectByDbProductName(dbProductName);

		} catch (SQLException e) {
			throw new RuntimeException("DbDialectUtil.getDbDialectByConnection获取失败", e);
		}
	}
}
