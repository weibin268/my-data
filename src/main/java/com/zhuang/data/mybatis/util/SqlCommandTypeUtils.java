package com.zhuang.data.mybatis.util;

import com.zhuang.data.orm.enums.SqlType;
import org.apache.ibatis.mapping.SqlCommandType;

public class SqlCommandTypeUtils {

    public static SqlCommandType getBySqlType(SqlType sqlType) {
        if (sqlType == SqlType.SELECT) {
            return SqlCommandType.SELECT;
        } else if (sqlType == SqlType.SELECT_COUNT) {
            return SqlCommandType.SELECT;
        } else if (sqlType == SqlType.INSERT) {
            return SqlCommandType.INSERT;
        } else if (sqlType == SqlType.UPDATE) {
            return SqlCommandType.UPDATE;
        } else if (sqlType == SqlType.DELETE) {
            return SqlCommandType.DELETE;
        } else {
            return null;
        }
    }

}
