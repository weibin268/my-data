package com.zhuang.data.orm.mapping;

public class ColumnMapping {

    private String columnName;
    private String propertyName;
    private Class propertyType;
    private boolean isId;
    private boolean isGeneratedValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Class getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Class propertyType) {
        this.propertyType = propertyType;
    }

    public boolean getIsId() {
        return isId;
    }

    public void setIsId(boolean isId) {
        this.isId = isId;
    }

    public boolean getIsGeneratedValue() {
        return isGeneratedValue;
    }

    public void setIsGeneratedValue(boolean isAutoGenerate) {
        this.isGeneratedValue = isAutoGenerate;
    }

    @Override
    public String toString() {
        return "ColumnMapping{" +
                "columnName='" + columnName + '\'' +
                ", isId=" + isId +
                ", propertyName='" + propertyName + '\'' +
                ", propertyType=" + propertyType +
                ", isGeneratedValue=" + isGeneratedValue +
                '}';
    }
}
