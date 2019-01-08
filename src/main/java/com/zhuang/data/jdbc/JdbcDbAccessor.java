package com.zhuang.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.config.MyDataProperties;
import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.exception.GetConnectionException;
import com.zhuang.data.model.PageInfo;
import com.zhuang.data.util.DbDialectUtils;

public class JdbcDbAccessor extends DbAccessor {

	private MyDataProperties zhuangDataProperties;
	private DbDialect dbDialect;
	private boolean autoCommit;

	public JdbcDbAccessor(String configFile) {
		zhuangDataProperties = new MyDataProperties(configFile);
		try {
			Class.forName(zhuangDataProperties.getJdbcDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbDialect = DbDialectUtils.getDbDialectByConnection(getConnection());
	}

	@Override
	public DbDialect getDbDialect() {
		return dbDialect;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(zhuangDataProperties.getJdbcUrl(),
					zhuangDataProperties.getJdbcUserName(), zhuangDataProperties.getJdbcPassword());
		} catch (SQLException e) {
			throw new GetConnectionException("JdbcDbAccessor获取Connection失败！", e);
		}
	}

	@Override
	public String getConfigFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAutoCommit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T queryEntity(String sql, Object parameter, Class<T> entityType) {
		Connection connection = getConnection();
		return null;
	}

	@Override
	public <T> List<T> queryEntities(String sql, Object parameter, Class<T> entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> pageQueryEntities(String sql, PageInfo pageInfo, Object parameter, Class<T> entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int executeNonQuery(String sql, Object parameter) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(Object entity, String[] propertyNames) {
		return 0;
	}


	@Override
	public int insertOrUpdate(Object entity) {
		return 0;
	}

	@Override
	public <T> List<T> selectByMap(Map<String, Object> propertyMap, Class<T> entityType) {
		return null;
	}
}
