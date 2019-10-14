package com.zhuang.data.orm.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuang.data.exception.OrmException;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.ColumnMapping;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.util.StringUtils;

public abstract class BaseSqlBuilder implements SqlBuilder {

    protected TableMapping tableMapping;
    protected PlaceHolderType placeHolderType;

    public BaseSqlBuilder(TableMapping tableMapping, PlaceHolderType placeHolderType) {
        this.tableMapping = tableMapping;
        this.placeHolderType = placeHolderType;
    }

    public BuildResult buildSelect() {
        BuildResult result = new BuildResult();
        Map<String, Integer> parameterOrder = new HashMap<String, Integer>();
        int order = 1;
        List<ColumnMapping> columns = tableMapping.getColumns();
        List<ColumnMapping> keyColumns = tableMapping.getKeyColumns();
        List<String> lsSelect = new ArrayList<String>();
        List<String> lsWhere = new ArrayList<String>();
        if (keyColumns.size() < 1)
            throw new OrmException("实体没有设置主键！");

        for (ColumnMapping col : columns) {
            lsSelect.add(resolveColumnName(col.getColumnName()) + " as " + col.getPropertyName());
        }
        for (ColumnMapping keyCol : keyColumns) {
            lsWhere.add(resolveColumnName(keyCol.getColumnName()) + " = " + getPlaceHolder(placeHolderType, keyCol.getColumnName()));
            parameterOrder.put(keyCol.getPropertyName(), order++);
        }
        String strSelect = String.join(" , ", lsSelect);
        String strWhere = String.join(" AND ", lsWhere);
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT ").append(strSelect).append(" FROM ").append(tableMapping.getTableName()).append(" WHERE ").append(strWhere);
        result.setParametersIndex(parameterOrder);
        result.setSql(sbSql.toString());
        return result;
    }

    public BuildResult buildInsert() {
        BuildResult result = new BuildResult();
        Map<String, Integer> parameterOrder = new HashMap<String, Integer>();
        int order = 1;
        List<ColumnMapping> insertColumns = tableMapping.getInsertColumns();
        List<String> lsColumnNames = new ArrayList<String>();
        List<String> lsParameterNames = new ArrayList<String>();
        if (insertColumns.size() < 1)
            throw new OrmException("实体没有对应要插入到数据的属性！");
        for (ColumnMapping col : insertColumns) {
            lsColumnNames.add(resolveColumnName(col.getColumnName()));
            lsParameterNames.add(getPlaceHolder(placeHolderType, col.getColumnName()));
            parameterOrder.put(col.getPropertyName(), order++);
        }
        String strColumnNames = String.join(" , ", lsColumnNames);
        String strParameterNames = String.join(" , ", lsParameterNames);
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("INSERT INTO ").append(tableMapping.getTableName()).append("(").append(strColumnNames).append(") VALUES(").append(strParameterNames).append(")");
        result.setParametersIndex(parameterOrder);
        result.setSql(sbSql.toString());
        return result;
    }

    public BuildResult buildUpdate() {
        BuildResult result = new BuildResult();
        Map<String, Integer> parameterOrder = new HashMap<String, Integer>();
        int order = 1;
        List<ColumnMapping> updateColumns = tableMapping.getUpdateColumns();
        List<ColumnMapping> keyColumns = tableMapping.getKeyColumns();
        List<String> lsUpdateSet = new ArrayList<String>();
        List<String> lsWhere = new ArrayList<String>();
        if (keyColumns.size() < 1)
            throw new OrmException("实体没有设置主键！");
        if (updateColumns.size() < 1)
            throw new OrmException("实体没有对应要更新到数据的属性！");
        for (ColumnMapping col : updateColumns) {
            lsUpdateSet.add(resolveColumnName(col.getColumnName()) + " = " + getPlaceHolder(placeHolderType, col.getColumnName()));
            parameterOrder.put(col.getPropertyName(), order++);
        }
        for (ColumnMapping keyCol : keyColumns) {
            lsWhere.add(resolveColumnName(keyCol.getColumnName()) + " = " + getPlaceHolder(placeHolderType, keyCol.getColumnName()));
            parameterOrder.put(keyCol.getPropertyName(), order++);
        }
        String strUpdateSet = String.join(" , ", lsUpdateSet);
        String strWhere = String.join(" AND ", lsWhere);
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("UPDATE ").append(tableMapping.getTableName()).append(" SET ").append(strUpdateSet).append(" WHERE ").append(strWhere);
        result.setParametersIndex(parameterOrder);
        result.setSql(sbSql.toString());
        return result;
    }

    public BuildResult buildDelete() {
        BuildResult result = new BuildResult();
        Map<String, Integer> parameterOrder = new HashMap<String, Integer>();
        int order = 1;
        List<ColumnMapping> keyColumns = tableMapping.getKeyColumns();
        List<String> lsWhere = new ArrayList<String>();
        if (keyColumns.size() < 1)
            throw new OrmException("实体没有设置主键！");
        for (ColumnMapping keyCol : keyColumns) {
            lsWhere.add(resolveColumnName(keyCol.getColumnName()) + " = " + getPlaceHolder(placeHolderType, keyCol.getColumnName()));
            parameterOrder.put(keyCol.getPropertyName(), order++);
        }
        String strWhere = String.join(" AND ", lsWhere);
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("DELETE FROM ").append(tableMapping.getTableName()).append(" WHERE ").append(strWhere);
        result.setParametersIndex(parameterOrder);
        result.setSql(sbSql.toString());
        return result;
    }

    protected String getPlaceHolder(PlaceHolderType placeHolderType, String columnName) {
        String result = "";
        if (placeHolderType == PlaceHolderType.QuestionMark) {
            result = "?";
        } else if (placeHolderType == PlaceHolderType.NumberSign) {
            result = "#{" + columnName + "}";
        } else if (placeHolderType == PlaceHolderType.DollarSign) {
            result = "${" + columnName + "}";
        }
        return result;
    }

    private String resolveColumnName(String columnName) {
        String result;
        if (tableMapping.isUnderscoreNaming()) {
            result = StringUtils.camelCaseToUnderscore(columnName);
        } else {
            result = columnName;
        }
        return result;
    }
}
