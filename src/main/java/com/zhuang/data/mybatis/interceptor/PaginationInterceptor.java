package com.zhuang.data.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.exception.ExecuteSqlException;
import com.zhuang.data.mybatis.MyBatisDbAccessor;
import com.zhuang.data.mybatis.model.PageQueryParameter;
import com.zhuang.data.pagination.DbPaging;
import com.zhuang.data.pagination.DbPagingFactory;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PaginationInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		Object[] args = invocation.getArgs();
		Connection connection = (Connection) args[0];

		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		Object parameter = statementHandler.getParameterHandler().getParameterObject();

		PageQueryParameter pageQueryParameter;
		if (parameter instanceof Map && ((Map<String, Object>) parameter).containsKey(MyBatisDbAccessor.PAGE_QUERY_PARAMETER_KEY)) {
			Map<String, Object> mapParameter = (Map<String, Object>) parameter;
			pageQueryParameter = (PageQueryParameter) mapParameter.get(MyBatisDbAccessor.PAGE_QUERY_PARAMETER_KEY);
			mapParameter.remove(MyBatisDbAccessor.PAGE_QUERY_PARAMETER_KEY);
			//metaStatementHandler.setValue("delegate.parameterHandler.parameterObject",parameter);
		} else {
			return invocation.proceed();
		}

		DbPaging dbPaging = DbPagingFactory.GetDbPaging(((DbAccessor) pageQueryParameter.getTarget()).getDbDialect());
		int startRowIndex = 1 + (pageQueryParameter.getPageIndex() - 1) * pageQueryParameter.getRowCount();
		String sqlPropertyName = "delegate.boundSql.sql";

		if (metaStatementHandler.findProperty(sqlPropertyName, true) == null) {
			sqlPropertyName = "boundSql.sql";
		}
		String originSql = (String) metaStatementHandler.getValue(sqlPropertyName);
		long totalRowCount = getTotalRowCount(metaStatementHandler, connection, dbPaging.getCountSql(originSql));
		pageQueryParameter.setTotalRowCount((int) totalRowCount);
		String pagedSql = dbPaging.getPageSql(originSql, pageQueryParameter.getOrderClause(), startRowIndex,
				pageQueryParameter.getRowCount());

		metaStatementHandler.setValue(sqlPropertyName, pagedSql);
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}

	public long getTotalRowCount(MetaObject metaStatementHandler, Connection connection, String countSql) {
		long result = 0;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler) metaStatementHandler
					.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			throw new ExecuteSqlException("getTotalRowCount", e);
		}
		return result;
	}
}
