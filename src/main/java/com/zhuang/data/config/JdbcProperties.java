package com.zhuang.data.config;

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
