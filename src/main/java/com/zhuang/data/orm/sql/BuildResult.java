package com.zhuang.data.orm.sql;

import java.util.Map;

public class BuildResult {

	private String sql;
	private Map<String, Integer> parametersIndex;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, Integer> getParametersIndex() {
		return parametersIndex;
	}

	public void setParametersIndex(Map<String, Integer> parametersIndex) {
		this.parametersIndex = parametersIndex;
	}
	
}
