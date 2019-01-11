package com.zhuang.data.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.zhuang.data.exception.LoadConfigException;
import com.zhuang.data.handler.DbExecuteHandlerFactory;
import com.zhuang.data.handler.DbExecutionHandler;

public class MyDataProperties {

    private Properties properties;
    public final static String DEFAULT_CONFIG_FILE_PATH = "config/my-data.properties";
    private final static String JDBC_DRIVER = "my.data.jdbc-driver";
    private final static String JDBC_URL = "my.data.jdbc-url";
    private final static String JDBC_USERNAME = "my.data.jdbc-username";
    private final static String JDBC_PASSWORD = "my.data.jdbc-password";
    private final static String REDIS_HOST = "my.data.redis-host";
    private final static String REDIS_PORT = "my.data.redis-port";
    private final static String DB_EXECUTION_HANDLERS = "my.data.db-execution-handlers";


    public MyDataProperties() {
        this(DEFAULT_CONFIG_FILE_PATH);
    }

    public MyDataProperties(String configFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new LoadConfigException("加载“fileupload.properties”配置文件出错！");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String getJdbcDriver() {
        return properties.getProperty(JDBC_DRIVER);
    }

    public String getJdbcUrl() {
        return properties.getProperty(JDBC_URL);
    }

    public String getJdbcUserName() {
        return properties.getProperty(JDBC_USERNAME);
    }

    public String getJdbcPassword() {
        return properties.getProperty(JDBC_PASSWORD);
    }

    public String getRedisHost() {
        return properties.getProperty(REDIS_HOST);
    }

    public int getRedisPort() {
        return Integer.parseInt(properties.getProperty(REDIS_PORT));
    }

    public List<String> getDbExecutionHandlers() {
        List<String> result = new ArrayList<>();
        String dbExecutionHandlers = properties.getProperty(DB_EXECUTION_HANDLERS);
        if (dbExecutionHandlers != null && !dbExecutionHandlers.isEmpty()) {
            for (String item : dbExecutionHandlers.split(",")) {
                result.add(item);
            }
        }
        return result;
    }

}
