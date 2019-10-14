package com.zhuang.data.mybatis.util;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.enums.SqlType;
import com.zhuang.data.orm.mapping.ColumnMapping;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.util.SqlBuilderUtils;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class MappedStatementUtils {

    private static Logger logger = LoggerFactory.getLogger(MappedStatementUtils.class);

    public static <T> String getMappedStatementId(Configuration configuration, DbDialect dbDialect, SqlType sqlType, Class<T> entityType, Class<?> parameterType) {
        return getMappedStatementId(configuration, dbDialect, sqlType, entityType, parameterType, null);
    }

    public static <T> String getMappedStatementId(Configuration configuration, DbDialect dbDialect, SqlType sqlType, Class<T> entityType, Class<?> parameterType, String[] propertyNames) {
        SqlCommandType sqlCommandType = SqlCommandTypeUtils.getBySqlType(sqlType);
        String mappedStatementId = entityType.getName() + "_" + sqlType + "_" + parameterType.getName();
        if (hasPropertyNames(propertyNames)) {
            mappedStatementId = mappedStatementId + "_" + String.join(".", propertyNames);
        }
        if (configuration.hasStatement(mappedStatementId)) return mappedStatementId;
        synchronized (MappedStatementUtils.class) {
            if (configuration.hasStatement(mappedStatementId)) return mappedStatementId;
            TableMapping tableMapping = new TableMapping(entityType);
            if (hasPropertyNames(propertyNames)) {
                if (sqlCommandType == SqlCommandType.UPDATE) {
                    tableMapping.filterColumns(propertyNames);
                } else if (sqlCommandType == SqlCommandType.SELECT) {
                    for (ColumnMapping columnMapping : tableMapping.getColumns()) {
                        if (Arrays.stream(propertyNames).anyMatch(c -> c.equals(columnMapping.getPropertyName()))) {
                            columnMapping.setIsId(true);
                        } else {
                            columnMapping.setIsId(false);
                        }
                    }
                }
            }
            String sql = SqlBuilderUtils.getSql(dbDialect, tableMapping, PlaceHolderType.NumberSign, sqlType);
            logger.debug("Sql:" + sql);
            Class<?> resultType = sqlType == SqlType.SELECT_COUNT ? Integer.class : entityType;
            configuration.addMappedStatement(createMappedStatement(configuration, mappedStatementId, sql, sqlCommandType, parameterType, resultType));
        }
        return mappedStatementId;
    }

    public static MappedStatement createMappedStatement(Configuration configuration, String mappedStatementId, String sql, SqlCommandType sqlCommandType, Class<?> parameterType, Class<?> resultType) {
        SqlSource sqlSource = configuration.getDefaultScriptingLanuageInstance().createSqlSource(configuration, sql, parameterType);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, mappedStatementId, sqlSource, sqlCommandType)
                .resultMaps(new ArrayList<ResultMap>() {{
                    add(new ResultMap.Builder(configuration, "defaultResultMap", resultType, new ArrayList<ResultMapping>(0)).build());
                }}).build();
        return mappedStatement;
    }

    private static boolean hasPropertyNames(String[] propertyNames) {
        return propertyNames != null && propertyNames.length > 0;
    }
}
