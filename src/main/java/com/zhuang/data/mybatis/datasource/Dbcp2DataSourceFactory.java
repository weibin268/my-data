package com.zhuang.data.mybatis.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

public class Dbcp2DataSourceFactory implements DataSourceFactory {

	private BasicDataSource dataSource;

	public Dbcp2DataSourceFactory() {
		dataSource = new BasicDataSource();
	}

	public void setProperties(Properties props) {
		dataSource.setDriverClassName(props.getProperty("driver"));
		dataSource.setUrl(props.getProperty("url"));
		dataSource.setUsername(props.getProperty("username"));
		dataSource.setPassword(props.getProperty("password"));
	}

	public DataSource getDataSource() {
		return dataSource;
	}
}
