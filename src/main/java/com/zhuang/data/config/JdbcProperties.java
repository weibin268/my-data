package com.zhuang.data.config;

import com.zhuang.data.exception.LoadConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcProperties {

    private Properties properties;
    private final static String JDBC_DRIVER = "jdbc.driver";
    private final static String JDBC_URL = "jdbc.url";
    private final static String JDBC_USERNAME = "jdbc.username";
    private final static String JDBC_PASSWORD = "jdbc.password";

    public JdbcProperties() {
        properties = new Properties();
    }

    public JdbcProperties(String driver, String url, String username, String password) {
        this();
        setDriver(driver);
        setUrl(url);
        setUserName(username);
        setPassword(password);
    }

    public JdbcProperties(String configFile) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
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

    public String getDriver() {
        return properties.getProperty(JDBC_DRIVER);
    }

    public String getUrl() {
        return properties.getProperty(JDBC_URL);
    }

    public String getUserName() {
        return properties.getProperty(JDBC_USERNAME);
    }

    public String getPassword() {
        return properties.getProperty(JDBC_PASSWORD);
    }

    public void setDriver(String driver) {
        properties.setProperty(JDBC_DRIVER, driver);
    }

    public void setUrl(String url) {
        properties.setProperty(JDBC_URL, url);
    }

    public void setUserName(String userName) {
        properties.setProperty(JDBC_USERNAME, userName);
    }

    public void setPassword(String password) {
        properties.setProperty(JDBC_PASSWORD, password);
    }

    public Properties getProperties() {
        return properties;
    }
}
