package com.zhuang.data.mybatis.interceptor;

import com.zhuang.data.handler.DbExecuteHandlerFactory;
import com.zhuang.data.handler.DbExecutionContext;
import com.zhuang.data.handler.DbExecutionHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DbExecutionInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        Object[] args = invocation.getArgs();
        Connection connection = (Connection) args[0];

        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        Object parameter = statementHandler.getParameterHandler().getParameterObject();

        List<DbExecutionHandler> dbExecutionHandlers = DbExecuteHandlerFactory.getDbExecuteHandlers();
        if (dbExecutionHandlers.size() == 0) {
            return invocation.proceed();
        }

        String sqlPropertyName = "delegate.boundSql.sql";
        if (metaStatementHandler.findProperty(sqlPropertyName, true) == null) {
            sqlPropertyName = "boundSql.sql";
        }
        String sql = (String) metaStatementHandler.getValue(sqlPropertyName);

        DbExecutionContext dbExecutionContext = new DbExecutionContext();
        dbExecutionContext.setConnection(connection);
        dbExecutionContext.setParameter(parameter);
        dbExecutionContext.setSql(sql);
        for (DbExecutionHandler dbExecutionHandler :
                dbExecutionHandlers) {
            dbExecutionHandler.handle(dbExecutionContext);
        }
        metaStatementHandler.setValue(sqlPropertyName, dbExecutionContext.getSql());
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }

}
