package com.zhuang.data.orm.util;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.enums.SqlType;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.sql.SqlBuilder;
import com.zhuang.data.orm.sql.SqlBuilderFactory;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author zhuang
 **/
public class SqlBuilderUtils {

    public static String getSql(DbDialect dbDialect, TableMapping tableMapping, PlaceHolderType placeHolderType, SqlType sqlType) {
        String sql = "";
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, tableMapping, placeHolderType);
        if (sqlType == SqlType.SELECT) {
            sql = sqlBuilder.buildSelect().getSql();
        } else if (sqlType == SqlType.SELECT_COUNT) {
            sql = sqlBuilder.buildSelectCount().getSql();
        } else if (sqlType == SqlType.INSERT) {
            sql = sqlBuilder.buildInsert().getSql();
        } else if (sqlType == SqlType.UPDATE) {
            sql = sqlBuilder.buildUpdate().getSql();
        } else if (sqlType == SqlType.DELETE) {
            sql = sqlBuilder.buildDelete().getSql();
        }
        return sql;
    }
}
