package com.zhuang.data.mybatis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.ColumnMapping;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.sql.SqlBuilder;
import com.zhuang.data.orm.sql.SqlBuilderFactory;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappedStatementUtils {

    private static Logger logger = LoggerFactory.getLogger(MappedStatementUtils.class);

    public static <T> String getMappedStatementId(DbDialect dbDialect, Class<T> entityType, Class<?> parameterType, Configuration configuration, SqlCommandType sqlCommandType) {
        return getMappedStatementId(dbDialect, entityType, parameterType, configuration, sqlCommandType, null);
    }

    public static <T> String getMappedStatementId(DbDialect dbDialect, Class<T> entityType, Class<?> parameterType, Configuration configuration, SqlCommandType sqlCommandType, String[] propertyNames) {
        String mappedStatementId = entityType.getName() + "-" + sqlCommandType + "-" + parameterType.getName();
        if (hasPropertyNames(propertyNames)) {
            mappedStatementId = mappedStatementId + "-" + String.join(".", propertyNames);
        }
        if (!configuration.hasStatement(mappedStatementId)) {
            synchronized (MappedStatementUtils.class) {
                if (!configuration.hasStatement(mappedStatementId)) {
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
                    String sql = SqlBuilderUtils.getSql(dbDialect, tableMapping, PlaceHolderType.NumberSign, sqlCommandType);
                    logger.debug("sql:" + sql);
                    configuration.addMappedStatement(createMappedStatement(configuration, sqlCommandType, parameterType, entityType, sql, mappedStatementId));
                }
            }
        }
        return mappedStatementId;
    }

    public static MappedStatement createMappedStatement(Configuration configuration, SqlCommandType sqlCommandType, Class<?> parameterType, Class<?> resulType, String sql, String mappedStatementId) {
        SqlSource sqlSource = configuration.getDefaultScriptingLanuageInstance().createSqlSource(configuration, sql, parameterType);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, mappedStatementId, sqlSource, sqlCommandType)
                .resultMaps(new ArrayList<ResultMap>() {
                    {
                        add(new ResultMap.Builder(configuration, "defaultResultMap", resulType, new ArrayList<ResultMapping>(0)).build());
                    }
                }).build();
        return mappedStatement;
    }

    private static boolean hasPropertyNames(String[] propertyNames) {
        return propertyNames != null && propertyNames.length > 0;
    }
}
