package com.zhuang.data.enums;

public enum DbProductNames {

    MySQL("MySQL"), Oracle("Oracle"), MSSQL("SQL Server"), DB2("DB2"), Sybase("Adaptive Server Enterprise");

    private String name;

    DbProductNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
