package com.zhuang.data.orm.sql;

import com.zhuang.data.enums.DbDialect;
import com.zhuang.data.orm.enums.PlaceHolderType;
import com.zhuang.data.orm.mapping.TableMapping;

/**
 * @author zhuang
 * @create 6/30/18 9:16 AM
 **/
public class SqlBuilderFactory {

    public static SqlBuilder createSqlBuilder(DbDialect dbDialect, TableMapping tableMapping, PlaceHolderType placeHolderType) {
        if (dbDialect == DbDialect.Oracle) {
            return new OracleSqlBuilder(tableMapping, placeHolderType);
        } else if (dbDialect == DbDialect.MySQL) {
            return new MySQLSqlBuilder(tableMapping, placeHolderType);
        }else if (dbDialect == DbDialect.MSSQL) {
            return new MSSQLSqlBuilder(tableMapping, placeHolderType);
        }
        return null;
    }

}
