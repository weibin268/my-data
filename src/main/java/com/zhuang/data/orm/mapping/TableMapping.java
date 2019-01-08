package com.zhuang.data.orm.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.zhuang.data.orm.annotation.Ignore;
import com.zhuang.data.orm.annotation.UnderscoreNaming;
import com.zhuang.data.orm.util.AnnotationUtils;
import com.zhuang.data.util.ReflectionUtils;
import com.zhuang.data.util.StringUtils;

public class TableMapping {

    private Class<?> entityType;
    private String tableName;
    private boolean isUnderscoreNaming;
    private List<ColumnMapping> columns;

    public String getTableName() {
        return tableName;
    }

    public boolean isUnderscoreNaming() {
        return isUnderscoreNaming;
    }

    public List<ColumnMapping> getColumns() {
        return columns;
    }

    public TableMapping(Class<?> entityType) {
        this.entityType = entityType;
        init();
    }

    public List<ColumnMapping> getKeyColumns() {
        List<ColumnMapping> result = new ArrayList<ColumnMapping>();
        for (ColumnMapping col : columns) {
            if (col.getIsId()) {
                result.add(col);
            }
        }
        return result;
    }

    public List<ColumnMapping> getInsertColumns() {
        List<ColumnMapping> result = new ArrayList<ColumnMapping>();
        for (ColumnMapping col : columns) {
            if (!col.getIsGeneratedValue()) {
                result.add(col);
            }
        }
        return result;
    }

    public List<ColumnMapping> getUpdateColumns() {
        List<ColumnMapping> result = new ArrayList<ColumnMapping>();
        for (ColumnMapping col : columns) {
            if (!col.getIsGeneratedValue() && !col.getIsId()) {
                result.add(col);
            }
        }
        return result;
    }

    public ColumnMapping getColumnByColumnName(String columnName) {
        for (ColumnMapping item : columns) {
            if (item.getColumnName().equalsIgnoreCase(columnName)) {
                return item;
            }
        }
        return null;
    }

    public void filterColumns(String[] propertyNames) {
        List<ColumnMapping> tempColumns = new ArrayList<>();
        for (ColumnMapping column : columns) {
            for (String propertyName : propertyNames) {
                if (column.getPropertyName().equalsIgnoreCase(propertyName)) {
                    tempColumns.add(column);
                    break;
                }
            }
        }
        columns = tempColumns;
    }

    private void init() {
        initTable();
        initColumns();
    }

    private void initColumns() {
        columns = new ArrayList<>();
        Field[] fields = ReflectionUtils.getDeclaredFields(entityType);
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            Ignore ignoreAnnotation;
            ignoreAnnotation = field.getAnnotation(Ignore.class);
            if (ignoreAnnotation != null) {
                continue;
            }
            ColumnMapping columnMapping = new ColumnMapping();
            columnMapping.setPropertyName(field.getName());
            columnMapping.setPropertyType(field.getType());
            String columnName = AnnotationUtils.getColumnName(field);
            if (columnName != null) {
                columnMapping.setColumnName(columnName);
            } else {
                columnMapping.setColumnName(field.getName());
            }
            columnMapping.setIsId(AnnotationUtils.isId(field));
            columnMapping.setIsGeneratedValue(AnnotationUtils.isGeneratedValue(field));
            columns.add(columnMapping);
        }
    }

    private void initTable() {
        //init isUnderscoreNaming
        UnderscoreNaming underscoreNamingAnnotation = ReflectionUtils.getAnnotation(entityType, UnderscoreNaming.class);
        isUnderscoreNaming = underscoreNamingAnnotation != null;
        //init tableName
        tableName = AnnotationUtils.getTableName(entityType);
        if (tableName == null) {
            tableName = isUnderscoreNaming ? StringUtils.camelCaseToUnderscore(entityType.getSimpleName()) : entityType.getSimpleName();
        }
    }
}
