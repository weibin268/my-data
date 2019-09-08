package com.zhuang.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyDataProperties {

    private static Logger logger = LoggerFactory.getLogger(MyDataProperties.class);

    private Properties properties;
    public final static String DEFAULT_CONFIG_FILE_PATH = "config/my-data.properties";
    public final static String JDBC_DRIVER = "my.data.jdbc-driver";
    public final static String JDBC_URL = "my.data.jdbc-url";
    public final static String JDBC_USERNAME = "my.data.jdbc-username";
    public final static String JDBC_PASSWORD = "my.data.jdbc-password";
    public final static String REDIS_HOST = "my.data.redis-host";
    public final static String REDIS_PORT = "my.data.redis-port";
    public final static String DB_EXECUTION_HANDLERS = "my.data.db-execution-handlers";
    public final static String UNDERSCORE_NAMING = "my.data.underscore-naming";

    private volatile static MyDataProperties myDataProperties;

    public static MyDataProperties getInstance() {
        if (myDataProperties == null) {
            synchronized (MyDataProperties.class) {
                if (myDataProperties == null) {
                    myDataProperties = new MyDataProperties();
                }
            }
        }
        return myDataProperties;
    }

    public MyDataProperties() {
        this(DEFAULT_CONFIG_FILE_PATH);
    }

    public MyDataProperties(String configFilePath) {
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.debug("未加载到“my-data.properties”配置文件！", e);
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

    public Properties getProperties() {
        return properties;
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
                result.add(item.trim());
            }
        }
        return result;
    }

    public Boolean getUnderscoreNaming() {
        String underscoreNaming = properties.getProperty(UNDERSCORE_NAMING);
        if (underscoreNaming != null && underscoreNaming.equalsIgnoreCase("false")) {
            return false;
        } else {
            return true;
        }
    }

}
