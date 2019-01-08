package com.zhuang.data.orm.sql;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.mybatis.util.JdbcTypeUtils;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;

public class OracleSqlBuilder extends BaseSqlBuilder {

    public OracleSqlBuilder(TableMapping tableMapping, PlaceHolderType placeHolderType) {
        super(tableMapping, placeHolderType);
    }

    @Override
    protected String getPlaceHolder(PlaceHolderType placeHolderType, String columnName) {
        String jdbcType = JdbcTypeUtils.getJdbcTypeName(DbDialect.Oracle, tableMapping.getColumnByColumnName(columnName).getPropertyType());
        if (jdbcType != null) {
            columnName = columnName + ",jdbcType=" + jdbcType;
        }
        String result=super.getPlaceHolder(placeHolderType,columnName);
        return result;
    }
}
