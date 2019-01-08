package com.zhuang.data.orm.mapping;

public class PropertyMapping {

    private String propertyName;
    private String propertyType;
    private String columnName;
    private String columnType;
    private String columnComment;
    private int  columnSize;
    private boolean isId;
    private boolean nullable;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean getIsId() {
        return isId;
    }

    public void setIsId(boolean isId) {
        this.isId = isId;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public String toString() {
        return "PropertyMapping{" +
                "propertyName='" + propertyName + '\'' +
                ", propertyType='" + propertyType + '\'' +
                ", columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", columnSize=" + columnSize +
                ", isId=" + isId +
                ", nullable=" + nullable +
                '}';
    }
}
