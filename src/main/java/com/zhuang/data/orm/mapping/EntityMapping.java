package com.zhuang.data.orm.mapping;

import com.zhuang.data.jdbc.JdbcUtils;
import com.zhuang.data.util.DataTypeUtils;
import com.zhuang.data.util.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityMapping {

    private String tableName;
    private String tableComment;//需在连接字符串中设置“useInformationSchema=true”
    private Connection connection;
    private List<PropertyMapping> properties;
    private String entityName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<PropertyMapping> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyMapping> properties) {
        this.properties = properties;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


    public EntityMapping(String tableName) {
        this(tableName, JdbcUtils.getConnection());
    }

    public EntityMapping(String tableName, Connection connection) {
        String[] arrTableName = tableName.split("\\|");
        if (arrTableName.length == 2) {
            this.tableName = arrTableName[0] + arrTableName[1];
            this.entityName = StringUtils.underscoreToCamelCase(arrTableName[1], true);
        } else {
            this.tableName = tableName;
            this.entityName = StringUtils.underscoreToCamelCase(tableName, true);
        }
        this.connection = connection;
        this.properties = new ArrayList<>();
        try {
            init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void init() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet rsTables = databaseMetaData.getTables(databaseMetaData.getConnection().getCatalog(), null, tableName, null);
        while (rsTables.next()) {
            tableComment = rsTables.getString("REMARKS");
        }
        ResultSet rsPrimaryKeys = databaseMetaData.getPrimaryKeys(databaseMetaData.getConnection().getCatalog(), null, tableName);
        List<String> primaryKeys = new ArrayList<>();
        while (rsPrimaryKeys.next()) {
            primaryKeys.add(rsPrimaryKeys.getString("COLUMN_NAME"));
        }
        ResultSet rsColumns = databaseMetaData.getColumns(databaseMetaData.getConnection().getCatalog(), null, tableName, null);
        while (rsColumns.next()) {
            String columnName = rsColumns.getString("COLUMN_NAME");
            PropertyMapping propertyMapping = new PropertyMapping();
            propertyMapping.setColumnName(columnName);
            propertyMapping.setPropertyName(StringUtils.underscoreToCamelCase(propertyMapping.getColumnName()));
            propertyMapping.setColumnType(rsColumns.getString("TYPE_NAME"));
            propertyMapping.setPropertyType(DataTypeUtils.getJavaTypeFromDbType(propertyMapping.getColumnType()).getSimpleName());
            propertyMapping.setColumnComment(rsColumns.getString("REMARKS"));
            propertyMapping.setColumnSize(rsColumns.getInt("COLUMN_SIZE"));
            propertyMapping.setNullable(rsColumns.getBoolean("NULLABLE"));
            if (primaryKeys.stream().anyMatch(c -> c.equalsIgnoreCase(columnName))) {
                propertyMapping.setIsId(true);
            } else {
                propertyMapping.setIsId(false);
            }
            properties.add(propertyMapping);
        }
    }

    public static List<EntityMapping> retrieveEntityMappingList(Connection connection) {
        List<EntityMapping> result = new ArrayList<>();
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rsTables = databaseMetaData.getTables(databaseMetaData.getConnection().getCatalog(), null, null, null);
            while (rsTables.next()) {
                String tableName = rsTables.getString("TABLE_NAME");
                result.add(new EntityMapping(tableName, connection));
            }
        } catch (SQLException e) {
            throw new RuntimeException("call EntityMapping.retrieveEntityMappingList error !", e);
        }
        return result;
    }


    @Override
    public String toString() {
        return "EntityMapping{" +
                "tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", properties=" + properties +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}
