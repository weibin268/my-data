package com.zhuang.data.mybatis.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor4legacy implements Interceptor {

	PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

	public Object intercept(Invocation invocation) throws Throwable {
		return paginationInterceptor.intercept(invocation);
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}

	private long getTotalRowCount(MetaObject metaStatementHandler, Connection connection, String countSql) {
		return paginationInterceptor.getTotalRowCount(metaStatementHandler, connection, countSql);
	}
}
