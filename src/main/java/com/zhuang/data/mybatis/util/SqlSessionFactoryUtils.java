package com.zhuang.data.mybatis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zhuang.data.config.DefaultConfigFilePaths;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtils {

    private static volatile SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory createSqlSessionFactory(String configFile, Properties properties) {
        SqlSessionFactory sqlSessionFactory;
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            synchronized (SqlSessionFactory.class) {
                if (sqlSessionFactory == null) {
                    sqlSessionFactory = createSqlSessionFactory(DefaultConfigFilePaths.MYBATIS_CONFIG, null);
                }
            }
        }
        return sqlSessionFactory;
    }

}
