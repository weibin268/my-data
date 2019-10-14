package com.zhuang.data.jdbc;

import java.sql.*;
import java.util.List;
import java.util.Map;

import com.zhuang.data.DbAccessor;
import com.zhuang.data.config.MyDataProperties;
import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.exception.ExecuteSqlException;
import com.zhuang.data.exception.GetConnectionException;
import com.zhuang.data.jdbc.util.PreparedStatementUtils;
import com.zhuang.data.jdbc.util.ResultSetUtils;
import com.zhuang.data.model.PageInfo;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.sql.BuildResult;
import com.zhuang.data.orm.sql.SqlBuilder;
import com.zhuang.data.orm.sql.SqlBuilderFactory;
import com.zhuang.data.util.DbDialectUtils;

public class JdbcDbAccessor extends DbAccessor {

    private MyDataProperties myDataProperties;
    private DbDialect dbDialect;
    private boolean autoCommit;

    public JdbcDbAccessor(String configFile) {
        myDataProperties = new MyDataProperties(configFile);
        try {
            Class.forName(myDataProperties.getJdbcDriver());
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
            return DriverManager.getConnection(myDataProperties.getJdbcUrl(),
                    myDataProperties.getJdbcUserName(), myDataProperties.getJdbcPassword());
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

    @Override
    public <T> List<T> selectList(Object objParams, Class<T> entityType) {
        return null;
    }

    @Override
    public <T> T selectOne(Object objParams, Class<T> entityType) {
        return null;
    }

    @Override
    public int selectCount(Object objParams, Class entityType) {
        return 0;
    }

    @Override
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

    @Override
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

    @Override
    public int update(Object entity, boolean excludeNullFields) {
        return 0;
    }

    @Override
    public int insertOrUpdate(Object entity) {
        return 0;
    }

    @Override
    public int delete(Object objKey, Class entityType) {
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
}
