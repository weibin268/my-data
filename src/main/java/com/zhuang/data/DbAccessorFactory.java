package com.zhuang.data;

import com.zhuang.data.config.DefaultConfigFilePaths;
import com.zhuang.data.config.MyDataProperties;
import com.zhuang.data.jdbc.JdbcDbAccessor;
import com.zhuang.data.mybatis.MyBatisDbAccessor;
import com.zhuang.data.mybatis.util.SqlSessionFactoryUtils;

import java.util.Properties;

public class DbAccessorFactory {

    private static volatile DbAccessor myBatisDbAccessor;
    private static volatile DbAccessor jdbcDbAccessor;

    public static DbAccessor getMyBatisDbAccessor() {
        if (myBatisDbAccessor == null) {
            synchronized (MyBatisDbAccessor.class) {
                if (myBatisDbAccessor == null) {
                    myBatisDbAccessor = new MyBatisDbAccessor(true);
                }
            }
        }
        return myBatisDbAccessor;
    }

    public static DbAccessor createMyBatisDbAccessor() {
        return new MyBatisDbAccessor(false);
    }

    public static DbAccessor getJdbcDbAccessor() {
        if (jdbcDbAccessor == null) {
            synchronized (JdbcDbAccessor.class) {
                if (jdbcDbAccessor == null) {
                    jdbcDbAccessor = createJdbcDbAccessor(MyDataProperties.DEFAULT_CONFIG_FILE_PATH);
                }
            }
        }
        return jdbcDbAccessor;
    }

    public static DbAccessor createMyBatisDbAccessor(Properties properties, boolean autoCommit) {
        return createMyBatisDbAccessor(null, properties, autoCommit);
    }

    public static DbAccessor createMyBatisDbAccessor(String configFile, Properties properties, boolean autoCommit) {
        if (configFile == null) {
            configFile = DefaultConfigFilePaths.MYBATIS_CONFIG;
        }
        return new MyBatisDbAccessor(SqlSessionFactoryUtils.createSqlSessionFactory(configFile, properties), autoCommit);
    }

    public static DbAccessor createJdbcDbAccessor(String configFile) {
        return new JdbcDbAccessor(configFile);
    }

}
