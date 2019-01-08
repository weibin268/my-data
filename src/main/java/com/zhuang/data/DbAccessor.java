package com.zhuang.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.exception.ExecuteSqlException;
import com.zhuang.data.jdbc.util.PreparedStatementUtils;
import com.zhuang.data.jdbc.util.ResultSetUtils;
import com.zhuang.data.model.PageInfo;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.sql.BuildResult;
import com.zhuang.data.orm.sql.SqlBuilder;
import com.zhuang.data.orm.sql.SqlBuilderFactory;

public abstract class DbAccessor {

    /*for attributes begin*/
    protected DbDialect dbDialect;
    protected String configFile;
    protected boolean autoCommit;
    /*for attributes end*/

    /*for get attributes begin*/
    abstract public DbDialect getDbDialect();

    abstract public Connection getConnection();

    abstract public String getConfigFile();

    abstract public boolean getAutoCommit();
    /*for get attributes end*/

    /*for common action begin*/
    abstract public <T> T queryEntity(String sql, Object parameter, Class<T> entityType);

    abstract public <T> List<T> queryEntities(String sql, Object parameter, Class<T> entityType);

    abstract public <T> List<T> pageQueryEntities(String sql, PageInfo pageInfo, Object parameter, Class<T> entityType);

    abstract public int executeNonQuery(String sql, Object parameter);
    /*for common action end*/

    /*for transaction begin*/
    abstract public void commit();

    abstract public void rollback();

    abstract public void close();
    /*for transaction end*/

    /*for entity begin*/
    public <T> T select(Object objKey, Class<T> entityType) {
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, new TableMapping(entityType), PlaceHolderType.QuestionMark);
        BuildResult buildResult = sqlBuilder.buildSelect();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(buildResult.getSql());
            PreparedStatementUtils.setParameter(preparedStatement, objKey, buildResult.getParametersIndex());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entityList = ResultSetUtils.readToEntities(resultSet, entityType);
            if (entityList.size() > 0)
                return entityList.get(0);
            else
                return null;
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                if (getAutoCommit())
                    connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public int insert(Object entity) {
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, new TableMapping(entity.getClass()), PlaceHolderType.QuestionMark);
        BuildResult buildResult = sqlBuilder.buildInsert();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(buildResult.getSql());
            PreparedStatementUtils.setParameter(preparedStatement, entity, buildResult.getParametersIndex());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                if (getAutoCommit())
                    connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public int update(Object entity) {
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, new TableMapping(entity.getClass()), PlaceHolderType.QuestionMark);
        BuildResult buildResult = sqlBuilder.buildUpdate();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(buildResult.getSql());
            PreparedStatementUtils.setParameter(preparedStatement, entity, buildResult.getParametersIndex());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                if (getAutoCommit())
                    connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    abstract public int update(Object entity, String[] propertyNames);

    public <T> int delete(Object objKey, Class<T> entityType) {
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, new TableMapping(entityType), PlaceHolderType.QuestionMark);
        BuildResult buildResult = sqlBuilder.buildDelete();
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(buildResult.getSql());
            PreparedStatementUtils.setParameter(preparedStatement, objKey, buildResult.getParametersIndex());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExecuteSqlException(e.getMessage(), e);
        } finally {
            try {
                if (getAutoCommit())
                    connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    /*for entity end*/

    public abstract int insertOrUpdate(Object entity);

    public abstract <T> List<T> selectByMap(Map<String, Object> propertyMap, Class<T> entityType);

    /*for public static begin*/
    public static DbAccessor get() {
        return DbAccessorFactory.getMyBatisDbAccessor();
    }

    public static DbAccessor create() {
        return DbAccessorFactory.createMyBatisDbAccessor();
    }
    /*for public static end*/
}
