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

    //region attributes
    protected DbDialect dbDialect;
    protected String configFile;
    protected boolean autoCommit;
    //endregion

    //region get attributes
    abstract public DbDialect getDbDialect();

    abstract public Connection getConnection();

    abstract public String getConfigFile();

    abstract public boolean getAutoCommit();
    //endregion

    //region common action
    abstract public <T> T queryEntity(String sql, Object parameter, Class<T> entityType);

    abstract public <T> List<T> queryEntities(String sql, Object parameter, Class<T> entityType);

    abstract public <T> List<T> pageQueryEntities(String sql, PageInfo pageInfo, Object parameter, Class<T> entityType);

    abstract public int executeNonQuery(String sql, Object parameter);
    //endregion

    //region transaction
    abstract public void commit();

    abstract public void rollback();

    abstract public void close();
    //endregion

    //region entity
    abstract public <T> T select(Object objKey, Class<T> entityType);

    abstract public int insert(Object entity);

    abstract public int update(Object entity);

    abstract public int update(Object entity, boolean excludeNullFields);

    abstract public <T> int delete(Object objKey, Class<T> entityType);

    abstract public int insertOrUpdate(Object entity);

    abstract public <T> List<T> selectList(Object objParams, Class<T> entityType);
    //endregion

    //region public static
    public static DbAccessor get() {
        return DbAccessorFactory.getMyBatisDbAccessor();
    }

    public static DbAccessor create() {
        return DbAccessorFactory.createMyBatisDbAccessor();
    }
    //endregion
}
