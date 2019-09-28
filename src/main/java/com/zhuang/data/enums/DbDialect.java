package com.zhuang.data.enums;

import java.util.Arrays;

public enum DbDialect {
	MySQL("MySQL"),
	MSSQL("SQL Server"),
	Oracle("Oracle"),
	DB2("DB2"),
	Sybase("Adaptive Server Enterprise");

	private String productName;

	DbDialect(String productName) {
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public static DbDialect getByProductName(String productName){
		return Arrays.stream(DbDialect.values()).filter(c->c.getProductName().equals(productName)).findFirst().get();
	}
}
