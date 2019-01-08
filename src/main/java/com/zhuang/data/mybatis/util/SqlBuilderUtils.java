package com.zhuang.data.mybatis.util;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;
import com.zhuang.data.orm.sql.SqlBuilder;
import com.zhuang.data.orm.sql.SqlBuilderFactory;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * @author zhuang
 * @create 7/1/18 10:37 AM
 **/
public class SqlBuilderUtils {

    public static String getSql(DbDialect dbDialect, TableMapping tableMapping, PlaceHolderType placeHolderType, SqlCommandType sqlCommandType) {
        String sql = "";
        SqlBuilder sqlBuilder = SqlBuilderFactory.createSqlBuilder(dbDialect, tableMapping, placeHolderType);
        if (sqlCommandType == SqlCommandType.SELECT) {
            sql = sqlBuilder.buildSelect().getSql();
        } else if (sqlCommandType == SqlCommandType.INSERT) {
            sql = sqlBuilder.buildInsert().getSql();
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            sql = sqlBuilder.buildUpdate().getSql();
        } else if (sqlCommandType == SqlCommandType.DELETE) {
            sql = sqlBuilder.buildDelete().getSql();
        }
        return sql;
    }
}
