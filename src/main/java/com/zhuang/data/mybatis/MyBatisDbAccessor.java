package com.zhuang.data.mybatis;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.exception.ExecuteSqlException;
import com.zhuang.data.exception.GetConnectionException;
import com.zhuang.data.model.PageInfo;
import com.zhuang.data.mybatis.model.PageQueryParameter;
import com.zhuang.data.mybatis.util.MappedStatementUtils;
import com.zhuang.data.mybatis.util.SqlSessionFactoryUtils;
import com.zhuang.data.util.BeanUtils;
import com.zhuang.data.util.DbDialectUtils;
import com.zhuang.data.util.EntityUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyBatisDbAccessor extends DbAccessor {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession globalSqlSession;
    public static final String PAGE_QUERY_PARAMETER_KEY = "PAGE_QUERY_PARAMETER_KEY";

    public MyBatisDbAccessor(boolean autoCommit) {
        this(SqlSessionFactoryUtils.getSqlSessionFactory(), autoCommit);
    }

    public MyBatisDbAccessor(SqlSessionFactory sqlSessionFactory, boolean autoCommit) {
        this.sqlSessionFactory = sqlSessionFactory;
        if (!autoCommit) {
            this.globalSqlSession = sqlSessionFactory.openSession();
        }
        super.autoCommit = autoCommit;
        super.dbDialect = DbDialectUtils.getDbDialectByDataSource(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public DbDialect getDbDialect() {
        return dbDialect;
    }

    @Override
    public Connection getConnection() {
        try {
            if (autoCommit) {
                return sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection();
            } else {
                return globalSqlSession.getConnection();
            }
        } catch (SQLException e) {
            throw new GetConnectionException("MyBatisDbAccessor获取Connection失败！", e);
        }
    }

    @Override
    public String getConfigFile() {
        return configFile;
    }

    @Override
    public boolean getAutoCommit() {
        return autoCommit;
    }

    @Override
    public <T> T queryEntity(String sql, Object parameter, Class<T> entityType) {
        T result;
        if (autoCommit) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                result = queryEntity(sql, parameter, sqlSession);
            } finally {
                sqlSession.close();
            }
        } else {
            result = queryEntity(sql, parameter, globalSqlSession);
        }
        return result;
    }

    @Override
    public <T> List<T> queryEntities(String sql, Object parameter, Class<T> entityType) {
        List<T> result;
        if (autoCommit) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                result = queryEntities(sql, parameter, sqlSession);
            } finally {
                sqlSession.close();
            }
        } else {
            result = queryEntities(sql, parameter, globalSqlSession);
        }
        return result;
    }

    @Override
    public int executeNonQuery(String sql, Object parameter) {
        int result;
        if (autoCommit) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                result = executeNonQuery(sql, parameter, sqlSession);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                throw new ExecuteSqlException(e.getMessage(), e);
            } finally {
                sqlSession.close();
            }
        } else {
            result = executeNonQuery(sql, parameter, globalSqlSession);
        }
        return result;
    }

    @Override
    public <T> List<T> pageQueryEntities(String sql, PageInfo pageInfo, Object parameter, Class<T> entityType) {
        List<T> result;
        if (autoCommit) {
            SqlSession sqlSession = sqlSessionFactory.openSession();
            try {
                result = pageQueryEntities(sql, pageInfo, parameter, sqlSession);
            } finally {
                sqlSession.close();
            }

        } else {
            result = pageQueryEntities(sql, pageInfo, parameter, globalSqlSession);
        }
        return result;
    }

    @Override
    public void commit() {
        if (!autoCommit && globalSqlSession != null) {
            globalSqlSession.commit(true);
        }
    }

    @Override
    public void rollback() {
        if (!autoCommit && globalSqlSession != null) {
            globalSqlSession.rollback(true);
        }
    }

    @Override
    public void close() {
        if (!autoCommit && globalSqlSession != null) {
            globalSqlSession.close();
        }
    }

    @Override
    public <T> T select(Object objKey, Class<T> entityType) {
        String mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entityType, objKey.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.SELECT);
        return queryEntity(mappedStatementId, objKey, entityType);
    }

    @Override
    public int insert(Object entity) {
        String mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entity.getClass(), entity.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.INSERT);
        return executeNonQuery(mappedStatementId, entity);
    }

    @Override
    public int update(Object entity) {
        return update(entity, false);
    }

    @Override
    public int update(Object entity, boolean excludeNullFields) {
        String mappedStatementId;
        if (excludeNullFields) {
            Map<String, Object> map = BeanUtils.objectToMap(entity);
            String[] propertyNames = map.keySet().toArray(new String[]{});
            mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entity.getClass(), entity.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.UPDATE, propertyNames);
        } else {
            mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entity.getClass(), entity.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.UPDATE);
        }
        return executeNonQuery(mappedStatementId, entity);
    }

    @Override
    public <T> int delete(Object objKey, Class<T> entityType) {
        String mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entityType, objKey.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.DELETE);
        return executeNonQuery(mappedStatementId, objKey);
    }

    @Override
    public int insertOrUpdate(Object entity) {
        int result = update(entity);
        if (result < 1) {
            result = insert(entity);
        }
        return result;
    }

    @Override
    public <T> List<T> selectByParams(Object objParams, Class<T> entityType) {
        Map<String, Object> mapParams = BeanUtils.objectToMap(objParams);
        String[] propertyNames = new String[mapParams.keySet().size()];
        propertyNames = mapParams.keySet().toArray(propertyNames);
        String mappedStatementId = MappedStatementUtils.getMappedStatementId(dbDialect, entityType, mapParams.getClass(), sqlSessionFactory.getConfiguration(), SqlCommandType.SELECT, propertyNames);
        return queryEntities(mappedStatementId, mapParams, entityType);
    }

    private <T> T queryEntity(String sql, Object parameter, SqlSession sqlSession) {
        T result;
        result = sqlSession.selectOne(sql, parameter);
        return result;
    }

    private <T> List<T> pageQueryEntities(String sql, PageInfo pageInfo, Object parameter, SqlSession sqlSession) {
        List<T> result;
        Map<String, Object> mapParameter = EntityUtils.convertToMap(parameter);
        if (mapParameter == null) {
            mapParameter = new HashMap<>();
        }
        PageQueryParameter pageQueryParameter = new PageQueryParameter();
        pageQueryParameter.setTarget(this);
        // pageQueryParameter.setParameter(parameter);
        pageQueryParameter.setOrderClause(pageInfo.getOrderClause());
        pageQueryParameter.setPageIndex(pageInfo.getPageIndex());
        pageQueryParameter.setRowCount(pageInfo.getRowCount());
        mapParameter.put(PAGE_QUERY_PARAMETER_KEY, pageQueryParameter);
        result = sqlSession.selectList(sql, mapParameter);
        pageInfo.setTotalRowCount(pageQueryParameter.getTotalRowCount());
        return result;
    }

    private int executeNonQuery(String sql, Object parameter, SqlSession sqlSession) {
        int result;
        MappedStatement mappedStatement = sqlSession.getConfiguration().getMappedStatement(sql);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (sqlCommandType == SqlCommandType.INSERT) {
            result = sqlSession.insert(sql, parameter);
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            result = sqlSession.update(sql, parameter);
        } else if (sqlCommandType == SqlCommandType.DELETE) {
            result = sqlSession.delete(sql, parameter);
        } else {
            throw new RuntimeException("sqlCommandType:" + sqlCommandType.toString() + "没有对应的执行方法！");
        }
        return result;
    }

    private <T> List<T> queryEntities(String sql, Object parameter, SqlSession sqlSession) {
        List<T> result;
        result = sqlSession.selectList(sql, parameter);
        return result;
    }
}
